package com.koko.blog;/*
@date   2022/1/28 - 15:53
@SH     Let's go! Fuck Everything!
*/

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * springboot启动类
 */
//代码生成器的dao接口上已经标注了@mapper注解,
// 但是以后可能会有自定义接口,保险起见加上.
@MapperScan("com.koko.blog.dao")
@SpringBootApplication
public class KokoBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(KokoBlogApplication.class,args);
    }
}
