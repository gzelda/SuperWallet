package com.superwallet.utils;


import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AccessFilter extends HttpServlet implements Filter {

    private static final long serialVersionUID = 1L;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    ////设置其他IP地址的机器可以直接访问到这个项目的后端
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) res;
        String[] allowDomain = {"http://localhost:8080", "http://localhost:8081", "http://localhost:8082"};
        Set allowedOrigins = new HashSet(Arrays.asList(allowDomain));
        String originHeader = ((HttpServletRequest) req).getHeader("Origin");
        if (allowedOrigins.contains(originHeader)) {
            httpResponse.setHeader("Access-Control-Allow-Origin", originHeader);
            httpResponse.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        }
        chain.doFilter(req, httpResponse);
    }
}
