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
 * @TableName dtu
 */
@TableName(value = "dtu")
@Data
public class Dtu implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * DTU编号
     */
    private String dtuId;
    /**
     * 添加时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime addTime;
    /**
     * 备注
     */
    private String remark;
    /**
     * 逻辑删除
     */
    private Integer isDelete;
    /**
     * 当前dtu是否在线 2023.11.25 WJ
     */
    private Integer isOnline;
    /**
     * 当前dtu地址 2023.11.25 WJ
     */
    private String address;


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
        Dtu other = (Dtu) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getDtuId() == null ? other.getDtuId() == null : this.getDtuId().equals(other.getDtuId()))
                && (this.getAddTime() == null ? other.getAddTime() == null : this.getAddTime().equals(other.getAddTime()))
                && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
                && (this.getIsDelete() == null ? other.getIsDelete() == null : this.getIsDelete().equals(other.getIsDelete()))
                && (this.getIsOnline() == null ? other.getIsOnline() == null : this.getIsOnline().equals(other.getIsOnline()))
                && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getDtuId() == null) ? 0 : getDtuId().hashCode());
        result = prime * result + ((getAddTime() == null) ? 0 : getAddTime().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getIsDelete() == null) ? 0 : getIsDelete().hashCode());
        result = prime * result + ((getIsOnline() == null) ? 0 : getIsOnline().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", dtuId=").append(dtuId);
        sb.append(", addTime=").append(addTime);
        sb.append(", remark=").append(remark);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", isOnline=").append(isOnline);
        sb.append(", address=").append(address);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}