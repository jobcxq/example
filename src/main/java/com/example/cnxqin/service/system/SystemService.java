package com.example.cnxqin.service.system;

import com.example.cnxqin.dao.SystemConfigDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author cnxqin
 * @desc
 * @date 2021/01/23 13:32
 */
@Service
public class SystemService {

    @Autowired
    SystemConfigDao systemConfigDao;

    public SystemService getByKeyAndCode(String key, String code){
        return systemConfigDao.getByKeyAndCode(key, code);
    }
}
