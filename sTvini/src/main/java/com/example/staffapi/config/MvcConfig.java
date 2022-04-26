package com.example.staffapi.config;

import com.example.staffapi.StaffApiApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.example.staffapi.config.ConfigConstants.API_PREFIX;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        var packageName = StaffApiApplication.class.getPackageName();

        configurer.addPathPrefix(API_PREFIX,
                HandlerTypePredicate
                        .forBasePackage(packageName)
                        .and(HandlerTypePredicate.forAnnotation(RestController.class))
        );
    }
}