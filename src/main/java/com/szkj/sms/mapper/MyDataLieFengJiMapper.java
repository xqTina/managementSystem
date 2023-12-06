package com.szkj.sms.mapper;

import com.szkj.sms.dto.DataLieFengJiDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: <a href="http://sixiwenwu.com"/>
 * @Date: 2021/9/23 19:50
 */
@Repository
public interface MyDataLieFengJiMapper {

    /**
     * 管理员从start开始查询limit条裂缝计数据
     *
     * @param start 开始索引
     * @param limit 数量
     * @return List<DataLieFengJiDto>
     */
    List<DataLieFengJiDto> selectListByLimit(@Param("start") int start, @Param("limit") int limit);

    /**
     * 管理员按顺序从start开始查询limit条裂缝计数据
     *
     * @param start   开始索引
     * @param limit   数量
     * @param orderBy 排序
     * @return List<DataLieFengJiDto>
     */
    List<DataLieFengJiDto> selectListByLimitOrderBy(@Param("start") int start, @Param("limit") int limit, @Param("orderBy") String orderBy);

    /**
     * 管理员查询裂缝计数据数量
     *
     * @return Integer
     */
    Integer selectCount();

    /**
     * 用户从start开始查询limit条裂缝计数据
     *
     * @param username 用户名
     * @param start    开始索引
     * @param limit    数量
     * @return List<DataLieFengJiDto>
     */
    List<DataLieFengJiDto> selectListByUsernameAndLimit(@Param("username") String username, @Param("start") int start, @Param("limit") int limit);

    /**
     * 用户按顺序从start开始查询limit条裂缝计数据
     *
     * @param username 用户名
     * @param start    开始索引
     * @param limit    数量
     * @param orderBy  排序
     * @return List<DataLieFengJiDto>
     */
    List<DataLieFengJiDto> selectListByUsernameAndLimitOrderBy(@Param("username") String username, @Param("start") int start, @Param("limit") int limit, @Param("orderBy") String orderBy);

    /**
     * 用户查询裂缝计数据数量
     *
     * @param username 用户名
     * @return Integer
     */
    Integer selectCountByUsername(@Param("username") String username);
}
