package com.szkj.sms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.szkj.sms.dto.DataZhenXianJiNewDto;
import com.szkj.sms.entity.*;
import com.szkj.sms.service.*;
import com.szkj.sms.util.JsonResult;
import com.szkj.sms.vo.RoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Controller
@Api(tags = "用户添加设备属性")
@RequestMapping("/pro")
//@ConfigurationProperties(prefix = "deviceDataUnit")
public class UserAddProController {


//    private String[] deviceArg;
    @Autowired
    private DataZhenxianjiService dataZhenxianjiService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DtuService dtuService;

    @Autowired
    private UserService userService;

    @Autowired
    private DeviceUserService deviceUserService;

    @Autowired
    private DataZhenxianjiUnitService dataZhenxianjiUnitService;


    @ApiOperation(value = "跳转")
    @GetMapping("/show")

    public String show(Model model,
                       @RequestParam(defaultValue = "0", value = "deviceDeviceId") String deviceDeviceId,
                       @RequestParam(defaultValue = "0", value = "deviceId") String deviceId,
                       @RequestParam(defaultValue = "0", value = "status") String status) {

        // 通过deviceDeviceId去查询最近的一条dataZhenxianji中的数据

        System.out.println("deviceDeviceId = " + deviceDeviceId);
        System.out.println("deviceDeviceId == \"NaN\" = " + deviceDeviceId == "NaN");
        System.out.println("deviceDeviceId.equals(\"NaN\") = " + deviceDeviceId.equals("NaN"));

        if(deviceDeviceId.equals("NaN")){
            model.addAttribute("failMsgs", "该选择为主目录，请在子目录设置参数");
            return "deviceAddProperty";
        }

        System.out.println("deviceId = " + deviceId);
        System.out.println("deviceId == \"0\" = " + deviceId == "0");
//        System.out.println(0 == deviceId);
        System.out.println("deviceId.equals(\"0\") = " + deviceId.toString().equals("0"));
        DataZhenxianji dataZhenxianji = dataZhenxianjiService.selectOneMaxDataByDeviceId(deviceId);
        System.out.println("dataZhenxianji = " + dataZhenxianji);
        model.addAttribute("deviceDeviceId",deviceDeviceId);
        model.addAttribute("deviceId",deviceId);
        // 存储最新一条数据时间
        model.addAttribute("dataTime", dataZhenxianji == null?LocalDateTime.now():dataZhenxianji.getDate().toString() +":"+ dataZhenxianji.getTime().toString());
      // 存储设备参数
//        Map<String,Object> deviceSelArg = new HashMap<>();
        ArrayList<String> deviceArg = new ArrayList<>();
//        deviceSelArg.put("压力(N/m2)","N/m2");
//        deviceSelArg.put("湿度","N/m3");
//        deviceSelArg.put("温度","N/m4");
//        deviceSelArg.put("频率","N/m5");
//        deviceSelArg.put("气压","N/m6");
//        model.addAttribute("proDeviceArg",deviceSelArg);
        // 表示更新数据表
        // 判断unit表中是否有
        System.out.println("deviceId = " + deviceId);
        QueryWrapper<DataZhenxianjiUnit> queryWrapper = new QueryWrapper<DataZhenxianjiUnit>()
                .eq("zhenxian_device_id",deviceId);
        List<DataZhenxianjiUnit> list = dataZhenxianjiUnitService.list(queryWrapper);
        System.out.println("listsss = " + list);
        if(list.size()!=0){
            DataZhenxianjiUnit dataZhenxianjiUnit = list.get(0);
            System.out.println("dataZhenxianjiUnitss = " + dataZhenxianjiUnit);
            try{
                //通过getDeclaredFields()方法获取对象类中的所有属性（含私有）
                Field[] dataZhenxianjiFields = dataZhenxianji.getClass().getDeclaredFields();
                Field[] declaredFields = dataZhenxianjiUnit.getClass().getDeclaredFields();
                for (Field field : dataZhenxianjiFields) {
                    //设置允许通过反射访问私有变量
                    field.setAccessible(true);
                    //获取字段的值
                    String valueF = field.get(dataZhenxianji).toString();
                    String nameF = field.getName();
                    Boolean flag = true;
                    if(valueF!="" && nameF!="id" && nameF !="channelId" && nameF!="deviceId"&& nameF!="date"&&nameF!="time"&& nameF!="serialVersionUID"){
                        for (Field declaredField : declaredFields) {
                            declaredField.setAccessible(true);
                            String nameD = declaredField.getName();
                            String valueD = (declaredField.get(dataZhenxianjiUnit)+"");
                            if(nameD == nameF && valueD.length()!=0){
//                                for (String s : deviceSelArg.keySet()) {
//                                   if( deviceSelArg.get(s).equals(valueD)){
                                       if(status.equals("1")){
//                                           deviceArg.add(valueF + "_" + s + "_" + valueD);
                                           deviceArg.add(valueF + "_" + valueD);
                                       }else if (status.equals("2")){
//                                           deviceArg.add(s + "_" +valueF + valueD);
                                           deviceArg.add(valueF + "_"+valueF + valueD);
                                       }
                                       flag = false;
                                       break;
                                   }
//                                }
//                                }
                        }

                        if(flag == true){
                            deviceArg.add(valueF);
                        }

                        model.addAttribute("failMsg", "");
                    }


                }
            }
            catch (Exception ex){
                //处理异常
                System.out.println("ex inner= " + ex);
            }
        }else {
           if (status.equals("2")) {
               model.addAttribute("failMsg", "暂无设置参数值，请先设置");
               return "showDeviceArg";
           }
            try {
                //通过getDeclaredFields()方法获取对象类中的所有属性（含私有）
                Field[] fields = dataZhenxianji.getClass().getDeclaredFields();
                for (Field field : fields) {
                    //设置允许通过反射访问私有变量
                    field.setAccessible(true);
                    //获取字段的值
                    String valueF = field.get(dataZhenxianji).toString();
                    String nameF = field.getName();

                    if (valueF != "" && nameF != "id" && nameF != "channelId" && nameF != "deviceId" && nameF != "date" && nameF != "time" && nameF != "serialVersionUID") {
                        deviceArg.add(valueF);
                    }
                }
            } catch (Exception ex) {
                //处理异常
                System.out.println("ex = " + ex);
            }
        }


        model.addAttribute("deviceArg",deviceArg);
        model.addAttribute("failMsgs", "success");
        if(status.equals("1")){
            return "deviceAddProperty";
        }else if (status.equals("2")){
            return "showDeviceArg";
        }
        // 根据设备id 查找相关的第一条属性
        return "deviceAddProperty";
    }


