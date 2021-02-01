package com.example.cnxqin.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.*;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "USER")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增
    private Long id;

    @Column(name = "name",length = 50)
    private String name;

    @Column(name = "phone_no",length = 50)
    private String phoneNo;

    @Column
    private Integer age;

    @Column
    private String sex;

    @Column
    private String addr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
