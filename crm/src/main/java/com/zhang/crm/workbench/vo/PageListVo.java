package com.zhang.crm.workbench.vo;

import java.util.List;

public class PageListVo<T> {

    private int total;
    private List<T> activityList;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<T> activityList) {
        this.activityList = activityList;
    }
}
