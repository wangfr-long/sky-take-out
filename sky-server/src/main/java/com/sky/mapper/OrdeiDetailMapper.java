package com.sky.mapper;

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrdeiDetailMapper {

    void insert(List<OrderDetail> orderDetails);
    @Select("select * from sky_take_out.order_detail where order_id=#{id}")
    List<OrderDetail> selectById(Long id);
    @Delete("delete from sky_take_out.order_detail where order_id=#{id}")
    void delete(Long id);
}
