package com.tyc.bos.dao.base;

import com.tyc.bos.domain.base.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IAreaDao extends JpaRepository<Area,String > {

    @Query(value = "select * from T_AREA t where t.C_CITY like ?1 or upper (t.C_CITYCODE) like ?1 or t.C_DISTRICT like ?1 " +
            "or t.C_PROVINCE like ?1 or t.C_SHORTCODE like ?1",nativeQuery = true)
    List<Area> findByQ(String q);

    Area findByProvinceAndCityAndDistrict(String province, String city, String district);
}
