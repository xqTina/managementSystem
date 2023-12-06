package com.szkj.sms.service;

import com.szkj.sms.dto.DataLieFengJiDto;
import com.szkj.sms.dto.DataQingXieJiDto;
import com.szkj.sms.dto.DataZhenXianJiDto;
import com.szkj.sms.dto.DataZhenXianJiExportDto;
import com.szkj.sms.entity.DataQingxieji;

import java.util.List;

/**
 * @Author: <a href="http://sixiwenwu.com"/>
 * @Date: 2021/9/23 20:22
 */
public interface MyDataService {

    /**
     * 获取从start开始的limit条裂缝计数据
     *
     * @param page  页码
     * @param limit 数量
     * @return List<DataLieFengJiDto>
     */
    List<DataLieFengJiDto> getDataLieFengJiPage(int page, int limit);

    /**
     * 获取裂缝计数据数量
     *
     * @return Integer
     */
    Integer getDataLieFengJiCount();

    /**
     * 按顺序获取从start开始的limit条裂缝计数据
     *
     * @param page    页码
     * @param limit   数量
     * @param orderBy 排序方式 asc 正序 desc 倒叙
     * @return List<DataLieFengJiDto>
     */
    List<DataLieFengJiDto> getDataLieFengJiPage(int page, int limit, String orderBy);

    /**
     * 获取从start开始的limit条倾斜计数据
     *
     * @param page  页码
     * @param limit 数量
     * @return List
     */
    List<DataQingXieJiDto> getDataQingXieJiPage(int page, int limit);

    /**
     * 获取倾斜计数据数量
     *
     * @return Integer
     */
    Integer getDataQingXieJiCount();

    /**
     * 按顺序获取从start开始的limit条倾斜计数据
     *
     * @param page    页码
     * @param limit   数量
     * @param orderBy 排序方式 asc 正序 desc 倒叙
     * @return List<DataQingXieJiDto>
     */
    List<DataQingXieJiDto> getDataQingXieJiPage(int page, int limit, String orderBy);

    /**
     * 获取从start开始的limit条正弦计数据
     *
     * @param page  页码
     * @param limit 数量
     * @return List
     */
    List<DataZhenXianJiDto> getDataZhenXianJiPage(int page, int limit);

    /**
     * 获取正弦计数据数量
     *
     * @return Integer
     */
    Integer getDataZhenXianJiCount();

    /**
     * 按顺序获取从start开始的limit条正弦计数据
     *
     * @param page    页码
     * @param limit   数量
     * @param orderBy 排序方式 asc 正序 desc 倒叙
     * @return List<DataZhenXianJiDto>
     */
    List<DataZhenXianJiDto> getDataZhenXianJiPage(int page, int limit, String orderBy);

    /**
     * 获取用户的从start开始的limit条裂缝计数据
     *
     * @param username 用户名
     * @param page     页码
     * @param limit    数量
     * @return List
     */
    List<DataLieFengJiDto> getDataLieFengJiPage(String username, int page, int limit);

    /**
     * 获取用户的裂缝计数据数量
     *
     * @param username 用户名
     * @return Integer
     */
    Integer getDataLieFengJiCount(String username);

    /**
     * 按顺序获取用户的从start开始的limit条裂缝计数据
     *
     * @param username 用户名
     * @param page     页码
     * @param limit    数量
     * @param orderBy  asc 正序 desc 倒叙
     * @return List
     */
    List<DataLieFengJiDto> getDataLieFengJiPage(String username, int page, int limit, String orderBy);

    /**
     * 获取从start开始的limit条倾斜计数据
     *
     * @param username 用户名
     * @param page     页码
     * @param limit    数 量
     * @return List
     */
    List<DataQingXieJiDto> getDataQingXieJiPage(String username, int page, int limit);

    /**
     * 获取用户的倾斜计数据数量
     *
     * @param username 用户名
     * @return Integer
     */
    Integer getDataQingXieJiCount(String username);

    /**
     * 按顺序获取用户的从start开始的limit条倾斜计数据
     *
     * @param username 用户名
     * @param page     页码
     * @param limit    数量
     * @param orderBy  asc 正序 desc 倒叙
     * @return List
     */
    List<DataQingXieJiDto> getDataQingXieJiPage(String username, int page, int limit, String orderBy);

    /**
     * 获取从start开始的limit条正弦计数据
     *
     * @param username 用户名
     * @param page     页码
     * @param limit    数量
     * @return List
     */
    List<DataZhenXianJiDto> getDataZhenXianJiPage(String username, int page, int limit);

    /**
     * 获取用户的正弦计数据数量
     *
     * @param username 用户名
     * @return Integer
     */
    Integer getDataZhenXianJiCount(String username);

    /**
     * 按顺序获取用户的从start开始的limit条正弦计数据
     *
     * @param username 用户名
     * @param page     页码
     * @param limit    数量
     * @param orderBy  asc 正序 desc 倒叙
     * @return List
     */
    List<DataZhenXianJiDto> getDataZhenXianJiPage(String username, int page, int limit, String orderBy);

    /**
     * 按顺序获取用户的从start开始的时间到end结束的时间正弦计数据
     *
     * @param username 用户名
     * @param begin   开始时间
     * @param end    结束时间
     * @param orderBy  asc 正序 desc 倒叙
     * @return List
     */
    List<DataZhenXianJiExportDto> getDataZhenXianJiExport(String username, String begin, String end, int dtuId, int number, String orderBy);

    List<DataZhenXianJiExportDto> getDataZhenXianJiExport(String begin, String end,int dtuId, int number, String desc);
}
