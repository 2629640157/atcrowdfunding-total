package com.you.crowd.service.impl;

import com.you.crowd.entity.po.MemberPO;
import com.you.crowd.entity.po.MemberPOExample;
import com.you.crowd.mapper.MemberPOMapper;
import com.you.crowd.service.api.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 游斌
 * @create 2020-07-26  9:44
 */
@Service
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {
    @Resource
    private MemberPOMapper memberPOMapper;

    public MemberPO getMemberPOByLoginAcctRemote(String loginAcct) {
        MemberPOExample memberPOExample = new MemberPOExample();
        MemberPOExample.Criteria criteria = memberPOExample.createCriteria();
        criteria.andLoginacctEqualTo(loginAcct);
        List<MemberPO> memberPOS = memberPOMapper.selectByExample(memberPOExample);
        if (memberPOS != null && memberPOS.size() > 0) {
            return memberPOS.get(0);
        }
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class, readOnly = false)
    public void saveNewMember(MemberPO memberPO) {
        memberPOMapper.insertSelective(memberPO);
    }

    @Override
    public void loginMember(String loginacct, String userpswd) {

    }
}
