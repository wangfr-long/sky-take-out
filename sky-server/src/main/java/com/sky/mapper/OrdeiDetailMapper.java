package com.sky.mapper;

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface OrdeiDetailMapper {

    void insert(List<OrderDetail> orderDetails);
    @Select("select * from sky_take_out.order_detail where order_id=#{id}")
    List<OrderDetail> selectById(Long id);
    @Delete("delete from sky_take_out.order_detail where order_id=#{id}")
    void delete(Long id);
    @Select("select d.name  from sky_take_out.order_detail d left  join sky_take_out.orders o on o.id = d.order_id" +
            " where date_format(o.order_time,'%Y-%m-%d')>=#{begin} and date_format(o.order_time,'%Y-%m-%d')<=#{end} and o.status=5 group by d.name order by sum(d.number) desc limit 0,10")
    List<String> reportName(LocalDate begin, LocalDate end);
    @Select("select sum(d.number) from sky_take_out.order_detail d left  join sky_take_out.orders o on o.id = d.order_id" +
            " where date_format(o.order_time,'%Y-%m-%d')>=#{begin} and date_format(o.order_time,'%Y-%m-%d')<=#{end} and o.status=5 group by d.name order by sum(d.number) desc limit 0,10")
    List<Long> reportNumber(LocalDate begin, LocalDate end);
}