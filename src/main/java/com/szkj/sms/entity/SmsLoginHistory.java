package com.szkj.sms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 登录日志
 *
 * @TableName sms_login_history
 */
@TableName(value = "sms_login_history")
@Data
public class SmsLoginHistory implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 登录用户名
     */
    private String loginUsername;
    /**
     * 用户权限
     */
    private String loginUserRole;
    /**
     * 登录IP
     */
    private String loginIp;
    /**
     * 操作系统
     */
    private String loginOs;
    /**
     * 浏览器
     */
    private String loginBrowser;
    /**
     * 登录结果
     */
    private String loginResult;
    /**
     * 登录时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime loginTime;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        SmsLoginHistory other = (SmsLoginHistory) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getLoginUsername() == null ? other.getLoginUsername() == null : this.getLoginUsername().equals(other.getLoginUsername()))
                && (this.getLoginUserRole() == null ? other.getLoginUserRole() == null : this.getLoginUserRole().equals(other.getLoginUserRole()))
                && (this.getLoginIp() == null ? other.getLoginIp() == null : this.getLoginIp().equals(other.getLoginIp()))
                && (this.getLoginOs() == null ? other.getLoginOs() == null : this.getLoginOs().equals(other.getLoginOs()))
                && (this.getLoginBrowser() == null ? other.getLoginBrowser() == null : this.getLoginBrowser().equals(other.getLoginBrowser()))
                && (this.getLoginResult() == null ? other.getLoginResult() == null : this.getLoginResult().equals(other.getLoginResult()))
                && (this.getLoginTime() == null ? other.getLoginTime() == null : this.getLoginTime().equals(other.getLoginTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getLoginUsername() == null) ? 0 : getLoginUsername().hashCode());
        result = prime * result + ((getLoginUserRole() == null) ? 0 : getLoginUserRole().hashCode());
        result = prime * result + ((getLoginIp() == null) ? 0 : getLoginIp().hashCode());
        result = prime * result + ((getLoginOs() == null) ? 0 : getLoginOs().hashCode());
        result = prime * result + ((getLoginBrowser() == null) ? 0 : getLoginBrowser().hashCode());
        result = prime * result + ((getLoginResult() == null) ? 0 : getLoginResult().hashCode());
        result = prime * result + ((getLoginTime() == null) ? 0 : getLoginTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", loginUsername=").append(loginUsername);
        sb.append(", loginUserRole=").append(loginUserRole);
        sb.append(", loginIp=").append(loginIp);
        sb.append(", loginOs=").append(loginOs);
        sb.append(", loginBrowser=").append(loginBrowser);
        sb.append(", loginResult=").append(loginResult);
        sb.append(", loginTime=").append(loginTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}