package com.tyc.bos.dao.base;

import com.tyc.bos.domain.base.TakeTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITakeTimeDao extends JpaRepository<TakeTime,Integer> {
}
