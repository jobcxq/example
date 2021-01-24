package com.example.cnxqin.service;

import com.example.cnxqin.dao.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author cnxqin
 * @desc
 * @date 2021/01/24 17:09
 */
public abstract class BaseService<T>{

    @Autowired
    private BaseDao<T> baseDao;

    public T saveUser(T entity){
        baseDao.save(entity);
        return entity;
    }

    public T getById(Long id){
        return baseDao.findById(id).orElse(null);
    }


}
