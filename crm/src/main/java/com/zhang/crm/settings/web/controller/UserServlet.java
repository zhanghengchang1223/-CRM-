package com.zhang.crm.settings.web.controller;

import com.zhang.crm.settings.domain.User;

import com.zhang.crm.settings.exception.LoginException;
import com.zhang.crm.settings.service.UserService;
import com.zhang.crm.settings.service.impl.UserServiceImpl;
import com.zhang.crm.utils.MD5Util;
import com.zhang.crm.utils.PrintJson;
import com.zhang.crm.utils.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserServlet extends HttpServlet {
    @Override
    public void service(HttpServletRequest requset, HttpServletResponse response)
            throws ServletException, IOException {

        // 进行路径的判断
        String path = requset.getServletPath();
        if ("/settings/user/login.do".equals(path)){
                login(requset,response);
        }
    }
    // 进行登录验证
    public void login(HttpServletRequest request, HttpServletResponse response) {
        // 获取前端传来的参数，账户，密码，ip
        String userAct = request.getParameter("userAct");
        String userPwd = request.getParameter("userPwd");
        String ip = request.getRemoteAddr();
        // 将密码转换成密文形式
        //userPwd = MD5Util.getMD5(userPwd);
        // 创建service对象,这里使用了动态代理的方式
        UserService userService = (UserService)ServiceFactory.getService(new UserServiceImpl());
        try {
            // 这里调用service中的login方法，并返回user类型的值，如果什么都没返回就是验证失败
            User user = userService.login(userAct,userPwd,ip);
            // 将账户密码存储到session域中
            request.getSession().setAttribute("user",user);
            // 程序执行到这里，说明验证通过，给前台返回一个true
            // 下面的字符串时一个json方式，适用于单个值
            // String str = "{\"success\":true}";
            // response.getWriter().print(str);
            // 这里使用util中的方法
            PrintJson.printJsonFlag(response,true);
        }catch (Exception e){
            e.printStackTrace();
            // 程序执行这里就表示验证失败，user有问题,将错误的异常给前台
            String msg = e.getMessage();
            // 这里使用map类型装success和msg
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("success",false);
            map.put("msg",msg);
            PrintJson.printJsonObj(response,map);
        }
    }
}
