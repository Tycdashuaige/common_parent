package com.tyc.bos.service.system.impl;

import com.tyc.bos.dao.system.IRoleDao;
import com.tyc.bos.domain.system.Menu;
import com.tyc.bos.domain.system.Permission;
import com.tyc.bos.domain.system.Role;
import com.tyc.bos.service.system.IRoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements IRoleService {

    @Resource
    private IRoleDao roleDao;

    @Override
    public Page<Role> pageQuery(Pageable pageable) {
        return roleDao.findAll(pageable);
    }

    @Override
    public void save(Role model, String menuIds, Integer[] permissionIds) {
        roleDao.save(model);
        if (StringUtils.isNotBlank(menuIds)) {
            String[] split = menuIds.split(",");
            for (int i = 0; i < split.length; i++) {
                Menu menu = new Menu();
                menu.setId(Integer.parseInt(split[i]));
                model.getMenus().add(menu);
            }
        }
        if (permissionIds != null) {
            for (Integer permissionId : permissionIds) {
                Permission permission = new Permission();
                permission.setId(permissionId);
                model.getPermissions().add(permission);
            }
        }
    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }
}
