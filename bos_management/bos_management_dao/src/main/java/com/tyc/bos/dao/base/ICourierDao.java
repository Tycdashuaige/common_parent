package com.tyc.bos.dao.base;

import com.tyc.bos.domain.base.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ICourierDao extends JpaRepository<Courier, Integer>,JpaSpecificationExecutor<Courier> {

    @Query(value = "update T_COURIER set C_DELTAG = 1 where C_ID = ?", nativeQuery = true)
    @Modifying
    void update(Integer courierId);

    Courier findById(int courierId);

    List<Courier> findByDeltagIsNull();
}
