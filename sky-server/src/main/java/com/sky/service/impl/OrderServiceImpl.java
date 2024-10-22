package com.sky.service.impl;

import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.entity.AddressBook;
import com.sky.entity.OrderDetail;
import com.sky.entity.Orders;
import com.sky.entity.ShoppingCart;
import com.sky.exception.OrderBusinessException;
import com.sky.exception.ShoppingCartBusinessException;
import com.sky.mapper.AddressBookMapper;
import com.sky.mapper.OrdeiDetailMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.ShopCartMapper;
import com.sky.service.OrderService;
import com.sky.vo.OrderSubmitVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private AddressBookMapper addressBookMapper;
    @Autowired
    private ShopCartMapper shopCartMapper;
    @Autowired
    private OrdeiDetailMapper ordeiDetailMapper;

    @Override
    @Transactional
    public OrderSubmitVO submit(OrdersSubmitDTO ordersSubmitDTO) {
        //处理异常情况：
        //1:地址簿为空：
        Long addressBookId = ordersSubmitDTO.getAddressBookId();
        AddressBook addressBook = addressBookMapper.getById(addressBookId);
        if (addressBook == null) {
            throw new OrderBusinessException(MessageConstant.ADDRESS_BOOK_IS_NULL);
        }
        //2:用户提交的订单数据为空：
        Long id = BaseContext.getCurrentId();
        List<ShoppingCart> shoppingCarts = shopCartMapper.selectById(id);
        if (shoppingCarts == null && shoppingCarts.size() == 0) {
            throw new ShoppingCartBusinessException(MessageConstant.SHOPPING_CART_IS_NULL);

        }
        //---------------------------------------------------------------------------
        Orders orders = new Orders();
        //拷贝信息
        BeanUtils.copyProperties(ordersSubmitDTO, orders);
        orders.setUserId(id);
        orders.setStatus(Orders.PENDING_PAYMENT);
        orders.setPayStatus(Orders.UN_PAID);
        orders.setOrderTime(LocalDateTime.now());
        //获取地址簿信息
        orders.setAddress(addressBook.getProvinceName() + addressBook.getCityName() +
                addressBook.getDistrictName() + addressBook.getDetail());
        orders.setPhone(addressBook.getPhone());
        orders.setConsignee(addressBook.getConsignee());
        // 获取订单号
        orders.setNumber(String.valueOf(System.currentTimeMillis()));
        //TODO 获取用户名
        orderMapper.insert(orders);
        //获取订单细节信息
        //1:查询购物车：
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (ShoppingCart shoppingCart : shoppingCarts) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orders.getId());
            BeanUtils.copyProperties(shoppingCart, orderDetail);
            orderDetails.add(orderDetail);
        }
        ordeiDetailMapper.insert(orderDetails);

        //清空购物车：
        shopCartMapper.clean(id);
        //封装OrderSubmitVO对象
        OrderSubmitVO submitVO = new OrderSubmitVO();
        submitVO.setId(orders.getId());
        submitVO.setOrderAmount(orders.getAmount());
        submitVO.setOrderTime(orders.getOrderTime());
        submitVO.setOrderNumber(orders.getNumber());
        return submitVO;
    }
}
