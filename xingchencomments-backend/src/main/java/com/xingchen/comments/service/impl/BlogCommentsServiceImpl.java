package com.xingchen.comments.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xingchen.comments.entity.BlogComments;
import com.xingchen.comments.mapper.BlogCommentsMapper;
import com.xingchen.comments.service.IBlogCommentsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Service
public class BlogCommentsServiceImpl extends ServiceImpl<BlogCommentsMapper, BlogComments> implements IBlogCommentsService {

}
