package com.zhang.crm.workbench.service.impl;

import com.zhang.crm.utils.SqlSessionUtil;
import com.zhang.crm.workbench.dao.DicTypeDao;
import com.zhang.crm.workbench.dao.DicValueDao;
import com.zhang.crm.workbench.domain.DicType;
import com.zhang.crm.workbench.domain.DicValue;
import com.zhang.crm.workbench.service.DicService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DicServiceImpl implements DicService {
    @Override
    public Map<String, List<DicValue>> getType() {
        Map<String, List<DicValue>> map = new HashMap<>();
        // 首先从数据库中将所有数据存储在list
        DicTypeDao dicTypeDao = SqlSessionUtil.getSqlSession().getMapper(DicTypeDao.class);
        DicValueDao dicVlaueDao = SqlSessionUtil.getSqlSession().getMapper(DicValueDao.class);
        List<DicType> dicList = dicTypeDao.getAll();
        // 遍历dicList，取出code
        for(DicType type:dicList) {
           String code = type.getCode();
           // 查dicValued的数据
           List<DicValue> dicValueList= dicVlaueDao.getValueByCode(code);
           map.put(code+"",dicValueList);
        }
        return map;
    }
}
