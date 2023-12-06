package com.szkj.sms.constant;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component //注册bean
@ConfigurationProperties(prefix = "device")
public class DeviceUnitConstant {
    private String[] units;
}
