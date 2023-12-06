package com.szkj.sms.service;

import com.szkj.sms.entity.DataZhenxianji;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface DataZhenxianjiService extends IService<DataZhenxianji> {
    DataZhenxianji selectOneMaxDataByDeviceId(String deviceId);
}
