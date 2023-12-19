package com.xiang;

import com.github.jeffreyning.mybatisplus.conf.EnableMPP;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.xiang.mapper")
@EnableScheduling
@EnableMPP
public class XiangyinBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(XiangyinBlogApplication.class,args);
    }

}

//问题：修改个人信息界面存在问题