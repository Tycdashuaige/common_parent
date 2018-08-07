package com.tyc.bos.service.take_delivery.impl;

import cn.tyc.crm.service.ICustomerService;
import com.tyc.bos.dao.base.IAreaDao;
import com.tyc.bos.dao.base.IFixedAreaDao;
import com.tyc.bos.dao.base.ISubareaDao;
import com.tyc.bos.domain.base.Area;
import com.tyc.bos.domain.base.Courier;
import com.tyc.bos.domain.base.FixedArea;
import com.tyc.bos.domain.base.SubArea;
import com.tyc.bos.domain.take_delivery.Order;
import com.tyc.bos.domain.take_delivery.WorkBill;
import com.tyc.bos.service.take_delivery.IOrderService;
import com.tyc.bos.dao.take_delivery.IOrderDao;
import com.tyc.bos.dao.take_delivery.IWorkBillDao;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional

public class OrderServiceImpl implements IOrderService {

    private static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    //订单
    @Resource
    private IOrderDao orderDao;

    //客户
    @Resource
    private ICustomerService customerServiceProxy;

    //工单
    @Resource
    private IWorkBillDao workBillDao;

    //区域
    @Resource
    private IAreaDao areaDao;

    //分区
    @Resource
    private ISubareaDao subareaDao;

    //定区
    private IFixedAreaDao fixedAreaDao;

    @Override
    public void save(Order order) {

        String orderType = "";

        Area sendArea = areaDao.findByProvinceAndCityAndDistrict(order.getSendArea().getProvince(), order.getSendArea().getCity(), order.getSendArea().getDistrict());
        Area recArea = areaDao.findByProvinceAndCityAndDistrict(order.getRecArea().getProvince(), order.getRecArea().getCity(), order.getRecArea().getDistrict());

        log.info("接收订单数据:" + order.toString());
        String sendAddress2 = order.getSendAddress();
        String sendAddress = sendAddress2;
        String fixedAreaId = customerServiceProxy.findFixedAreaIdByAddress(sendAddress);

        Courier cr = null;
        if (StringUtils.isNotBlank(fixedAreaId)) {

            FixedArea fixedArea = fixedAreaDao.findOne(fixedAreaId);
            order.setStatus("1");

            //发送短信给快递员
            System.out.println("发送短信给快递员");

            Set<Courier> couriers = fixedArea.getCouriers();

            for (Courier courier : couriers) {
                cr = courier;
                orderType = "自动分单";
                this.generateWorkBill(order, cr);
                break;
            }
        } else {

            String id = sendArea.getId();
            List<SubArea> subAreas = subareaDao.findByAreaId(id);

            String sendAddress1 = order.getSendAddress();
            for (SubArea subArea : subAreas) {
                String keyWords = subArea.getKeyWords();
                String assistKeyWords = subArea.getAssistKeyWords();

                if (sendAddress1.contains(keyWords) || sendAddress1.contains(assistKeyWords)) {

                    Set<Courier> couriers = subArea.getFixedArea().getCouriers();
                    if (couriers != null && couriers.size() > 0) {
                        for (Courier courier : couriers) {
                            if (courier != null) {
                                cr = courier;
                                orderType = "自动分单";
                                this.generateWorkBill(order, cr);
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (sendArea == null) {
            log.warn("发送人省市区为空");
        }
        if (recArea == null) {
            log.warn("收件人省市区为空");
        }
        if (StringUtils.isBlank(orderType)) {
            order.setOrderType("手动分单");
        } else {
            order.setOrderType(orderType);
        }

        order.setCourier(cr);
        order.setOrderNum(UUID.randomUUID().toString());
        order.setOrderTime(new Date());
        order.setSendArea(sendArea);
        order.setRecArea(recArea);

        orderDao.save(order);
    }

    private void generateWorkBill(Order order, Courier cr) {

        WorkBill workBill = new WorkBill();
        workBill.setOrder(order);
        workBill.setCourier(cr);
        workBill.setBuildtime(new Date());
        workBill.setPickstate("1");
        workBill.setType("1");
        workBill.setRemark(order.getRemark());
        workBill.setSmsNumber("123456789012345");
        workBill.setAttachbilltimes(1);
        workBillDao.save(workBill);
    }
}
