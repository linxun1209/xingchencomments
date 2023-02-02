package com.xingchen.comments.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xingchen.comments.dto.LoginFormDTO;
import com.xingchen.comments.dto.Result;
import com.xingchen.comments.entity.User;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
public interface IUserService extends IService<User> {

    Result sendCode(String phone, HttpSession session);

    Result login(LoginFormDTO loginForm, HttpSession session);

    Result sign();

    Result signCount();

}
