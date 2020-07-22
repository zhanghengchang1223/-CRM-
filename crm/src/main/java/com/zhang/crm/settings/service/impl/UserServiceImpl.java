package com.zhang.crm.settings.service.impl;

import com.zhang.crm.settings.dao.UserDao;
import com.zhang.crm.settings.domain.User;
import com.zhang.crm.settings.exception.LoginException;
import com.zhang.crm.settings.service.UserService;
import com.zhang.crm.utils.DateTimeUtil;
import com.zhang.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.Map;

public class UserServiceImpl implements UserService {
    // 这里会引入一个Dao对象
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
    @Override
    public User login(String userAct, String userPwd, String ip) throws LoginException {
        /**
         * 这里就是进行账户，密码，ip，时间，有效日期等的判断
         */
        // 创建集合，将账户密码存进去
        Map<String,String> mapUser = new HashMap<String,String>();
        mapUser.put("userAct",userAct);
        mapUser.put("userPwd",userPwd);
        // 调用Dao中查询方法，返回User
        User user = userDao.login(mapUser);
        // 对user进行判断，啥问题没有就返回user
        if (user!=null) {
            // 在user中保存了很多数据库值
            // 取出expireTime，lockState，allowIps，跟User属性名相同
            String expireTime = user.getExpireTime();
            String allowIps = user.getAllowIps();
            String lockState = user.getLockState();
            // 获取系统当前时间，判断是否失效
            String systime = DateTimeUtil.getSysTime();
            if (expireTime.compareTo(systime) < 0) {
                throw new LoginException("此账号已失效！");
            //} else if (!allowIps.contains(ip)) {
              //  throw new LoginException("此账号不在允许ip范围！");
                // 这里将字符串放在前面防止了空指针的问题
            } else if ("0".equals(lockState)) {
                throw new LoginException("此账号已锁定！");
            }

        }else{
            throw new LoginException("账号密码错误！");
        }
        return user;
    }
}
