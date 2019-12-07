package com.security;

import com.controller.UserConfigController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 如果使用异步登陆，
 自定义成功类需要实现AuthenticationSuccessHandler成功接口
 */
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    //使用spring默认支持的json数据处理依赖包,将对象转成json格式的字符串
    private ObjectMapper objectMapper = new ObjectMapper();
    //日志
    private static final Logger logger = LoggerFactory.getLogger(UserConfigController.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //这里调用接口,把登录时间存储到数据库
        User principal = (User)authentication.getPrincipal();
        logger.info(principal.getUsername()+"在"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"发起了登录请求");

        //返回登录成功的信息,必要时在前端显示
        Map map = new HashMap<>();
        map.put("success", true);
        String jsonStr = objectMapper.writeValueAsString(map);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonStr);
    }
}

