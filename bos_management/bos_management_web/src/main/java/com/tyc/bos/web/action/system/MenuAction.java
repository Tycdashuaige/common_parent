package com.tyc.bos.web.action.system;

import com.tyc.bos.domain.system.Menu;
import com.tyc.bos.service.system.IMenuService;
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
public class MenuAction extends CommonAction<Menu> {

    @Resource
    private IMenuService menuService;

    @Action("menuAction_listajax")
    public String listajax() throws IOException {
        List<Menu> list = menuService.findByParentMenuIsNull();
        this.list2Json(list);
        return NONE;
    }

    @Action(value = "menuAction_save",results = {@Result(name = "success",location = "pages/system/menu.html",type = "redirect")})
    public String save(){

        menuService.save(getModel());
        return SUCCESS;
    }

    @Action(value = "menuAction_pageQuery")
    public String pageQuery() throws IOException {

        String page1 = getModel().getPage();
        int page = Integer.parseInt(page1);
        Pageable pageable= new PageRequest(page - 1, rows);
        Page<Menu> menuPage = menuService.pageQuery(pageable);
        this.page2Json(menuPage);
        return NONE;
    }

    @Action(value = "menuAction_findMenu")
    public String findMenu() throws IOException {

        List<Menu> menuList = menuService.findMenu();
        this.list2Json(menuList);
        //this.list2JsonIncludes(menuList,new String[]{"page","priority","description","roles","childrenMenus","parentMenu"});
        return NONE;
    }
}
