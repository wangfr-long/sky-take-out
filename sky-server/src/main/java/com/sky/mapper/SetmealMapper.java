package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SetmealMapper {

    /**
     * 根据分类id查询套餐的数量
     * @param id
     * @return
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);
    @Select("select count(id) from setmeal_dish where id=#{id}" )
    Integer countByDishId(Long id);
    @AutoFill(OperationType.INSERT)
    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into setmeal (category_id, name, price, description, image,status,create_time,create_user,update_user,update_time) VALUES " +
            "(#{categoryId},#{name},#{price},#{description},#{image},#{status},#{createTime},#{createUser},#{updateUser},#{updateTime})")
    void insertSetmeal(Setmeal setmeal);

    Page<SetmealVO> selectLimit(SetmealPageQueryDTO setmealPageQueryDTO);
}
