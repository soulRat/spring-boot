package com.soul.rat.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * 启动类
 *
 * @author zhujx
 */
@SpringBootApplication
@ComponentScan({"com.soul.rat"})
@EnableElasticsearchRepositories(basePackages = "com.soul.rat")
public class MyApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }


}