    @ResponseBody
    @ApiOperation(value = "添加属性管理页面路由")
    @PostMapping("/user/add/{deviceId}")
    public JsonResult JsonResult(Model model,
                         @PathVariable Integer deviceId,
                         @RequestBody DataZhenxianjiUnit dataZhenxianjiUnit) {

        // 判断当前设备id是否存在
        DataZhenxianji dataZhenxianji = dataZhenxianjiService.selectOneMaxDataByDeviceId(String.valueOf(deviceId));
        QueryWrapper<DataZhenxianjiUnit> dataZhenxianjiUnitQueryWrapper = new QueryWrapper<DataZhenxianjiUnit>()
                .eq("zhenxian_device_id",deviceId);
        List<DataZhenxianjiUnit> zhenxianjiUnitList = dataZhenxianjiUnitService.list(dataZhenxianjiUnitQueryWrapper);
        boolean save;
        if(zhenxianjiUnitList.size() == 0){
            // 存入数据库
            // 没有设置过单位则直接添加
            System.out.println("dataZhenxianjiUnit = " + dataZhenxianjiUnit);
            // 实时更新该正弦id
//            dataZhenxianjiUnit.setId(dataZhenxianji.getId());
            save = dataZhenxianjiUnitService.save(dataZhenxianjiUnit);
        }else{
            // 根据id保存
            // 如何已设置单位则直接更新
            dataZhenxianjiUnit.setId(zhenxianjiUnitList.get(0).getId());
            // 更新
             save = dataZhenxianjiUnitService.updateById(dataZhenxianjiUnit);
        }

        if(!save){
            return new JsonResult(0, "设置失败");
        }
        return new JsonResult(0, "设置成功");

    }



