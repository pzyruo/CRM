package com.pzyruo.crm.workbench.dao;

import com.pzyruo.crm.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkDao {
    int getCountByAids(List<String> ids);

    int deleteByAids(List<String> ids);

    List<ActivityRemark> getRemarkListById(String activityId);

    int deleteById(String id);

    int saveRemark(ActivityRemark ar);
}
