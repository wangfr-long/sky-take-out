package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderVO;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderMapper {
    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into sky_take_out.orders (number, status, user_id, address_book_id, order_time, " +
            "checkout_time, pay_method, pay_status, amount, remark, phone, address, user_name, consignee," +
            " cancel_reason, rejection_reason, cancel_time, estimated_delivery_time, " +
            "delivery_status, delivery_time, pack_amount, tableware_number, tableware_status) VALUES " +
            "(#{number}, #{status}, #{userId}, #{addressBookId}, #{orderTime}," +
            " #{checkoutTime}, #{payMethod}, #{payStatus}, #{amount}, #{remark}, #{phone}, " +
            "#{address}, #{userName}, #{consignee}, #{cancelReason}, #{rejectionReason}, " +
            "#{cancelTime}, #{estimatedDeliveryTime}, #{deliveryStatus}, #{deliveryTime}, " +
            "#{packAmount}, #{tablewareNumber}, #{tablewareStatus})")
    void insert(Orders orders);
    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String orderNumber);

    /**
     * 修改订单信息
     * @param orders
     */
    void update(Orders orders);

    Page<OrderVO> selectHistory(OrdersPageQueryDTO pageQueryDTO);
    @Select("select * from orders where id=#{id}")
    Orders selectById(Long id);
    @Delete("delete from orders where id=#{id}")
    void deleteById(Long id);

    @Select("select count(*) from orders where status=#{status}")
    Integer selectStatus(Integer status);
    @Select("select * from orders where status=#{status} and order_time<#{orderTime}")
    List<Orders> select(Long status, LocalDateTime orderTime);
}
