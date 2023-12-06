package com.szkj.sms.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="http://sixiwenwu.com"/>
 * @date 2021/11/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostMailConfig {
    private String mailHost;
    private String mailUsername;
    private String mailNickname;
    private String mailPassword;
    private Integer mailPort;
}
