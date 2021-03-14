package com.you.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 游斌
 * @create 2020-08-09  15:48
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortalProjectVO {
    private Integer projectId;
    private String projectName;
    private String headerPicturePath;
    private Integer money;
    private String deployDate;
    private Integer percentage;
    private Integer supporter;
}
