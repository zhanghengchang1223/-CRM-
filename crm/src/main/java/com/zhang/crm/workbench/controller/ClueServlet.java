package com.zhang.crm.workbench.controller;

import com.zhang.crm.settings.domain.User;
import com.zhang.crm.settings.service.UserService;
import com.zhang.crm.utils.DateTimeUtil;
import com.zhang.crm.utils.PrintJson;
import com.zhang.crm.utils.ServiceFactory;
import com.zhang.crm.utils.UUIDUtil;
import com.zhang.crm.workbench.domain.Activity;
import com.zhang.crm.workbench.domain.Clue;
import com.zhang.crm.workbench.service.ActivityService;
import com.zhang.crm.workbench.service.ClueService;
import com.zhang.crm.workbench.service.impl.ActivityServiceImpl;
import com.zhang.crm.workbench.service.impl.ClueServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ClueServlet extends HttpServlet {
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 进行路径的判断
        String path = request.getServletPath();
        if ("/workbench/clue/getUserList.do".equals(path)){
            addUser(request,response);
        }else if ("/workbench/clue/saveClue.do".equals(path)){
            saveClue(request,response);
        }else if ("/workbench/clue/pageList.do".equals(path)){
            pageList(request,response);
        }else if ("/workbench/clue/detail.do".equals(path)){
            detail(request,response);
        }else if ("/workbench/clue/detailList.do".equals(path)){
            detailList(request,response);
        }else if ("/workbench/clue/unbund.do".equals(path)){
            unbund(request,response);
        }
    }
    // 解除活动关联
    private void unbund(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        boolean flag  = clueService.unbund(id);
        PrintJson.printJsonFlag(response,flag);
    }

    // 获取关联活动列表
    private void detailList(HttpServletRequest request, HttpServletResponse response) {
        String clueId = request.getParameter("clueId");
        ActivityService service = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<Activity> activityList = service.detailList(clueId);
        PrintJson.printJsonObj(response,activityList);
    }

    //获取详细页列表
    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        Clue clue = clueService.detail(id);
        request.setAttribute("clue",clue);
        request.getRequestDispatcher("/workbench/clue/detail.jsp").forward(request,response);
    }

    // 进行线索列表查询并分页
    private void pageList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("pageList方法");
        String pageNoStr=request.getParameter("pageNo");
        int pageNo = Integer.valueOf(pageNoStr);
        String pageSizeStr=request.getParameter("pageSize");
        int pageSize = Integer.valueOf(pageSizeStr);
        // 计算出略过的记录数
        int skipCount = (pageNo-1)*pageSize;
        // 将传递过来的参数尽心Map
        Map<String,Object> pageMap = new HashMap<>();
        pageMap.put("pageSize",pageSize);
        pageMap.put("skipCount",skipCount);

        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        List<Clue> clueList = clueService.pageList(pageMap);
        PrintJson.printJsonObj(response,clueList);
    }

    // 保存线索信息
    private void saveClue(HttpServletRequest request, HttpServletResponse response) {
        // 接收参数
        String id = UUIDUtil.getUUID();  // 随机生成的ID
        String fullname = request.getParameter("fullname");
        String appellation = request.getParameter("appellation");
        String owner = request.getParameter("owner");
        String company = request.getParameter("company");
        String job = request.getParameter("job");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String website = request.getParameter("website");
        String mphone = request.getParameter("mphone");
        String state = request.getParameter("state");
        String source = request.getParameter("source");
        String createBy = ((User)request.getSession().getAttribute("user")).getName();  // 这个值得注意
        String createTime =  DateTimeUtil.getSysTime();
        String description = request.getParameter("description");
        String contactSummary = request.getParameter("contactSummary");

        // 将取得的参数进行打包成对象
        Clue clue = new Clue();
        clue.setId(id);
        clue.setFullname(fullname);
        clue.setAppellation(appellation);
        clue.setOwner(owner);
        clue.setCompany(company);
        clue.setJob(job);
        clue.setEmail(email);
        clue.setPhone(phone);
        clue.setWebsite(website);
        clue.setMphone(mphone);
        clue.setState(state);
        clue.setSource(source);
        clue.setCreateBy(createBy);
        clue.setCreateTime(createTime);
        clue.setDescription(description);
        clue.setContactSummary(contactSummary);
        /*request.setAttribute("clue",clue);*/
        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        boolean flag = clueService.saveClue(clue);
        PrintJson.printJsonFlag(response,flag);
    }
    // 查询线索提供提供人列表
    private void addUser(HttpServletRequest request, HttpServletResponse response) {
        ActivityService service = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<User> userList = service.addUser();
        PrintJson.printJsonObj(response,userList);
    }
}
