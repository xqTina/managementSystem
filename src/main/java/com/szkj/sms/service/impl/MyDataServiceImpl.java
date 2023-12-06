package com.szkj.sms.service.impl;

import com.szkj.sms.dto.DataLieFengJiDto;
import com.szkj.sms.dto.DataQingXieJiDto;
import com.szkj.sms.dto.DataZhenXianJiDto;
import com.szkj.sms.dto.DataZhenXianJiExportDto;
import com.szkj.sms.mapper.MyDataLieFengJiMapper;
import com.szkj.sms.mapper.MyDataQingXieJiMapper;
import com.szkj.sms.mapper.MyDataZhenXianJiMapper;
import com.szkj.sms.service.MyDataService;
import com.tencentcloudapi.dayu.v20180709.models.OrderBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: <a href="http://sixiwenwu.com"/>
 * @Date: 2021/9/23 20:23
 */
@Service
public class MyDataServiceImpl implements MyDataService {
    private final MyDataLieFengJiMapper myDataLieFengJiMapper;
    private final MyDataQingXieJiMapper myDataQingXieJiMapper;
    private final MyDataZhenXianJiMapper myDataZhenXianJiMapper;

    @Autowired
    public MyDataServiceImpl(MyDataLieFengJiMapper myDataLieFengJiMapper, MyDataQingXieJiMapper myDataQingXieJiMapper, MyDataZhenXianJiMapper myDataZhenXianJiMapper) {
        this.myDataLieFengJiMapper = myDataLieFengJiMapper;
        this.myDataQingXieJiMapper = myDataQingXieJiMapper;
        this.myDataZhenXianJiMapper = myDataZhenXianJiMapper;
    }

    @Override
    public List<DataLieFengJiDto> getDataLieFengJiPage(int page, int limit) {
        return myDataLieFengJiMapper.selectListByLimit((page - 1) * limit, limit);
    }

    @Override
    public Integer getDataLieFengJiCount() {
        return myDataLieFengJiMapper.selectCount();
    }

    @Override
    public List<DataLieFengJiDto> getDataLieFengJiPage(int page, int limit, String orderBy) {
        return myDataLieFengJiMapper.selectListByLimitOrderBy((page - 1) * limit, limit, orderBy);
    }

    @Override
    public List<DataQingXieJiDto> getDataQingXieJiPage(int page, int limit) {
        return myDataQingXieJiMapper.selectListByLimit((page - 1) * limit, limit);
    }

    @Override
    public Integer getDataQingXieJiCount() {
        return myDataQingXieJiMapper.selectCount();
    }

    @Override
    public List<DataQingXieJiDto> getDataQingXieJiPage(int page, int limit, String orderBy) {
        return myDataQingXieJiMapper.selectListByLimitOrderBy((page - 1) * limit, limit, orderBy);
    }

    @Override
    public List<DataZhenXianJiDto> getDataZhenXianJiPage(int page, int limit) {
        return myDataZhenXianJiMapper.selectListByLimit((page - 1) * limit, limit);
    }

    @Override
    public Integer getDataZhenXianJiCount() {
        return myDataZhenXianJiMapper.selectCount();
    }

    @Override
    public List<DataZhenXianJiDto> getDataZhenXianJiPage(int page, int limit, String orderBy) {
        return myDataZhenXianJiMapper.selectListByLimitOrderBy((page - 1) * limit, limit, orderBy);
    }

    @Override
    public List<DataLieFengJiDto> getDataLieFengJiPage(String username, int page, int limit) {
        return myDataLieFengJiMapper.selectListByUsernameAndLimit(username, (page - 1) * limit, limit);
    }

    @Override
    public Integer getDataLieFengJiCount(String username) {
        return myDataLieFengJiMapper.selectCountByUsername(username);
    }

    @Override
    public List<DataLieFengJiDto> getDataLieFengJiPage(String username, int page, int limit, String orderBy) {
        return myDataLieFengJiMapper.selectListByUsernameAndLimitOrderBy(username, (page - 1) * limit, limit, orderBy);
    }

    @Override
    public List<DataQingXieJiDto> getDataQingXieJiPage(String username, int page, int limit) {
        return myDataQingXieJiMapper.selectListByUsernameAndLimit(username, (page - 1) * limit, limit);
    }

    @Override
    public Integer getDataQingXieJiCount(String username) {
        return myDataQingXieJiMapper.selectCountByUsername(username);
    }

    @Override
    public List<DataQingXieJiDto> getDataQingXieJiPage(String username, int page, int limit, String orderBy) {
        return myDataQingXieJiMapper.selectListByUsernameAndLimitOrderBy(username, (page - 1) * limit, limit, orderBy);
    }

    @Override
    public List<DataZhenXianJiDto> getDataZhenXianJiPage(String username, int page, int limit) {
        return myDataZhenXianJiMapper.selectListByUsernameAndLimit(username, (page - 1) * limit, limit);
    }

    @Override
    public Integer getDataZhenXianJiCount(String username) {
        return myDataZhenXianJiMapper.selectCountByUsername(username);
    }

    @Override
    public List<DataZhenXianJiDto> getDataZhenXianJiPage(String username, int page, int limit, String orderBy) {
        return myDataZhenXianJiMapper.selectListByUsernameAndLimitOrderBy(username, (page - 1) * limit, limit, orderBy);
    }

    @Override
    public List<DataZhenXianJiExportDto> getDataZhenXianJiExport(String username, String begin, String end, int dtuId,  String orderBy) {
        return myDataZhenXianJiMapper.getDataZhenXianJiExport(username,begin,end,dtuId,orderBy);
    }

    @Override
    public List<DataZhenXianJiExportDto> getDataZhenXianJiExport(String begin, String end, int dtuId, String orderBy) {
        return myDataZhenXianJiMapper.selectListByLimitOrderByExport(begin,end,dtuId,orderBy);
    }


}
