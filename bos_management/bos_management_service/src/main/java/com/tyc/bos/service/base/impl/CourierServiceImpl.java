package com.tyc.bos.service.base.impl;

import com.tyc.bos.dao.base.ICourierDao;
import com.tyc.bos.domain.base.Courier;
import com.tyc.bos.service.base.ICourierService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class CourierServiceImpl implements ICourierService {

    @Resource
    private ICourierDao courierDao;

    @Override
    public void save(Courier courier) {

        courierDao.save(courier);
    }

    @Override
    public Page<Courier> pageQuery(Specification<Courier> spct, Pageable pageable) {
        return (Page<Courier>) courierDao.findAll(spct,pageable);
    }

    @RequiresPermissions(value = "courier:delete")
    @Override
    public void delete(String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] split = ids.split(",");
            boolean flag = true;
            for (int i = 0; i < split.length; i++) {
                Courier courier = courierDao.findById(Integer.parseInt(split[i]));
                if (courier == null) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                for (int i = 0; i < split.length; i++) {
                    courierDao.update(Integer.parseInt(split[i]));
                }
            } else {
                System.out.println("非空操作");
            }
        }
    }

    @Override
    public List<Courier> findByDeltagIsNull() {
        return courierDao.findByDeltagIsNull();
    }
}
