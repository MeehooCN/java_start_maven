package com.meehoo.biz.core.basic.domain.setting;

import com.meehoo.biz.core.basic.domain.IdEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by wangjian on 2017-12-05.
 * 操作日志
 */
@Entity
@Table(name="sys_log")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@Accessors(chain = true)
public class OptLog extends IdEntity {

//    @Id
//    @GeneratedValue(generator = "system-uuid")
//    @GenericGenerator(name = "system-uuid", strategy = "uuid")
//    private String id;

    /**
     * 操作人
     */
    @Column(length = 50)
    private String userId;

    @Column(length = 50)
    private String userName;

    /**
     * 角色
     */
    @Column(length = 50)
    private String roleName;

    @Column(length = 50)
    private String orgId;

    @Column(length = 50)
    private String orgName;

//    /**
//     * 所属类型
//     * @see Organization#orgType
//     */
//    @Column(columnDefinition = "INT default 0")
//    private int orgType;

//    @ManyToOne
//    private ProOrgType proOrgType;

    /**
     * 操作时间
     */
    @Column
    private Date createTime;

    /**
     * 操作内容
     */
    @Column
    private String opt;

    /**
     * 类型
     */
    @Column
    private String type;
}
