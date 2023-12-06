package com.szkj.sms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @TableName data_zhenxianji
 */
@TableName(value = "data_zhenxianji_unit")
@Data
public class DataZhenxianjiUnit implements Serializable {
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
    private Integer zhenxianDeviceId;
    /**
     *
     */
    private String temperature;
    /**
     *
     */
    private String freqency;

    /**
     *
     */
    private String yingbian;

    /**
     *
     */
    private String data3;

    /**
     *
     */
    private String data4;

    /**
     *
     */
    private String data5;

    /**
     *
     */
    private String data6;

    /**
     *
     */
    private String data7;

    /**
     *
     */
    private String data8;

    /**
     *
     */
    private String data9;

    /**
     *
     */
    private String data10;

    /**
     *
     */
    private String data11;

    /**
     *
     */
    private String data12;

    /**
     *
     */
    private String data13;

    /**
     *
     */
    private String data14;

    /**
     *
     */
    private String data15;

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
        DataZhenxianjiUnit other = (DataZhenxianjiUnit) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getZhenxianDeviceId() == null ? other.getZhenxianDeviceId() == null : this.getZhenxianDeviceId().equals(other.getZhenxianDeviceId()))
                && (this.getTemperature() == null ? other.getTemperature() == null : this.getTemperature().equals(other.getTemperature()))
                && (this.getYingbian() == null ? other.getYingbian() == null : this.getYingbian().equals(other.getYingbian()))
                && (this.getFreqency() == null ? other.getFreqency() == null : this.getFreqency().equals(other.getFreqency()))
                && (this.getData3() == null ? other.getData3() == null : this.getData3().equals(other.getData3()))
                && (this.getData4() == null ? other.getData4() == null : this.getData4().equals(other.getData4()))
                && (this.getData5() == null ? other.getData5() == null : this.getData5().equals(other.getData5()))
                && (this.getData6() == null ? other.getData6() == null : this.getData6().equals(other.getData6()))
                && (this.getData7() == null ? other.getData7() == null : this.getData7().equals(other.getData7()))
                && (this.getData8() == null ? other.getData8() == null : this.getData8().equals(other.getData8()))
                && (this.getData9() == null ? other.getData9() == null : this.getData9().equals(other.getData9()))
                && (this.getData10() == null ? other.getData10() == null : this.getData10().equals(other.getData10()))
                && (this.getData11() == null ? other.getData11() == null : this.getData11().equals(other.getData11()))
                && (this.getData12() == null ? other.getData12() == null : this.getData12().equals(other.getData12()))
                && (this.getData13() == null ? other.getData13() == null : this.getData13().equals(other.getData13()))
                && (this.getData14() == null ? other.getData14() == null : this.getData14().equals(other.getData14()))
                && (this.getData15() == null ? other.getData15() == null : this.getData15().equals(other.getData15()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getZhenxianDeviceId() == null) ? 0 : getZhenxianDeviceId().hashCode());
        result = prime * result + ((getTemperature() == null) ? 0 : getTemperature().hashCode());
        result = prime * result + ((getFreqency() == null) ? 0 : getFreqency().hashCode());
        result = prime * result + ((getYingbian() == null) ? 0 : getYingbian().hashCode());
        result = prime * result + ((getData3() == null) ? 0 : getData3().hashCode());
        result = prime * result + ((getData4() == null) ? 0 : getData4().hashCode());
        result = prime * result + ((getData5() == null) ? 0 : getData5().hashCode());
        result = prime * result + ((getData6() == null) ? 0 : getData6().hashCode());
        result = prime * result + ((getData7() == null) ? 0 : getData7().hashCode());
        result = prime * result + ((getData8() == null) ? 0 : getData8().hashCode());
        result = prime * result + ((getData9() == null) ? 0 : getData9().hashCode());
        result = prime * result + ((getData10() == null) ? 0 : getData10().hashCode());
        result = prime * result + ((getData11() == null) ? 0 : getData11().hashCode());
        result = prime * result + ((getData12() == null) ? 0 : getData12().hashCode());
        result = prime * result + ((getData13() == null) ? 0 : getData13().hashCode());
        result = prime * result + ((getData14() == null) ? 0 : getData14().hashCode());
        result = prime * result + ((getData15() == null) ? 0 : getData15().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", getZhenxianDeviceId=").append(zhenxianDeviceId);
        sb.append(", temperature=").append(temperature);
        sb.append(", freqency=").append(freqency);
        sb.append(", getYingbian=").append(yingbian);
        sb.append(", data3=").append(data3);
        sb.append(", data4=").append(data4);
        sb.append(", data5=").append(data5);
        sb.append(", data6=").append(data6);
        sb.append(", data7=").append(data7);
        sb.append(", data8=").append(data8);
        sb.append(", data9=").append(data9);
        sb.append(", data10=").append(data10);
        sb.append(", data11=").append(data11);
        sb.append(", data12=").append(data12);
        sb.append(", data13=").append(data13);
        sb.append(", data14=").append(data14);
        sb.append(", data15=").append(data15);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}