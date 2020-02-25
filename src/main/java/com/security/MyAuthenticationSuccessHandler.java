package com.security;

import com.controller.UserConfigController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pojo.User;
import com.service.UserConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 如果使用异步登陆，
 自定义成功类需要实现AuthenticationSuccessHandler成功接口
 */
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    //使用spring默认支持的json数据处理依赖包,将对象转成json格式的字符串
    private ObjectMapper objectMapper = new ObjectMapper();
    //日志
    private static final Logger logger = LoggerFactory.getLogger(UserConfigController.class);
    @Autowired
    UserConfigService userConfigService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //这里调用接口,把登录时间存储到数据库
        User principal = (User)authentication.getPrincipal();
        logger.info(principal.getUsername()+"在"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"发起了登录请求");

        //将身份信息赋值
        principal.setIdentity(userConfigService.findIdentity(principal.getId()));
        //封装用户信息
        String s = objectMapper.writeValueAsString(principal);
        httpServletRequest.setAttribute("message",s);
        response.setContentType("text/json;charset=utf-8");
        //页面与可用的关系,现在可以根据登录情况下的getPrincipal得到用户的详细信息
        httpServletRequest.getRequestDispatcher("/usermessage").forward(httpServletRequest,response);
    }
}

