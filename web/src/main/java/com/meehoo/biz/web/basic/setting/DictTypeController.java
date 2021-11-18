package com.meehoo.biz.web.basic.setting;

import com.meehoo.biz.common.util.StringUtil;
import com.meehoo.biz.core.basic.domain.setting.DataDict;
import com.meehoo.biz.core.basic.domain.setting.DictType;
import com.meehoo.biz.core.basic.param.HttpResult;
import com.meehoo.biz.core.basic.ro.IdRO;
import com.meehoo.biz.core.basic.ro.bos.SearchConditionListRO;
import com.meehoo.biz.core.basic.ro.setting.DictTypeRO;
import com.meehoo.biz.core.basic.service.common.ICommonService;
import com.meehoo.biz.core.basic.service.setting.IDictTypeService;
import com.meehoo.biz.core.basic.util.VOUtil;
import com.meehoo.biz.core.basic.vo.setting.DataDictVO;
import com.meehoo.biz.core.basic.vo.setting.DictTypeVO;
import com.meehoo.biz.web.basic.common.BaseController;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据字典 类型
 *
 * @author xg
 * @create 2017-05-11-14:45
 */
@Api(tags = "数据字典类型管理")
@RestController
@RequestMapping("/sysmanage/dictType")
@AllArgsConstructor
public class DictTypeController extends BaseController<DictType, DictTypeVO> {
    private final IDictTypeService dictTypeService;
    private final ICommonService commonService;


    /**
     * 根据数据字典的类型查询
     * @param idRO  id = 类型
     * @return
     */
    @RequestMapping(value = "getByType", method = RequestMethod.GET)
    @ResponseBody
    public HttpResult<List<DataDictVO>> getByType(@RequestBody IdRO idRO){
        Map<String,Object> map = new HashMap<>();
        List<DataDict> dataDictList = dictTypeService.queryListByColumn(DataDict.class,"dType",idRO.getId());
        List<DataDictVO> voList = VOUtil.convertDomainListToTempList(dataDictList, DataDictVO.class);
        return HttpResult.success(voList);
    }

    /**
     * 新增某个数据字典类型
     */
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public HttpResult create(@RequestBody DictTypeRO dictTypeRO) throws Exception{
        DictType oldType = new DictType();
        oldType.setId(null);
        boolean hasNoNumber = !StringUtil.stringNotNull(dictTypeRO.getCode());
        if (hasNoNumber) {
            oldType.setCode(commonService.getBizObjectSerialNumber("DictType"));
        }else {
            oldType.setCode(dictTypeRO.getCode());
        }
        oldType.setName(dictTypeRO.getName());
        oldType.setModule(dictTypeRO.getModule());
        dictTypeService.save(oldType);
        return HttpResult.success();
    }

    /**
     * 编辑
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public HttpResult update(@RequestBody DictTypeRO dictTypeRO) {
        try {

            if(!StringUtil.stringNotNull(dictTypeRO.getId())){
                throw new RuntimeException("未查询到当前业务对象");
            }

            DictType oldPM = null;
            oldPM = dictTypeService.queryById(DictType.class, dictTypeRO.getId());
            oldPM.setCode(dictTypeRO.getCode());
            oldPM.setName(dictTypeRO.getName());
            oldPM.setModule(dictTypeRO.getModule());
            dictTypeService.update(oldPM);
            return HttpResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 查询所有数据字典类型
     *
     * @throws Exception
     */
    @RequestMapping(value = "getAllDictType", method = RequestMethod.POST)
    public HttpResult<List<DictTypeVO>> getAllDictType(@RequestBody SearchConditionListRO searchConditionListRO) throws Exception {
        try {
            List<DictTypeVO> dictTypeVOList = dictTypeService.listAll(DictType.class, DictTypeVO.class,searchConditionListRO.getSearchConditionList());
            return HttpResult.success(dictTypeVOList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public HttpResult<Boolean> delete(String id){
        return confirmDelete(id);
    }
}
