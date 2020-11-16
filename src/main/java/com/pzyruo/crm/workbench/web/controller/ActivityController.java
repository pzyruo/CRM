package com.pzyruo.crm.workbench.web.controller;

import com.pzyruo.crm.settings.domain.User;
import com.pzyruo.crm.settings.service.UserService;
import com.pzyruo.crm.settings.service.UserServiceImpl;
import com.pzyruo.crm.utils.*;
import com.pzyruo.crm.workbench.domain.Activity;
import com.pzyruo.crm.workbench.service.ActivityService;
import com.pzyruo.crm.workbench.service.impl.ActivityServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("进入市场活动控制器");

        String path = request.getServletPath();

        if ("/workbench/activity/getUserList.do".equals(path)){
            getUserList(request,response);
            //xxx(request,response);
        }else if ("/workbench/activity/save.do".equals(path)){
            save(request,response);
        }
    }

    private void save(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入添加市场活动模块");
         String  id = UUIDUtil.getUUID();   //  活动id
         String  owner = request.getParameter("owner");  // 拥有者
         String  name = request.getParameter("name");  // 名字
         String  startDate = request.getParameter("startDate");  //  开始日期
         String  endDate = request.getParameter("endDate");  //  结束日期
         String  cost = request.getParameter("cost");  //  花费
         String  description= request.getParameter("description") ;  //  描述
         String  createTime = DateTimeUtil.getSysTime(); //  创建时间
         String  createBy = ((User) request.getSession().getAttribute("user")).getName();//  创建者

        Activity a = new Activity();
        a.setCreateBy(createBy);
        a.setCreateTime(createTime);
        a.setDescription(description);
        a.setCost(cost);
        a.setStartDate(startDate);
        a.setEndDate(endDate);
        a.setName(name);
        a.setId(id);
        a.setOwner(owner);

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag =as.save(a);
        PrintJson.printJsonFlag(response,flag);
    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("取得用户信息列表");
        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> uList = us.getUserList();
        PrintJson.printJsonObj(response,uList);
    }

}

