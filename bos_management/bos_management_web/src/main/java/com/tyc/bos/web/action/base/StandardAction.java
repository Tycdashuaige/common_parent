package com.tyc.bos.web.action.base;

import com.tyc.bos.domain.base.Standard;
import com.tyc.bos.service.IStandardService;
import com.tyc.bos.web.action.common.CommonAction;
import org.apache.shiro.authz.UnauthorizedException;
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
public class StandardAction extends CommonAction<Standard> {

    @Resource
    private IStandardService standardService;

    @Action(value = "standardAction_save", results = {
            @Result(name = "success", location = "/pages/base/standard.html", type = "redirect"),
            @Result(name="unauthorized",location="/unauthorized.html",type="redirect")})
    public String saveStandard() {
        try {
            standardService.save(getModel());
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof UnauthorizedException){
                return "unauthorized";
            }else {
                return ERROR;
            }
        }
        return SUCCESS;
    }

    @Action(value = "standardAction_pageQuery")
    public String pageQuery() throws IOException {

        Pageable pageable = new PageRequest(page - 1, rows);

        Page<Standard> page = standardService.pageQuery(pageable);

        this.page2Json(page);

        return NONE;
    }

    @Action(value = "standard_findAll")
    public String findAll() throws IOException {

        List<Standard> list = standardService.findAll();

        this.list2Json(list);

        return NONE;
    }

}
