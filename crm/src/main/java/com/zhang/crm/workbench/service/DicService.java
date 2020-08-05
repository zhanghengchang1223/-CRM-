package com.zhang.crm.workbench.service;

import com.zhang.crm.workbench.domain.DicValue;

import java.util.List;
import java.util.Map;

public interface DicService {
    Map<String, List<DicValue>> getType();
}