    @ResponseBody
    @ApiOperation(value = "test")
    @GetMapping("/data_zhenxianji/table")
    public JsonResult findProNew(Model model,@RequestParam(defaultValue = "1", value = "page") Integer page,
                              @RequestParam(defaultValue = "10", value = "limit") Integer limit) {
        Page<Dtu> dtuPage = new Page<>(page, limit);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // 查询没有删除的设备
        QueryWrapper<Dtu> dtuQueryWrapper = new QueryWrapper<Dtu>()
                .eq("is_delete",0);
        // 分页
        // 查询所有dtu以及dtu下的设备
        Page<Dtu> pages = null;
        List<Dtu> dtuList = dtuService.list(dtuQueryWrapper);
        // 根据device表查询相关dtu对应的数据和device_id
        // 1. 查询dtu设备
           // a.查询没有被删除dtu设备
        // 存储总数据表
        List<DataZhenXianJiNewDto> newDtoArrayList = new ArrayList<>();
        // 循环遍历
        int i = 1;
        if ( auth.getAuthorities().toString().equals(RoleVo.ADMIN) || auth.getAuthorities().toString().equals(RoleVo.DEV)) {
            pages = dtuService.page(dtuPage,dtuQueryWrapper);
            for (Dtu dtu : pages.getRecords()) {
                DataZhenXianJiNewDto xianJiNewDto = new DataZhenXianJiNewDto();
                xianJiNewDto.setId(i++);
                xianJiNewDto.setAddress(dtu.getAddress());
                xianJiNewDto.setIsOnline(dtu.getIsOnline());
                xianJiNewDto.setName(dtu.getDtuId());
                xianJiNewDto.setDeviceId(0);
                xianJiNewDto.setZhenXianId(0);
                // 存储子节点
                List<DataZhenXianJiNewDto> dataZhenXianJiNewDtoList = new ArrayList<>();
                // 查询 根据dtu的id去查询device表
                QueryWrapper<Device> deviceQueryWrapper = new QueryWrapper<Device>()
                        .eq("dtu_id",dtu.getId()).eq("is_delete",0);
                List<Device> deviceList = deviceService.list(deviceQueryWrapper);
                for (Device device : deviceList) {
                    if(device.getIsDelete() == 1){
                        continue;
                    }
                    DataZhenXianJiNewDto dataZhenXianJiNewDto = new DataZhenXianJiNewDto();
                    dataZhenXianJiNewDto.setName(dtu.getDtuId());
                    dataZhenXianJiNewDto.setId(i++);
                    dataZhenXianJiNewDto.setIsOnline(dtu.getIsOnline());
                    dataZhenXianJiNewDto.setDeviceId(device.getId());
                    dataZhenXianJiNewDto.setDeviceDeviceId(device.getDeviceId());
                    dataZhenXianJiNewDto.setAddress(dtu.getAddress());

                    // 查询最近一条数据
//                    DataZhenxianji dataZhenxianji = dataZhenxianjiService.selectOneMaxDataByDeviceId(String.valueOf(device.getId()));
//                    dataZhenXianJiNewDto.setZhenXianId(dataZhenxianji.getId());
                    dataZhenXianJiNewDtoList.add(dataZhenXianJiNewDto);
                }
                xianJiNewDto.setChildren(dataZhenXianJiNewDtoList);
                newDtoArrayList.add(xianJiNewDto);
            }

        }  else if (auth.getAuthorities().toString().equals(RoleVo.USER)){
            // 通过用户名查询用户Id
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<User>()
                    .eq("username",auth.getName());
            User user = userService.list(userQueryWrapper).get(0);
            // 查询设备中间表
            QueryWrapper<DeviceUser> deviceUserQueryWrapper = new QueryWrapper<DeviceUser>()
                    .eq("user_id",user.getId()).eq("is_delete",0);
            List<DeviceUser> deviceUserList = deviceUserService.list(deviceUserQueryWrapper);
            // 查询device表中所有的设备
            for (DeviceUser deviceUser : deviceUserList) {
                DataZhenXianJiNewDto xianJiNewDto = new DataZhenXianJiNewDto();
                QueryWrapper<Device> deviceQueryWrapper = new QueryWrapper<Device>()
                        .eq("id",deviceUser.getDeviceId())
                        .eq("is_delete",0).eq("is_delete",0);
                Device device = deviceService.list(deviceQueryWrapper).get(0);

                QueryWrapper<Dtu> dtuQueryWrapper1 = new QueryWrapper<Dtu>()
                        .eq("id",device.getDtuId())
                        .eq("is_delete",0);
                List<Dtu> dtuList1 = dtuService.list(dtuQueryWrapper1);
                if(dtuList1.size() == 0){
                    continue;
                }
                Dtu dt= dtuList1.get(0);
                xianJiNewDto.setId(i++);
                xianJiNewDto.setName(dt.getDtuId());
                xianJiNewDto.setIsOnline(dt.getIsOnline());
                xianJiNewDto.setAddress(dt.getAddress());
                xianJiNewDto.setDeviceId(0);
//                xianJiNewDto.setDeviceDeviceId(device.getDeviceId());
                // 查询device表
                Boolean flag = true; // 表示是否为子目录
                for (DataZhenXianJiNewDto dtoZhenXian : newDtoArrayList) {
                    // 判断是否是子目录  dtoZhenXian.getName() 相当于 dtu_id
                    if(dt.getDtuId().equals(dtoZhenXian.getName())){
                        xianJiNewDto.setDeviceId(device.getId());
                        xianJiNewDto.setDeviceDeviceId(device.getDeviceId());
                        // 查询最近一条数据
//                        DataZhenxianji dataZhenxianji = dataZhenxianjiService.selectOneMaxDataByDeviceId(String.valueOf(device.getId()));
//                        xianJiNewDto.setZhenXianId(dataZhenxianji.getId());
                        dtoZhenXian.getChildren().add(xianJiNewDto);
                        flag = false;
                        break;
                    }
                }

                if(flag){
                    List<DataZhenXianJiNewDto> dataZhenXianJiNewDtoList = new ArrayList<>();
                    DataZhenXianJiNewDto chilData = new DataZhenXianJiNewDto();
                    chilData.setId(i++);
                    chilData.setName(dt.getDtuId());
                    chilData.setIsOnline(dt.getIsOnline());
                    chilData.setAddress(dt.getAddress());
                    chilData.setDeviceDeviceId(device.getDeviceId());
                    chilData.setDeviceId(device.getId());
//                    DataZhenxianji dataZhenxianji = dataZhenxianjiService.selectOneMaxDataByDeviceId(String.valueOf(device.getId()));
//                    chilData.setZhenXianId(dataZhenxianji.getId());
                    dataZhenXianJiNewDtoList.add(chilData);
                    xianJiNewDto.setChildren(dataZhenXianJiNewDtoList);
                    newDtoArrayList.add(xianJiNewDto);
                }
            }
        }
        for (DataZhenXianJiNewDto xianJiNewDto : newDtoArrayList) {
            System.out.println("xianJiNewDto = " + xianJiNewDto);
        }
        return new JsonResult(0, "success",newDtoArrayList,pages == null?newDtoArrayList.size():pages.getTotal());


    }




