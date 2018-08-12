package com.tyc.bos.dao.system;

import com.tyc.bos.domain.system.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IMenuDao extends JpaRepository<Menu,Integer> {

    List<Menu> findByParentMenuIsNull();

    @Query("select m from Menu m inner join m.roles r inner join r.users u where u.id = ?1 and m.parentMenu= null")
    List<Menu> findByUserId(int id);
}
