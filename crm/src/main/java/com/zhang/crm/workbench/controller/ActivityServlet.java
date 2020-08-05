package com.zhang.crm.workbench.controller;


import com.oracle.deploy.update.UpdateInfo;
import com.zhang.crm.settings.domain.User;
import com.zhang.crm.utils.DateTimeUtil;
import com.zhang.crm.utils.PrintJson;
import com.zhang.crm.utils.ServiceFactory;
import com.zhang.crm.utils.UUIDUtil;
import com.zhang.crm.workbench.domain.Activity;
import com.zhang.crm.workbench.domain.ActivityMarker;
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
        }else if ("/workbench/activity/update.do".equals(path)){
            update(requset,response);
        }else if ("/workbench/activity/updateInfo.do".equals(path)){
            updateInfo(requset,response);
        }else if ("/workbench/activity/detail.do".equals(path)){
            detail(requset,response);
        }else if ("/workbench/activity/remarkContentList.do".equals(path)){
            remarkContentList(requset,response);
        }else if ("/workbench/activity/remarkSave.do".equals(path)){
            remarkSave(requset,response);
        }
    }
    // 进行备注信息的添加保存操作
    private void remarkSave(HttpServletRequest requset, HttpServletResponse response) {
        // 输入参数
        String id = UUIDUtil.getUUID();
        String noteContent = requset.getParameter("noteContent");
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User)requset.getSession().getAttribute("user")).getName();
        String editFlag ="0";
        String activityId =requset.getParameter("id");

        ActivityMarker ac = new ActivityMarker();
        ac.setActivityId(activityId);
        ac.setCreateBy(createBy);
        ac.setCreateTime(createTime);
        ac.setEditFlag(editFlag);
        ac.setId(id);
        ac.setNoteContent(noteContent);
        ActivityService service = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag=service.remarkSave(ac);
        // 可以将ac的信息传递给前端
        Map<String,Object> map = new HashMap<>();
        map.put("flag",flag);
        map.put("ac",ac);
        PrintJson.printJsonObj(response,map);
    }

    // 获取备注信息
    private void remarkContentList(HttpServletRequest requset, HttpServletResponse response) {
        // 这里获得的id是remark列表的外键id
        String id= requset.getParameter("id");
        ActivityService service = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<ActivityMarker> acList = service.remarkContentList(id);  // 返回备注信息
        PrintJson.printJsonObj(response,acList);
    }

    // 获取详细信息列表
    private void detail(HttpServletRequest requset, HttpServletResponse response) throws ServletException, IOException {
        String id= requset.getParameter("id");
        ActivityService service = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        // 根据传入的id调用方法，在地城查询数据库，返回相应的activityList
        Activity activity = service.detail(id);
        // 使用传统的转发重定向
        // 首先将得到的activity保存到request域中，为啥是request？，就近原则呗
        requset.setAttribute("activity",activity);
        // 转发,不能使用重定向
        requset.getRequestDispatcher("/workbench/activity/detail.jsp").forward(requset,response);
    }
    // 进行信息的修改
    private void updateInfo(HttpServletRequest requset, HttpServletResponse response) {
        ActivityService service = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        // 获取参数
        String id= requset.getParameter("id");
        String owner=requset.getParameter("owner");
        String name=requset.getParameter("name");
        String startDate=requset.getParameter("startDate");
        String endDate=requset.getParameter("endDate");
        String cost=requset.getParameter("cost");
        String description=requset.getParameter("description");
        // 获取修改系统时间
        String editTime = DateTimeUtil.getSysTime();
        // 修改人当前登录用户
        String editBy  = ((User)requset.getSession().getAttribute("user")).getName();
        //将参数保存在对象中
        Activity activity = new Activity();
        activity.setId(id);
        activity.setOwner(owner);
        activity.setName(name);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);
        activity.setCost(cost);
        activity.setDescription(description);
        activity.setEditTime(editTime);
        activity.setEditBy(editBy);

        boolean flag= service.updateInfo(activity);
        PrintJson.printJsonFlag(response,flag);
    }

    private void update(HttpServletRequest requset, HttpServletResponse response) {
        // 获取前端传递的参数
        String id=requset.getParameter("id");
        ActivityService service = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        // 执行service方法后会返回两个数据ulist，alist,使用Map装数据
        Map<String,Object> date = service.update(id);
        PrintJson.printJsonObj(response,date);
    }
    // 活动信息删除
    private void delete(HttpServletRequest requset, HttpServletResponse response) {
        // 获取前端传递的参数
        String[] ids = requset.getParameterValues("id");
        ActivityService service = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = service.delete(ids);
        PrintJson.printJsonFlag(response,flag);
    }
   // 活动信息页面展示
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
    // 获取用户列表
    private void addUser(HttpServletRequest requset, HttpServletResponse response) {
        ActivityService service = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<User> userList = service.addUser();
        PrintJson.printJsonObj(response,userList);
    }
}
