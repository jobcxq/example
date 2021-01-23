package com.example.cnxqin.dao;

import com.example.cnxqin.entity.SystemConfig;
import com.example.cnxqin.service.system.SystemService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author cnxqin
 * @desc
 * @date 2021/01/23 13:25
 */
public interface SystemConfigDao extends JpaRepository<SystemConfig, Integer> {

    @Query("from SystemConfig where configKey = ?1 and configCode = ?2")
    SystemService getByKeyAndCode(String key, String code);

}
