package com.szkj.sms.mapper;

import com.szkj.sms.dto.DeviceDto;
import com.szkj.sms.dto.UserDeviceDto;
import com.szkj.sms.entity.Device;
import com.szkj.sms.entity.Dtu;
import com.szkj.sms.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: <a href="http://sixiwenwu.com"/>
 * @Date: 2021/9/23 17:10
 */
@Repository
public interface MyDeviceMapper {

    /**
     * 查所有设备数据
     *
     * @return List<DeviceDto>
     */
    List<DeviceDto> selectList();

    /**
     * 从start开始查limit条设备数据
     *
     * @param start 开始索引
     * @param limit 数量
     * @return List<DeviceDto>
     */
    List<DeviceDto> selectList(@Param("start") int start, @Param("limit") int limit);

    /**
     * 按顺序从start开始查limit条设备数据
     *
     * @param start   开始索引
     * @param limit   数量
     * @param orderBy 顺序
     * @return List<DeviceDto
     */
    List<DeviceDto> selectList(@Param("start") int start, @Param("limit") int limit, @Param("orderBy") String orderBy);

    /**
     * 根据搜索项按顺序从start开始查limit条设备数据
     *
     * @param start   开始索引
     * @param limit   数量
     * @param orderBy 顺序
     * @param key     搜索关键字
     * @return List<DeviceDto
     */
    List<DeviceDto> selectList(@Param("start") int start, @Param("limit") int limit, @Param("orderBy") String orderBy, @Param("key") String key,@Param("deviceType") String deviceType);

    /**
     * 查询设备数量
     *
     * @return Integer
     */
    Integer selectCount();

    /**
     * 按搜索关键字获取采集设备数量
     *
     * @param key 搜索关键字
     * @return Integer
     */
    Integer selectCount(@Param("key") String key);

    /**
     * 查用户所有设备数据
     *
     * @param username 用户名
     * @return List<DeviceDto>
     */
    List<UserDeviceDto> selectListByUsername(@Param("username") String username);

    /**
     * 从start开始查limit条用户设备数据
     *
     * @param username 用户名
     * @param start    开始索引
     * @param limit    数量
     * @return List<DeviceDto>
     */
    List<UserDeviceDto> selectListByUsername(@Param("username") String username, @Param("start") int start, @Param("limit") int limit);

    /**
     * 按顺序从start开始查limit条用户设备数据
     *
     * @param username 用户名
     * @param start    开始索引
     * @param limit    数量
     * @param orderBy  顺序
     * @return List<DeviceDto
     */
    List<UserDeviceDto> selectListByUsername(@Param("username") String username, @Param("start") int start, @Param("limit") int limit, @Param("orderBy") String orderBy);

    /**
     * 按顺序和搜索关键字从start开始查limit条用户设备数据
     *
     * @param username 用户名
     * @param start    开始索引
     * @param limit    数量
     * @param orderBy  顺序
     * @param key      搜索关键字
     * @return List<DeviceDto
     */
    List<UserDeviceDto> selectListByUsername(@Param("username") String username, @Param("start") int start, @Param("limit") int limit, @Param("orderBy") String orderBy, @Param("key") String key);

    /**
     * 查询用户设备数量
     *
     * @param username 用户名
     * @return Integer
     */
    Integer selectCountByUsername(@Param("username") String username);

    /**
     * 按关键字查询用户设备数量
     *
     * @param username 用户名
     * @param key      搜索关键字
     * @return Integer
     */
    Integer selectCountByUsernameAndSearchKey(@Param("username") String username, @Param("key") String key);

    /**
     * 查询用户的类型为deviceType的设备数量
     *
     * @param username   用户名
     * @param deviceType 设备类型
     * @return Integer
     */
    Integer selectCountByUsernameAndDeviceType(@Param("username") String username, @Param("deviceType") String deviceType);

    /**
     * 查询用户绑定设备的所有上级DTU列表(不重复)
     *
     * @param username 用户名
     * @return List<Dtu>
     */
    List<Dtu> selectDtuListByUsername(@Param("username") String username);

    /**
     * 查询指定DTU下用户绑定的设备列表(不重复)
     *
     * @param username 用户名
     * @param dtuId    dtu.id
     * @return List<Device>
     */
    List<Device> selectDeviceListByUsernameAndDtuId(@Param("username") String username, @Param("dtuId") int dtuId);

    /**
     * 查询指定DTU和Device下用户绑定的设备的通道数量，！！！！假定通过device和dtuid的筛选后只会有一个结果
     *
     * @param username 用户名
     * @param dtuId    dtu.id
     * @param deviceId device.id
     * @return Integer
     */
    Integer selectNumberOfChannelsByUsernameAndDtuIdAndDeviceId(@Param("username") String username, @Param("dtuId") int dtuId, @Param("deviceId") int deviceId);

    /**
     * 通过DTU序列号和采集设备序列号查询设备ID
     *
     * @param dtuId    DTU序列号
     * @param deviceId 设备序列号
     * @return 设备ID
     */
    Integer selectDeviceIdByDtuDtuIdAndDeviceDeviceId(@Param("dtuId") String dtuId, @Param("deviceId") String deviceId);

    /**
     * 根据设备ID查询设备详情
     *
     * @param id 设备ID
     * @return DeviceDto
     */
    DeviceDto selectOneById(@Param("id") Integer id);

    /**
     * 根据设备ID查询该设备绑定的用户
     *
     * @param id 设备ID
     * @return List<User>
     */
    List<User> selectUserListById(@Param("id") Integer id);
}
