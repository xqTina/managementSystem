package com.szkj.sms.mapper;

import com.szkj.sms.dto.DataZhenXianJiDto;
import com.szkj.sms.dto.DataZhenXianJiExportDto;
import com.szkj.sms.entity.DataZhenxianji;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: <a href="http://sixiwenwu.com"/>
 * @Date: 2021/9/23 21:52
 */
@Repository
public interface MyDataZhenXianJiMapper {
    /**
     * 从start开始查询limit条正弦计数据
     *
     * @param start 开始索引
     * @param limit 数量
     * @return List<DataZhenXianJiDto>
     */
    List<DataZhenXianJiDto> selectListByLimit(@Param("start") int start, @Param("limit") int limit);

    /**
     * 按顺序从start开始查询limit条正弦计数据
     *
     * @param start   开始索引
     * @param limit   数量
     * @param orderBy 排序
     * @return List<DataZhenXianJiDto>
     */
    List<DataZhenXianJiDto> selectListByLimitOrderBy(@Param("start") int start, @Param("limit") int limit, @Param("orderBy") String orderBy);

    /**
     * 查询正弦计数据数量
     *
     * @return Integer
     */
    Integer selectCount();

    /**
     * 用户从start开始查询limit条正弦计数据
     *
     * @param username 用户名
     * @param start    开始索引
     * @param limit    数量
     * @return List<DataZhenXianJiDto>
     */
    List<DataZhenXianJiDto> selectListByUsernameAndLimit(@Param("username") String username, @Param("start") int start, @Param("limit") int limit);

    /**
     * 用户按顺序从start开始查询limit条正弦计数据
     *
     * @param username 用户名
     * @param start    开始索引
     * @param limit    数量
     * @param orderBy  排序
     * @return List<DataZhenXianJiDto>
     */
    List<DataZhenXianJiDto> selectListByUsernameAndLimitOrderBy(@Param("username") String username, @Param("start") int start, @Param("limit") int limit, @Param("orderBy") String orderBy);

    /**
     * 按顺序获取用户的从start开始的时间到end结束的时间正弦计数据
     *
     * @param username 用户名
     * @param begin   开始时间
     * @param end    结束时间
     * @param orderBy  asc 正序 desc 倒叙
     * @return List
     */
    List<DataZhenXianJiExportDto> getDataZhenXianJiExport(@Param("username")String username, @Param("begin") String begin, @Param("end") String end, @Param("dtuId") int dtuId,  @Param("orderBy") String orderBy);

    List<DataZhenXianJiExportDto> selectListByLimitOrderByExport(@Param("begin") String begin, @Param("end") String end,@Param("dtuId") int dtuId,@Param("orderBy") String orderBy);

    /**
     * 用户查询正弦计数据数量
     * @param username 用户名
     * @return Integer
     */
    Integer selectCountByUsername(@Param("username") String username);

    @Select("SELECT * FROM ( SELECT MAX(id) ids FROM data_zhenxianji where device_id = #{deviceId}) a LEFT JOIN data_zhenxianji z ON a.ids = z.id")
    DataZhenxianji selectOneMaxDataByDeviceId(@Param("deviceId") String deviceId);
}
