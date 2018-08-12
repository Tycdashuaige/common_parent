package com.tyc.bos.dao.base;

import com.tyc.bos.domain.base.SubArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ISubareaDao extends JpaRepository<SubArea,String> {

    @Query(value = "select * from T_SUB_AREA where C_AREA_ID = ?1",nativeQuery = true)
    List<SubArea> findByAreaId(String id);

    //@Query(value = "select a.province,count(t) from SubArea s inner join s.area a group by a.provice")
    @Query(value = "select a.C_PROVINCE,count(1) from T_SUB_AREA sa, T_AREA a where sa.C_AREA_ID = a.C_ID group by a.C_PROVINCE", nativeQuery = true)
    List<Object> findBySubareas();
}
