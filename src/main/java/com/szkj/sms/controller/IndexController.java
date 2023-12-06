package com.szkj.sms.controller;

import com.szkj.sms.config.SystemConfig;
import com.szkj.sms.service.impl.DeviceServiceImpl;
import com.szkj.sms.service.impl.DtuServiceImpl;
import com.szkj.sms.service.impl.MyDeviceServiceImpl;
import com.szkj.sms.util.JsonResult;
import com.szkj.sms.vo.RoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sixiwenwu
 */
@Api(tags = "首页控制器")
@Controller
public class IndexController {


    private final MyDeviceServiceImpl myDeviceService;
    private final DtuServiceImpl dtuService;
    private final DeviceServiceImpl deviceService;

    @Autowired
    public IndexController(MyDeviceServiceImpl myDeviceService, DtuServiceImpl dtuService, DeviceServiceImpl deviceService) {
        this.myDeviceService = myDeviceService;
        this.dtuService = dtuService;
        this.deviceService = deviceService;
    }

    @ApiOperation(value = "系统首页页面路由")
    @GetMapping({"/index", "/"})
    public String index() {
        return "index";
    }

    @ApiOperation(value = "系统欢迎页页面路由")
    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

    @ApiOperation(value = "获取欢迎页显示的系统信息")
    @ResponseBody
    @GetMapping("/welcomeInfo")
    public JsonResult getWelcomeInfo() {
        Map<String, Object> infoMap = new HashMap<>();
        infoMap.put("systemName", SystemConfig.systemName);
        infoMap.put("systemVersion", SystemConfig.version);
        try {
            infoMap.put("systemIp", InetAddress.getLocalHost().getHostAddress().toString());
            infoMap.put("osName", System.getProperties().getProperty("os.name"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.getAuthorities().toString().equals(RoleVo.USER)) {
            infoMap.put("dtuNum", dtuService.count());
            infoMap.put("deviceNum", deviceService.count());
        } else {
            infoMap.put("deviceNum", myDeviceService.userDeviceCount(auth.getName()));
        }
        return new JsonResult(0, "success", infoMap);
    }

}
