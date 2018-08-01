package com.tyc.bos.service.base.impl;

import com.tyc.bos.dao.base.IFixedAreaDao;
import com.tyc.bos.domain.base.FixedArea;
import com.tyc.bos.service.IFixedAreaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class FixedAreaServiceImpl implements IFixedAreaService {

    @Resource
    private IFixedAreaDao fixedAreaDao;

    @Override
    public void save(FixedArea model) {
        fixedAreaDao.save(model);
    }

    @Override
    public Page<FixedArea> pageQuery(Pageable pageable) {
        return fixedAreaDao.findAll(pageable);
    }
}
