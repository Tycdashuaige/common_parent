package com.tyc.bos.service.system.impl;

import com.tyc.bos.dao.system.IPermissionDao;
import com.tyc.bos.domain.system.Permission;
import com.tyc.bos.service.system.IPermissionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class PermissionServiceImpl implements IPermissionService {

    @Resource
    private IPermissionDao permissionDao;

    @Override
    public Page<Permission> pageQuery(Pageable pageable) {
        return permissionDao.findAll(pageable);
    }

    @Override
    public void save(Permission model) {
        permissionDao.save(model);
    }
}
