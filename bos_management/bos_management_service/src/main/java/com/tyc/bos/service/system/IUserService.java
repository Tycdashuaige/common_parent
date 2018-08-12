package com.tyc.bos.service.system;

import com.tyc.bos.domain.system.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author tangyucong
 * @Title: IUserService
 * @ProjectName common_parent
 * @Description: TODO
 * @date 2018/8/822:01
 */
public interface IUserService {
    void save(User model, Integer[] roleIds);

    Page<User> pageQuery(Pageable pageable);
}
