package com.sky.service;

import com.sky.dto.*;
import com.sky.entity.Orders;
import com.sky.result.PageResult;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

public interface OrderService {
    OrderSubmitVO submit(OrdersSubmitDTO ordersSubmitDTO);
    /**
     * 订单支付
     * @param ordersPaymentDTO
     * @return
     */
    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    /**
     * 支付成功，修改订单状态
     * @param outTradeNo
     */
    void paySuccess(String outTradeNo);

    PageResult selectHistory(OrdersPageQueryDTO pageQueryDTO);

    OrderVO selectDetails(Long id);

    void cancelOrder(Long id);

    void orderAgain(Long id);

    OrderStatisticsVO count();

    void confirm(OrdersConfirmDTO ordersConfirmDTO);

    void reject(OrdersRejectionDTO ordersRejectionDTO);

    void cancel(OrdersCancelDTO ordersCancelDTO);

    void delivery(Long id);

    void complete(Long id);

    Orders reminder(Long id);

    PageResult search(OrdersPageQueryDTO ordersPageQueryDTO);

//    OrderVO detail(Long id);
}
