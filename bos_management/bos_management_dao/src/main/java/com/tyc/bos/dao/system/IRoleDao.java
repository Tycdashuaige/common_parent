package com.tyc.bos.dao.system;

import com.tyc.bos.domain.system.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleDao extends JpaRepository<Role,Integer> {
}
