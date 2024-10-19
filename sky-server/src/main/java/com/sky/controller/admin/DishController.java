package com.sky.controller.admin;

import com.github.pagehelper.Page;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/admin/dish")
public class DishController {
    @Autowired
    private DishService dishService;
    @PostMapping
    public Result addDish(@RequestBody DishDTO dishDTO){
        dishService.addDish(dishDTO);
        return Result.success();
    }
    @GetMapping("/page")
    public Result selectLimit(DishPageQueryDTO dishPageQueryDTO){
        PageResult pageResult=dishService.selectLimit(dishPageQueryDTO);
        return Result.success(pageResult);
    }
    @DeleteMapping
    public Result deleteDish(@RequestParam List<Long> ids){
        dishService.deleteDish(ids);
        return Result.success();
    }
    @GetMapping("/{id}")
    public Result<DishVO> selectById(@PathVariable Long id){
       DishVO dishVO= dishService.selectById(id);
        return Result.success(dishVO);
    }
    @PutMapping
    public Result update(@RequestBody DishDTO dishDTO){
        dishService.update(dishDTO);
        return Result.success();
    }
    @PostMapping("/status/{status}")
    public Result setStatus(@PathVariable Integer status,Long id){
        dishService.setStatus(status,id);
        return Result.success();
    }

    /**
     * 根据菜品类型查询菜品，用于添加套餐的页面回显
     * @return
     */
   @GetMapping("/list")
    public Result selectByType(Long categoryId,String name){
        log.info("根据分类显示菜品{}",categoryId);
       List<Dish> dish= dishService.selectByType(categoryId,name);
       return Result.success(dish);
   }
}
