package com.pzyruo.crm.workbench.service.impl;

import com.pzyruo.crm.settings.dao.UserDao;
import com.pzyruo.crm.settings.domain.User;
import com.pzyruo.crm.utils.SqlSessionUtil;
import com.pzyruo.crm.vo.PaginationVo;
import com.pzyruo.crm.workbench.dao.ActivityDao;
import com.pzyruo.crm.workbench.dao.ActivityRemarkDao;
import com.pzyruo.crm.workbench.domain.Activity;
import com.pzyruo.crm.workbench.domain.ActivityRemark;
import com.pzyruo.crm.workbench.service.ActivityService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityServiceImpl implements ActivityService {
    private final ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private final ActivityRemarkDao activityRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);
    private final UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
    @Override
    public boolean save(Activity a) {
        boolean flag = true;
        int count = activityDao.save(a);
        if (count!=1){
            flag = false;
        }

        return flag;
    }

    @Override
    public PaginationVo<Activity> pageList(Map<String, Object> map) {
        System.out.println("我进来了");
        //取得total
        int total = activityDao.getTotalByCondition(map);
        //取得dataList
        List<Activity> dataList = activityDao.getActivityListByCondition(map);
        //创建一个vo对象，将total和dataList传入到VO
        PaginationVo<Activity> vo = new PaginationVo<>();
        vo.setId(total);
        vo.setDataList(dataList);
        //将vo返回
        return vo;
    }

    @Override
    public boolean delete(String idstr) {
        System.out.println("进入删除页面");
        boolean flag = true;
        String[] strs = idstr.split("&");
        List<String> ids = new ArrayList<>();
        for(String s : strs) {
            ids.add(s.split("=")[1]);
        }
        //先查询出需要删除的备注的数量
        int count1 = activityRemarkDao.getCountByAids(ids);
        System.out.println(count1);
        // 删除备注，返回受到影响的条数，实际删除的数量
        int count2 = activityRemarkDao.deleteByAids(ids);
        System.out.println(count2);

        if (count1!=count2){
            flag = false;
        }
        // 删除市场活动
        int count3 = activityDao.delete(ids);
        System.out.println(count3);
//        if(count3!=ids.length){
//            flag =false;
//        }
        return flag;
    }

    @Override
    public Map<String, Object> getUserListAndActivity(String id) {
        //  需要List和a
        List<User> userList =userDao.getUserList();

        Activity a= activityDao.getById(id);

        Map<String,Object> map = new HashMap<>();
        map.put("uList",userList);
        map.put("a",a);
        return map;
    }

    @Override
    public boolean update(Activity a) {
        boolean flag = true;
        System.out.println(a);

        int count = activityDao.update(a);

        if (count!=1){
            flag = false;
        }

        return flag;
    }

    @Override
    public Activity detail(String id) {

       Activity a =  activityDao.detail(id);
        return a;

    }

    @Override
    public List<ActivityRemark> getRemarkListById(String activityId) {

        return activityRemarkDao.getRemarkListById(activityId);
    }

    @Override
    public boolean deleteRemark(String id) {

        boolean flag = true;
        int count = activityRemarkDao.deleteById(id);
        if (count!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public boolean saveRemark(ActivityRemark ar) {

        boolean flag = true;
        int count= activityRemarkDao.saveRemark(ar);
        if (count!=1)
            flag =false;
        return flag;
    }


}
