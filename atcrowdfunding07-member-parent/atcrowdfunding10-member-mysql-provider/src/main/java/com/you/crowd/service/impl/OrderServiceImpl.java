package com.you.crowd.service.impl;

import com.you.crowd.entity.po.AddressPO;
import com.you.crowd.entity.po.AddressPOExample;
import com.you.crowd.entity.po.OrderPO;
import com.you.crowd.entity.po.OrderProjectPO;
import com.you.crowd.entity.vo.AddressVO;
import com.you.crowd.entity.vo.OrderProjectVO;
import com.you.crowd.entity.vo.OrderVO;
import com.you.crowd.entity.vo.PortalTypeVO;
import com.you.crowd.mapper.AddressPOMapper;
import com.you.crowd.mapper.OrderPOMapper;
import com.you.crowd.mapper.OrderProjectPOMapper;
import com.you.crowd.service.api.MemberService;
import com.you.crowd.service.api.OrderService;
import com.you.crowd.service.api.ProjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * @author 游斌
 * @create 2020-08-13  19:14
 */
@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderProjectPOMapper orderProjectPOMapper;
    @Resource
    private AddressPOMapper addressPOMapper;
    @Resource
    private OrderPOMapper orderPOMapper;

    @Override
    public OrderProjectVO getOrderProjectVO(Integer projectId, Integer returnId) {
        OrderProjectVO orderProjectVO = orderProjectPOMapper.selectOrderProjectVO(returnId);
        return orderProjectVO;
    }

    @Override
    public List<AddressVO> getAddressVOList(Integer memberId) {
        AddressPOExample addressPOExample = new AddressPOExample();
        AddressPOExample.Criteria criteria = addressPOExample.createCriteria();
        criteria.andMemberIdEqualTo(memberId);
        List<AddressPO> addressPOList = addressPOMapper.selectByExample(addressPOExample);
        //把addressPOList集合的值赋给addressVOList
        List<AddressVO> addressVOList = new ArrayList<>();
        for (AddressPO addressPO : addressPOList) {
            AddressVO addressVO = new AddressVO();
            BeanUtils.copyProperties(addressPO, addressVO);
            addressVOList.add(addressVO);
        }
        return addressVOList;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void addAddressVO(AddressVO addressVO) {
        AddressPO addressPO = new AddressPO();
        BeanUtils.copyProperties(addressVO, addressPO);
        addressPOMapper.insertSelective(addressPO);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void addOrderVO(OrderVO orderVO) {
        //把orderPO添加到数据库
        OrderPO orderPO = new OrderPO();
        BeanUtils.copyProperties(orderVO, orderPO);
        orderPOMapper.insertSelective(orderPO);
        //把orderVO中orderProjectVO添加到数据库
        OrderProjectPO orderProjectPO = new OrderProjectPO();
        OrderProjectVO orderProjectVO = orderVO.getOrderProjectVO();
        BeanUtils.copyProperties(orderProjectVO, orderProjectPO);
        //获取orderId
        Integer orderPOId = orderPO.getId();
        orderProjectPO.setOrderId(orderPOId);
        orderProjectPOMapper.insertSelective(orderProjectPO);
    }


}
