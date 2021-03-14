package com.you.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 游斌
 * @create 2020-08-09  15:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortalTypeVO {
    private Integer id;
    private String name;
    private String remark;
    private List<PortalProjectVO> portalProjectVOList;
}
