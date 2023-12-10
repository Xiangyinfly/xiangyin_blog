package com.xiang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.xiang.mapper")
@EnableScheduling
public class XiangyinBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(XiangyinBlogApplication.class,args);
    }

}
