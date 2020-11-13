package com.pzyruo.crm.web.filter;

import com.pzyruo.crm.settings.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("进入登陆验证阶段225");
        //上级转下级需要强制类型转换，下级转上级是自动类型提升
        HttpServletRequest httpServletRequest  = (HttpServletRequest)request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String path  =httpServletRequest.getServletPath();
        if("/login.jsp".equals(path)||"/settings/user/login.do".equals(path)){
            chain.doFilter(request,response);
        }else {
            //从session中获取User对象
            HttpSession session = httpServletRequest.getSession();
            User user = (User) session.getAttribute("user");
            //如果user不为空说明，有登陆过予以放行
            if (user!=null){
                chain.doFilter(request,response);
            }else {
                //重定向到登录页
//            String str = "/crm";
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/login.jsp");
            }
        }

    }
}
