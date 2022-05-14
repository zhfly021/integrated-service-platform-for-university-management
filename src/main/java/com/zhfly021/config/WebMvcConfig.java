package com.zhfly021.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author： wzz
 * @date： 2021-03-18 14:10
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${path.savePath")
    String path;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("file:" + path + "/static");
    }

}
