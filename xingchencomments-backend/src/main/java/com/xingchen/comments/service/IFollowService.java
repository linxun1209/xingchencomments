package com.xingchen.comments.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xingchen.comments.dto.Result;
import com.xingchen.comments.entity.Follow;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xing'chen
 * @since 2021-12-22
 */
public interface IFollowService extends IService<Follow> {

    Result follow(Long followUserId, Boolean isFollow);

    Result isFollow(Long followUserId);

    Result followCommons(Long id);
}
