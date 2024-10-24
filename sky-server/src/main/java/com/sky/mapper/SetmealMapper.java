package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface SetmealMapper {

    /**
     * 根据分类id查询套餐的数量
     *
     * @param id
     * @return
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);

    @Select("select count(id) from setmeal_dish where id=#{id}")
    Integer countByDishId(Long id);

    @AutoFill(OperationType.INSERT)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into setmeal (category_id, name, price, description, image,status,create_time,create_user,update_user,update_time) VALUES " +
            "(#{categoryId},#{name},#{price},#{description},#{image},#{status},#{createTime},#{createUser},#{updateUser},#{updateTime})")
    void insertSetmeal(Setmeal setmeal);

    Page<SetmealVO> selectLimit(SetmealPageQueryDTO setmealPageQueryDTO);

    List<Setmeal> selectByIds(List<Long> ids);

    void delete(List<Long> ids);

    @Select("select * from setmeal where id=#{id}")
    Setmeal selectById(Long id);

    @AutoFill(OperationType.UPDATE)
    void update(Setmeal setmeal);
    /**
     * 动态条件查询套餐
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);

    /**
     * 根据套餐id查询菜品选项
     * @param setmealId
     * @return
     */
    @Select("select sd.name, sd.copies, d.image, d.description " +
            "from setmeal_dish sd left join dish d on sd.dish_id = d.id " +
            "where sd.setmeal_id = #{setmealId}")
    List<DishItemVO> getDishItemBySetmealId(Long setmealId);
    Integer countByMap(Map map);
}
