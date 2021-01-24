package com.example.cnxqin.dao;

import com.example.cnxqin.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserDao extends BaseDao<User> {

    @Modifying
    @Query("update User set name = ?1 where id = ?2")
    int updateNameById(String name, Long id);

    @Modifying
    @Query(value = "update user set name = ? where id = ?",nativeQuery = true)
    int updateNameByIdNative(String name, Long id);
}
