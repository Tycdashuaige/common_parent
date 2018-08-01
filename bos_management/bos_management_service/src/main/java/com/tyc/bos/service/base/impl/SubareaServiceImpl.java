package com.tyc.bos.service.base.impl;

import com.tyc.bos.dao.base.ISubareaDao;
import com.tyc.bos.domain.base.SubArea;
import com.tyc.bos.service.ISubareaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class SubareaServiceImpl implements ISubareaService {

    @Resource
    private ISubareaDao subareaDao;
    @Override
    public void save(SubArea model) {
        subareaDao.save(model);
    }

    @Override
    public Page<SubArea> pageQuery(Pageable pageable) {
        return subareaDao.findAll(pageable);
    }
}
