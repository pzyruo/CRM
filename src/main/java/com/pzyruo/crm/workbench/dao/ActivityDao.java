package com.pzyruo.crm.workbench.dao;

import com.pzyruo.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityDao {
    int save(Activity a);

    List<Activity> getActivityListByCondition(Map<String, Object> map);
    int getTotalByCondition(Map<String, Object> map);

    int delete(List<String> ids);

    Activity getById(String id);

    int update(Activity a);

    Activity detail(String id);
}
