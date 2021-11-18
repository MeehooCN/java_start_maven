package com.meehoo.biz.core.basic.domain.bos;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * Created by XG on 2016-06-01.
 */
@Entity
@Table(name = "bos_serial_number")
@DynamicInsert
@DynamicUpdate
@Data
public class SerialNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 100, nullable = false)
    private String bizObject;

    @Column(length = 50)
    private String prefix;


    @Column(length = 20)
    private String placeHolder;

    @Column
    private Long seq;
}