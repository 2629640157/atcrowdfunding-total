package com.you.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 游斌
 * @create 2020-08-06  15:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberLauchInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;
    // 简单介绍
    private String descriptionSimple;

    // 详细介绍
    private String descriptionDetail;

    // 联系电话
    private String phoneNum;

    // 客服电话
    private String serviceNum;
}
