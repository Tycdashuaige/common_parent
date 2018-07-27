package com.tyc.bos.web.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.tyc.bos.domain.Standard;
import com.tyc.bos.service.IStandardService;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class StandardAction extends ActionSupport implements ModelDriven<Standard> {

    @Resource
    private IStandardService standardService;

    private Standard model;

    @Override
    public Standard getModel() {
        if (model == null) {
            model = new Standard();
        }
        return model;
    }

    @Action(value = "standardAction_save", results = {@Result(name = "success", location = "/pages/base/standard.html", type = "redirect")})
    private String saveStandard() {
        standardService.save(model);
        return SUCCESS;
    }

}
