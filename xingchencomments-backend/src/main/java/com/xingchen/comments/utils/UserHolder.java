package com.xingchen.comments.utils;


import com.xingchen.comments.dto.UserDTO;

/**
 * 线程内缓存的用户信息
 * @author xing'chen
 */
public class UserHolder {
    private static final ThreadLocal<UserDTO> tl = new ThreadLocal<>();

    public static void saveUser(UserDTO user){
        tl.set(user);
    }

    public static UserDTO getUser(){
        return tl.get();
    }

    public static void removeUser(){
        tl.remove();
    }
}
