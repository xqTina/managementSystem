package com.szkj.sms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.szkj.sms.dto.DataZhenXianJiNewDto;
import com.szkj.sms.entity.DataZhenxianji;
import com.szkj.sms.entity.Device;
import com.szkj.sms.entity.Dtu;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

/**
 * @Author: <a href="http://sixiwenwu.com"/>
 * @Date: 2021/9/23 19:33
 */
@Controller
@Api(tags = "数据表格接口")
@RequestMapping("/datatable")
public class DatatableController {

    private final MyDataServiceImpl myDataService;
    private final MyDeviceServiceImpl myDeviceService;
    private final DeviceServiceImpl deviceService;
    private final DataZhenxianjiServiceImpl dataZhenxianjiService;
    private final DtuServiceImpl dtuService;

    @Autowired
    public DatatableController(MyDataServiceImpl myDataService, MyDeviceServiceImpl myDeviceService,DeviceServiceImpl deviceService,DataZhenxianjiServiceImpl dataZhenxianjiService,DtuServiceImpl dtuService) {
        this.myDataService = myDataService;
        this.myDeviceService = myDeviceService;
        this.deviceService = deviceService;
        this.dataZhenxianjiService = dataZhenxianjiService;
        this.dtuService = dtuService;
    }

    @ApiOperation(value = "数据表格页面路由")
    @GetMapping("/show")
    public String datatable(Model model) {
        // map存放各个表格是否显示的值
        Map<String, Boolean> isShow = new HashMap<>(3);
        isShow.put("all", true);
        isShow.put("liefengji", true);
        isShow.put("qingxieji", true);
        isShow.put("zhenxianji", true);
        // 数量，最后判断显示的表格数量，若为0则用户绑定了设备但是绑定设备已不存在或不是表格规定的设备类型
        int showNum = 3;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().toString().equals(RoleVo.USER)) {
            // 当前认证的为用户,判断用户有没有该类型的设备,没有前台则不会显示
            if (myDeviceService.userDeviceCount(auth.getName()) == 0) {
                // 用户未绑定任何设备
                isShow.put("all", false);
            }
            if (myDeviceService.userDeviceCountByDeviceType(auth.getName(), "liefengji") == 0) {
                isShow.put("liefengji", false);
                showNum -= 1;
            }
            if (myDeviceService.userDeviceCountByDeviceType(auth.getName(), "qingxieji") == 0) {
                isShow.put("qingxieji", false);
                showNum -= 1;
            }
            if (myDeviceService.userDeviceCountByDeviceType(auth.getName(), "zhenxianji") == 0) {
                isShow.put("zhenxianji", false);
                showNum -= 1;
            }
        }
        // 为0则用户绑定了设备但是绑定设备已不存在或不是表格规定的设备类型,依然没有查询到任何数据
        if (showNum == 0) {
            isShow.put("all", false);
        }
        model.addAttribute("isShow", isShow);
        return "datatable";
    }

    @ResponseBody
    @GetMapping("/data_liefengji/table")
    public JsonResult dataLieFengJiTable(@RequestParam(defaultValue = "1", value = "page") Integer page,
                                         @RequestParam(defaultValue = "10", value = "limit") Integer limit) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().toString().equals(RoleVo.USER)) {
            // 用户，返回用户可查看的
            return new JsonResult(
                    0,
                    "success",
                    myDataService.getDataLieFengJiPage(auth.getName(), page, limit, "desc"),
                    myDataService.getDataLieFengJiCount(auth.getName()));
        } else if (auth.getAuthorities().toString().equals(RoleVo.ADMIN) || auth.getAuthorities().toString().equals(RoleVo.DEV)) {
            // 管理员，返回全部
            return new JsonResult(
                    0,
                    "success",
                    myDataService.getDataLieFengJiPage(page, limit, "desc"),
                    myDataService.getDataLieFengJiCount());
        }
        // 匿名用户，未登录
        return new JsonResult(-1, "anonymous");
    }

    @ResponseBody
    @GetMapping("/data_qingxieji/table")
    public JsonResult dataQingXieJiTable(@RequestParam(defaultValue = "1", value = "page") Integer page,
                                         @RequestParam(defaultValue = "10", value = "limit") Integer limit) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().toString().equals(RoleVo.USER)) {
            // 用户，返回用户可查看的
            return new JsonResult(
                    0,
                    "success",
                    myDataService.getDataQingXieJiPage(auth.getName(), page, limit, "desc"),
                    myDataService.getDataQingXieJiCount(auth.getName()));
        } else if (auth.getAuthorities().toString().equals(RoleVo.ADMIN) || auth.getAuthorities().toString().equals(RoleVo.DEV)) {
            // 管理员，返回全部
            return new JsonResult(
                    0,
                    "success",
                    myDataService.getDataQingXieJiPage(page, limit, "desc"),
                    myDataService.getDataQingXieJiCount());
        }
        // 匿名用户，未登录
        return new JsonResult(-1, "anonymous");
    }

    @ResponseBody
    @GetMapping("/data_zhenxianji/table")
    public JsonResult dataZhenXianJiTable(@RequestParam(defaultValue = "1", value = "page") Integer page,
                                          @RequestParam(defaultValue = "10", value = "limit") Integer limit) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().toString().equals(RoleVo.USER)) {
            // 用户，返回用户可查看的
            return new JsonResult(
                    0,
                    "success",
                    myDataService.getDataZhenXianJiPage(auth.getName(), page, limit, "desc"),
                    myDataService.getDataZhenXianJiCount(auth.getName()));
        } else if (auth.getAuthorities().toString().equals(RoleVo.ADMIN) || auth.getAuthorities().toString().equals(RoleVo.DEV)) {
            // 管理员，返回全部
            return new JsonResult(
                    0,
                    "success",
                    myDataService.getDataZhenXianJiPage(page, limit, "desc"),
                    myDataService.getDataZhenXianJiCount());
        }
        // 匿名用户，未登录
        return new JsonResult(-1, "anonymous");
    }


    @ResponseBody
    @GetMapping("/data_zhenxianji/export")
    public JsonResult dataZhenXianJiExport(@RequestParam(value = "begin") String begin,
                                          @RequestParam(value = "end") String end,
                                           @RequestParam(value = "dtuId",defaultValue = "0") int dtuId,
                                           @RequestParam(value = "number",defaultValue = "0") int number) {
        if (begin.equals("")||end.equals("")||dtuId==0||number==0){
            return new JsonResult(-1,"请填写完整");
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().toString().equals(RoleVo.USER)) {
            // 用户，返回用户可查看的
            return new JsonResult(
                    0,
                    "success",
                    myDataService.getDataZhenXianJiExport(auth.getName(), begin, end,dtuId,number, "desc"),
                    myDataService.getDataZhenXianJiCount(auth.getName()));
        } else if (auth.getAuthorities().toString().equals(RoleVo.ADMIN) || auth.getAuthorities().toString().equals(RoleVo.DEV)) {
            // 管理员，返回全部
            return new JsonResult(
                    0,
                    "success",
                    myDataService.getDataZhenXianJiExport(begin,end,dtuId,number, "desc"),
                    myDataService.getDataZhenXianJiCount());
        }
        // 匿名用户，未登录
        return new JsonResult(-1, "anonymous");
    }

}
