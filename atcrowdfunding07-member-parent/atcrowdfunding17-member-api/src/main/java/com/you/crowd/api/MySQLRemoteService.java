package com.you.crowd.api;

import com.you.crowd.entity.po.MemberPO;
import com.you.crowd.entity.vo.*;
import com.you.ssm.util.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author 游斌
 * @create 2020-07-26  9:40
 */
@FeignClient("atguigu-crowd-mysql")
public interface MySQLRemoteService {

    @RequestMapping("/get/memberpo/by/login/acct/remote")
    ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam("loginacct") String loginAcct);

    @RequestMapping("/save/member/remote")
    ResultEntity<String> saveNewMember(@RequestBody MemberPO memberPO);

    @RequestMapping("/save/project/vo/remote")
    ResultEntity<String> saveProjectVORemote(@RequestBody ProjectVO projectVO, @RequestParam("memberLoginVOId") Integer memberLoginVOId);

    @RequestMapping("/get/portal/type/project/date/remote")
    ResultEntity<List<PortalTypeVO>> getPortalTypeProjectDateRemote();

    @RequestMapping("/get/project/detail/remote/{projectId}")
    ResultEntity<DetailProjectVO> getDetailProjectVORemote(@PathVariable("projectId") Integer projectId);

    @RequestMapping("/get/order/project/vo/remote")
    ResultEntity<OrderProjectVO> getOrderProjectVORemote(@RequestParam("projectId") Integer projectId, @RequestParam("returnId") Integer returnId);

    @RequestMapping("/get/order/address/vo/remote")
    ResultEntity<List<AddressVO>> getAddressVORemote(@RequestParam("memberId") Integer memberId);

    @RequestMapping("/add/order/address/vo/remote")
    ResultEntity<String> addAddressVORemote(@RequestBody AddressVO addressVO);

    @RequestMapping("/save/pay/order/vo/remote")
    ResultEntity<String> saveOrderVORemote(@RequestBody OrderVO orderVO);
}
