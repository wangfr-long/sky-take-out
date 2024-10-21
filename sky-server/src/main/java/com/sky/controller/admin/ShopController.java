package com.sky.controller.admin;

import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/admin/shop")
public class ShopController {
    @Autowired
    private RedisTemplate redisTemplate;
    public static final String KEY="SHOP_STATUS";
    @PutMapping("/{status}")
    public Result setStatus(@PathVariable Integer status){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(KEY,status);
        return Result.success();
    }
    @GetMapping("/status")
    public Result get(){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Integer status = (Integer) valueOperations.get(KEY);
        return Result.success(status);
    }
}
