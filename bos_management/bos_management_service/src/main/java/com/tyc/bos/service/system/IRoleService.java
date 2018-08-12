package com.tyc.bos.service.system;

import com.tyc.bos.domain.system.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IRoleService {
    Page<Role> pageQuery(Pageable pageable);

    void save(Role model, String menuIds, Integer[] permissionIds);

    List<Role> findAll();
}
