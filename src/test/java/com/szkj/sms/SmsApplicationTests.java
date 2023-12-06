package com.szkj.sms;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.szkj.sms.entity.DataZhenxianji;
import com.szkj.sms.entity.DataZhenxianjiUnit;
import com.szkj.sms.mapper.DataZhenxianjiMapper;
import com.szkj.sms.mapper.DataZhenxianjiUnitMapper;
import com.szkj.sms.mapper.MyDataZhenXianJiMapper;
import lombok.extern.log4j.Log4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.util.List;

@SpringBootTest
class SmsApplicationTests {

    @Autowired
    private DataZhenxianjiMapper dataZhenxianjiMapper;

    @Autowired
    private DataZhenxianjiUnitMapper dataZhenxianjiUnitMapper;



    @Test
    void contextLoads() throws IllegalAccessException {
//        以device_id为38为例 查询正弦第一页5条数据
        Page<DataZhenxianji> dataZhenxianjiPage = dataZhenxianjiMapper.selectPage(new Page<>(1, 5), null);
        for (DataZhenxianji dataZhenxianji : dataZhenxianjiPage.getRecords()) {
            QueryWrapper<DataZhenxianjiUnit> dataZhenxianjiUnitQueryWrapper = new QueryWrapper<>();
            dataZhenxianjiUnitQueryWrapper.eq("zhenxian_device_id", dataZhenxianji.getDeviceId());
            DataZhenxianjiUnit dataZhenxianjiUnit = dataZhenxianjiUnitMapper.selectOne(dataZhenxianjiUnitQueryWrapper);
            if (dataZhenxianjiUnit==null) continue;  //没有查到正弦设备的单位

            Field[] dataZhenxianjiFields = dataZhenxianji.getClass().getDeclaredFields();//获取正弦字段

            Field[] dataZhenxianjiUnitFields = dataZhenxianjiUnit.getClass().getDeclaredFields();//获取设备单位字段

            for (Field zhenxianFeild: dataZhenxianjiFields) {
//                System.out.println("字段："+zhenxianFeild.getName());
                //设置对象的访问权限，保证对private的属性的访问
                zhenxianFeild.setAccessible(true);
                for (Field zhenxianUnitFeild : dataZhenxianjiUnitFields) {
                    zhenxianUnitFeild.setAccessible(true);
//                    System.out.println(zhenxianFeild.getName()+"--------"+zhenxianUnitFeild.getName());
                    if (zhenxianFeild.getName()==zhenxianUnitFeild.getName()){  //判断属性值相等
                        System.out.println("属性:"+zhenxianFeild.getName()+"正弦值---"+zhenxianFeild.get(dataZhenxianji)+"正弦单位值："+zhenxianUnitFeild.get(dataZhenxianjiUnit));
//                        dataZhenxianji.setFreqency();
                    }
                }
            }
        }

    }

}
