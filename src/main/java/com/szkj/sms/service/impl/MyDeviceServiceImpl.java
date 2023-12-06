package com.szkj.sms.service.impl;

import com.szkj.sms.dto.DeviceDto;
import com.szkj.sms.dto.UserDeviceDto;
import com.szkj.sms.entity.Device;
import com.szkj.sms.entity.Dtu;
import com.szkj.sms.entity.User;
import com.szkj.sms.mapper.MyDeviceMapper;
import com.szkj.sms.service.MyDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * MyDeviceService接口实现类
 *
 * @Author: <a href="http://sixiwenwu.com"/>
 * @Date: 2021/9/23 17:30
 */
@Service
public class MyDeviceServiceImpl implements MyDeviceService {
    private final MyDeviceMapper myDeviceMapper;

    @Autowired
    public MyDeviceServiceImpl(MyDeviceMapper myDeviceMapper) {
        this.myDeviceMapper = myDeviceMapper;
    }

    @Override
    public List<DeviceDto> getDevicePage() {
        return myDeviceMapper.selectList();
    }

    @Override
    public List<DeviceDto> getDevicePage(int page, int limit) {
        return myDeviceMapper.selectList((page - 1) * limit, limit);
    }

    @Override
    public List<DeviceDto> getDevicePage(int page, int limit, String orderBy) {
        return myDeviceMapper.selectList((page - 1) * limit, limit, orderBy);
    }

    @Override
    public List<DeviceDto> getDevicePage(int page, int limit, String orderBy, String key,String deviceType) {
        return myDeviceMapper.selectList((page - 1) * limit, limit, orderBy, key,deviceType);
    }

    @Override
    public Integer deviceCount() {
        return myDeviceMapper.selectCount();
    }

    @Override
    public Integer deviceCount(String key) {
        return myDeviceMapper.selectCount(key);
    }

    @Override
    public List<UserDeviceDto> getDevicePage(String username) {
        return myDeviceMapper.selectListByUsername(username);
    }

    @Override
    public List<UserDeviceDto> getDevicePage(String username, int page, int limit) {
        return myDeviceMapper.selectListByUsername(username, (page - 1) * limit, limit);
    }

    @Override
    public List<UserDeviceDto> getDevicePage(String username, int page, int limit, String orderBy) {
        return myDeviceMapper.selectListByUsername(username, (page - 1) * limit, limit, orderBy);
    }

    @Override
    public List<UserDeviceDto> getDevicePage(String username, int page, int limit, String orderBy, String key) {
        return myDeviceMapper.selectListByUsername(username, (page - 1) * limit, limit, orderBy, key);
    }

    @Override
    public Integer userDeviceCount(String username) {
        return myDeviceMapper.selectCountByUsername(username);
    }

    @Override
    public Integer userDeviceCount(String username, String key) {
        return myDeviceMapper.selectCountByUsernameAndSearchKey(username, key);
    }

    @Override
    public Integer userDeviceCountByDeviceType(String username, String deviceType) {
        return myDeviceMapper.selectCountByUsernameAndDeviceType(username, deviceType);
    }

    @Override
    public List<Dtu> getUserDtu(String username) {
        return myDeviceMapper.selectDtuListByUsername(username);
    }

    @Override
    public List<Device> getUserDtuDevice(String username, int dtuId) {
        return myDeviceMapper.selectDeviceListByUsernameAndDtuId(username, dtuId);
    }

    @Override
    public Integer getUserDtuDeviceNumberOfChannels(String username, int dtuId, int deviceId) {
        return myDeviceMapper.selectNumberOfChannelsByUsernameAndDtuIdAndDeviceId(username, dtuId, deviceId);
    }

    @Override
    public Integer getDeviceId(String dtuId, String deviceId) {
        return myDeviceMapper.selectDeviceIdByDtuDtuIdAndDeviceDeviceId(dtuId, deviceId);
    }

    @Override
    public DeviceDto getDevice(Integer deviceId) {
        return myDeviceMapper.selectOneById(deviceId);
    }

    @Override
    public List<User> getUser(Integer id) {
        return myDeviceMapper.selectUserListById(id);
    }
}
