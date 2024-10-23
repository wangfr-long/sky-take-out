package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class OrderTask {
    @Autowired
    private OrderMapper orderMapper;
    @Scheduled(cron = "0 0/1 * * * ?")
    public void PENDING_PAYMENTtask(){
        log.info("定时任务已触发：{}",new Date());
        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(-15);

        List<Orders>orders= orderMapper.select(Long.valueOf(Orders.PENDING_PAYMENT),localDateTime);
        for (Orders order : orders) {
            order.setStatus(Orders.CANCELLED);
            order.setCancelReason("超时未支付");
            order.setCancelTime(LocalDateTime.now());
            orderMapper.update(order);
        }
    }
    @Scheduled(cron = "0 0 1 * * ?")
    public void  DELIVERY_IN_PROGRESStask(){
        LocalDateTime localDateTime = LocalDateTime.now().plusHours(-1);
        List<Orders> orders = orderMapper.select(Long.valueOf(Orders.DELIVERY_IN_PROGRESS), localDateTime);
        for (Orders order : orders) {
            order.setStatus(Orders.COMPLETED);
            orderMapper.update(order);
        }
    }
}
