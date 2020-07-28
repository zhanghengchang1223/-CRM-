package com.zhang.crm.workbench.controller;


import com.zhang.crm.settings.domain.User;
import com.zhang.crm.utils.DateTimeUtil;
import com.zhang.crm.utils.PrintJson;
import com.zhang.crm.utils.ServiceFactory;
import com.zhang.crm.utils.UUIDUtil;
import com.zhang.crm.workbench.domain.Activity;
import com.zhang.crm.workbench.service.ActivityService;
import com.zhang.crm.workbench.service.impl.ActivityServiceImpl;
import com.zhang.crm.workbench.vo.PageListVo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        }else if ("/workbench/activity/pageList.do".equals(path)){
            pageList(requset,response);
        }else if ("/workbench/activity/delete.do".equals(path)){
            delete(requset,response);
        }
    }

    private void delete(HttpServletRequest requset, HttpServletResponse response) {
        // 获取前端传递的参数
        String[] ids = requset.getParameterValues("id");
        ActivityService service = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = service.delete(ids);
        PrintJson.printJsonFlag(response,flag);
    }

    private void pageList(HttpServletRequest requset, HttpServletResponse response) {
        System.out.println("pageList方法执行");
        // 获取参数
        String owner=requset.getParameter("owner");
        String name=requset.getParameter("name");
        String startDate=requset.getParameter("startDate");
        String endDate=requset.getParameter("endDate");
        String pageNoStr=requset.getParameter("pageNo");
        int pageNo = Integer.valueOf(pageNoStr);
        String pageSizeStr=requset.getParameter("pageSize");
        int pageSize = Integer.valueOf(pageSizeStr);
        // 计算出略过的记录数
        int skipCount = (pageNo-1)*pageSize;
        // 将传递过来的参数尽心Map
        Map<String,Object> pageMap = new HashMap<>();
        pageMap.put("owner",owner);
        pageMap.put("name",name);
        pageMap.put("pageSize",pageSize);
        pageMap.put("skipCount",skipCount);
        pageMap.put("startDate",startDate);
        pageMap.put("endDate",endDate);

        // 这里返回结果是totle和List数据，因为是经常使用的，所以此处创建一个vo类进行装返回值
        ActivityService service = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        // 调用service中的方法并传递参数
        PageListVo<Activity> page = service.pageList(pageMap);
        PrintJson.printJsonObj(response,page);
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
        activity.setOwner(owner);
        activity.setName(name);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);
        activity.setCost(cost);
        activity.setDescription(description);
        activity.setCreateTime(createTime);

        boolean flag= service.saveActivity(activity);
        PrintJson.printJsonFlag(response,flag);
    }

    private void addUser(HttpServletRequest requset, HttpServletResponse response) {
        ActivityService service = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<User> userList = service.addUser();
        PrintJson.printJsonObj(response,userList);
    }
}
