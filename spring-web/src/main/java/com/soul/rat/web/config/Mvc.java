package com.soul.rat.web.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;

/**
 * mvc配置
 *
 * @author 朱家兴
 * @date 2019-08-24 12:07
 */
@Slf4j
@Configuration
public class Mvc extends OncePerRequestFilter implements WebMvcConfigurer {

    private static final String OPTIONS = "OPTIONS";
    private static final String STATIC = "/static/**";

    @PostConstruct
    public void init() {

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String referer = request.getHeader("origin");
        if (StringUtils.isNotBlank(referer)) {
            URL url = new URL(referer);
            String origin = url.getProtocol() + "://" + url.getHost();
            if (url.getPort() != -1) {
                origin += ":" + url.getPort();
            }
            response.addHeader("Access-Control-Allow-Origin", origin);
            response.addHeader("Access-Control-Allow-Credentials", "true");
        } else {
            response.addHeader("Access-Control-Allow-Origin", "*");
        }
        response.addHeader("Access-Control-Allow-Methods", request.getHeader("Access-Control-Request-Method"));
        response.addHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));

        if (OPTIONS.equalsIgnoreCase(request.getMethod())) {
            return;
        }
        filterChain.doFilter(request, response);
    }

    /**
     * CORS跨域设置,不起作用
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    }

    /**
     * JSON序列化 用于将controller返回的实体类转换成json串
     */
    @Bean
    public FastJsonHttpMessageConverter jsonConverter() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(SerializerFeature.WriteNullBooleanAsFalse, SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNonStringKeyAsString, SerializerFeature.DisableCircularReferenceDetect);
        converter.setFastJsonConfig(config);
        return converter;
    }

    /**
     * 静态资源路径配置
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!registry.hasMappingForPattern(STATIC)) {
            registry.addResourceHandler(STATIC).addResourceLocations("classpath:/static/");
        }
    }
}
