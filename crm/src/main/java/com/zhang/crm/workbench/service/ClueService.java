package com.zhang.crm.workbench.service;

import com.zhang.crm.workbench.domain.Clue;

import java.util.List;
import java.util.Map;

public interface ClueService {
    boolean saveClue(Clue clue);

    List<Clue> pageList(Map<String, Object> pageMap);

    Clue detail(String id);

    boolean unbund(String id);
}
