package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     *
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);
    @Options(keyProperty = "id",useGeneratedKeys = true)
    @AutoFill(value = OperationType.INSERT)
    @Insert("insert into dish (name, category_id, price, image, description, create_time, update_time, create_user, update_user,status) VALUES" +
            " (#{name},#{categoryId},#{price},#{image},#{description},#{createTime},#{updateTime},#{createUser},#{updateUser},#{status})")
    void insertDish(Dish dish);

    Page<DishVO> selectLimit(DishPageQueryDTO dishPageQueryDTO);
    @Select("select d.*,c.name as categoryName from dish d left outer join sky_take_out.category c on d.category_id = c.id where d.id=#{id}")
    DishVO selectById(Long id);

    void delete(List<Long> ids);
    @AutoFill(OperationType.UPDATE)
    void update(Dish dish);

    List<Dish> selectByTypeId(Dish dish);
}
