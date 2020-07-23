package com.zhang.crm.workbench.dao;

import com.zhang.crm.settings.domain.User;
import com.zhang.crm.workbench.domain.Activity;

import java.util.List;

public interface ActivityDao {
    List<User> addUser();

    int saveActivity(Activity activity);
}
