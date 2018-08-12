package com.tyc.bos.web.action.system;

import com.tyc.bos.domain.system.Role;
import com.tyc.bos.service.system.IRoleService;
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
public class RoleAction extends CommonAction<Role> {

    private String menuIds;

    private Integer[] permissionIds;

    public void setMenuIds(String menuIds) {
        this.menuIds = menuIds;
    }

    public void setPermissionIds(Integer[] permissionIds) {
        this.permissionIds = permissionIds;
    }

    @Resource
    private IRoleService roleService;

    /**
     * @return java.lang.String
     * @Description //TODO
     * @Date 16:38 2018/8/8
     * @Param []
     */
    @Action(value = "roleAction_pageQuery")
    public String pageQuery() throws IOException {
        Pageable pageable = new PageRequest(page - 1, rows);
        Page<Role> rolePage = roleService.pageQuery(pageable);
        this.page2Json(rolePage);
        return NONE;
    }

    @Action(value = "roleAction_save", results = {
            @Result(name = "success", location = "/pages/system/role.html", type = "redirect")}
    )
    public String save() {
        roleService.save(getModel(), menuIds, permissionIds);
        return SUCCESS;
    }

    @Action(value = "roleAction_findAll")
    public String findAll() throws IOException {
        List<Role> list = roleService.findAll();
        this.list2Json(list);
        return NONE;
    }
}
