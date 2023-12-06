package com.szkj.sms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 系统参数
 * @TableName sys_settings
 */
@TableName(value ="sys_settings")
@Data
public class SysSettings implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private String emailHost;

    /**
     * 
     */
    private Integer emailPort;

    /**
     * 
     */
    private String emailUsername;

    /**
     * 
     */
    private String emailPassword;

    /**
     * 
     */
    private String emailNickname;

    /**
     * 
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

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
        SysSettings other = (SysSettings) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getEmailHost() == null ? other.getEmailHost() == null : this.getEmailHost().equals(other.getEmailHost()))
            && (this.getEmailPort() == null ? other.getEmailPort() == null : this.getEmailPort().equals(other.getEmailPort()))
            && (this.getEmailUsername() == null ? other.getEmailUsername() == null : this.getEmailUsername().equals(other.getEmailUsername()))
            && (this.getEmailPassword() == null ? other.getEmailPassword() == null : this.getEmailPassword().equals(other.getEmailPassword()))
            && (this.getEmailNickname() == null ? other.getEmailNickname() == null : this.getEmailNickname().equals(other.getEmailNickname()))
            && (this.getIsDelete() == null ? other.getIsDelete() == null : this.getIsDelete().equals(other.getIsDelete()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getEmailHost() == null) ? 0 : getEmailHost().hashCode());
        result = prime * result + ((getEmailPort() == null) ? 0 : getEmailPort().hashCode());
        result = prime * result + ((getEmailUsername() == null) ? 0 : getEmailUsername().hashCode());
        result = prime * result + ((getEmailPassword() == null) ? 0 : getEmailPassword().hashCode());
        result = prime * result + ((getEmailNickname() == null) ? 0 : getEmailNickname().hashCode());
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
        sb.append(", emailHost=").append(emailHost);
        sb.append(", emailPort=").append(emailPort);
        sb.append(", emailUsername=").append(emailUsername);
        sb.append(", emailPassword=").append(emailPassword);
        sb.append(", emailNickname=").append(emailNickname);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}