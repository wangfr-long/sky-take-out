package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.exception.SetmealEnableFailedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    @Transactional
    public void addSetmeal(SetmealDTO setmealDTO) {
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmeal.setStatus(0);
        setmealMapper.insertSetmeal(setmeal);
        setmealDishes.forEach(setmealDish -> {
            setmealDish.setSetmealId(setmeal.getId());
        });
        setmealDishMapper.insertSetmealDishs(setmealDishes);
    }

    @Override
    public PageResult selectLimit(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        Page<SetmealVO> setmeals = setmealMapper.selectLimit(setmealPageQueryDTO);
        return new PageResult(setmeals.getTotal(), setmeals.getResult());
    }

    @Override
    @Transactional
    public void delete(List<Long> ids) {
        List<Setmeal> setmeals = setmealMapper.selectByIds(ids);
        setmeals.forEach(setmeal -> {
            Integer status = setmeal.getStatus();
            if (status == StatusConstant.ENABLE) {
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        });
        setmealMapper.delete(ids);
        setmealDishMapper.delete(ids);
    }

    @Override
    public SetmealVO selectById(Long id) {
        Setmeal setmeal = setmealMapper.selectById(id);
        String name = categoryMapper.selectNameById(setmeal.getCategoryId());
        List<SetmealDish> setmealDishes = setmealDishMapper.selectById(id);
        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal, setmealVO);
        setmealVO.setSetmealDishes(setmealDishes);
        setmealVO.setCategoryName(name);
        return setmealVO;
    }

    @Override
    @Transactional
    public void update(SetmealDTO setmealDTO) {
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        setmealMapper.update(setmeal);
        setmealDishMapper.deleteById(setmeal.getId());
        setmealDishes.forEach(setmealDish -> {
            setmealDish.setSetmealId(setmeal.getId());
        });
        if (setmealDishes!=null&&setmealDishes.size()>0){
            setmealDishMapper.insertSetmealDishs(setmealDishes);
        }
    }

    @Override
    @Transactional
    public void setStatus(Integer status, Long id) {
        if (status==StatusConstant.ENABLE){
       List<Dish>dishes=  setmealDishMapper.getDishBySetmealId(id);
        dishes.forEach(dish -> {
            if (status==StatusConstant.ENABLE&&dish.getStatus()==StatusConstant.DISABLE){
                throw new SetmealEnableFailedException(MessageConstant.SETMEAL_ENABLE_FAILED);
            }
        });}
        Setmeal setmeal = Setmeal.builder().status(status).id(id).build();
        setmealMapper.update(setmeal);
    }
    public List<Setmeal> list(Setmeal setmeal) {
        List<Setmeal> list = setmealMapper.list(setmeal);
        return list;
    }

    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    public List<DishItemVO> getDishItemById(Long id) {
        return setmealMapper.getDishItemBySetmealId(id);
    }
}
