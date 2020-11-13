package com.pzyruo.crm.workbench.web.controller;

import com.pzyruo.crm.settings.domain.User;
import com.pzyruo.crm.settings.service.UserService;
import com.pzyruo.crm.settings.service.UserServiceImpl;
import com.pzyruo.crm.utils.MD5Util;
import com.pzyruo.crm.utils.PrintJson;
import com.pzyruo.crm.utils.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ActivityController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("进入市场活动控制器");

        String path = request.getServletPath();

        if ("/workbench/activity/xxx.do".equals(path)){
            //xxx(request,response);
        }else if ("/workbench/activity/xxx.do".equals(path)){

        }
    }

    }

