package com.zhang.crm.settings.service;

import com.zhang.crm.settings.domain.User;

public interface UserService {

    User login(String userAct, String userPwd, String ip);
}
