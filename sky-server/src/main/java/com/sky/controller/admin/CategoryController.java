package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "/admin/category")
@Slf4j
@Api(tags = "分类管理接口")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/page")
    @ApiOperation("分页查询接口")
    public Result selectLimit(CategoryPageQueryDTO pageQueryDTO){
        PageResult pageResult=categoryService.selectLimit(pageQueryDTO);
        return Result.success(pageResult);
    }
    @ApiOperation("新增分类接口")
    @PostMapping
    public Result addCategory(@RequestBody CategoryDTO categoryDTO){
            categoryService.addCategory(categoryDTO);
            return Result.success();
    }
    @PostMapping("status/{status}")
    public Result setStatus(@PathVariable Integer status,Long id){
        categoryService.setStatus(status,id);
        return Result.success();
    }
    @DeleteMapping()
    public Result deleteById(Long id){
        categoryService.deleteById(id);
        return Result.success();
    }
    @PutMapping
    public Result updateCategory(@RequestBody CategoryDTO categoryDTO){
        categoryService.updateCategory(categoryDTO);
        return Result.success();
    }
    @GetMapping("/list")
    public Result selectByType(Integer type){
        List<Category>categories=categoryService.selectByType(type);
        return Result.success(categories);
    }
}
