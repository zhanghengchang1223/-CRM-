package com.zhang.crm.listener;

import com.zhang.crm.utils.ServiceFactory;
import com.zhang.crm.workbench.domain.DicValue;
import com.zhang.crm.workbench.service.DicService;
import com.zhang.crm.workbench.service.impl.DicServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SystemInitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // 传递进来的参数就是能够监听作用域
        // 这里创建的全局作用域对象的作用就是接收数据库传来的数据
        // 数据字典传输过来的数据是map中装的list
        // 创建上下文作用域
        ServletContext application = sce.getServletContext();
        DicService dicService = (DicService) ServiceFactory.getService(new DicServiceImpl());
        Map<String, List<DicValue>> map  = dicService.getType();
        // 取出map中的key
        Set<String> set = map.keySet();
        for (String key:set){
            System.out.println("key====>"+key);
            application.setAttribute(key,map.get(key));
        }
    }
}
