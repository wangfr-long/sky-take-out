package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ShopCartMapper {
    @Insert("insert into sky_take_out.shopping_cart (name, image, user_id, dish_id, setmeal_id, dish_flavor, amount, create_time) " +
            "VALUES (#{name}, #{image}, #{userId}, #{dishId}, #{setmealId}, #{dishFlavor}, #{amount}, #{createTime})")
    void addCart(ShoppingCart shoppingCart);

    List<ShoppingCart> list(ShoppingCart shoppingCart);

    void update(ShoppingCart shoppingCart);
@Delete("delete from sky_take_out.shopping_cart where user_id=#{currentId}")
    void clean(Long currentId);

    void updateOne(ShoppingCart shoppingCart);
    @Delete("delete from sky_take_out.shopping_cart where id=#{id}")
    void deleteById(Long id);
    @Select(" select * from sky_take_out.shopping_cart where user_id=#{id}")
    List<ShoppingCart> selectById(Long id);

    void insert(List<ShoppingCart> shoppingCarts);
}
