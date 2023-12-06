package com.szkj.sms.mapper;

import com.szkj.sms.entity.Dtu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * @Entity com.szkj.sms.entity.Dtu
 */
@Repository
public interface DtuMapper extends BaseMapper<Dtu> {

    /**
     * 根据用户名获取用户的dtu编号
     * @param name
     * @return
     */
    ArrayList<Dtu> getUserDeviceDtu(@Param("name") String name);

    /**
     * 根据用户名获取设备dtu的数量
     * @param name
     * @return
     */
    int getUserDeviceDtuCount(@Param("name") String name);

}




