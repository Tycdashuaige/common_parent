package com.tyc.bos.service.take_delivery;

import com.tyc.bos.domain.take_delivery.Order;

import javax.jws.WebService;

@WebService
public interface IOrderService {

    void save(Order order);
}
