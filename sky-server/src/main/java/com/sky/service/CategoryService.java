package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.List;

public interface CategoryService {

    PageResult selectLimit(CategoryPageQueryDTO pageQueryDTO);

    void addCategory(CategoryDTO categoryDTO);


    void setStatus(Integer status, Long id);

    void deleteById(Long id);

    void updateCategory(CategoryDTO categoryDTO);

    List<Category> selectByType(Integer type);

    List<Category> list(Integer type);
}
