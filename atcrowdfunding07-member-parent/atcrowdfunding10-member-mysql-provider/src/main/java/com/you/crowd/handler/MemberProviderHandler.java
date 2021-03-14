package com.you.crowd.handler;

import com.you.crowd.entity.po.MemberPO;
import com.you.crowd.service.api.MemberService;
import com.you.ssm.constant.CrowdConstant;
import com.you.ssm.util.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 游斌
 * @create 2020-07-26  9:43
 */
@RestController
public class MemberProviderHandler {
    @Resource
    private MemberService memberService;

    Logger logger = LoggerFactory.getLogger(MemberProviderHandler.class);

    @RequestMapping("/get/memberpo/by/login/acct/remote")
    public ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam("loginacct") String loginAcct) {
        try {
            MemberPO memberPO = memberService.getMemberPOByLoginAcctRemote(loginAcct);
            return ResultEntity.successWithData(memberPO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    @RequestMapping("/save/member/remote")
    public ResultEntity<String> saveNewMember(@RequestBody MemberPO memberPO) {
        try {
            logger.info(memberPO.toString());
            memberService.saveNewMember(memberPO);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                return ResultEntity.failed(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
            return ResultEntity.failed(e.getMessage());
        }
    }
}
