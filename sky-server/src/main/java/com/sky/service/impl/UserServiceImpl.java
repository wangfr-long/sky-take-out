package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.mapper.UserMapper;
import com.sky.properties.JwtProperties;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";
    public static final String GRANT_TYPE = "authorization_code";
    @Autowired
    private WeChatProperties weChatProperties;

    @Autowired
    private UserMapper userMapper;
    @Override
    public User login(UserLoginDTO userLoginDTO) {
        String openid = getOpenId(userLoginDTO.getCode());
        //判断openid是否为空
        if (openid == null) {
            throw new LayerInstantiationException(MessageConstant.LOGIN_FAILED);
        }
        //查看用户是否存在;
         User user= userMapper.selectByOpenId(openid);
        if (user==null){
            User user1 = new User();
            user1.setOpenid(openid);
            userMapper.insert(user1);
            return user1;
        }
        return user;
    }
    private String getOpenId(String code){
        Map<String, String> map = new HashMap<>();
        map.put("appid", weChatProperties.getAppid());
        map.put("grant_type", GRANT_TYPE);
        map.put("secret", weChatProperties.getSecret());
        map.put("js_code", code);
        String s = HttpClientUtil.doGet(WX_LOGIN, map);
        JSONObject jsonObject = JSON.parseObject(s);
        String openid = (String) jsonObject.get("openid");
        return openid;
    }
}
