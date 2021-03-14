package com.you.crowd.service.api;

import com.you.crowd.entity.po.MemberPO;

/**
 * @author 游斌
 * @create 2020-07-26  9:45
 */
public interface MemberService {
    MemberPO getMemberPOByLoginAcctRemote(String loginAcct);

    void saveNewMember(MemberPO memberPO);

    void loginMember(String loginacct, String userpswd);

}
