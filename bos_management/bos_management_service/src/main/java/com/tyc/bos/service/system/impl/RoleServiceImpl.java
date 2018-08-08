package com.tyc.bos.service.system.impl;

import com.tyc.bos.dao.system.IRoleDao;
import com.tyc.bos.domain.system.Role;
import com.tyc.bos.service.system.IRoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class RoleServiceImpl implements IRoleService {

    @Resource
    private IRoleDao roleDao;

    @Override
    public Page<Role> pageQuery(Pageable pageable) {
        return roleDao.findAll(pageable);
    }
}
