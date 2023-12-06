package com.szkj.sms.service;

import com.szkj.sms.dto.DeviceDto;
import com.szkj.sms.dto.UserDeviceDto;
import com.szkj.sms.entity.Device;
import com.szkj.sms.entity.Dtu;
import com.szkj.sms.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 设备列表相关操作接口
 *
 * @Author: <a href="http://sixiwenwu.com"/>
 * @Date: 2021/9/23 17:28
 */

public interface MyDeviceService {

    /**
     * 获取采集设备列表
     *
     * @return List<DeviceDto>
     */
    List<DeviceDto> getDevicePage();

    /**
     * 分页获取采集设备列表
     *
     * @param page  页码
     * @param limit 数量
     * @return List<DeviceDto>
     */
    List<DeviceDto> getDevicePage(int page, int limit);

    /**
     * 分页排序获取采集设备列表
     *
     * @param page    页码
     * @param limit   数量
     * @param orderBy 排序
     * @return List<DeviceDto>
     */
    List<DeviceDto> getDevicePage(int page, int limit, String orderBy);

    /**
     * 分页排序按搜索关键字获取采集设备列表
     *
     * @param key     关键字
     * @param page    页码
     * @param limit   数量
     * @param orderBy 排序
     * @return List<DeviceDto>
     */
    List<DeviceDto> getDevicePage(int page, int limit, String orderBy, String key,String deviceType);

    /**
     * 获取采集设备数量
     *
     * @return Integer
     */
    Integer deviceCount();

    /**
     * 按搜索关键字获取采集设备数量
     *
     * @param key 搜索关键字
     * @return Integer
     */
    Integer deviceCount(String key);

    /**
     * 获取用户采集设备列表
     *
     * @param username 用户名
     * @return List<DeviceDto>
     */
    List<UserDeviceDto> getDevicePage(String username);

    /**
     * 分页获取用户采集设备列表
     *
     * @param username 用户名
     * @param page     页码
     * @param limit    数量
     * @return List<DeviceDto>
     */
    List<UserDeviceDto> getDevicePage(String username, int page, int limit);

    /**
     * 分页排序获取用户采集设备列表
     *
     * @param username 用户名
     * @param page     页码
     * @param limit    数量
     * @param orderBy  排序
     * @return List<DeviceDto>
     */
    List<UserDeviceDto> getDevicePage(String username, int page, int limit, String orderBy);

    /**
     * 分页按搜索关键字排序获取用户采集设备列表
     *
     * @param username 用户名
     * @param page     页码
     * @param limit    数量
     * @param orderBy  排序
     * @param key      关键字
     * @return List<DeviceDto>
     */
    List<UserDeviceDto> getDevicePage(String username, int page, int limit, String orderBy, String key);

    /**
     * 获取用户采集设备数量
     *
     * @param username 用户名
     * @return Integer
     */
    Integer userDeviceCount(String username);

    /**
     * 按搜索关键字获取用户采集设备数量
     *
     * @param username 用户名
     * @param key      搜索关键字
     * @return Integer
     */
    Integer userDeviceCount(String username, String key);

    /**
     * 获取用户的类型为deviceType的采集设备数量
     *
     * @param username   用户名
     * @param deviceType 设备类型
     * @return Integer
     */
    Integer userDeviceCountByDeviceType(String username, String deviceType);

    /**
     * 查询用户绑定设备的所有上级DTU列表(不重复)
     *
     * @param username 用户名
     * @return List<Dtu>
     */
    List<Dtu> getUserDtu(String username);

    /**
     * 查询指定DTU下用户绑定的设备列表(不重复)
     *
     * @param username 用户名
     * @param dtuId    dtu.id
     * @return List<Device>
     */
    List<Device> getUserDtuDevice(String username, int dtuId);

    /**
     * 查询指定DTU和Device下用户绑定的设备的通道数量，！！！！假定通过device和dtuid的筛选后只会有一个结果
     *
     * @param username 用户名
     * @param dtuId    dtu.id
     * @param deviceId device.id
     * @return Integer
     */
    Integer getUserDtuDeviceNumberOfChannels(String username, int dtuId, int deviceId);

    /**
     * 通过DTU序列号和采集设备序列号查询设备ID
     *
     * @param dtuId    DTU序列号
     * @param deviceId 设备序列号
     * @return 设备ID
     */
    Integer getDeviceId(String dtuId, String deviceId);

    /**
     * 根据设备ID查询设备详情
     *
     * @param deviceId 设备ID
     * @return DeviceDto
     */
    DeviceDto getDevice(Integer deviceId);

    /**
     * 根据设备ID查询该设备绑定的用户
     *
     * @param id 设备ID
     * @return List<User>
     */
    List<User> getUser(Integer id);
}