    // 根据设备id查询当前所有正弦数据
    @ResponseBody
    @ApiOperation(value = "根据设备id查询当前所有正弦数据")
    @GetMapping("/data_zhenxianji/data")
    public JsonResult findDataByDeviceID(Model model,@RequestParam(defaultValue = "1", value = "page") Integer page,
                                 @RequestParam(defaultValue = "10", value = "limit") Integer limit,
                                 @RequestParam(defaultValue = "0", value = "deviceId") Integer deviceId,
                                 @RequestParam(defaultValue = "0", value = "dtuId") String dtuId,
                                         @RequestParam(defaultValue = "0", value = "deviceDeviceId") String deviceDeviceId){
        System.out.println("dtuId = " + dtuId);
        Page<DataZhenxianji> zhenxianjiPage = new Page<>(page, limit);
        QueryWrapper<DataZhenxianji> dataZhenxianjiQueryWrapper = new QueryWrapper<DataZhenxianji>()
                .eq("device_id",deviceId)
                .orderByDesc("date","time");
        Page<DataZhenxianji> pages = dataZhenxianjiService.page(zhenxianjiPage,dataZhenxianjiQueryWrapper);
        // 查询用户是否添加了数据参数
        QueryWrapper<DataZhenxianjiUnit> dataZhenxianjiUnitQueryWrapper = new QueryWrapper<DataZhenxianjiUnit>()
                .eq("zhenxian_device_id",deviceId);
        List<DataZhenxianjiUnit> dataZhenxianjiUnits = dataZhenxianjiUnitService.list(dataZhenxianjiUnitQueryWrapper);
        if (dataZhenxianjiUnits.size()!=0){
            // 该用户设置了参数 拼接
            DataZhenxianjiUnit dataZhenxianjiUnit = dataZhenxianjiUnits.get(0);
            for (DataZhenxianji dataZhenxianji : pages.getRecords()) {
                // 直接拼接单位
                dataZhenxianji.setTemperature(dataZhenxianji.getTemperature() + dataZhenxianjiUnit.getTemperature());
                dataZhenxianji.setFreqency(dataZhenxianji.getFreqency()+ dataZhenxianjiUnit.getFreqency());
                dataZhenxianji.setYingbian(dataZhenxianji.getYingbian() + dataZhenxianjiUnit.getYingbian());
                dataZhenxianji.setData3(dataZhenxianji.getData3() + dataZhenxianjiUnit.getData3());
                dataZhenxianji.setData4(dataZhenxianji.getData4() + dataZhenxianjiUnit.getData4());
                dataZhenxianji.setData5(dataZhenxianji.getData5() + dataZhenxianjiUnit.getData5());
                dataZhenxianji.setData6(dataZhenxianji.getData6() + dataZhenxianjiUnit.getData6());
                dataZhenxianji.setData7(dataZhenxianji.getData7() + dataZhenxianjiUnit.getData7());
                dataZhenxianji.setData8(dataZhenxianji.getData8() + dataZhenxianjiUnit.getData8());
                dataZhenxianji.setData9(dataZhenxianji.getData9() + dataZhenxianjiUnit.getData9());
                dataZhenxianji.setData10(dataZhenxianji.getData10() + dataZhenxianjiUnit.getData10());
                dataZhenxianji.setData11(dataZhenxianji.getData11() + dataZhenxianjiUnit.getData11());
                dataZhenxianji.setData12(dataZhenxianji.getData11() + dataZhenxianjiUnit.getData11());
                dataZhenxianji.setData13(dataZhenxianji.getData13() + dataZhenxianjiUnit.getData13());
                dataZhenxianji.setData14(dataZhenxianji.getData14() + dataZhenxianjiUnit.getData14());
                dataZhenxianji.setData15(dataZhenxianji.getData15() + dataZhenxianjiUnit.getData15());
                // 设备设备序列号
                dataZhenxianji.setDeviceId(Integer.valueOf(deviceDeviceId));
                dataZhenxianji.setDtuId(dtuId);

            }

        }

        return new JsonResult(0, "success",pages.getRecords(),pages.getTotal());
    }

}
