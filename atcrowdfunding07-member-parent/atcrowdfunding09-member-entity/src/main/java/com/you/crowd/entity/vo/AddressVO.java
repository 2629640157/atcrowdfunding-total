package com.you.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 游斌
 * @create 2020-08-13  22:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String receiveName;

    private String phoneNum;

    private String address;

    private Integer memberId;
}
