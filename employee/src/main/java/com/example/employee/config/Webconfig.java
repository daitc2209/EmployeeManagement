//package com.example.employee.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//@EnableWebMvc
//public class Webconfig implements WebMvcConfigurer {
//    public void addCorsMappings (CorsRegistry registry){
//        registry.addMapping("/**")
//                .allowedOrigins("*")
//                .allowedMethods("PUT", "DELETE", "POST", "GET","OPTIONS","HEADER")
//                .allowCredentials(false).maxAge(3600);
//    }
//}
