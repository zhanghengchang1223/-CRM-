package com.zhang.crm.workbench.service.impl;

import com.zhang.crm.settings.domain.User;
import com.zhang.crm.utils.SqlSessionUtil;
import com.zhang.crm.workbench.dao.ActivityDao;
import com.zhang.crm.workbench.domain.Activity;
import com.zhang.crm.workbench.service.ActivityService;

import java.util.List;

public class ActivityServiceImpl implements ActivityService {
    // 这里会引入一个Dao对象
    private ActivityDao activityDao  = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    @Override
    public List<User> addUser() {
        List<User> userList = activityDao.addUser();
        return userList;
    }

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
}
