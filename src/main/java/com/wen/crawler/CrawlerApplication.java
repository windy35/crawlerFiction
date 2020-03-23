package com.wen.crawler;

import com.wen.crawler.config.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@SpringBootApplication
public class CrawlerApplication implements WebMvcConfigurer {
    @Resource
    private PassportInterceptor passportInterceptor;

    public static void main(String[] args) {
        SpringApplication.run(CrawlerApplication.class, args);
    }
    /**
     * 注册拦截器
     * @param registry
     */
    //注册拦截器，springboot已经做好静态资源(js/css等)的映射，无需额外配置
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor)//增加过滤的方法类
                .addPathPatterns("/**");//定义过滤的范围,拦截/下所有请求
//                .excludePathPatterns("/login");//排除某些请求，这些请求无需拦截
    }
}
