package com.zhang.crm.settings.domain;

/**
 * 用户实体类的创建，并创建set和get方法
 */
public class User {
    /**
     * 这里对于多个属性使用了批量操作
     * 1.在Navicat中将字段进行复制，然后粘贴到IDEA中
     * 2.按住Alt,鼠标按住下滑直接对所有四段一起操作
     */
    private String id ;  //用户编号
    private String loginAct;  //登录名称
    private String name;  // 实名制姓名
    private String loginPwd;  //登录密码
    private String email;  //邮箱
    private String expireTime;  //失效时间
    private String lockState;  //锁定状态 0：锁定 1：启用
    private String deptno;  //部门编号
    private String allowIps;  //允许访问的ip
    private String createTime;  //创建时间
    private String createBy;  //创建人
    private String editTime;  //修改时间
    private String editBy;  //修改人

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginAct() {
        return loginAct;
    }

    public void setLoginAct(String loginAct) {
        this.loginAct = loginAct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getLockState() {
        return lockState;
    }

    public void setLockState(String lockState) {
        this.lockState = lockState;
    }

    public String getDeptno() {
        return deptno;
    }

    public void setDeptno(String deptno) {
        this.deptno = deptno;
    }

    public String getAllowIps() {
        return allowIps;
    }

    public void setAllowIps(String allowIps) {
        this.allowIps = allowIps;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getEditTime() {
        return editTime;
    }

    public void setEditTime(String editTime) {
        this.editTime = editTime;
    }

    public String getEditBy() {
        return editBy;
    }

    public void setEditBy(String editBy) {
        this.editBy = editBy;
    }
}
