package com.tyc.bos.dao.system;

import com.tyc.bos.domain.system.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserDao extends JpaRepository<User,Integer> {

    User findByUsername(String username);
}
