package com.tyc.bos.web.action.system;

import com.tyc.bos.domain.system.User;
import com.tyc.bos.service.system.IUserService;
import com.tyc.bos.web.action.common.CommonAction;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
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
public class UserAction extends CommonAction<User> {

    @Resource
    private IUserService userService;

    private Integer[] roleIds;

    public void setRoleIds(Integer[] roleIds) {
        this.roleIds = roleIds;
    }

    private String checkcode;

    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }


    @Action(value = "userAction_login", results = {
            @Result(name = "success", location = "/index.html", type = "redirect"),
            @Result(name = "home", location = "/login.html", type = "redirect")
    })
    public String login() {

        String sessionCheckCode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");

        if (StringUtils.isNotBlank(sessionCheckCode) && StringUtils.isNotBlank(checkcode) && sessionCheckCode.equals(checkcode)) {

            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(model.getUsername(), model.getPassword());
            try {
                subject.login(usernamePasswordToken);
                User user = (User) subject.getPrincipal();
                if (user == null) {
                    return "home";
                }
                ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);
            } catch (AuthenticationException e) {
                e.printStackTrace();
                return "home";
            }
        }
        return SUCCESS;
    }

    @Action(value = "userAction_logout", results = {@Result(name = "login", location = "login.html", type = "redirect")})
    public String logout() {

        SecurityUtils.getSubject().logout();
        return LOGIN;
    }

    @Action(value = "userAction_save", results = {@Result(name = "success", location = "/pages/system/userlist.html", type = "redirect")})
    public String save() {
        userService.save(getModel(), roleIds);
        return SUCCESS;
    }

    @Action(value = "userAction_pageQuery")
    public String pageQuery() throws IOException {

        Pageable pageable = new PageRequest(page - 1, rows);
        Page<User> userPage = userService.pageQuery(pageable);
        this.page2Json(userPage);
        return NONE;
    }
}
