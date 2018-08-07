package com.tyc.bos.dao.base;

import com.tyc.bos.domain.base.SubArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ISubareaDao extends JpaRepository<SubArea,String> {

    @Query(value = "select * from T_SUB_AREA where C_AREA_ID = ?1",nativeQuery = true)
    List<SubArea> findByAreaId(String id);
}
