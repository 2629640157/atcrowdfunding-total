package com.you.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 游斌
 * @create 2020-07-31  20:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberLoginVO implements Serializable {
    static final long serialVersionUID = 44567812346L;
    private Integer id;
    private String loginacct;
    private String username;
    private String email;

}
