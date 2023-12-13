package com.szkj.sms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.szkj.sms.entity.User;
import com.szkj.sms.service.impl.UserServiceImpl;
import com.szkj.sms.util.JsonResult;
import com.szkj.sms.vo.RoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;

/**
 * @Author: <a href="http://sixiwenwu.com"/>
 * @Date: 2021/9/23 15:29
 */
@Controller
@Api(tags = "用户操作接口")
@RequestMapping("/user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserServiceImpl userService;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "用户管理页面路由")
    @GetMapping("/show")
    public String user() {
        return "user";
    }

    @ApiOperation(value = "注册用户", notes = "获取注册用户的数据")
    @ResponseBody
    @PostMapping("/add")
    public JsonResult addUser(@RequestBody User user) {
//        用户名 邮箱 电话号码均不重复
        QueryWrapper<User> search = new QueryWrapper<User>();
        search.like("email",user.getEmail()).or()
                .like("phone",user.getPhone()).or()
                .like("username",user.getUsername());
        int count = userService.count(search);
        if (count!=0){
            return new JsonResult(-1, "该用户已经注册");
        }
        user.setRole("USER");
        if (!userService.save(user)) {
            return new JsonResult(-1, "添加失败，请稍后重试");
        }
        return new JsonResult(0, "添加成功");
    }

    @ApiOperation(value = "用户编辑与添加页面路由")
    @GetMapping("/edit/user")
    public String editOrAddDevice(Model model, @RequestParam(defaultValue = "0", value = "uid") Integer uid) {
        if (uid > 0) {
            model.addAttribute("user", userService.getById(uid));
        }
        return "userEdit";
    }

    @ApiOperation(value = "用户表格数据", notes = "获取用户表格数据")
    @ResponseBody
    @GetMapping("/table")
    public JsonResult userTable(@RequestParam(defaultValue = "1", value = "page") Integer page,
                                @RequestParam(defaultValue = "30", value = "limit") Integer limit,
                                @RequestParam(defaultValue = "", value = "key") String key) {
        QueryWrapper<User> search = new QueryWrapper<User>()
                .like("username", key).or()
                .like("email", key).or()
                .like("name", key).or()
                .like("phone", key);
        return new JsonResult(
                0,
                "success",
                userService.page(new Page<>(page, limit), search).getRecords(),
                userService.count(search));
    }

    @ApiOperation(value = "获取普通用户列表")
    @ResponseBody
    @GetMapping("/userList")
    public JsonResult userList() {
        return new JsonResult(
                0,
                "success",
                userService.list(new QueryWrapper<User>().eq("role", "USER")));
    }

    @ApiOperation(value = "启用/停用用户")
    @ResponseBody
    @GetMapping("/switchUse")
    public JsonResult switchUserUse(@RequestParam(defaultValue = "0", value = "uid") Integer uid,
                                    @RequestParam(value = "status") Boolean status) {
        if (uid <= 0 || status == null) {
            return new JsonResult(-1, "参数错误");
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // 判断执行操作的用户
        if (auth.getAuthorities().toString().equals(RoleVo.USER)) {
            return new JsonResult(-1, "权限不足无法修改");
        }
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", uid)
                .eq("role", "USER")
                .set("is_use", status ? 1 : 0);
        if (!userService.update(updateWrapper)) {
            return new JsonResult(-1, "修改失败，请稍后重试");
        }
        return new JsonResult(0, status ? "该账户已启用" : "该账户已停用");
    }


    @ApiOperation(value = "设置用户过期时间")
    @ResponseBody
    @GetMapping("/updateEDate")
    public JsonResult updateUserExpireTime(@RequestParam(defaultValue = "0", value = "uid") Integer uid,
                                           @RequestParam(value = "time") String time) {
        if (uid <= 0) {
            return new JsonResult(-1, "参数错误");
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // 判断执行操作的用户
        if (auth.getAuthorities().toString().equals(RoleVo.USER)) {
            return new JsonResult(-1, "权限不足无法修改");
        }
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", uid)
                .eq("role", "USER");
        if (StringUtils.isBlank(time)) {
            updateWrapper.set("expire_date", null);
        } else {
            updateWrapper.set("expire_date", time);
        }
        if (!userService.update(updateWrapper)) {
            return new JsonResult(-1, "修改失败，请稍后重试");
        }
        return new JsonResult(0, "设置成功");
    }

    @ApiOperation(value = "获取用户设备dtu编号")
    @ResponseBody
    @GetMapping("/device_dtu")
    public JsonResult getUserDeviceDtu() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().toString().equals(RoleVo.USER)) {
            // 用户，返回用户已有的设备的dtu
            return new JsonResult(
                    0,
                    "success",
                    userService.getUserDeviceDtu(auth.getName()),
                    userService.getUserDeviceDtuCount(auth.getName())
            );
        } else if (auth.getAuthorities().toString().equals(RoleVo.ADMIN)){
            //管理员返回全部
            return new JsonResult(
                    0,
                    "success",
                    userService.getUserDeviceDtu(),
                    userService.getUserDeviceDtuCount()
            );
        }
        // 匿名用户，未登录
        return new JsonResult(-1, "anonymous");
    }


    @ApiOperation(value = "修改密码页面路由")
    @GetMapping("/password")
    public String password() {
        return "password";
    }


}
