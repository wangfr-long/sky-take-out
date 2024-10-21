package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @DeleteMapping
    public Result delete(@RequestParam List<Long> ids){
        setmealService.delete(ids);
        return Result.success();
    }

    /**
     * 根据id查询套餐，用于页面回显
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<SetmealVO> selectById(@PathVariable Long id){
        log.info("根据id查询{}",id);
        SetmealVO setmealVO=  setmealService.selectById(id);
        return Result.success(setmealVO);
    }

    /**
     * 修改套餐
     * @param setmealDTO
     * @return
     */
    @PutMapping
    public Result update(@RequestBody SetmealDTO setmealDTO){
        log.info("修改套餐{}",setmealDTO);
        setmealService.update(setmealDTO);
        return Result.success();
    }
    @PostMapping("/status/{status}")
    public Result setStatus(@PathVariable Integer status,Long id){
        setmealService.setStatus(status,id);
        return Result.success();
    }
}
