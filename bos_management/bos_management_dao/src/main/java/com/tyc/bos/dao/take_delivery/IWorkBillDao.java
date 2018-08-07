package com.tyc.bos.dao.take_delivery;

import com.tyc.bos.domain.take_delivery.WorkBill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IWorkBillDao extends JpaRepository<WorkBill,Integer> {
}
