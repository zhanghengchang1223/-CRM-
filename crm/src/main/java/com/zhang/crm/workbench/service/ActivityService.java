package com.zhang.crm.workbench.service;

import com.zhang.crm.settings.domain.User;
import com.zhang.crm.workbench.domain.Activity;
import com.zhang.crm.workbench.domain.ActivityMarker;
import com.zhang.crm.workbench.vo.PageListVo;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    List<User> addUser();

    boolean saveActivity(Activity activity);

    PageListVo<Activity> pageList(Map<String, Object> pageMap);

    boolean delete(String[] ids);

    Map<String, Object> update(String id);

    boolean updateInfo(Activity activity);

    Activity detail(String id);

    List<ActivityMarker> remarkContentList(String id);

    boolean remarkSave(ActivityMarker ac);

    List<Activity> detailList(String clueId);
}
