package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {
    void addDish(DishDTO dishDTO);

    PageResult selectLimit(DishPageQueryDTO dishPageQueryDTO);

    void deleteDish(List<Long> ids);

    DishVO selectById(Long id);

    void update(DishDTO dishDTO);

    void setStatus(Integer status,Long id);

    List<Dish> selectByType(Long categoryId,String name);
}
