package com.zhang.crm.workbench.controller;

import com.zhang.crm.settings.domain.User;
import com.zhang.crm.utils.DateTimeUtil;
import com.zhang.crm.utils.PrintJson;
import com.zhang.crm.utils.ServiceFactory;
import com.zhang.crm.utils.UUIDUtil;
import com.zhang.crm.workbench.domain.Activity;
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
        }else if ("/workbench/activity/save.do".equals(path)){
            saveActivity(requset,response);
        }
    }

    private void saveActivity(HttpServletRequest requset, HttpServletResponse response) {
        ActivityService service = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        // 获取参数
        String id= UUIDUtil.getUUID();
        String owner=requset.getParameter("owner");
        String name=requset.getParameter("name");
        String startDate=requset.getParameter("startDate");
        String endDate=requset.getParameter("endDate");
        String cost=requset.getParameter("cost");
        String description=requset.getParameter("description");
        // 获取系统时间
        String createTime = DateTimeUtil.getSysTime();

        //将参数保存在对象中
        Activity activity = new Activity();
        activity.setId(id);
        activity.setId(owner);
        activity.setId(name);
        activity.setId(startDate);
        activity.setId(endDate);
        activity.setId(cost);
        activity.setId(description);

        boolean flag= service.saveActivity(activity);
        PrintJson.printJsonFlag(response,flag);
    }

    private void addUser(HttpServletRequest requset, HttpServletResponse response) {
        ActivityService service = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<User> userList = service.addUser();
        PrintJson.printJsonObj(response,userList);
    }
}
