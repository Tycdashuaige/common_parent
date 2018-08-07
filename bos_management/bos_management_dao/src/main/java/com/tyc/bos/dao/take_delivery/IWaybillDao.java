package com.tyc.bos.dao.take_delivery;

import com.tyc.bos.domain.take_delivery.WayBill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IWaybillDao extends JpaRepository<WayBill,Integer> {
}
