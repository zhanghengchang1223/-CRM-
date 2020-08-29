package com.zhang.crm.workbench.service.impl;

import com.zhang.crm.utils.SqlSessionUtil;
import com.zhang.crm.workbench.dao.ClueActivityRelationDao;
import com.zhang.crm.workbench.dao.ClueDao;
import com.zhang.crm.workbench.domain.Clue;
import com.zhang.crm.workbench.service.ClueService;

import java.util.List;
import java.util.Map;

public class ClueServiceImpl implements ClueService {

    private ClueDao clueDao = SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
    private ClueActivityRelationDao clueActivityRelationDao = SqlSessionUtil.getSqlSession().getMapper(ClueActivityRelationDao.class);
    @Override
    public boolean saveClue(Clue clue) {
        boolean flag = true;
        int count = clueDao.saveClue(clue);
        if (count==0){
            flag = false;
        }
        return flag;
    }

    @Override
    public List<Clue> pageList(Map<String, Object> pageMap) {
        List<Clue> clueList = clueDao.pageList(pageMap);
        return clueList;
    }

    @Override
    public Clue detail(String id) {
        Clue clue = clueDao.detailById(id);
        return clue;
    }

    @Override
    public boolean unbund(String id) {
        boolean flag = clueActivityRelationDao.unbund(id);
        return flag;
    }
}
