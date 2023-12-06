package com.szkj.sms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

/**
 * @TableName data_liefengji
 */
@TableName(value = "data_liefengji")
@Data
public class DataLiefengji implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     *
     */
    private Integer deviceId;
    /**
     *
     */
    private Integer channelId;
    /**
     *
     */
    private LocalDate date;
    /**
     *
     */
    private LocalTime time;
    /**
     * 温度
     */
    private Double temperature;
    /**
     * 缝裂宽度
     */
    private Double slotWidth;

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
        DataLiefengji other = (DataLiefengji) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getDeviceId() == null ? other.getDeviceId() == null : this.getDeviceId().equals(other.getDeviceId()))
                && (this.getChannelId() == null ? other.getChannelId() == null : this.getChannelId().equals(other.getChannelId()))
                && (this.getDate() == null ? other.getDate() == null : this.getDate().equals(other.getDate()))
                && (this.getTime() == null ? other.getTime() == null : this.getTime().equals(other.getTime()))
                && (this.getTemperature() == null ? other.getTemperature() == null : this.getTemperature().equals(other.getTemperature()))
                && (this.getSlotWidth() == null ? other.getSlotWidth() == null : this.getSlotWidth().equals(other.getSlotWidth()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getDeviceId() == null) ? 0 : getDeviceId().hashCode());
        result = prime * result + ((getChannelId() == null) ? 0 : getChannelId().hashCode());
        result = prime * result + ((getDate() == null) ? 0 : getDate().hashCode());
        result = prime * result + ((getTime() == null) ? 0 : getTime().hashCode());
        result = prime * result + ((getTemperature() == null) ? 0 : getTemperature().hashCode());
        result = prime * result + ((getSlotWidth() == null) ? 0 : getSlotWidth().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", deviceId=").append(deviceId);
        sb.append(", channelId=").append(channelId);
        sb.append(", date=").append(date);
        sb.append(", time=").append(time);
        sb.append(", temperature=").append(temperature);
        sb.append(", slotWidth=").append(slotWidth);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}