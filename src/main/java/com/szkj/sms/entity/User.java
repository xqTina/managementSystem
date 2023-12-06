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
 * @TableName user
 */
@TableName(value = "user")
@Data
public class User implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String pwdhash;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 姓名
     */
    private String name;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 用户规则
     */
    private String role;
    /**
     * 用户有效期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expireDate;
    /**
     * 用户注册时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registerTime;
    /**
     * 用户备注
     */
    private String remark;
    /**
     * 用户启用/停用
     */
    private Integer isUse;
    /**
     * 逻辑删除
     */
    private Integer isDelete;

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
        User other = (User) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
                && (this.getPwdhash() == null ? other.getPwdhash() == null : this.getPwdhash().equals(other.getPwdhash()))
                && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
                && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
                && (this.getPhone() == null ? other.getPhone() == null : this.getPhone().equals(other.getPhone()))
                && (this.getRole() == null ? other.getRole() == null : this.getRole().equals(other.getRole()))
                && (this.getExpireDate() == null ? other.getExpireDate() == null : this.getExpireDate().equals(other.getExpireDate()))
                && (this.getRegisterTime() == null ? other.getRegisterTime() == null : this.getRegisterTime().equals(other.getRegisterTime()))
                && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
                && (this.getIsUse() == null ? other.getIsUse() == null : this.getIsUse().equals(other.getIsUse()))
                && (this.getIsDelete() == null ? other.getIsDelete() == null : this.getIsDelete().equals(other.getIsDelete()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getPwdhash() == null) ? 0 : getPwdhash().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getPhone() == null) ? 0 : getPhone().hashCode());
        result = prime * result + ((getRole() == null) ? 0 : getRole().hashCode());
        result = prime * result + ((getExpireDate() == null) ? 0 : getExpireDate().hashCode());
        result = prime * result + ((getRegisterTime() == null) ? 0 : getRegisterTime().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getIsUse() == null) ? 0 : getIsUse().hashCode());
        result = prime * result + ((getIsDelete() == null) ? 0 : getIsDelete().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", username=").append(username);
        sb.append(", pwdhash=").append(pwdhash);
        sb.append(", email=").append(email);
        sb.append(", name=").append(name);
        sb.append(", phone=").append(phone);
        sb.append(", role=").append(role);
        sb.append(", expireDate=").append(expireDate);
        sb.append(", registerTime=").append(registerTime);
        sb.append(", remark=").append(remark);
        sb.append(", isUse=").append(isUse);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}