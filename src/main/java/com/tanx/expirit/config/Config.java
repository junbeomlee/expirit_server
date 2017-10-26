package com.tanx.expirit.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.tanx.expirit.UserResolver;
import com.tanx.expirit.user.UserRepository;
import com.tanx.expirit.util.ExpiritSessionInterceptor;

@Configuration
public class Config {

	@Bean
    public WebMvcConfigurerAdapter webMvcConfigurerAdapter() {
        return new WebMvcConfigurerAdapter() {
        	
        	@Autowired
        	UserRepository userRepository;
        	
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new ExpiritSessionInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/version")
                .excludePathPatterns("/login")
                .excludePathPatterns("/signUp")
                .excludePathPatterns("/videos/**")
                .excludePathPatterns("/test")
                .excludePathPatterns("/v2/api-docs");
          
            }
            
            @Override
            public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
            	UserResolver userResolver = new UserResolver(userRepository);
                argumentResolvers.add(userResolver);
            }
        };
    }
}
