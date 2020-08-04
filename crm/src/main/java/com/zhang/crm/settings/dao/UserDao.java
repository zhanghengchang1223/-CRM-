package com.zhang.crm.settings.dao;

import com.zhang.crm.settings.domain.User;


import java.util.List;
import java.util.Map;

// 用户Dao接口对应的mapper文件就是UserDao.xml
public interface UserDao {

    User login(Map<String, String> mapUser);

    List<User> getUserList();
}
