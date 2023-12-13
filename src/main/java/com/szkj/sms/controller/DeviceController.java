package com.szkj.sms.controller;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.szkj.sms.dto.DeviceDto;
import com.szkj.sms.entity.Device;
import com.szkj.sms.entity.DeviceUser;
import com.szkj.sms.entity.Dtu;
import com.szkj.sms.entity.User;
import com.szkj.sms.service.impl.*;
import com.szkj.sms.util.JsonResult;
import com.szkj.sms.vo.RoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @Author: <a href="http://sixiwenwu.com"/>
 * @Date: 2021/9/23 18:33
 */
@Controller
@Api(tags = "设备操作接口")
@RequestMapping("/device")
public class DeviceController {
    private final MyDeviceServiceImpl myDeviceService;
    private final DeviceUserServiceImpl deviceUserService;
    private final UserServiceImpl userService;
    private final DtuServiceImpl dtuService;
    private final DeviceServiceImpl deviceService;

    @Autowired
    public DeviceController(MyDeviceServiceImpl myDeviceService, DeviceUserServiceImpl deviceUserService, UserServiceImpl userService, DtuServiceImpl dtuService, DeviceServiceImpl deviceService) {
        this.myDeviceService = myDeviceService;
        this.deviceUserService = deviceUserService;
        this.userService = userService;
        this.dtuService = dtuService;
        this.deviceService = deviceService;
    }

