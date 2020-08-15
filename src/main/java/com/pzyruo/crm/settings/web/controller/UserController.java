package com.pzyruo.crm.settings.web.controller;

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

public class UserController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("进入用户控制器");

        String path = request.getServletPath();

        if ("/settings/user/login.do".equals(path)){
            login(request,response);
        }else if ("/settings/user/xxx.do".equals(path)){

        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到验证登陆");
        String loginAct = request.getParameter("loginAct");
        String loginPwd = request.getParameter("loginPwd");
        //将密码的明文转换为MD5的密文形式
        loginPwd = MD5Util.getMD5(loginAct);
        //接受ip地址
        String ip = request.getRemoteAddr();
        System.out.println("ip--------:"+ip);


        //未来的业务层开发，统一使用代理类形态的接口对象
        UserService us =(UserService) ServiceFactory.getService(new UserServiceImpl());

        try {
            User user = us.login(loginAct,loginPwd,ip);
            request.getSession().setAttribute("user",user);
            //如果程序执行到此处，说明业务层没有为controller抛出任何异常
            //表示登陆成功
            PrintJson.printJsonFlag(response,true);
        }catch (Exception e){
            e.printStackTrace();
            //执行到此，说明业务层为我们验证登陆失败，为controller抛出了异常
//            {"success":true,"msg":?}
            String msg = e.getMessage();
            /*
            *  我们现在作为controller，需要为ajax请求提供对象信息
            *   1.将多项信息打包成map，将map解析为json串
            *   2.创建一个Vo
            *           private boolean success;
            *           private String msg;
            *       若果对于展现的信息将来还会大量使用，我们创建一个波类，使用方便。
            *       如果对于展现的信息只有在这个需求中能够使用，我们使用map就可以了
            *
            * */

            Map<String,Object> map = new HashMap<>();
            map.put("success",false);
            map.put("msg",msg);
            PrintJson.printJsonObj(response,map);
        }
    }
}
