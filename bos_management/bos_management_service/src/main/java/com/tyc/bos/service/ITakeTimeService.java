package com.tyc.bos.service;

import com.tyc.bos.domain.base.TakeTime;

import java.util.List;

public interface ITakeTimeService {
    List<TakeTime> findAll();
}
