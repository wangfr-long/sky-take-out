package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.*;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Override
    @Transactional//保证数据的一致性
    public void addDish(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.insertDish(dish);
        Long id = dish.getId();
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() != 0) {
            for (DishFlavor dishFlavor : flavors) {
                dishFlavor.setDishId(dish.getId());
            }
            dishFlavorMapper.InsertDishFlavor(flavors);
        }
    }

    @Override
    public PageResult selectLimit(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<DishVO> dishes = dishMapper.selectLimit(dishPageQueryDTO);
        return new PageResult(dishes.getTotal(), dishes.getResult());
    }

    @Override
    @Transactional
    public void deleteDish(List<Long> ids) {
        //如果起售中就不能删除
        ids.forEach(id -> {
            DishVO dish = dishMapper.selectById(id);
            if (dish.getStatus() == StatusConstant.ENABLE) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        });
        //被套餐关联的菜品不能删除
        List<Long> setmealId = setmealDishMapper.getSetmealIdByDishId(ids);
        if (setmealId.size() > 0 && setmealId != null) {
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        dishMapper.delete(ids);
        dishFlavorMapper.deleteBYDishIds(ids);
    }

    @Override
    public DishVO selectById(Long id) {
        DishVO dishVO = dishMapper.selectById(id);
        List<DishFlavor> dishFlavors = dishFlavorMapper.selectFlavorById(id);
        dishVO.setFlavors(dishFlavors);
        return dishVO;
    }

    @Override
    @Transactional
    public void update(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.update(dish);
        List<DishFlavor> flavors = dishDTO.getFlavors();
        flavors.forEach(dishFlavor ->{
            dishFlavor.setDishId(dish.getId());
        });
        dishFlavorMapper.deleteBYDishId(dish.getId());
        if (flavors.size() != 0 && flavors != null) {
            dishFlavorMapper.InsertDishFlavor(flavors);
        }
    }

    @Override
    public void setStatus(Integer status,Long id) {
        Dish dish = new Dish();
        dish.setStatus(status);
        dish.setId(id);
        dishMapper.update(dish);
    }

    @Override
    public List<Dish> selectByType(Long categoryId,String name) {
        Dish dish1 = new Dish();
        dish1.setStatus(StatusConstant.ENABLE);
        dish1.setCategoryId(categoryId);
        dish1.setName(name);
        List<Dish> dish=  dishMapper.selectByTypeId(dish1);
      return dish;
    }
}
