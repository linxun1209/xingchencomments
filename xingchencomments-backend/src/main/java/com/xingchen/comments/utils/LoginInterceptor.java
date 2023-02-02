package com.xingchen.comments.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//这个对象是我们手动创建的对象，不是Spring创建的，所以这个类里面的对象属性，只能由构造函数注入
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1。判断是否需要拦截（TreadLocal中是否有用户）
        if(UserHolder.getUser()==null){
            log.info("======================================"+String.valueOf(UserHolder.getUser().getId()));
            response.setStatus(401);
            return false;
        }


        return true;
    }

    //controller执行之后
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    //把当前ThreadLocal中的信息清楚，然后检查登录状态时又重新查redis,获得数据，存储到当前ThreadLocal中，需要销毁用户信息，避免内存泄漏
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.removeUser();
    }
}
