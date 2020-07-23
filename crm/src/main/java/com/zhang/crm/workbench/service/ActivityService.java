package com.zhang.crm.workbench.service;

import com.zhang.crm.settings.domain.User;
import com.zhang.crm.workbench.domain.Activity;

import java.util.List;

public interface ActivityService {
    List<User> addUser();

    boolean saveActivity(Activity activity);
}
