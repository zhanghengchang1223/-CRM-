package com.zhang.crm.workbench.service.impl;

import com.zhang.crm.settings.domain.User;
import com.zhang.crm.utils.SqlSessionUtil;
import com.zhang.crm.workbench.dao.ActivityDao;
import com.zhang.crm.workbench.domain.Activity;
import com.zhang.crm.workbench.service.ActivityService;
import com.zhang.crm.workbench.vo.PageListVo;

import java.util.List;
import java.util.Map;

public class ActivityServiceImpl implements ActivityService {
    // 这里会引入一个Dao对象
    private ActivityDao activityDao  = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    // 用户名称的查询
    @Override
    public List<User> addUser() {
        List<User> userList = activityDao.addUser();
        return userList;
    }
    // 进行市场活动的添加
    @Override
    public boolean saveActivity(Activity activity) {
        // 执行添加操作
        boolean flag = true;
        int count =activityDao.saveActivity(activity);
        if (count==0){
            flag=false;
        }
        return flag;
    }
    // 进行市场活动展示
    @Override
    public PageListVo<Activity> pageList(Map<String, Object> pageMap) {
        // 这里的totle和展示的list会根据查询条件的变化而变化
        int total = activityDao.getTotleByCondition(pageMap);
        List<Activity> activityList=activityDao.getActivityListByCondition(pageMap);
        PageListVo pageListVo = new PageListVo();
        pageListVo.setActivityList(activityList);
        pageListVo.setTotle(total);
        return pageListVo;
    }
}
