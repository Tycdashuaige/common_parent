package com.tyc.bos.service.base.impl;

import com.tyc.bos.dao.base.ICourierDao;
import com.tyc.bos.dao.base.IFixedAreaDao;
import com.tyc.bos.dao.base.ITakeTimeDao;
import com.tyc.bos.domain.base.Courier;
import com.tyc.bos.domain.base.FixedArea;
import com.tyc.bos.domain.base.TakeTime;
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

    @Resource
    private ICourierDao courierDao;

    @Resource
    private ITakeTimeDao takeTimeDao;

    @Override
    public void save(FixedArea model) {
        fixedAreaDao.save(model);
    }

    @Override
    public Page<FixedArea> pageQuery(Pageable pageable) {
        return fixedAreaDao.findAll(pageable);
    }

    @Override
    public void associationCourierToFixedArea(String id, Integer courierId, Integer takeTimeId) {
        Courier courier = courierDao.findOne(courierId);
        FixedArea fixedArea = fixedAreaDao.findOne(id);
        fixedArea.getCouriers().add(courier);

        TakeTime takeTime = takeTimeDao.findOne(takeTimeId);

        courier.setTakeTime(takeTime);
    }
}
