package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShopCartService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/shoppingCart")
@Slf4j
public class ShopCartController {
    @Autowired
    private ShopCartService shopCartService;

    /**
     * 添加购物车
     * @param shoppingCartDTO
     * @return
     */
    @PostMapping("/add")
    public Result addCart(@RequestBody ShoppingCartDTO shoppingCartDTO){
        log.info("添加购物车{}",shoppingCartDTO);
        shopCartService.addCart(shoppingCartDTO);
        return Result.success();
    }
    @GetMapping("/list")
    public Result select(){
      List<ShoppingCart>shoppingCarts= shopCartService.select();
      return Result.success(shoppingCarts);
    }
    @DeleteMapping("/clean")
    public Result clean(){
        shopCartService.clean();
        return Result.success();
    }
    @PostMapping ("/sub")
    public Result deleteOne(@RequestBody ShoppingCartDTO shoppingCartDTO){
        shopCartService.updateOne(shoppingCartDTO);
        return Result.success();
    }

}
