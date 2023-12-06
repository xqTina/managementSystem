package com.szkj.sms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.szkj.sms.entity.SmsLoginHistory;
import com.szkj.sms.service.impl.SmsLoginHistoryServiceImpl;
import com.szkj.sms.util.JsonResult;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
*
* @author <a href="http://sixiwenwu.com"/>
* @date 2021/11/26
*/
@Controller
public class SystemController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final SmsLoginHistoryServiceImpl smsLoginHistoryService;

    @Autowired
    public SystemController(SmsLoginHistoryServiceImpl smsLoginHistoryService){
        this.smsLoginHistoryService = smsLoginHistoryService;
    }

    @ApiOperation(value = "登录日志页面路由")
    @GetMapping("/loginHistory")
    public String loginHistory() {
        return "loginHistory";
    }

    @ApiOperation(value = "获取登录日志表格数据")
    @ResponseBody
    @GetMapping("/loginHistory/table")
    public JsonResult loginHistoryTable(@RequestParam(defaultValue = "1", value = "page") Integer page,
                                        @RequestParam(defaultValue = "30", value = "limit") Integer limit) {

        return new JsonResult(
                0,
                "success",
                smsLoginHistoryService.page(new Page<>(page, limit), new QueryWrapper<SmsLoginHistory>().orderByDesc("id")).getRecords(),
                smsLoginHistoryService.count());
    }

    @ApiOperation(value = "删除登录日志")
    @ResponseBody
    @DeleteMapping("/loginHistory/{id}")
    public JsonResult loginHistoryTable(@PathVariable(value = "id") Integer id) {
        if (smsLoginHistoryService.removeById(id)){
            return JsonResult.success();
        }
        return JsonResult.error("删除失败");
    }

}
