package com.meehoo.biz.core.basic.service.setting;

import com.meehoo.biz.common.util.BaseUtil;
import com.meehoo.biz.common.util.StringUtil;
import com.meehoo.biz.core.basic.dao.setting.IProvinceCityAreaDao;
import com.meehoo.biz.core.basic.domain.setting.ProvinceCityArea;
import com.meehoo.biz.core.basic.service.BaseService;
import com.meehoo.biz.core.basic.vo.setting.AreaSelectVO;
import com.meehoo.biz.core.basic.vo.setting.CitySelectVO;
import com.meehoo.biz.core.basic.vo.setting.ProvinceCityAreaSelectVO;
import com.meehoo.biz.core.basic.vo.setting.ProvinceSelectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2016-10-19.
 */
@Service
@Transactional
public class ProvinceCityAreaServiceImpl extends BaseService implements IProvinceCityAreaService {
    private final IProvinceCityAreaDao provinceCityAreaDao;

    @Autowired
    public ProvinceCityAreaServiceImpl(IProvinceCityAreaDao provinceCityAreaDao) {
        this.provinceCityAreaDao = provinceCityAreaDao;
    }

    @Override
    public List<ProvinceCityArea> getArea(String provinceCode, String cityCode) throws Exception {
        return provinceCityAreaDao.getOnlyArea(provinceCode, cityCode, "");
    }

    @Override
    /*@Cacheable(value = { "info" })*/
    public ProvinceCityAreaSelectVO getProvinceCityAreaSelectVO(String provinceName) throws Exception {
        ProvinceCityAreaSelectVO provinceCityAreaSelectVO = new ProvinceCityAreaSelectVO();
        List<ProvinceCityArea>   provinceCityAreas        = this.provinceCityAreaDao.findByName(provinceName);
        if (BaseUtil.collectionNotNull(provinceCityAreas)) {
            ProvinceCityArea       provinceCityArea     = provinceCityAreas.get(0);
            List<ProvinceCityArea> provinceCityAreaList = this.provinceCityAreaDao.findByProvinceCode(provinceCityArea.getProvinceCode());
            provinceCityAreaSelectVO = this.getProvinceCityAreaSelectVO(provinceCityAreaList, "");
        }
        return provinceCityAreaSelectVO;
    }

    //value??????name; for API
    @Override
  /*  @Cacheable(value = { "info" })*/
    public ProvinceCityAreaSelectVO getProvinceCityAreaSelectVOForApi(String provinceName) throws Exception {
        ProvinceCityAreaSelectVO provinceCityAreaSelectVO = new ProvinceCityAreaSelectVO();
        List<ProvinceCityArea>   provinceCityAreas        = this.provinceCityAreaDao.findByName(provinceName);
        if (BaseUtil.collectionNotNull(provinceCityAreas)) {
            ProvinceCityArea       provinceCityArea     = provinceCityAreas.get(0);
            List<ProvinceCityArea> provinceCityAreaList = this.provinceCityAreaDao.findByProvinceCode(provinceCityArea.getProvinceCode());
            provinceCityAreaSelectVO = this.getProvinceCityAreaSelectVOForApi(provinceCityAreaList, "");
        }
        return provinceCityAreaSelectVO;
    }

    /**
     * ????????????
     *
     * @return
     * @throws Exception
     */
    @Override
    public ProvinceCityAreaSelectVO getOnlyProvince() throws Exception {
        List<ProvinceCityArea>   provinceCityAreaList     = provinceCityAreaDao.getOnlyProvince(ProvinceCityArea.CODE_AREA_EMPTY, ProvinceCityArea.CODE_CITY_EMPTY);
        ProvinceCityAreaSelectVO provinceCityAreaSelectVO = new ProvinceCityAreaSelectVO();
        ProvinceSelectVO provinceSelectVO         = null;
        List<ProvinceSelectVO>   voList                   = new ArrayList<>();
        for (int i = 0; i < provinceCityAreaList.size(); i++) {
            provinceSelectVO = new ProvinceSelectVO();
            provinceSelectVO.setLabel(provinceCityAreaList.get(i).getName());
            provinceSelectVO.setValue(provinceCityAreaList.get(i).getProvinceCode().concat(ProvinceCityArea.CODE_CITY_EMPTY).concat(ProvinceCityArea.CODE_AREA_EMPTY));
            provinceSelectVO.setChildren(null);
            voList.add(provinceSelectVO);
        }
        provinceCityAreaSelectVO.setProvinceSelectVOList(voList);
        return provinceCityAreaSelectVO;
    }

