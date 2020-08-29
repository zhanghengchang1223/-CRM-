package com.zhang.crm.workbench.dao;

import com.zhang.crm.workbench.domain.Clue;

import java.util.List;
import java.util.Map;

public interface ClueDao {

    int saveClue(Clue clue);

    List<Clue> pageList(Map<String,Object> pageMap);

    Clue detailById(String id);
}
