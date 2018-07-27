package com.tyc.bos.service.impl;

import com.tyc.bos.dao.IStandardDao;
import com.tyc.bos.domain.Standard;
import com.tyc.bos.service.IStandardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class StandardServiceImpl implements IStandardService {

    @Resource
    private IStandardDao standardDao;

    @Override
    public void save(Standard model) {
        standardDao.save(model);
    }
}
