package com.pzyruo.settings.test;

import com.pzyruo.crm.utils.MD5Util;


public class Test01 {
    public static void main(String[] args) {
        /*//验证失效时间
        String expireTime = "2020-01-14 16:10:10";
        //当前时间
        String currentTime = DateTimeUtil.getSysTime();

        int count = expireTime.compareTo(currentTime);
        System.out.println(count);*/

        //完全写法
        /*Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = sdf.format(date);
        System.out.println(str);*/

        //验证账号锁定状态
//        String lockState = "0";
//        if ("0".equals(lockState)){
//            System.out.println("账号已锁定");
//        }

       /* //浏览器端的ip地址
        String ip= "192.168.1.1";
        String allowIps = "192.168.1.1,192.168.2.1,192.168.3.1";
        if (allowIps.contains(ip)){
            System.out.println("有效的ip，运行访问");
        }else{
            System.out.println("无效的IP地址，请联系管理员");
        }*/

        String pwd = "pzyruo";
        pwd = MD5Util.getMD5(pwd);
        System.out.println(pwd);
    }
}
