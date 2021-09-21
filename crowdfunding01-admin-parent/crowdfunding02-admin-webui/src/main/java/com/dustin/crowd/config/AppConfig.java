package com.dustin.crowd.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.dustin.crowd.mvc.interceptor.LoginInterceptor;

@Configuration
public class AppConfig implements WebMvcConfigurer {
//
	@Autowired
	LoginInterceptor loginInterceptor;
//
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/admin/to/login/page.html").setViewName("admin-login");
		registry.addViewController("/admin/to/main/page.html").setViewName("admin-main");
		registry.addViewController("/").setViewName("admin-main");
//		<mvc:view-controller path="/admin/to/login/page.html" view-name="admin-login"/>
//		<mvc:view-controller path="/admin/to/main/page.html" view-name="admin-main"/>
	}
//
//
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginInterceptor).addPathPatterns("/**")
				.excludePathPatterns("/admin/to/login/page.html")
				.excludePathPatterns("/admin/do/login.html")
				.excludePathPatterns("/admin/do/logout.html")
				.excludePathPatterns("/bootstrap/**","/css/**","/fonts/**","/img/**","/jquery/**")
				.excludePathPatterns("/layer/**","/script/**","/ztree/**");
	}
	
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
////		 registry.addResourceHandler("/resource/**").addResourceLocations("/static/");;
//	}
}
