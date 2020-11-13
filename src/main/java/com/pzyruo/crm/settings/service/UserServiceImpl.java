package com.pzyruo.crm.settings.service;

import com.pzyruo.crm.exception.LoginException;
import com.pzyruo.crm.settings.dao.UserDao;
import com.pzyruo.crm.settings.domain.User;
import com.pzyruo.crm.utils.DateTimeUtil;
import com.pzyruo.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.Map;

public class UserServiceImpl implements UserService {

    @Override
    public User login(String loginAct, String loginPwd, String ip) throws LoginException {

        UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
        Map<String,String> map = new HashMap<>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);

        User user = userDao.login(map);
        if (user == null) {
            throw new LoginException("账号密码错误");
        }

        //如果程序能够成功执行到这里，说明账号密码正确
        //需要继续向下验证

//        验证失效时间
        String expireTime = user.getExpireTime();
        String currentTime = DateTimeUtil.getSysTime();//获取当前时间
        if (expireTime.compareTo(currentTime)<0){ //当前时间与账户时间进行比较
            throw new LoginException("账号已失效，请重新输入");
        }
//        验证锁定状态
        String lockState = user.getLockState();
        if ("0".equals(lockState)){  //锁定状态
            throw new LoginException("账号已锁定");
        }
//        验证ip地址

        String Ips = user.getAllowIps();
        //if (Ips==null||Ips=="")  抛异常
        if (!Ips.contains(ip)) {  //contains  判断请求的Ip地址是否包含在运行的IP地址
            throw new LoginException("ip地址受限，请联系管理员");
        }

        return user;
    }
}
