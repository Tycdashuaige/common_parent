package com.tyc.bos.service.system;

import com.tyc.bos.domain.system.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRoleService {
    Page<Role> pageQuery(Pageable pageable);
}
