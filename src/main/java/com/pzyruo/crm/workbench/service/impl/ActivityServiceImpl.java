package com.pzyruo.crm.workbench.service.impl;

import com.pzyruo.crm.utils.SqlSessionUtil;
import com.pzyruo.crm.workbench.dao.ActivityDao;
import com.pzyruo.crm.workbench.service.ActivityService;

public class ActivityServiceImpl implements ActivityService {
    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
}
