package com.tyc.bos.web.action.base;

import cn.tyc.crm.domain.Customer;
import cn.tyc.crm.service.ICustomerService;
import com.tyc.bos.domain.base.FixedArea;
import com.tyc.bos.service.IFixedAreaService;
import com.tyc.bos.web.action.common.CommonAction;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class FixedAreaAction extends CommonAction<FixedArea> {

    @Resource
    private ICustomerService crmProxy;

    private List<Integer> customerIds;

    public void setCustomerIds(List<Integer> customerIds) {
        this.customerIds = customerIds;
    }

    private Integer courierId;

    private Integer takeTimeId;

    public void setCourierId(Integer courierId) {
        this.courierId = courierId;
    }

    public void setTakeTimeId(Integer takeTimeId) {
        this.takeTimeId = takeTimeId;
    }

    @Resource
    private IFixedAreaService fixedAreaService;


    @Action(value = "fixedAreaAction_save", results = {@Result(name = "success", location = "/pages/base/fixed_area.html", type = "redirect")})
    public String save() {
        fixedAreaService.save(getModel());
        return SUCCESS;
    }

    @Action(value = "fixedAreaAction_pageQuery")
    public String pageQuery() throws IOException {
        Pageable pageable = new PageRequest(page - 1, rows);
        Page<FixedArea> page = fixedAreaService.pageQuery(pageable);
        this.page2Json(page);
        return NONE;
    }

    @Action(value = "fixedAreaAction_findByFixedAreaIdIsNull")
    public String findByFixedAreaIdIsNull() throws IOException {
        List<Customer> byFixedAreaIdIsNull = crmProxy.findByFixedAreaIdIsNull();
        this.list2Json(byFixedAreaIdIsNull);
        return NONE;
    }

    @Action(value = "fixedAreaAction_findByFixedAreaId")
    public String findByFixedAreaId() throws IOException {
        List<Customer> byFixedAreaId = crmProxy.findByFixedAreaId(model.getId());
        this.list2Json(byFixedAreaId);
        return NONE;
    }

    @Action(value = "fixedAreaAction_assignCustomers2FixedArea", results = {@Result(name = "success", location = "/pages/base/fixed_area.html", type = "redirect")})
    public String assignCustomers2FixedArea() {
        crmProxy.assignCustomers2FixedArea(model.getId(), customerIds);
        return SUCCESS;
    }

    @Action(value = "fixedAreaAction_associationCourierToFixedArea",results = {@Result(name = "success", location = "/pages/base/fixed_area.html", type = "redirect")})
    public String associationCourierToFixedArea() {
        fixedAreaService.associationCourierToFixedArea(model.getId(),courierId,takeTimeId);
        return SUCCESS;
    }
}
