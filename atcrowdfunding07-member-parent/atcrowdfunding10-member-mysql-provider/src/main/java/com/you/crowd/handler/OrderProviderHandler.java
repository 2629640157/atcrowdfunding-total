package com.you.crowd.handler;

import com.you.crowd.entity.vo.AddressVO;
import com.you.crowd.entity.vo.OrderProjectVO;
import com.you.crowd.entity.vo.OrderVO;
import com.you.crowd.service.api.OrderService;
import com.you.ssm.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 游斌
 * @create 2020-08-13  19:09
 */
@RestController
public class OrderProviderHandler {
    @Autowired
    private OrderService orderService;

    @RequestMapping("/get/order/project/vo/remote")
    ResultEntity<OrderProjectVO> getOrderProjectVORemote(@RequestParam("projectId") Integer projectId, @RequestParam("returnId") Integer returnId) {
        try {
            OrderProjectVO orderProjectVO = orderService.getOrderProjectVO(projectId, returnId);
            return ResultEntity.successWithData(orderProjectVO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    @RequestMapping("/get/order/address/vo/remote")
    ResultEntity<List<AddressVO>> getAddressVORemote(@RequestParam("memberId") Integer memberId) {
        try {
            List<AddressVO> addressVOList = orderService.getAddressVOList(memberId);
            return ResultEntity.successWithData(addressVOList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    @RequestMapping("/add/order/address/vo/remote")
    ResultEntity<String> addAddressVORemote(@RequestBody AddressVO addressVO) {
        try {
            orderService.addAddressVO(addressVO);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }

    }

    @RequestMapping("/save/pay/order/vo/remote")
    ResultEntity<String> saveOrderVORemote(@RequestBody OrderVO orderVO) {
        try {
            orderService.addOrderVO(orderVO);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }
}
