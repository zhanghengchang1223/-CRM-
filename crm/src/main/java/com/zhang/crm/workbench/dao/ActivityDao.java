package com.zhang.crm.workbench.dao;

import com.zhang.crm.settings.domain.User;
import com.zhang.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityDao {
    // 查询数据库汇总的总人数信息
    List<User> addUser();
    // 进行市场活动的保存
    int saveActivity(Activity activity);
    // 格局查询条件得出的市场活动总条数
    int getTotleByCondition(Map<String, Object> pageMap);
    // 根据条件查询出的市场活动列表
    List<Activity> getActivityListByCondition(Map<String, Object> pageMap);
    // 根据ID值进行删除
    int delete(String[] ids);
    // 根据ID值进行查询
    Activity getById(String id);

    int updateInfo(Activity activity);

    Activity detail(String id);

    List<Activity> detailList(String clueId);
}