    /**
     * ???????????????????????????????????????????????????????????????????????????
     *
     * @param provinceCode ??????????????????
     * @return
     * @throws Exception
     */
    @Override
    public ProvinceCityAreaSelectVO getOnlyCity(String provinceCode) throws Exception {
        List<ProvinceCityArea>   provinceCityAreaList     = provinceCityAreaDao.getOnlyCity(provinceCode, ProvinceCityArea.CODE_AREA_EMPTY);
        ProvinceCityAreaSelectVO provinceCityAreaSelectVO = new ProvinceCityAreaSelectVO();
        List<ProvinceSelectVO>   voList                   = new ArrayList<>();
        List<CitySelectVO>       citySelectVOList         = new ArrayList<>();
        ProvinceSelectVO         provinceSelectVO         = new ProvinceSelectVO();
        CitySelectVO             citySelectVO             = null;
        for (int i = 0; i < provinceCityAreaList.size(); i++) {
            ProvinceCityArea provinceCityArea = provinceCityAreaList.get(i);
            if (provinceCityArea.getCityCode().indexOf(ProvinceCityArea.CODE_CITY_EMPTY) > -1) {
                //?????????c00????????????????????????
                provinceSelectVO.setLabel(provinceCityArea.getName());
                provinceSelectVO.setValue(provinceCityArea.getProvinceCode().concat(ProvinceCityArea.CODE_CITY_EMPTY).concat(ProvinceCityArea.CODE_AREA_EMPTY));
            } else {
                //?????????????????????
                citySelectVO = new CitySelectVO();
                citySelectVO.setLabel(provinceCityArea.getName());
                citySelectVO.setValue(provinceCityArea.getProvinceCode().concat(provinceCityArea.getCityCode()).concat(ProvinceCityArea.CODE_AREA_EMPTY));
                citySelectVO.setChildren(null);
                citySelectVOList.add(citySelectVO);
            }
        }
        provinceSelectVO.setChildren(citySelectVOList);
        voList.add(provinceSelectVO);
        provinceCityAreaSelectVO.setProvinceSelectVOList(voList);
        return provinceCityAreaSelectVO;
    }

    /**
     * ???????????????????????????????????????????????????????????????
     *
     * @param provinceCode ??????????????????
     * @param cityCode     ??????????????????
     * @return
     * @throws Exception
     */
    @Override
    public ProvinceCityAreaSelectVO getOnlyArea(String provinceCode, String cityCode) throws Exception {
        List<ProvinceCityArea>   provinceCityAreaList     = provinceCityAreaDao.getOnlyArea(provinceCode, cityCode, ProvinceCityArea.CODE_CITY_EMPTY);
        ProvinceCityAreaSelectVO provinceCityAreaSelectVO = new ProvinceCityAreaSelectVO();
        List<ProvinceSelectVO>   voList                   = new ArrayList<>();
        List<CitySelectVO>       citySelectVOList         = new ArrayList<>();
        List<AreaSelectVO>       areaSelectVOList         = new ArrayList<>();
        ProvinceSelectVO         provinceSelectVO         = null;
        CitySelectVO             citySelectVO             = null;
        AreaSelectVO             areaSelectVO             = null;
        for (int i = 0; i < provinceCityAreaList.size(); i++) {
            ProvinceCityArea provinceCityArea = provinceCityAreaList.get(i);
            if (provinceCityArea.getCityCode().indexOf(ProvinceCityArea.CODE_CITY_EMPTY) > -1 &&
                    provinceCityArea.getAreaCode().indexOf(ProvinceCityArea.CODE_AREA_EMPTY) > -1) {
                //??????a00?????????c00??????????????????
                provinceSelectVO = new ProvinceSelectVO();
                provinceSelectVO.setLabel(provinceCityArea.getName());
                provinceSelectVO.setValue(provinceCityArea.getProvinceCode().concat(ProvinceCityArea.CODE_CITY_EMPTY).concat(ProvinceCityArea.CODE_AREA_EMPTY));
            } else if (provinceCityArea.getAreaCode().indexOf(ProvinceCityArea.CODE_AREA_EMPTY) > -1 &&
                    provinceCityArea.getCityCode().indexOf(ProvinceCityArea.CODE_CITY_EMPTY) < 0) {
                //??????a00???????????????c00??????????????????
                citySelectVO = new CitySelectVO();
                citySelectVO.setLabel(provinceCityArea.getName());
                citySelectVO.setValue(provinceCityArea.getProvinceCode().concat(provinceCityArea.getCityCode()).concat(ProvinceCityArea.CODE_AREA_EMPTY));
                citySelectVOList.add(citySelectVO);
            } else {
                //????????????????????????
                areaSelectVO = new AreaSelectVO();
                areaSelectVO.setLabel(provinceCityArea.getName());
                areaSelectVO.setValue(provinceCityArea.getProvinceCode().concat(provinceCityArea.getCityCode()).concat(provinceCityArea.getAreaCode()));
                areaSelectVOList.add(areaSelectVO);
            }
        }
        citySelectVO.setChildren(areaSelectVOList);
        provinceSelectVO.setChildren(citySelectVOList);
        voList.add(provinceSelectVO);
        provinceCityAreaSelectVO.setProvinceSelectVOList(voList);

        return provinceCityAreaSelectVO;
    }

