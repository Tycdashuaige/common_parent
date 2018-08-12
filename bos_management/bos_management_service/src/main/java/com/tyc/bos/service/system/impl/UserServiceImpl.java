package com.tyc.bos.service.system.impl;

import com.tyc.bos.dao.system.IUserDao;
import com.tyc.bos.domain.system.Role;
import com.tyc.bos.domain.system.User;
import com.tyc.bos.service.system.IUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author tangyucong
 * @Title: UserServiceImpl
 * @ProjectName common_parent
 * @Description: TODO
 * @date 2018/8/822:01
 */
@Service
@Transactional
public class UserServiceImpl implements IUserService {

    @Resource
    private IUserDao userdao;

    @Override
    public void save(User model, Integer[] roleIds) {
        userdao.save(model);
        for (int i = 0; i < roleIds.length; i++) {
            Role role = new Role();
            role.setId(roleIds[i]);
            model.getRoles().add(role);
        }
    }

    @Override
    public Page<User> pageQuery(Pageable pageable) {
        return userdao.findAll(pageable);
    }
}
