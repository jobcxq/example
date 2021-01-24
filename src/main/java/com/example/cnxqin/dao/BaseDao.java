package com.example.cnxqin.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author cnxqin
 * @desc
 * @date 2021/01/24 17:10
 */
@NoRepositoryBean
public interface BaseDao<T> extends JpaRepository<T, Long> {

    /**
     * 查不到数据会出现异常 EntityNotFoundException
     * @param id
     * @return
     */
    @Deprecated
    T getOne(Long id);


}
