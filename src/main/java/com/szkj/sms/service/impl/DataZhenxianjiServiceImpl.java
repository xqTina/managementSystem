package com.szkj.sms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.szkj.sms.entity.DataZhenxianji;
import com.szkj.sms.mapper.MyDataZhenXianJiMapper;
import com.szkj.sms.service.DataZhenxianjiService;
import com.szkj.sms.mapper.DataZhenxianjiMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class DataZhenxianjiServiceImpl extends ServiceImpl<DataZhenxianjiMapper, DataZhenxianji>
        implements DataZhenxianjiService {

    @Autowired
    private MyDataZhenXianJiMapper myDataZhenXianJiMapper;

    @Override
    public DataZhenxianji selectOneMaxDataByDeviceId(String deviceId) {
        DataZhenxianji dataZhenxianji = myDataZhenXianJiMapper.selectOneMaxDataByDeviceId(deviceId);
        return dataZhenxianji;
    }


}




