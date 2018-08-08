package com.tyc.bos.service.system.impl;

import com.tyc.bos.dao.system.IMenuDao;
import com.tyc.bos.domain.system.Menu;
import com.tyc.bos.service.system.IMenuService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class MenuServiceImpl implements IMenuService {

    @Resource
    private IMenuDao menuDao;

    @Override
    public List<Menu> findAll() {
        return menuDao.findByParentMenuIsNull();
    }

    @Override
    public void save(Menu model) {
        if (model.getParentMenu() != null && model.getParentMenu().getId() == 0) {
            model.setParentMenu(null);
        }
        menuDao.save(model);
    }

    @Override
    public Page<Menu> pageQuery(Pageable pageable) {
        return menuDao.findAll(pageable);
    }
}
