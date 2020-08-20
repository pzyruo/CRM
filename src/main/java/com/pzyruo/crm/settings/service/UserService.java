package com.pzyruo.crm.settings.service;

import com.pzyruo.crm.exception.LoginException;
import com.pzyruo.crm.settings.domain.User;

import java.util.List;

public interface UserService {
    User login(String loginAct, String loginPwd, String ip) throws LoginException;

}
