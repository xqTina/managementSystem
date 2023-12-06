package com.szkj.sms.controller;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.szkj.sms.entity.Device;
import com.szkj.sms.entity.Dtu;
import com.szkj.sms.service.impl.DeviceServiceImpl;
import com.szkj.sms.service.impl.DtuServiceImpl;
import com.szkj.sms.util.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @author sixiwenwu
 */
@Controller
@Api(tags = "DTU操作接口")
@RequestMapping("dtu")
public class DTUController {

    private final DtuServiceImpl dtuService;
    private final DeviceServiceImpl deviceService;

    @Autowired
    public DTUController(DtuServiceImpl dtuService, DeviceServiceImpl deviceService) {
        this.dtuService = dtuService;
        this.deviceService = deviceService;
    }

    @ApiOperation(value = "DTU管理页面路由")
    @GetMapping("/show")
    public String dtu() {
        return "dtu";
    }

    @ResponseBody
    @GetMapping("/table")
    public JsonResult dtuTable(@RequestParam(defaultValue = "1", value = "page") Integer page,
                               @RequestParam(defaultValue = "10", value = "limit") Integer limit,
                               @RequestParam(defaultValue = "", value = "key") String key) {
        QueryWrapper<Dtu> search = new QueryWrapper<Dtu>()
                .like("dtu_id", key).or()
                .like("remark", key);
        return new JsonResult(
                0,
                "success",
                dtuService.page(new Page<>(page, limit), search).getRecords(),
                dtuService.count(search));
    }

    @ResponseBody
    @GetMapping("/saveOrUpdate")
    public JsonResult saveOrUpdateDtu(@RequestParam(defaultValue = "0", value = "id") Integer id,
                                      @RequestParam(value = "dtuId") String dtuId,
                                      @RequestParam(defaultValue = "", value = "remark") String remark,
                                      @RequestParam(defaultValue = "", value = "address") String address) {
        // 基本参数判断,id和dtuId不能同时为空
        if (id == null || id <= 0 && StringUtils.isBlank(dtuId)) {
            return new JsonResult(-1, "参数错误");
        }
        //区分是添加还是更新
        Boolean isAdd = true;
        // 定义dtu
        Dtu dtu = new Dtu();
        if (id > 0) {
            // dtu存在,更新为数据库中的dtu
            isAdd = false;
            dtu = dtuService.getById(id);
        }
        if (isAdd){
            QueryWrapper<Dtu> dtuQueryWrapper = new QueryWrapper<>();
            dtuQueryWrapper.like("dtu_id",dtuId);
            Dtu one = dtuService.getOne(dtuQueryWrapper);
            if (one!=null){
                return new JsonResult(-1, "请勿添加重复的设备编号");
            }
        }
        // 更新/添加信息
        dtu.setDtuId(dtuId);
        dtu.setAddTime(LocalDateTime.now());
        dtu.setRemark(remark);
        dtu.setAddress(address);
        // 写入数据库
        if (!dtuService.saveOrUpdate(dtu)) {
            return new JsonResult(-1, "操作失败");
        }
        return new JsonResult(0, "操作成功");
    }

    @ResponseBody
    @GetMapping("/delete")
    public JsonResult deleteDtu(@RequestParam(value = "list") String list) {
        JSONArray idList = JSONArray.parseArray(list);
        String cantDel = "";
        String delError = "";
        // 删除可行性判断
        for (Object id : idList) {
            if (deviceService.count(new QueryWrapper<Device>().eq("dtu_id", id)) > 0) {
                cantDel += id + " ";
            }
        }
        if (!"".equals(cantDel)) {
            return new JsonResult(-1, "编号为 " + cantDel + " 的DTU下存在绑定的采集设备,请删除采集设备后再删除DTU设备");
        }
        // 删除设备
        for (Object id : idList) {
            if (!dtuService.update(new UpdateWrapper<Dtu>().eq("id", id).set("is_delete", 1))) {
                delError += id + " ";
            }
        }
        return new JsonResult(0, "删除操作已完成 " + ("".equals(delError) ? "" : ",编号为 " + delError + " 的DTU删除失败,请留意"));
    }

    @ResponseBody
    @GetMapping("/select/{id}")
    public JsonResult selectDtu(@PathVariable("id") String id) {
        Dtu dtu = dtuService.getById(id);
        return new JsonResult(
            0,
            "success",
            dtu);
    }
}
