package com.soul.rat.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * hello
 *
 * @author zhujx
 * @date 2021/12/17 09:59
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello,world!";
    }
}
