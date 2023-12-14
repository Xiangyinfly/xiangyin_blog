package com.xiang.config;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 设置允许跨域的路径
        registry.addMapping("/**")
        // 设置允许跨域请求的域名
                .allowedOriginPatterns("*")
        // 是否允许cookie
                .allowCredentials(true)
        // 设置允许的请求方式
                .allowedMethods("GET", "POST", "DELETE", "PUT")
        // 设置允许的header属性
                .allowedHeaders("*")
        // 跨域允许时间
                .maxAge(3600);
    }

    //配置long类型序列化后都变成string类型！！！
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        Jackson2ObjectMapperBuilderCustomizer cunstomizer = jacksonObjectMapperBuilder -> {
            jacksonObjectMapperBuilder.serializerByType(Long.TYPE, ToStringSerializer.instance);
            jacksonObjectMapperBuilder.serializerByType(Long.class, ToStringSerializer.instance);
        };

        return cunstomizer;
    }
}