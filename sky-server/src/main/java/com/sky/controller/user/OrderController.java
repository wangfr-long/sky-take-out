package com.sky.controller.user;

import com.alibaba.fastjson.JSON;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.entity.Orders;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import com.sky.webSocket.WebSocketServer;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController("userOrderController")
@RequestMapping("/user/order")
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private WebSocketServer webSocketServer;
    @PostMapping("/submit")
    public Result submit(@RequestBody OrdersSubmitDTO ordersSubmitDTO){
     OrderSubmitVO submitVO= orderService.submit(ordersSubmitDTO);

     return Result.success(submitVO);
    }
    @PutMapping("/payment")
    @ApiOperation("订单支付")
    public Result<String> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        log.info("订单支付：{}", ordersPaymentDTO);
        orderService.paySuccess(ordersPaymentDTO.getOrderNumber());
        return  Result.success();
    }
    @GetMapping("/historyOrders")
    public Result selectHistory( OrdersPageQueryDTO pageQueryDTO){
      PageResult orderVOPageResult= orderService.selectHistory(pageQueryDTO);
      return Result.success(orderVOPageResult);
    }
    @GetMapping("/orderDetail/{id}")
    public Result selectDetails(@PathVariable Long id){
     OrderVO orderVO=orderService.selectDetails(id);
       return Result.success(orderVO);
    }
    @PutMapping("/cancel/{id}")
    public Result cancelOrder(@PathVariable Long id){
        orderService.cancelOrder(id);
        return Result.success();
    }
    @PostMapping("/repetition/{id}")
    public Result orderAgain(@PathVariable Long id){
        orderService.orderAgain(id);
        return Result.success();
    }
    @GetMapping("/reminder/{id}")
    public Result reminder(@PathVariable Long id){
       Orders orders= orderService.reminder(id);
        return Result.success();
    }
}
