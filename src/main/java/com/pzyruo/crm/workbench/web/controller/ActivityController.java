package com.pzyruo.crm.workbench.web.controller;

import com.pzyruo.crm.settings.domain.User;
import com.pzyruo.crm.settings.service.UserService;
import com.pzyruo.crm.settings.service.UserServiceImpl;
import com.pzyruo.crm.utils.*;
import com.pzyruo.crm.vo.PaginationVo;
import com.pzyruo.crm.workbench.dao.ActivityRemarkDao;
import com.pzyruo.crm.workbench.domain.Activity;
import com.pzyruo.crm.workbench.domain.ActivityRemark;
import com.pzyruo.crm.workbench.service.ActivityService;
import com.pzyruo.crm.workbench.service.impl.ActivityServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
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
        }else if ("/workbench/activity/pageList.do".equals(path)){
            pageList(request,response);
        }else if ("/workbench/activity/delete.do".equals(path)){
            delete(request,response);
        }else if ("/workbench/activity/getUserListAndActivity.do".equals(path)){
            getUserListAndActivity(request,response);
        }else if ("/workbench/activity/update1.do".equals(path)){
            update(request,response);
        }else if ("/workbench/activity/detail.do".equals(path)){
            detail(request,response);
        }else if ("/workbench/activity/getRemarkListById.do".equals(path)){
            getRemarkListById(request,response);
        }else if ("/workbench/activity/deleteRemark.do".equals(path)){
            deleteRemark(request,response);
        }
        else if ("/workbench/activity/saveRemark.do".equals(path)){
            saveRemark(request,response);
        }


    }

    private void saveRemark(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行添加备注的操作");
        String noteContent = request.getParameter("noteContent");
        String activityId = request.getParameter("activityId");
        String id = UUIDUtil.getUUID();
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User)request.getSession().getAttribute("user")).getName();
        String editFlag = "0";
        ActivityRemark ar = new ActivityRemark();
        ar.setActivityId(activityId);
        ar.setCreateBy(createBy);
        ar.setCreateTime(createTime);
        ar.setNoteContent(noteContent);
        ar.setEditFlag(editFlag);
        ar.setId(id);


        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = as.saveRemark(ar);

        Map<String,Object> map = new HashMap<>();
        map.put("success",flag);
        map.put("ar",ar);
        PrintJson.printJsonObj(response,map);
    }

    private void deleteRemark(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入删除ID操作");
        String id = request.getParameter("id");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = as.deleteRemark(id);
        PrintJson.printJsonFlag(response,flag);
    }

    private void getRemarkListById(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("根据市场活动id去信息");

        String activityId = request.getParameter("activityId");
        System.out.println(activityId);
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        List<ActivityRemark> list = as.getRemarkListById(activityId);
        PrintJson.printJsonObj(response,list);
    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入到详细信息页的操作");
        String id = request.getParameter("id");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        Activity a = as.detail(id);
        //把a存起来
        request.setAttribute("a",a);
        request.getRequestDispatcher("/workbench/activity/detail.jsp").forward(request,response);
    }

    private void update(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入更新页面操作");
        String  id =request.getParameter("id");   //  活动id
        String  owner = request.getParameter("owner");  // 拥有者
        String  name = request.getParameter("name");  // 名字
        String  startDate = request.getParameter("startDate");  //  开始日期
        String  endDate = request.getParameter("endDate");  //  结束日期
        String  cost = request.getParameter("cost");  //  花费
        String  description= request.getParameter("description") ;  //  描述
        String  editTime = DateTimeUtil.getSysTime(); //  修改时间
        String  editBy = ((User) request.getSession().getAttribute("user")).getName();//  修改者
        System.out.println(editBy);
        System.out.println(editTime);

        Activity a = new Activity();
        a.setDescription(description);
        a.setCost(cost);
        a.setStartDate(startDate);
        a.setEndDate(endDate);
        a.setName(name);
        a.setId(id);
        a.setOwner(owner);
        a.setEditTime(editTime);
        a.setEditBy(editBy);
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag =as.update(a);
        PrintJson.printJsonFlag(response,flag);


    }

    private void getUserListAndActivity(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到用户查询列表，根据市场货id查询单条记录");
        String id = request.getParameter("id");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
       /*
       *        业务层Controller调用Service的方法，需要考虑前端需要什么
       * */
        Map<String,Object> map = as.getUserListAndActivity(id);
        PrintJson.printJsonObj(response,map);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行市场活动的删除操作");
        String ids =  request.getParameterValues("param")[0];
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = as.delete(ids);
        PrintJson.printJsonFlag(response,flag);
    }


    private void pageList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到查询市场活动信息列表的操作，结合条件查询，分页查询");
        String name = request.getParameter("name");
        String owner = request.getParameter("owner");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String pageNoStr = request.getParameter("pageNo");
        int pageNo = Integer.parseInt(pageNoStr);
        //每页展现的记录数
        String pageSizeStr = request.getParameter("pageSize");
        int pageSize = Integer.parseInt(pageSizeStr);
        //略过的记录数
        int skipCount = (pageNo-1)*pageSize;
        Map<String,Object> map = new HashMap<>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("skipCount",skipCount);
        map.put("pageSize",pageSize);

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        // 返回的是一个List，市场活动信息列表，查询总条数
        //复用性高使用VO，
       PaginationVo<Activity> vo =  as.pageList(map);
       PrintJson.printJsonObj(response,vo);


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

