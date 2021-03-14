package com.you.crowd.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 游斌
 * @create 2020-07-30  22:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "shortmessageproperties")
public class ShortMessageProperties {
    private String host;
    private String path;
    private String method;
    private String appCode;
    private String templateId;

}
