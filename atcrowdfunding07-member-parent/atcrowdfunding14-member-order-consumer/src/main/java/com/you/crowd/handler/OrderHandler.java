package com.you.crowd.handler;

import com.netflix.discovery.converters.Auto;
import com.you.crowd.api.MySQLRemoteService;
import com.you.crowd.entity.vo.AddressVO;
import com.you.crowd.entity.vo.MemberLoginVO;
import com.you.crowd.entity.vo.OrderProjectVO;
import com.you.ssm.constant.CrowdConstant;
import com.you.ssm.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

/**
 * @author 游斌
 * @create 2020-08-13  18:54
 */
@Controller
public class OrderHandler {

    @Resource
    private MySQLRemoteService mySQLRemoteService;

    @RequestMapping("/confirm/return/info/{projectId}/{returnId}")
    public String showReturnConfirmInfo(@PathVariable("projectId") Integer projectId,
                                        @PathVariable("returnId") Integer returnId,
                                        HttpSession session) {
        ResultEntity<OrderProjectVO> orderProjectVOResultEntity = mySQLRemoteService.getOrderProjectVORemote(projectId, returnId);
        if (ResultEntity.SUCCESS.equals(orderProjectVOResultEntity.getOperationResult())) {
            OrderProjectVO orderProjectVO = orderProjectVOResultEntity.getQueryData();
            session.setAttribute("orderProjectVO", orderProjectVO);
        }
        return "confirm_return";
    }

    @RequestMapping("/confirm/order/{returnCount}")
    public String showConfirmOrderInfo(@PathVariable("returnCount") Integer returnCount, HttpSession session) {
        OrderProjectVO orderProjectVO = (OrderProjectVO) session.getAttribute("orderProjectVO");
        orderProjectVO.setReturnCount(returnCount);
        session.setAttribute("orderProjectVO", orderProjectVO);
        MemberLoginVO memberLoginVO = (MemberLoginVO) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
        Integer memberId = memberLoginVO.getId();
        //通过用户id从数据库查询该用户保存的收货地址
        ResultEntity<List<AddressVO>> addressVOListResult = mySQLRemoteService.getAddressVORemote(memberId);
        if (ResultEntity.SUCCESS.equals(addressVOListResult.getOperationResult())) {
            List<AddressVO> addressVOList = addressVOListResult.getQueryData();
            session.setAttribute("addressVOList", addressVOList);
        }
        return "confirm_order";
    }


    @RequestMapping("/confirm/add/new/addressVO")
    public String addAddressVO(AddressVO addressVO, HttpSession session) {
        //1.给添加的地址赋memberId
        MemberLoginVO memberLoginVO = (MemberLoginVO) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
        Integer memberId = memberLoginVO.getId();
        addressVO.setMemberId(memberId);
        ResultEntity<String> resultEntity = mySQLRemoteService.addAddressVORemote(addressVO);
        //2.从session域 获取 orderProjectVO对象
        OrderProjectVO orderProjectVO = (OrderProjectVO) session.getAttribute("orderProjectVO");
        //3.从orderProjectVO对象中获取returnCount
        Integer returnCount = orderProjectVO.getReturnCount();
        //4.重定向到指定地址,重新进入确认订单页面
        return "redirect:http://www.crowd.com/order/confirm/order/" + returnCount;

    }

}
