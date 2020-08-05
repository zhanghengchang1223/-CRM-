package com.zhang.crm.workbench.dao;

import com.zhang.crm.workbench.domain.DicValue;

import java.util.List;

public interface DicValueDao {
    List<DicValue> getValueByCode(String code);
}
