package com.you.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 游斌
 * @create 2020-08-15  17:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVO implements Serializable {
    //	主键
    private Integer id;
    //	订单号
    private String orderNum;
    //	支付宝流水单号
    private String payOrderNum;
    //	订单金额
    private Double orderAmount;
    //	是否开发票
    private Integer invoice;
    //	发票抬头
    private String invoiceTitle;
    //	备注
    private String orderRemark;

    private String addressId;

    private OrderProjectVO orderProjectVO;


}
