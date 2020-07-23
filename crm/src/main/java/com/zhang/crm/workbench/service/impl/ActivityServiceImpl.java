package com.zhang.crm.workbench.service.impl;

import com.zhang.crm.settings.domain.User;
import com.zhang.crm.utils.SqlSessionUtil;
import com.zhang.crm.workbench.dao.ActivityDao;
import com.zhang.crm.workbench.service.ActivityService;

import java.util.List;

public class ActivityServiceImpl implements ActivityService {
    // 这里会引入一个Dao对象
    private ActivityDao userDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    @Override
    public List<User> addUser() {
        List<User> userList = userDao.addUser();
        return userList;
    }
}