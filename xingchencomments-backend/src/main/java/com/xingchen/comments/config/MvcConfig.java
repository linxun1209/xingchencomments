package com.xingchen.comments.config;


import com.xingchen.comments.utils.LoginInterceptor;
import com.xingchen.comments.utils.RefreshTokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;


/**
 * 拦截器配置
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {


    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).excludePathPatterns(
                "/user/code",
                "/user/login",
                "/log/hot",
                "/shop/**", ////放行以shop打头的所有请求
                "/shop-type/**",
                "/upload/**",
                "/voucher/**"
        ).order(1);
        registry.addInterceptor(new RefreshTokenInterceptor(stringRedisTemplate)).addPathPatterns("/**").order(0);
        //拦截所有请求

        //order是拦截器执行的顺序，值越大优先级越低，一般默认为0

    }


}