    @Override
    /*@Cacheable(value = { "info" })*/
    public ProvinceCityAreaSelectVO getAllProvinceCityAreaSelectVO(String exceptCodeName) throws Exception {
        ProvinceCityAreaSelectVO provinceCityAreaSelectVO = new ProvinceCityAreaSelectVO();

        List<ProvinceCityArea> provinceCityAreaList = this.provinceCityAreaDao.findAll();
        provinceCityAreaSelectVO = this.getProvinceCityAreaSelectVO(provinceCityAreaList, exceptCodeName);

        return provinceCityAreaSelectVO;
    }

    //value??????name; for API
    @Override
   /* @Cacheable(value = { "info" })*/
    public ProvinceCityAreaSelectVO getAllProvinceCityAreaSelectVOForApi(String except) throws Exception {
        ProvinceCityAreaSelectVO provinceCityAreaSelectVO = new ProvinceCityAreaSelectVO();

        List<ProvinceCityArea> provinceCityAreaList = this.provinceCityAreaDao.findAll();
        provinceCityAreaSelectVO = this.getProvinceCityAreaSelectVOForApi(provinceCityAreaList, except);

        return provinceCityAreaSelectVO;
    }

    private ProvinceCityAreaSelectVO getProvinceCityAreaSelectVO(List<ProvinceCityArea> provinceCityAreaList, String except) {
        ProvinceCityAreaSelectVO provinceCityAreaSelectVO = new ProvinceCityAreaSelectVO();
        List<ProvinceSelectVO>   provinceSelectVOs        = new ArrayList<>();
        if (BaseUtil.collectionNotNull(provinceCityAreaList)) {
            List<ProvinceCityArea> provinceList = provinceCityAreaList.stream()
                    .filter(p -> p.getCityCode().equals("c00"))
                    .filter(p -> p.getAreaCode().equals("a00"))
                    .collect(Collectors.toList());
            List<ProvinceCityArea> cityList = provinceCityAreaList.stream()
                    .filter(p -> !p.getCityCode().equals("c00"))
                    .filter(p -> p.getAreaCode().equals("a00"))
                    .collect(Collectors.toList());
            List<ProvinceCityArea> areaList = provinceCityAreaList.stream()
                    .filter(p -> !p.getCityCode().equals("c00"))
                    .filter(p -> !p.getAreaCode().equals("a00"))
                    .collect(Collectors.toList());

            Map<String, List<ProvinceCityArea>> cityAreaMap     = new HashMap<>();
            Map<String, List<ProvinceCityArea>> provinceCityMap = new HashMap<>();

            //??????????????????
            String                 areaCityCode = "";
            List<ProvinceCityArea> cityAreaList = null;
            for (ProvinceCityArea area : areaList) {
                areaCityCode = area.getProvinceCode().concat(area.getCityCode());
                if (cityAreaMap.containsKey(areaCityCode)) {
                    cityAreaMap.get(areaCityCode).add(area);
                } else {
                    cityAreaList = new ArrayList<>();
                    cityAreaList.add(area);
                    cityAreaMap.put(areaCityCode, cityAreaList);
                }
            }

            //???????????????
            String                 cityProvinceCode = "";
            List<ProvinceCityArea> provinceCityList = null;
            for (ProvinceCityArea ctiy : cityList) {
                cityProvinceCode = ctiy.getProvinceCode();
                if (provinceCityMap.containsKey(cityProvinceCode)) {
                    provinceCityMap.get(cityProvinceCode).add(ctiy);
                } else {
                    provinceCityList = new ArrayList<>();
                    provinceCityList.add(ctiy);
                    provinceCityMap.put(cityProvinceCode, provinceCityList);
                }
            }

            ProvinceSelectVO   provinceSelectVO = null;
            CitySelectVO       citySelectVO     = null;
            List<CitySelectVO> citySelectVOs    = null;
            AreaSelectVO       areaSelectVO     = null;
            List<AreaSelectVO> areaSelectVOs    = null;

            String       areaValue     = "";
            CharSequence areaCharSequence;
            String       cityValue     = "";
            CharSequence cityCharSequence;
            String       provinceValue = "";
            CharSequence provinceCharSequence;
            for (ProvinceCityArea province : provinceList) {
                provinceValue = province.getProvinceCode().concat(province.getCityCode()).
                        concat(province.getAreaCode()).concat("|").concat(province.getName());
                if (StringUtil.stringNotNull(except) && except.contains(provinceValue)) {
                    continue;
                }
                provinceSelectVO = new ProvinceSelectVO();
                provinceSelectVO.setLabel(province.getName());
                provinceSelectVO.setValue(provinceValue);

                List<ProvinceCityArea> provinceCitys = provinceCityMap.get(province.getProvinceCode());
                citySelectVOs = new ArrayList<>();
                for (ProvinceCityArea city : provinceCitys) {
                    cityValue = city.getProvinceCode().concat(city.getCityCode()).
                            concat(city.getAreaCode()).concat("|").concat(city.getName());
                    if (StringUtil.stringNotNull(except) && except.contains(cityValue)) {
                        continue;
                    }
                    citySelectVO = new CitySelectVO();
                    citySelectVO.setLabel(city.getName());
                    citySelectVO.setValue(cityValue);

                    List<ProvinceCityArea> cityAreas = cityAreaMap.get(city.getProvinceCode().concat(city.getCityCode()));
                    areaSelectVOs = new ArrayList<>();
                    for (ProvinceCityArea area : cityAreas) {
                        areaValue = area.getProvinceCode().concat(area.getCityCode()).concat(area.getAreaCode()).concat("|").concat(area.getName());
                        if (StringUtil.stringNotNull(except) && except.contains(areaValue)) {
                            continue;
                        }
                        areaSelectVO = new AreaSelectVO();
                        areaSelectVO.setLabel(area.getName());
                        areaSelectVO.setValue(areaValue);

                        areaSelectVOs.add(areaSelectVO);
                    }
                    citySelectVO.setChildren(areaSelectVOs);
                    citySelectVOs.add(citySelectVO);
                }

                provinceSelectVO.setChildren(citySelectVOs);
                provinceSelectVOs.add(provinceSelectVO);

            }
        }
        provinceCityAreaSelectVO.setProvinceSelectVOList(provinceSelectVOs);
        return provinceCityAreaSelectVO;
    }

