package com.tyc.bos.dao.system;

import com.tyc.bos.domain.system.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPermissionDao extends JpaRepository<Permission,Integer> {
}
