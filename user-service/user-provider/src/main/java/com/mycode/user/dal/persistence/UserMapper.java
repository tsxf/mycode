package com.mycode.user.dal.persistence;

import com.mycode.user.dal.entity.User;

/**
 * 蛮小江
 * 2018/7/2 20:57
 */
public interface UserMapper {
    /**
     * 根据用户名获取user对象
     * @param userName
     * @return
     */
    User getUserByUserName(String userName);
}
