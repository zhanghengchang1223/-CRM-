package com.zhang.crm.workbench.vo;

import java.util.List;

public class PageListVo<T> {

    private int totle;
    private List<T> activityList;

    public int getTotle() {
        return totle;
    }

    public void setTotle(int totle) {
        this.totle = totle;
    }

    public List<T> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<T> activityList) {
        this.activityList = activityList;
    }
}