    /**
     * value????????? code|name ??????????????????????????????code
     *
     * @param provinceCityAreaList
     * @param except
     * @return
     */
    private ProvinceCityAreaSelectVO getProvinceCityAreaSelectVOForApi(List<ProvinceCityArea> provinceCityAreaList, String except) {
        ProvinceCityAreaSelectVO provinceCityAreaSelectVO = new ProvinceCityAreaSelectVO();
        List<ProvinceSelectVO>   provinceSelectVOs        = new ArrayList<>();
        if (BaseUtil.collectionNotNull(provinceCityAreaList)) {
            List<ProvinceCityArea> provinceList = provinceCityAreaList.stream()
                    .filter(p -> p.getCityCode().equals("c00"))
                    .filter(p -> p.getAreaCode().equals("a00"))
                    .collect(Collectors.toList());
            List<ProvinceCityArea> cityList = provinceCityAreaList.stream()
                    .filter(p -> !p.getCityCode().equals("c00"))
                    .filter(p -> p.getAreaCode().equals("a00"))
                    .collect(Collectors.toList());
            List<ProvinceCityArea> areaList = provinceCityAreaList.stream()
                    .filter(p -> !p.getCityCode().equals("c00"))
                    .filter(p -> !p.getAreaCode().equals("a00"))
                    .collect(Collectors.toList());

            Map<String, List<ProvinceCityArea>> cityAreaMap     = new HashMap<>();
            Map<String, List<ProvinceCityArea>> provinceCityMap = new HashMap<>();

            //??????????????????
            String                 areaCityCode = "";
            List<ProvinceCityArea> cityAreaList = null;
            for (ProvinceCityArea area : areaList) {
                areaCityCode = area.getProvinceCode().concat(area.getCityCode());
                if (cityAreaMap.containsKey(areaCityCode)) {
                    cityAreaMap.get(areaCityCode).add(area);
                } else {
                    cityAreaList = new ArrayList<>();
                    cityAreaList.add(area);
                    cityAreaMap.put(areaCityCode, cityAreaList);
                }
            }

            //???????????????
            String                 cityProvinceCode = "";
            List<ProvinceCityArea> provinceCityList = null;
            for (ProvinceCityArea ctiy : cityList) {
                cityProvinceCode = ctiy.getProvinceCode();
                if (provinceCityMap.containsKey(cityProvinceCode)) {
                    provinceCityMap.get(cityProvinceCode).add(ctiy);
                } else {
                    provinceCityList = new ArrayList<>();
                    provinceCityList.add(ctiy);
                    provinceCityMap.put(cityProvinceCode, provinceCityList);
                }
            }

            ProvinceSelectVO   provinceSelectVO = null;
            CitySelectVO       citySelectVO     = null;
            List<CitySelectVO> citySelectVOs    = null;
            AreaSelectVO       areaSelectVO     = null;
            List<AreaSelectVO> areaSelectVOs    = null;

            String       areaValue     = "";
            CharSequence areaCharSequence;
            String       cityValue     = "";
            CharSequence cityCharSequence;
            String       provinceValue = "";
            CharSequence provinceCharSequence;
            for (ProvinceCityArea province : provinceList) {
                provinceValue = province.getProvinceCode().concat(province.getCityCode()).
                        concat(province.getAreaCode());
                if (StringUtil.stringNotNull(except) && except.contains(provinceValue)) {
                    continue;
                }
                provinceSelectVO = new ProvinceSelectVO();
                provinceSelectVO.setLabel(province.getName());
                provinceSelectVO.setValue(provinceValue);

                List<ProvinceCityArea> provinceCitys = provinceCityMap.get(province.getProvinceCode());
                citySelectVOs = new ArrayList<>();
                for (ProvinceCityArea city : provinceCitys) {
                    cityValue = city.getProvinceCode().concat(city.getCityCode()).
                            concat(city.getAreaCode());
                    if (StringUtil.stringNotNull(except) && except.contains(cityValue)) {
                        continue;
                    }
                    citySelectVO = new CitySelectVO();
                    citySelectVO.setLabel(city.getName());
                    citySelectVO.setValue(cityValue);

                    List<ProvinceCityArea> cityAreas = cityAreaMap.get(city.getProvinceCode().concat(city.getCityCode()));
                    areaSelectVOs = new ArrayList<>();
                    for (ProvinceCityArea area : cityAreas) {
                        areaValue = area.getProvinceCode().concat(area.getCityCode()).concat(area.getAreaCode());
                        if (StringUtil.stringNotNull(except) && except.contains(areaValue)) {
                            continue;
                        }
                        areaSelectVO = new AreaSelectVO();
                        areaSelectVO.setLabel(area.getName());
                        areaSelectVO.setValue(areaValue);

                        areaSelectVOs.add(areaSelectVO);
                    }
                    citySelectVO.setChildren(areaSelectVOs);
                    citySelectVOs.add(citySelectVO);
                }

                provinceSelectVO.setChildren(citySelectVOs);
                provinceSelectVOs.add(provinceSelectVO);

            }
        }
        provinceCityAreaSelectVO.setProvinceSelectVOList(provinceSelectVOs);
        return provinceCityAreaSelectVO;
    }
}
