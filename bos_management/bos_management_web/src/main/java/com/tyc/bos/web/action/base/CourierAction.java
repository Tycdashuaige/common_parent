package com.tyc.bos.web.action.base;

import com.tyc.bos.domain.base.Courier;
import com.tyc.bos.domain.base.Standard;
import com.tyc.bos.service.base.ICourierService;
import com.tyc.bos.web.action.common.CommonAction;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class CourierAction extends CommonAction<Courier> {

    @Resource
    private ICourierService courierService;

    private String ids;

    public void setIds(String ids) {
        this.ids = ids;
    }

    @Action(value = "courierAction_save", results = {@Result(name = "success", location = "/pages/base/courier.html", type = "redirect")})
    public String save() {
        courierService.save(getModel());
        return SUCCESS;
    }

    @Action(value = "courierAction_pageQuery")
    public String pageQuery() throws IOException {

        final String company = getModel().getCompany();
        final String courierNum = getModel().getCourierNum();
        final String type = getModel().getType();
        final Standard standard = getModel().getStandard();

        Specification<Courier> specification = new Specification<Courier>() {

            @Override
            public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                List<Predicate> list = new ArrayList<>();
                if (StringUtils.isNotBlank(company)) {
                    Predicate p1 = cb.like(root.get("company").as(String.class), company);
                    list.add(p1);
                }
                if (StringUtils.isNotBlank(courierNum)) {
                    Predicate p2 = cb.equal(root.get("courierNum").as(String.class), courierNum);
                    list.add(p2);
                }
                if (StringUtils.isNotBlank(type)) {
                    Predicate p3 = cb.equal(root.get("type").as(String.class), type);
                    list.add(p3);
                }
                if (standard != null && StringUtils.isNotBlank(standard.getName())) {
                    Join<Object, Object> join = root.join("standard");
                    Predicate p4 = cb.equal(root.get("name").as(String.class), standard.getName());
                    list.add(p4);
                }
                if (list.size() == 0) {
                    return null;
                }
                Predicate[] predicates = new Predicate[list.size()];

                return cb.and(list.toArray(predicates));
            }
        };

        Pageable pageable = new PageRequest(page - 1, rows);
        Page<Courier> page = courierService.pageQuery(specification, pageable);

        this.page2Json(page);

        return NONE;
    }

    private static final String FAIL = "fail";


    @Action(value = "courierAction_delete", results = {@Result(name = "success", location = "/pages/base/courier.html", type = "redirect"),
            @Result(name = "fail", location = "/pages/base/error.html", type = "redirect")})
    public String delete() {
        try {
            courierService.delete(ids);
            return SUCCESS;
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return FAIL;
        }
    }

    @Action(value = "courierAction_listajax")
    public String listajax() throws IOException {
        List<Courier> list = courierService.findByDeltagIsNull();
        this.list2Json(list);
        return NONE;
    }
}
