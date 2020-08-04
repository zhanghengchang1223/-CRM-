package com.zhang.crm.workbench.dao;

import com.zhang.crm.workbench.domain.ActivityMarker;

import java.util.List;

public interface ActivityMarkerDao {
    // 获取相应id关联的数据数量
    int getCountByIds(String[] ids);
    // 实际删除的数量
    int deleteCountByIds(String[] ids);

    List<ActivityMarker> remarkerListByActivityId(String id);

    int remarkSave(ActivityMarker ac);
}
