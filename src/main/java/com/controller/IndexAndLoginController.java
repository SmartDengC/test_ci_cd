package com.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName IndexAndLoginController
 * @Version V1.0
 * @Author 唐世杰
 * @Date 2019/11/28 19:12
 * @Description TODO
 **/
@Controller
public class IndexAndLoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexAndLoginController.class);

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/index")
    public String index(){
        return "index";
    }


    public static void main(String[] args) {

        LOGGER.info("Test log4j2 info");
        LOGGER.warn("Test log4j2 warn");
        LOGGER.error("Test log4j2 error");
        System.out.println(System.getProperty("user.home"));
    }
}