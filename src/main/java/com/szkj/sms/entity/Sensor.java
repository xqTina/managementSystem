package com.szkj.sms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

/**
 * @TableName sensor
 */
@TableName(value = "sensor")
@Data
public class Sensor implements Serializable {
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
     * 设备ID
     */
    private String deviceId;
    /**
     * 设备通道数量
     */
    private Integer numberOfChannels;
    /**
     * 设备所属用户id
     */
    private Integer userId;

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
        Sensor other = (Sensor) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getDtuId() == null ? other.getDtuId() == null : this.getDtuId().equals(other.getDtuId()))
                && (this.getDeviceId() == null ? other.getDeviceId() == null : this.getDeviceId().equals(other.getDeviceId()))
                && (this.getNumberOfChannels() == null ? other.getNumberOfChannels() == null : this.getNumberOfChannels().equals(other.getNumberOfChannels()))
                && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getDtuId() == null) ? 0 : getDtuId().hashCode());
        result = prime * result + ((getDeviceId() == null) ? 0 : getDeviceId().hashCode());
        result = prime * result + ((getNumberOfChannels() == null) ? 0 : getNumberOfChannels().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
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
        sb.append(", deviceId=").append(deviceId);
        sb.append(", numberOfChannels=").append(numberOfChannels);
        sb.append(", userId=").append(userId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}