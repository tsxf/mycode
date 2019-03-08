package com.mycode.user;

import com.mycode.user.dto.UserLoginRequest;
import com.mycode.user.dto.UserLoginResponse;

/**
 * 用户模块API定义
 */
public interface IUserCoreService {
    UserLoginResponse login(UserLoginRequest request);
}
