package com.koko.blog.config;/*
@date   2022/1/28 - 18:58
@SH     Let's go! Fuck Everything!
*/

import com.koko.blog.interceptor.AdminLoginInterceptor;
import com.koko.blog.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class KokoWebMvcConfigurer implements WebMvcConfigurer {

    @Autowired
    private AdminLoginInterceptor adminLoginInterceptor;
    @Value("${file.filePathWindow}")
    private String filePathWindow;
    @Value("${file.filePathLinux}")
    private String filePathLinux;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminLoginInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/login")
                .excludePathPatterns("/admin/dist/**")
                .excludePathPatterns("/admin/plugins/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")){
            registry.addResourceHandler("/upload/**")
                    .addResourceLocations("file:"+ filePathWindow);
        }else{
            registry.addResourceHandler("/upload/**")
                    .addResourceLocations("file:"+ filePathLinux);
        }

    }
}
