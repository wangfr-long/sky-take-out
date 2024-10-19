package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    void InsertDishFlavor(List<DishFlavor> dishFlavors);

    void deleteBYDishIds(List<Long> ids);
    @Select("select * from sky_take_out.dish_flavor where dish_id=#{id}")
    List<DishFlavor> selectFlavorById(Long id);
    @Delete("delete from sky_take_out.dish_flavor where dish_id=#{id}")
    void deleteBYDishId(Long id);
}
