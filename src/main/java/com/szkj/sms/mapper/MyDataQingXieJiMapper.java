package com.szkj.sms.mapper;

import com.szkj.sms.dto.DataLieFengJiDto;
import com.szkj.sms.dto.DataQingXieJiDto;
import com.szkj.sms.entity.DataQingxieji;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: <a href="http://sixiwenwu.com"/>
 * @Date: 2021/9/23 21:36
 */
@Repository
public interface MyDataQingXieJiMapper {
    /**
     * 从start开始查询limit条倾斜计数据
     *
     * @param start 开始索引
     * @param limit 数量
     * @return List<DataQingXieJiDto>
     */
    List<DataQingXieJiDto> selectListByLimit(@Param("start") int start, @Param("limit") int limit);

    /**
     * 按顺序从start开始查询limit条倾斜计数据
     *
     * @param start   开始索引
     * @param limit   数量
     * @param orderBy 排序
     * @return List<DataQingXieJiDto>
     */
    List<DataQingXieJiDto> selectListByLimitOrderBy(@Param("start") int start, @Param("limit") int limit, @Param("orderBy") String orderBy);

    /**
     * 查询倾斜计数据数量
     *
     * @return Integer
     */
    Integer selectCount();

    /**
     * 用户从start开始查询limit条倾斜计数据
     *
     * @param username 用户名
     * @param start    开始索引
     * @param limit    数量
     * @return List<DataQingXieJiDto>
     */
    List<DataQingXieJiDto> selectListByUsernameAndLimit(@Param("username") String username, @Param("start") int start, @Param("limit") int limit);

    /**
     * 用户按顺序从start开始查询limit条倾斜计数据
     *
     * @param username 用户名
     * @param start    开始索引
     * @param limit    数量
     * @param orderBy  排序
     * @return List<DataQingXieJiDto>
     */
    List<DataQingXieJiDto> selectListByUsernameAndLimitOrderBy(@Param("username") String username, @Param("start") int start, @Param("limit") int limit, @Param("orderBy") String orderBy);

    /**
     * 用户查询倾斜计数据数量
     *
     * @param username 用户名
     * @return Integer
     */
    Integer selectCountByUsername(@Param("username") String username);
}
