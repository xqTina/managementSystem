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
 * @TableName device
 */
@TableName(value = "device")
@Data
public class Device implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * DTU_id
     */
    private Integer dtuId;
    /**
     * 设备序列号
     */
    private String deviceId;
    /**
     * 设备通道数
     */
    private Integer numberOfChannels;
    /**
     * 设备类型
     */
    private String deviceType;
    /**
     * 数据1最大值
     */
    private Double maxAlarmValue1;
    /**
     * 数据1最小值
     */
    private Double minAlarmValue1;
    /**
     * 数据2最大值
     */
    private Double maxAlarmValue2;
    /**
     * 数据2最小值
     */
    private Double minAlarmValue2;
    /**
     * 数据3最大值
     */
    private Double maxAlarmValue3;
    /**
     * 数据3最小值
     */
    private Double minAlarmValue3;
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
        Device other = (Device) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getDtuId() == null ? other.getDtuId() == null : this.getDtuId().equals(other.getDtuId()))
                && (this.getDeviceId() == null ? other.getDeviceId() == null : this.getDeviceId().equals(other.getDeviceId()))
                && (this.getNumberOfChannels() == null ? other.getNumberOfChannels() == null : this.getNumberOfChannels().equals(other.getNumberOfChannels()))
                && (this.getDeviceType() == null ? other.getDeviceType() == null : this.getDeviceType().equals(other.getDeviceType()))
                && (this.getMaxAlarmValue1() == null ? other.getMaxAlarmValue1() == null : this.getMaxAlarmValue1().equals(other.getMaxAlarmValue1()))
                && (this.getMinAlarmValue1() == null ? other.getMinAlarmValue1() == null : this.getMinAlarmValue1().equals(other.getMinAlarmValue1()))
                && (this.getMaxAlarmValue2() == null ? other.getMaxAlarmValue2() == null : this.getMaxAlarmValue2().equals(other.getMaxAlarmValue2()))
                && (this.getMinAlarmValue2() == null ? other.getMinAlarmValue2() == null : this.getMinAlarmValue2().equals(other.getMinAlarmValue2()))
                && (this.getMaxAlarmValue3() == null ? other.getMaxAlarmValue3() == null : this.getMaxAlarmValue3().equals(other.getMaxAlarmValue3()))
                && (this.getMinAlarmValue3() == null ? other.getMinAlarmValue3() == null : this.getMinAlarmValue3().equals(other.getMinAlarmValue3()))
                && (this.getAddTime() == null ? other.getAddTime() == null : this.getAddTime().equals(other.getAddTime()))
                && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
                && (this.getIsDelete() == null ? other.getIsDelete() == null : this.getIsDelete().equals(other.getIsDelete()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getDtuId() == null) ? 0 : getDtuId().hashCode());
        result = prime * result + ((getDeviceId() == null) ? 0 : getDeviceId().hashCode());
        result = prime * result + ((getNumberOfChannels() == null) ? 0 : getNumberOfChannels().hashCode());
        result = prime * result + ((getDeviceType() == null) ? 0 : getDeviceType().hashCode());
        result = prime * result + ((getMaxAlarmValue1() == null) ? 0 : getMaxAlarmValue1().hashCode());
        result = prime * result + ((getMinAlarmValue1() == null) ? 0 : getMinAlarmValue1().hashCode());
        result = prime * result + ((getMaxAlarmValue2() == null) ? 0 : getMaxAlarmValue2().hashCode());
        result = prime * result + ((getMinAlarmValue2() == null) ? 0 : getMinAlarmValue2().hashCode());
        result = prime * result + ((getMaxAlarmValue3() == null) ? 0 : getMaxAlarmValue3().hashCode());
        result = prime * result + ((getMinAlarmValue3() == null) ? 0 : getMinAlarmValue3().hashCode());
        result = prime * result + ((getAddTime() == null) ? 0 : getAddTime().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
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
        sb.append(", dtuId=").append(dtuId);
        sb.append(", deviceId=").append(deviceId);
        sb.append(", numberOfChannels=").append(numberOfChannels);
        sb.append(", deviceType=").append(deviceType);
        sb.append(", maxAlarmValue1=").append(maxAlarmValue1);
        sb.append(", minAlarmValue1=").append(minAlarmValue1);
        sb.append(", maxAlarmValue2=").append(maxAlarmValue2);
        sb.append(", minAlarmValue2=").append(minAlarmValue2);
        sb.append(", maxAlarmValue3=").append(maxAlarmValue3);
        sb.append(", minAlarmValue3=").append(minAlarmValue3);
        sb.append(", addTime=").append(addTime);
        sb.append(", remark=").append(remark);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}