package com.zhang.crm.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class loginFilter implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        /**
         * 这里我们要使用重定向方式指定路径，并从session域中取对象进行判断，ServletRquest不能满足需求，
         * 需要使用HttpServletRquest
         */
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        // 获取session
        HttpSession session = request.getSession();
        // 这里对于login.jsp和login.do不能拦截，不然就会无限循环
        // 对路径进行判断
        String path = request.getServletPath();
        if("/login.do".equals(path) || "/login.jsp".equals(path)){
            filterChain.doFilter(request,response);
        }else {
            if (session != null) {
                // 放行
                filterChain.doFilter(request, response);
            } else {
                // 重定向,request.getContextPath()获取项目路径
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            }
        }
    }
}
