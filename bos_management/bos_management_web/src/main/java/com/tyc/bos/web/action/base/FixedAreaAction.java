package com.tyc.bos.web.action.base;

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

@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class FixedAreaAction extends CommonAction<FixedArea> {

    @Resource
    private IFixedAreaService fixedAreaService;

    @Action(value = "fixedAreaAction_save",results = {@Result(name = "success",location = "/pages/base/fixed_area.html",type = "redirect")})
    public String save(){
        fixedAreaService.save(getModel());
        return SUCCESS;
    }

    @Action(value = "fixedAreaAction_pageQuery")
    public String pageQuery() throws IOException {
        Pageable pageable = new PageRequest(page-1,rows);
        Page<FixedArea> page = fixedAreaService.pageQuery(pageable);
        this.page2Json(page);
        return NONE;
    }
}
