package com.zhang.crm.workbench.controller;

import com.zhang.crm.settings.domain.User;
import com.zhang.crm.utils.PrintJson;
import com.zhang.crm.workbench.service.ActivityService;
import com.zhang.crm.workbench.service.impl.ActivityServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ActivityServlet extends HttpServlet {
    @Override
    public void service(HttpServletRequest requset, HttpServletResponse response)
            throws ServletException, IOException {

        // 进行路径的判断
        String path = requset.getServletPath();
        if ("/workbench/activity/add.do".equals(path)){
            addUser(requset,response);
        }
    }

    private void addUser(HttpServletRequest requset, HttpServletResponse response) {
        ActivityService service = new ActivityServiceImpl();
        List<User> userList = service.addUser();
        PrintJson.printJsonObj(response,userList);
    }
}
