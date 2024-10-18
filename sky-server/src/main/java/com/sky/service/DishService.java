package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;

import java.util.List;

public interface DishService {
    void addDish(DishDTO dishDTO);

    PageResult selectLimit(DishPageQueryDTO dishPageQueryDTO);

    void deleteDish(List<Long> ids);
}
