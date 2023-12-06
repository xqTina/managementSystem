package com.szkj.sms.service;

import com.szkj.sms.entity.Dtu;
import com.szkj.sms.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public interface UserService extends IService<User> {

    ArrayList<Dtu> getUserDeviceDtu(String name);

    int getUserDeviceDtuCount(String name);

    ArrayList<Dtu> getUserDeviceDtu();

    int getUserDeviceDtuCount();
}
