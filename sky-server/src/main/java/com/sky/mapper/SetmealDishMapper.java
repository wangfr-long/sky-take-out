package com.sky.mapper;

import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
    List<Long> getSetmealIdByDishId(List<Long> ids);

    void insertSetmealDishs(List<SetmealDish> setmealDishes);

    void delete(List<Long> ids);
    @Select("select * from setmeal_dish where setmeal_id=#{id}")
    List<SetmealDish> selectById(Long id);
@Delete("delete from setmeal_dish where setmeal_id=#{id}")
    void deleteById(Long id);
    @Select("select d.* from setmeal_dish s  right  outer join sky_take_out.dish d on d.id = s.dish_id where setmeal_id=#{id}")
    List<Dish> getDishBySetmealId(Long id);
    @Select("select s.* from setmeal_dish right outer join sky_take_out.setmeal s on s.id = setmeal_dish.setmeal_id where dish_id=#{id}")
    List<Setmeal> getSetmealByDishId(Long id);
}
