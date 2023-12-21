package com.xiang;

import com.github.jeffreyning.mybatisplus.conf.EnableMPP;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xiang.mapper")
@EnableMPP
public class BlogAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogAdminApplication.class, args);
    }
}

//TODO 查询菜单树的算法有问题！！！
//TODO 中间表删除就是物理删除
//TODO 所有模糊查询的非空判断条件换成Objects.nonNull