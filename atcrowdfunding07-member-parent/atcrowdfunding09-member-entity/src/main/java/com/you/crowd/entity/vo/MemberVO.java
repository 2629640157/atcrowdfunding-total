package com.you.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 游斌
 * @create 2020-07-31  20:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberVO {
    private String loginacct;
    private String userpswd;
    private String username;
    private String email;
    private String phoneNum;
    private String code;
}
