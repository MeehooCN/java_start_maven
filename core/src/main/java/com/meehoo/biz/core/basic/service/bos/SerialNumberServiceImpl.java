package com.meehoo.biz.core.basic.service.bos;

import com.meehoo.biz.common.util.BaseUtil;
import com.meehoo.biz.common.util.StringUtil;
import com.meehoo.biz.core.basic.dao.bos.ISerialNumberDao;
import com.meehoo.biz.core.basic.domain.bos.SerialNumber;
import com.meehoo.biz.core.basic.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-06-01.
 */
@Service
@Transactional
public class SerialNumberServiceImpl  extends BaseService implements ISerialNumberService  {
    private final ISerialNumberDao serialNumberDao;

    @Autowired
    public SerialNumberServiceImpl(ISerialNumberDao serialNumberDao){
        this.serialNumberDao = serialNumberDao;
    }

    @Override
    public Map<String,Object> getBizObjectMaxSeq(String bizObject) {
        Map<String,Object> retMap = new HashMap<String,Object>();
        try {
            List<SerialNumber> lstSerialNumber = serialNumberDao.queryBizObjSerialNumber(bizObject);
            if(BaseUtil.collectionNotNull(lstSerialNumber)){
                SerialNumber serialNumber = lstSerialNumber.get(0);
                long seq = serialNumber.getSeq();
                String strPrefix = StringUtil.stringNotNull(serialNumber.getPrefix()) && !serialNumber.getPrefix().equals("null")? serialNumber.getPrefix():"";
                String placeHolder = StringUtil.stringNotNull(serialNumber.getPlaceHolder()) && !serialNumber.getPlaceHolder().equals("null")? serialNumber.getPlaceHolder():"";
                String strNumber = strPrefix + placeHolder + String.valueOf(seq + 1);
                retMap.put("newSerialNumber",strNumber);
                retMap.put("oldSerialNumber",serialNumber);
            }
        }
        catch (Exception e){
            retMap.put("newSerialNumber","error");
            e.printStackTrace();
        }

        return retMap;
    }
}
