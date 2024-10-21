package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

public interface ShopCartService {
    void addCart(ShoppingCartDTO shoppingCartDTO);

    List<ShoppingCart> select();

    void clean();
}
