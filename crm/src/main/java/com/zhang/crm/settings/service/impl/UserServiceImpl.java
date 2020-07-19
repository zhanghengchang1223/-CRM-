package com.zhang.crm.settings.service.impl;

import com.zhang.crm.settings.dao.UserDao;
import com.zhang.crm.settings.domain.User;
import com.zhang.crm.settings.service.UserService;
import com.zhang.crm.utils.SqlSessionUtil;

public class UserServiceImpl implements UserService {
    // 这里会引入一个Dao对象
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
    @Override
    public User login(String userAct, String userPwd, String ip) {
        /**
         * 这里就是进行账户，密码，ip，时间，有效日期等的判断
         */
        User user = new User();
        return user;
    }
}
