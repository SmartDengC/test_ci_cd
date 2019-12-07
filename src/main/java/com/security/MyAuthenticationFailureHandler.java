package com.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 处理登录失败的处理器
 */
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
    //使用spring默认支持的json数据处理依赖包,将对象转成json格式的字符串
    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        Map map = new HashMap<>();
        map.put("success", false);
        map.put("errorMsg",e.getMessage());
        String jsonStr = objectMapper.writeValueAsString(map);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonStr);
    }
}

