package com.tyc.bos.dao.system;

import com.tyc.bos.domain.system.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IRoleDao extends JpaRepository<Role,Integer> {

    @Query("select r from Role r inner join r.users u where u.id = ?1")
    List<Role> findByUser(int id);
}
