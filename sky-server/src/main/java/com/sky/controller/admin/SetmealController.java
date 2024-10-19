package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@Api("套餐接口")
@RequestMapping("/admin/setmeal")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;

    /**
     * 新增菜品
     * @param setmealDTO
     * @return
     */
    @PostMapping
    @ApiOperation("新增菜品")
    public Result addSetmeal(@RequestBody SetmealDTO setmealDTO){
        log.info("新增菜品{}",setmealDTO);
        setmealService.addSetmeal(setmealDTO);
        return Result.success();
    }

    /**
     * 套餐分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    public Result<PageResult> selectLimit(SetmealPageQueryDTO setmealPageQueryDTO){
        log.info("新增菜品{}",setmealPageQueryDTO);
        PageResult pageResult=setmealService.selectLimit(setmealPageQueryDTO);
        return Result.success(pageResult);
    }
}
