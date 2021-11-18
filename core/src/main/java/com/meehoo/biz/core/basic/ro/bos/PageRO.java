package com.meehoo.biz.core.basic.ro.bos;


import com.meehoo.biz.core.basic.param.PageCriteria;
import com.meehoo.biz.core.basic.param.SearchCondition;
import com.meehoo.biz.core.basic.ro.IdRO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjian on 2017/8/30.
 * 接收前台分页参数vo
 */
@Getter
@Setter
public class PageRO{
//    private String status;

    private int rows;  //每页数量

    private int page;  //当前第几页

//    private List<SearchCondition> searchConditionList;

    public PageRO() {
        rows = 10;
        page = 1;
    }

//    public List<SearchCondition> getSearchConditionList() {
//        if (searchConditionList == null) {
//            searchConditionList = new ArrayList<>();
//        }
//        return searchConditionList;
//    }
//
//    public void setSearchConditionList(List<SearchCondition> searchConditionList) {
//        this.searchConditionList = searchConditionList;
//    }

    public PageCriteria toPageCriteria() {
        return new PageCriteria(page, rows);
    }

}
