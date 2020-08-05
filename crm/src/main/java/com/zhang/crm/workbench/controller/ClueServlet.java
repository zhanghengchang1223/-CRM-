package com.zhang.crm.workbench.controller;

import com.zhang.crm.settings.domain.User;
import com.zhang.crm.settings.service.UserService;
import com.zhang.crm.utils.PrintJson;
import com.zhang.crm.utils.ServiceFactory;
import com.zhang.crm.workbench.service.ActivityService;
import com.zhang.crm.workbench.service.impl.ActivityServiceImpl;
import com.zhang.crm.workbench.service.impl.ClueServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ClueServlet extends HttpServlet {
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 进行路径的判断
        String path = request.getServletPath();
        if ("/workbench/clue/getUserList.do".equals(path)){
            addUser(request,response);
        }
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response) {
        ActivityService service = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<User> userList = service.addUser();
        PrintJson.printJsonObj(response,userList);
    }
}
