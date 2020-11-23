package com.pzyruo.crm.workbench.service;

import com.pzyruo.crm.vo.PaginationVo;
import com.pzyruo.crm.workbench.domain.Activity;

import java.util.Map;

public interface ActivityService {

    boolean save(Activity a);

    PaginationVo<Activity> pageList(Map<String, Object> map);

    boolean delete(String ids);
}
