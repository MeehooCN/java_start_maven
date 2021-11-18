package com.meehoo.biz.core.basic.dao.setting;


import com.meehoo.biz.core.basic.domain.setting.ProvinceCityArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2017/8/10.
 */
public interface IProvinceCityAreaDao extends JpaRepository<ProvinceCityArea, Long> {
    @Query("FROM ProvinceCityArea p WHERE p.name=?1")
    List<ProvinceCityArea> findByName(String name);

    @Query("FROM ProvinceCityArea p WHERE p.provinceCode=?1")
    List<ProvinceCityArea> findByProvinceCode(String provinceCode);

    @Query("from ProvinceCityArea p where p.provinceCode = ?1 and p.areaCode = ?2 order by p.cityCode asc")
    List<ProvinceCityArea> getOnlyCity(String provinceCode, String areaEmpty);

    @Query("from ProvinceCityArea p where p.areaCode = ?1 and p.cityCode = ?2 order by p.provinceCode asc")
    List<ProvinceCityArea> getOnlyProvince(String areaEmpty, String cityEmpty);

    @Query("from ProvinceCityArea p where p.provinceCode = ?1 and (p.cityCode = ?2 or p.cityCode = ?3) order by p.areaCode asc")
    List<ProvinceCityArea> getOnlyArea(String provinceCode, String cityCode, String cityEmpty);

    @Query("from ProvinceCityArea p where p.name = ?1 and p.provinceCode = 'p50'")
    ProvinceCityArea findCQAreaByName(String name);

}

