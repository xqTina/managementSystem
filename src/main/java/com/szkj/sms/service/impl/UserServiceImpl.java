package com.szkj.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.szkj.sms.entity.Dtu;
import com.szkj.sms.entity.User;
import com.szkj.sms.mapper.DtuMapper;
import com.szkj.sms.service.UserService;
import com.szkj.sms.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    private final DtuMapper dtuMapper;

    @Autowired
    public UserServiceImpl(DtuMapper dtuMapper){
        this.dtuMapper = dtuMapper;
    }

    @Override
    public ArrayList<Dtu> getUserDeviceDtu(String name) {
        return dtuMapper.getUserDeviceDtu(name);
    }

    @Override
    public int getUserDeviceDtuCount(String name) {
        return dtuMapper.getUserDeviceDtuCount(name);
    }

    @Override
    public ArrayList<Dtu> getUserDeviceDtu() {
        QueryWrapper<Dtu> dtuQueryWrapper = new QueryWrapper<>();
        dtuQueryWrapper.eq("is_delete",0);
        List<Dtu> dtus = dtuMapper.selectList(dtuQueryWrapper);
       return  (ArrayList)dtus;
    }

    @Override
    public int getUserDeviceDtuCount() {
        QueryWrapper<Dtu> dtuQueryWrapper = new QueryWrapper<>();
        dtuQueryWrapper.eq("is_delete",0);
        return dtuMapper.selectCount(dtuQueryWrapper);
    }
}




