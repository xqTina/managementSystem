package com.szkj.sms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.szkj.sms.entity.*;
import com.szkj.sms.service.DataZhenxianjiUnitService;
import com.szkj.sms.service.impl.*;
import com.szkj.sms.util.JsonResult;
import com.szkj.sms.vo.RoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * @Author: <a href="http://sixiwenwu.com"/>
 * @Date: 2021/9/25 12:14
 */
@Controller
@Api(tags = "数据图表接口")
@RequestMapping("/chart")
public class ChartController {

    private final MyDataServiceImpl myDataService;
    private final MyDeviceServiceImpl myDeviceService;
    private final DtuServiceImpl dtuService;
    private final DeviceServiceImpl deviceService;
    private final DataZhenxianjiUnitService dataZhenxianjiUnitService;
    private final DataLiefengjiServiceImpl dataLiefengjiService;
    private final DataQingxiejiServiceImpl dataQingxiejiService;
    private final DataZhenxianjiServiceImpl dataZhenxianjiService;

    @Autowired
    public ChartController(MyDataServiceImpl myDataService, MyDeviceServiceImpl myDeviceService, DtuServiceImpl dtuService, DeviceServiceImpl deviceService, DataZhenxianjiServiceImpl dataZhenxianjiService, DataLiefengjiServiceImpl dataLiefengjiService, DataQingxiejiServiceImpl dataQingxiejiService,DataZhenxianjiUnitService dataZhenxianjiUnitService) {
        this.myDataService = myDataService;
        this.myDeviceService = myDeviceService;
        this.dtuService = dtuService;
        this.deviceService = deviceService;
        this.dataLiefengjiService = dataLiefengjiService;
        this.dataQingxiejiService = dataQingxiejiService;
        this.dataZhenxianjiService = dataZhenxianjiService;
        this.dataZhenxianjiUnitService = dataZhenxianjiUnitService;
    }

    @ApiOperation(value = "数据图表页面路由", notes = "前往图表查看页面，通过Model携带用户可查看的DTU列表")
    @GetMapping("/show")
    public String chart(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().toString().equals(RoleVo.USER)) {
            model.addAttribute("dtuList", myDeviceService.getUserDtu(auth.getName()));
        } else {
            model.addAttribute("dtuList", dtuService.list());
        }
        return "chart";
    }

    @ApiOperation(value = "获取用户可查看设备列表", notes = "根据选择的DTU获取该用户可查看的设备列表")
    @ResponseBody
    @GetMapping("/getUserDevice")
    public JsonResult getUserDevice(Integer dtuId) {
        if (dtuId <= 0) {
            return new JsonResult(-1, "参数错误");
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().toString().equals(RoleVo.USER)) {
            return new JsonResult(0, "success", myDeviceService.getUserDtuDevice(auth.getName(), dtuId));
        } else {
            return new JsonResult(0, "success", deviceService.list(new QueryWrapper<Device>().eq("dtu_id", dtuId)));
        }
    }

    @ApiOperation(value = "获取用户可查看的通道数量", notes = "根据选择的DTU和Device获取该用户查看的设备的通道数量")
    @ResponseBody
    @GetMapping("getUDNOC")
    public JsonResult getUserDeviceNumberOfChannels(Integer dtuId, Integer deviceId) {
        if (dtuId <= 0 || deviceId <= 0) {
            return new JsonResult(-1, "参数错误");
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().toString().equals(RoleVo.USER)) {
            return new JsonResult(0, "success", myDeviceService.getUserDtuDeviceNumberOfChannels(auth.getName(), dtuId, deviceId));
        } else {
            Device device = deviceService.getOne(new QueryWrapper<Device>().eq("dtu_id", dtuId).eq("id", deviceId));
            return new JsonResult(0, "success", device.getNumberOfChannels());
        }
    }

    @ApiOperation(value = "获取图表数据", notes = "根据选择获取数据返回前台生成图表")
    @ResponseBody
    @GetMapping("/getData")
    public JsonResult getData(String date, Integer deviceId) {
        //传过来的deviceId 为主键
        //, Integer noc  || noc < 0
        if (date.length() != 10 || deviceId <= 0 ) {
            return new JsonResult(-1, "参数错误");
        }
        // 先查询设备类型 传入设备编号查询
        Device device = deviceService.getById(deviceId);
        if (device == null) {
            return new JsonResult(-1, "设备不存在");
        }
        // 查询设备的单位
        DataZhenxianjiUnit zhenxianDeviceUnit = dataZhenxianjiUnitService.getOne(new QueryWrapper<DataZhenxianjiUnit>().eq("zhenxian_device_id", device.getDeviceId()));
        //存数据
        HashMap<String, Object> dataMap = new HashMap<>();
        //存单位
        dataMap.put("unit",zhenxianDeviceUnit);
        // 根据类型不同返回不同的数据
        switch (device.getDeviceType()) {
            case "liefengji":
                dataMap.put("data",dataLiefengjiService.list(new QueryWrapper<DataLiefengji>().eq("device_id", device.getDeviceId()).eq("date", date)));
                return new JsonResult(0, "liefengji", dataMap);
            case "qingxieji":
                dataMap.put("data",dataQingxiejiService.list(new QueryWrapper<DataQingxieji>().eq("device_id", device.getDeviceId()).eq("date", date)));
                return new JsonResult(0, "qingxieji", dataMap);
            case "zhenxianji":
                dataMap.put("data",dataZhenxianjiService.list(new QueryWrapper<DataZhenxianji>().eq("device_id", device.getDeviceId()).eq("date", date)));
                return new JsonResult(0, "zhenxianji", dataMap);
            default:
                return new JsonResult(-1, "此设备未定义设备类型，无法获取数据，请在设备管理中定义设备类型");
        }
    }
}
