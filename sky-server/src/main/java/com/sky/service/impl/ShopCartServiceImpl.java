package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShopCartMapper;
import com.sky.service.ShopCartService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShopCartServiceImpl implements ShopCartService {
    @Autowired
    private ShopCartMapper shopCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;
    @Override
    public void addCart(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId());
        List<ShoppingCart> shoppingCarts= shopCartMapper.list(shoppingCart);
        //判断购物车是否已经存在菜品或者套餐
        if (shoppingCarts.size()>0&&shoppingCarts!=null){
            shopCartMapper.update(shoppingCart);
            return;
        }
        //将购物车中的信息插入到表中
        if (shoppingCartDTO.getDishId()!=null){//如果是菜品
            DishVO dishVO = dishMapper.selectById(shoppingCartDTO.getDishId());
            shoppingCart.setImage(dishVO.getImage());
            shoppingCart.setAmount(dishVO.getPrice());
            shoppingCart.setName(dishVO.getName());
            shoppingCart.setDishId(shoppingCartDTO.getDishId());
        }else if (shoppingCartDTO.getSetmealId()!=null) {//如果是套餐
            Setmeal setmeal = setmealMapper.selectById(shoppingCartDTO.getSetmealId());
            shoppingCart.setImage(setmeal.getImage());
            shoppingCart.setAmount(setmeal.getPrice());
            shoppingCart.setName(setmeal.getName());
            shoppingCart.setSetmealId(shoppingCartDTO.getSetmealId());
        }
        shoppingCart.setCreateTime(LocalDateTime.now());
        shopCartMapper.addCart(shoppingCart);
    }

    @Override
    public List<ShoppingCart> select() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(BaseContext.getCurrentId());
        List<ShoppingCart> list = shopCartMapper.list(shoppingCart);
        return list;
    }

    @Override
    public void clean() {

        shopCartMapper.clean(BaseContext.getCurrentId());

    }

    @Override
    public void updateOne(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId());
        shopCartMapper.updateOne(shoppingCart);
        List<ShoppingCart> list = shopCartMapper.list(shoppingCart);
        ShoppingCart shoppingCart1 = list.get(0);
        if (shoppingCart1.getNumber()==0){
            shopCartMapper.deleteById(shoppingCart1.getId());
        }
    }
}