    @ApiOperation(value = "设备管理页面路由")
    @GetMapping("/show")
    public String device(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().toString().equals(RoleVo.ADMIN) || auth.getAuthorities().toString().equals(RoleVo.DEV)) {
            // 管理员前往设备管理页面
//            return "device";
            QueryWrapper<Device> searchDevice = new QueryWrapper<Device>()
                    .select("distinct remark");
            model.addAttribute("deviceTypeList", deviceService.list(searchDevice));
            return "device";
        }
        // 普通用户前往设备查看页面
        return "userDevice";
    }

    @ResponseBody
    @GetMapping("/table")
    public JsonResult deviceTable(@RequestParam(defaultValue = "1", value = "page") Integer page,
                                  @RequestParam(defaultValue = "10", value = "limit") Integer limit,
                                  @RequestParam(defaultValue = "", value = "key") String key,
                                  @RequestParam(defaultValue = "", value = "deviceType") String deviceType) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().toString().equals(RoleVo.USER)) {
            // 用户，返回用户可查看的
            return new JsonResult(
                    0,
                    "success",
                    myDeviceService.getDevicePage(auth.getName(), page, limit, "", key),
                    myDeviceService.userDeviceCount(auth.getName(), key)
            );
        } else if (auth.getAuthorities().toString().equals(RoleVo.ADMIN) || auth.getAuthorities().toString().equals(RoleVo.DEV)) {
            // 管理员，返回全部
            return new JsonResult(
                    0,
                    "success",
                    myDeviceService.getDevicePage(page, limit, "", key,deviceType),
                    myDeviceService.deviceCount(key));
        }
        // 匿名用户，未登录
        return new JsonResult(-1, "anonymous");
    }


    @ResponseBody
    @GetMapping("/user/bind")
    public JsonResult userBindDevice(@RequestParam(defaultValue = "", value = "dtuId") String dtuId,
                                     @RequestParam(defaultValue = "", value = "deviceId") String deviceId) {
        // 参数基本校验
        if (StringUtils.isAnyBlank(dtuId, deviceId)) {
            return new JsonResult(-1, "序列号不能为空");
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // 操作用户权限检验
        if (auth.getAuthorities().toString().equals(RoleVo.USER)) {
            // 是用户提交的绑定，进行绑定
            // 设备是否存在
            Integer bindDeviceId = myDeviceService.getDeviceId(dtuId, deviceId);
            if (bindDeviceId == null) {
                return new JsonResult(-1, "未找到相关设备");
            }
            // 绑定对象
            DeviceUser add = new DeviceUser();
            // 用户id通过UserService现查
            add.setUserId(userService.getOne(new QueryWrapper<User>().eq("username", auth.getName())).getId());
            add.setDeviceId(bindDeviceId);
            // 绑定
            if (!deviceUserService.saveOrUpdate(add)) {
                return new JsonResult(-1, "绑定失败");
            }
            return new JsonResult(0, "绑定成功");
        }
        return new JsonResult(-1, "仅普通用户可绑定设备");
    }

    @ResponseBody
    @PostMapping("/user/bind")
    public JsonResult adminBindDeviceUser(@RequestBody DeviceUser bind) {
        // 参数基本校验
        if (userService.getById(bind.getUserId()) == null || deviceService.getById(bind.getDeviceId()) == null) {
            return new JsonResult(-1, "用户或设备不存在");
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // 操作用户权限检验
        if (auth.getAuthorities().toString().equals(RoleVo.ADMIN) || auth.getAuthorities().toString().equals(RoleVo.DEV)) {
            // 是管理员提交的绑定，进行绑定
            // 初步绑定校验
            if (deviceUserService.count(new QueryWrapper<DeviceUser>().eq("user_id", bind.getUserId()).eq("device_id", bind.getDeviceId())) > 0) {
                return new JsonResult(-1, "该设备已绑定该账号，请勿重复绑定");
            }
            // 绑定
            if (!deviceUserService.saveOrUpdate(bind)) {
                return new JsonResult(-1, "绑定失败");
            }
            return new JsonResult(0, "绑定成功");
        }
        return new JsonResult(-1, "权限不足无法绑定");
    }

    @ResponseBody
    @GetMapping("/user/removeBind")
    public JsonResult userRemoveBindDevice(@RequestParam(defaultValue = "", value = "list") String list) {
        if (StringUtils.isBlank(list)) {
            return new JsonResult(-1, "未选择任何要解除绑定的设备");
        }
        JSONArray idList = JSONArray.parseArray(list);
        String removeError = "";
        // 循环解除绑定
        for (Object id : idList) {
            if (!deviceUserService.update(new UpdateWrapper<DeviceUser>().eq("id", id).set("is_delete", 1))) {
                removeError += id + " ";
            }
        }
        return new JsonResult(0, "操作完成" + ("".equals(removeError) ? "" : ",ID为 " + removeError + " 的绑定记录解除失败,请留意"));

    }

    @GetMapping("/edit/show")
    public String editOrAddDevice(Model model, @RequestParam(defaultValue = "0", value = "did") Integer did) {
        if (did > 0) {
            model.addAttribute("device", myDeviceService.getDevice(did));
            model.addAttribute("bindUser", myDeviceService.getUser(did));
        }
        return "deviceEdit";
    }

    @ResponseBody
    @PostMapping("/edit")
    public JsonResult editOrAddDevice(@RequestBody DeviceDto device) {
        if (device == null) {
            return new JsonResult(-1, "参数错误");
        }
        // DTU校验
        Dtu dtu = dtuService.getOne(new QueryWrapper<Dtu>().eq("dtu_id", device.getDtuDtuId()).eq("is_delete",0));
        if (dtu == null) {
            return new JsonResult(-1, "DTU不存在");
        }
        if(Integer.parseInt(device.getDeviceId())<0||Integer.parseInt(device.getDeviceId())>255){
            return new JsonResult(-1, "设备序列号需在0-255之间");
        }
        //查询同一个网关下是否有同一个设备序列号
        QueryWrapper<Device> wrapper = new QueryWrapper<>();
        wrapper.eq("dtu_id",dtu.getId()).eq("device_id",device.getDeviceId());
        Device one = deviceService.getOne(wrapper);
        if (one!=null){
            return new JsonResult(-1, "该DTU下已有该序列号的设备");
        }
        // 构建device
        Device add = new Device();
        if (device.getId() != null) {
            add = deviceService.getById(device.getId());
        }
        add.setDtuId(dtu.getId());
        add.setDeviceId(device.getDeviceId());
        //修改为默认值
//        add.setNumberOfChannels(device.getNumberOfChannels());
//        add.setDeviceType(device.getDeviceType() == null ? "" : device.getDeviceType());
        add.setNumberOfChannels(3);
        add.setDeviceType("zhenxianji");

        add.setAddTime(LocalDateTime.now());
        add.setRemark(device.getRemark());
        add.setMaxAlarmValue1(device.getMaxAlarmValue1());
        add.setMinAlarmValue1(device.getMinAlarmValue1());
        add.setMaxAlarmValue2(device.getMaxAlarmValue2());
        add.setMinAlarmValue2(device.getMinAlarmValue2());
        add.setMaxAlarmValue3(device.getMaxAlarmValue3());
        add.setMinAlarmValue3(device.getMinAlarmValue3());
        if (!deviceService.saveOrUpdate(add)) {
            return new JsonResult(-1, "保存失败");
        }
        return new JsonResult(0, "操作成功");
    }

    @ResponseBody
    @GetMapping("/delete")
    public JsonResult deleteDevice(@RequestParam(defaultValue = "", value = "list") String list) {
        if (StringUtils.isBlank(list)) {
            return new JsonResult(-1, "未选择任何要删除的设备");
        }
        JSONArray idList = JSONArray.parseArray(list);
        String cantDel = "";
        // 删除可行性判断
        for (Object id : idList) {
            if (deviceUserService.count(new QueryWrapper<DeviceUser>().eq("device_id", id)) > 0) {
                cantDel += id + " ";
            }
        }
        if (!"".equals(cantDel)) {
            return new JsonResult(-1, "删除失败,ID为 " + cantDel + " 的采集设备下存在绑定的用户,请解除绑定后再删除采集设备");
        }
        String deleteError = "";
        // 循环解除绑定
        for (Object id : idList) {
            if (!deviceService.update(new UpdateWrapper<Device>().eq("id", id).set("is_delete", 1))) {
                deleteError += id + " ";
            }
        }
        return new JsonResult(0, "操作完成" + ("".equals(deleteError) ? "" : ",ID为 " + deleteError + " 的采集设备删除失败,请留意"));

    }
}
