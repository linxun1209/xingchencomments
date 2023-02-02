package com.xingchen.comments;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


/**
 * @author xing'chen
 */
@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScan("com.xingchen.comments.mapper")
@SpringBootApplication
public class CommentsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommentsApplication.class, args);
    }

}
