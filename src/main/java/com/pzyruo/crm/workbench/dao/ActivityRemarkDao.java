package com.pzyruo.crm.workbench.dao;

import java.util.List;

public interface ActivityRemarkDao {
    int getCountByAids(List<String> ids);

    int deleteByAids(List<String> ids);
}
