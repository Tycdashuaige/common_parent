package com.tyc.bos.dao.system;

import com.tyc.bos.domain.system.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IPermissionDao extends JpaRepository<Permission, Integer> {

    @Query("select p from Permission p inner join p.roles r inner join r.users u where u.id = ?1")
    List<Permission> findByUser(int id);
}
