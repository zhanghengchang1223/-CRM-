package com.zhang.crm.workbench.service.impl;

import com.zhang.crm.settings.domain.User;
import com.zhang.crm.utils.SqlSessionUtil;
import com.zhang.crm.workbench.dao.ActivityDao;
import com.zhang.crm.workbench.dao.ActivityMarkerDao;
import com.zhang.crm.workbench.domain.Activity;
import com.zhang.crm.workbench.domain.ActivityMarker;
import com.zhang.crm.workbench.service.ActivityService;
import com.zhang.crm.workbench.vo.PageListVo;

import java.util.List;
import java.util.Map;

public class ActivityServiceImpl implements ActivityService {
    // 这里会引入一个Dao对象
    private ActivityDao activityDao  = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private ActivityMarkerDao activityMarkerDaoDao  = SqlSessionUtil.getSqlSession().getMapper(ActivityMarkerDao.class);

    // 用户名称的查询
    @Override
    public List<User> addUser() {
        List<User> userList = activityDao.addUser();
        return userList;
    }
    // 进行市场活动的添加
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
    // 进行市场活动展示
    @Override
    public PageListVo<Activity> pageList(Map<String, Object> pageMap) {
        // 这里的totle和展示的list会根据查询条件的变化而变化
        int total = activityDao.getTotleByCondition(pageMap);
        List<Activity> activityList=activityDao.getActivityListByCondition(pageMap);
        // 将得到的数据使用Vo进行存储传值
        PageListVo pageListVo = new PageListVo();
        pageListVo.setActivityList(activityList);
        pageListVo.setTotal(total);
        return pageListVo;
    }

    @Override
    public boolean delete(String[] ids) {
        boolean flag = true;
        // 查询出需要删除的备注的数量
        int count1 = activityMarkerDaoDao.getCountByIds(ids);
        // 删除备注，返回受影响的条数
        int count2 = activityMarkerDaoDao.deleteCountByIds(ids);
        if (count1!=count2){
            flag=false;
        }
        // 删除市场活动
        int count3 = activityDao.delete(ids);
        if (count3!=ids.length){
            flag=false;
        }
        return flag;
    }
}
