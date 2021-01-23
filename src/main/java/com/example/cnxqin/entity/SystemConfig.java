package com.example.cnxqin.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author cnxqin
 * @desc
 * @date 2021/01/23 13:25
 */
@Data
@Entity
@Table(name = "system_config")
public class SystemConfig implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增
    private Long id;

    @Column(name = "config_key")
    private String configKey;

    @Column(name = "config_code")
    private String configCode;

    @Column(name = "config_value")
    private String configValue;

    @Column(name = "config_name")
    private String configName;

    @Column(name = "remark")
    private String remark;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

}
