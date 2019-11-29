package com.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 如果使用异步登陆，
 自定义成功类需要实现AuthenticationSuccessHandler成功接口
 */
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    //使用spring默认支持的json数据处理依赖包,将对象转成json格式的字符串
    private ObjectMapper objectMapper = new ObjectMapper();

    /*
    authentication代表认证成功后的信息
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Map map = new HashMap<>();
        map.put("success", true);
        String jsonStr = objectMapper.writeValueAsString(map);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonStr);
    }
}

