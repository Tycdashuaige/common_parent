package com.tyc.bos.web.action.base;

import com.tyc.bos.domain.base.SubArea;
import com.tyc.bos.service.ISubareaService;
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
public class SubareaAction extends CommonAction<SubArea> {

    @Resource
    private ISubareaService subareaService;

    @Action(value = "subareaAction_save", results = {@Result(name = "success", location = "pages/base/sub_area.html", type = "redirect")})
    public String save() {
        subareaService.save(getModel());
        return SUCCESS;
    }

    @Action(value = "subareaAction_pageQuery")
    public String pageQuery() throws IOException {
        Pageable pageable = new PageRequest(page - 1, rows);
        Page<SubArea> list = subareaService.pageQuery(pageable);
        this.page2Json(list);
        return NONE;
    }
}
