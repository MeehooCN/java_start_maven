package com.meehoo.biz.core.basic.param;


import lombok.Data;
import org.hibernate.Criteria;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

@Data
public class PageCriteria {

    private int page = 1;
    private int rows = 10;
    private String sort;
    private String order;   //desc 降序 ；asc 升序

    public PageCriteria() {
        this.order = "desc";
    }

    public PageCriteria(int page,int rows) {
        this.page = page;
        this.rows = rows;
    }

    public int getFirstResult() {
        return (page - 1) * rows;
    }

    public int getMaxResult() {
        return rows;
    }

    public Criteria configCriteria(Criteria criteria) {
        criteria.setFirstResult(getFirstResult());
        criteria.setMaxResults(getMaxResult());
        return criteria;
    }

    public Pageable toPageable() {
        Sort s = null;
        if (StringUtils.hasText(sort)) {
            if (sort != null && sort.equals("asc")) {
                s =Sort.by(Sort.Direction.ASC, sort);
            } else {
                s = Sort.by(Sort.Direction.DESC, sort);
            }
        }
        return  PageRequest.of(page > 0 ? page - 1 : page, rows, s);
    }

}
