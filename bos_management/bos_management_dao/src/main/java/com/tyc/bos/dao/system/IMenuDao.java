package com.tyc.bos.dao.system;

import com.tyc.bos.domain.system.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IMenuDao extends JpaRepository<Menu,Integer> {
    List<Menu> findByParentMenuIsNull();
}
