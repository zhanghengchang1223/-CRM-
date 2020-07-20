package com.zhang.crm.settings.service;

import com.zhang.crm.settings.domain.User;
import com.zhang.crm.settings.exception.LoginException;

public interface UserService {

    User login(String userAct, String userPwd, String ip) throws LoginException;
}
