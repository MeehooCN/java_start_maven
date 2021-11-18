package com.meehoo.biz.core.basic.vo.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created by CZ on 2018/2/9.
 */
@Setter
@Getter
@NoArgsConstructor
public class OrganizationTreeTotalVO {
    private long total;
    private List<OrganizationTreeVO> children;
}