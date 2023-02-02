package com.xingchen.comments.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;

import com.xingchen.comments.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.xingchen.comments.utils.RedisConstants.LOGIN_USER_KEY;
import static com.xingchen.comments.utils.RedisConstants.LOGIN_USER_TTL;


/**
 * 自动刷新redis登录token有效期
 */
//这个拦截器将拦截所有对象，这个拦截器只有刷新redis的作用
public class RefreshTokenInterceptor implements HandlerInterceptor {
    //这个对象是我们手动创建的对象，不是Spring创建的，所以这个类里面的对象属性，只能由构造函数注入
    public RefreshTokenInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //TODO.1.获取请求头中的token
        String token = request.getHeader("authorization");
        //判断字符串是否为空
        if (StrUtil.isBlank(token)) {

            return true;
        }
        //TODO.2.基于token获取redis中的用户
        String key = RedisConstants.LOGIN_USER_KEY + token;
        Map<Object, Object> userMap = stringRedisTemplate.opsForHash().entries(key);
        //TODO。3.判断用户是否存在
        if (userMap.isEmpty()) {
            //TODO.4.不存在拦截

            return true;
        }
        //5.存在，存储用户信息到TheadLocal
        UserDTO userDTO = BeanUtil.fillBeanWithMap(userMap, new UserDTO(), false);
        //6.存在，存储用户信息到TheadLocal
        UserHolder.saveUser(userDTO);
        //7.刷新token的有效期
        stringRedisTemplate.expire(key, RedisConstants.LOGIN_USER_TTL, TimeUnit.MINUTES);
        //8.放行
        return true;
    }

    //controller执行之后
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    //需要销毁用户信息，避免内存泄漏
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.removeUser();
    }
}