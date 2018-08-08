package com.tyc.bos.web.action.system;

import com.tyc.bos.domain.system.Permission;
import com.tyc.bos.service.system.IPermissionService;
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
public class PermissionAction extends CommonAction<Permission> {

    @Resource
    private IPermissionService permissionService;

    @Action(value = "permissionAction_pageQuery")
    public String pageQuery() throws IOException {

        Pageable pageable = new PageRequest(page - 1, rows);
        Page<Permission> permissionPage = permissionService.pageQuery(pageable);
        this.page2Json(permissionPage);
        return NONE;
    }

    @Action(value = "permissionAction_save",results = {@Result(name = "success",location = "pages/system/permission.html",type = "redirect")})
    public String save(){
        permissionService.save(getModel());
        return SUCCESS;
    }


}
