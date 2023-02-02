package com.xingchen.comments.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xingchen.comments.dto.Result;
import com.xingchen.comments.entity.Blog;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xing'chen
 * @since 2021-12-22
 */
public interface IBlogService extends IService<Blog> {

    Result queryHotBlog(Integer current);

    Result queryBlogById(Long id);

    Result likeBlog(Long id);

    Result queryBlogLikes(Long id);

    Result saveBlog(Blog blog);

    Result queryBlogOfFollow(Long max, Integer offset);

}
