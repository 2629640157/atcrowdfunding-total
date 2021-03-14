package com.you.crowd.service.api;

import com.you.crowd.entity.vo.AddressVO;
import com.you.crowd.entity.vo.OrderProjectVO;
import com.you.crowd.entity.vo.OrderVO;

import java.util.List;

/**
 * @author 游斌
 * @create 2020-08-13  19:11
 */
public interface OrderService {
    OrderProjectVO getOrderProjectVO(Integer projectId, Integer returnId);

    List<AddressVO> getAddressVOList(Integer memberId);

    void addAddressVO(AddressVO addressVO);

    void addOrderVO(OrderVO orderVO);
}
