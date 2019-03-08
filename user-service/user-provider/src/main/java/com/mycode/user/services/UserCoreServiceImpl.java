package com.mycode.user.services;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.mycode.user.IUserCoreService;
import com.mycode.user.ResponseCodeEnum;
import com.mycode.user.dal.entity.User;
import com.mycode.user.dal.persistence.UserMapper;
import com.mycode.user.dto.UserLoginRequest;
import com.mycode.user.dto.UserLoginResponse;
import com.mycode.user.exception.ExceptionUtil;
import com.mycode.user.exception.ServiceException;
import com.mycode.user.exception.ValidateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 蛮小江
 * 2018/7/2 11:41
 */
//对user定义的API实现
@Service("userCoreService")
public class UserCoreServiceImpl implements IUserCoreService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

     @Autowired
     UserMapper userMapper;

    @Override
    public UserLoginResponse login(UserLoginRequest request) {
        logger.info("login request:" + request);
        UserLoginResponse response = new UserLoginResponse();
        try {
            //校验参数
            beforeValidParam(request);
            //从DB中取出User数据
            User user = userMapper.getUserByUserName(request.getUserName());
            if (StringUtils.isBlank(user.getPassword()) || !user.getPassword().equals(request.getPassword())) {
                response.setCode(ResponseCodeEnum.USERORPASSWORD_ERRROR.getCode());
                response.setMsg(ResponseCodeEnum.USERORPASSWORD_ERRROR.getMsg());
                return response;
            }
            response.setUserName(user.getUsername());
            response.setCode(ResponseCodeEnum.SUCCESS.getCode());
            response.setMsg(ResponseCodeEnum.SUCCESS.getMsg());
            response.setUid(user.getId());
            response.setMobile(user.getMobile());
            response.setAvatar(user.getAvatar());
        } catch (Exception e) {
            logger.error("login occur exception:", e);
            ServiceException exception = (ServiceException) ExceptionUtil.handlerException4biz(e);
            response.setCode(exception.getErrorCode());
            response.setMsg(exception.getErrorMessage());
        } finally {
            logger.info("login response->" + response);
        }
        return response;
    }

    /**
     * 参数校验
     * @param request
     * @throws ValidateException
     */
    private void beforeValidParam(UserLoginRequest request) throws ValidateException {
        if(request==null){
            throw new ValidateException("请求对象为空");
        }
        if(StringUtils.isBlank(request.getUserName())){
            throw new ValidateException("用户名为空");
        }
        if(StringUtils.isBlank(request.getPassword())){
            throw new ValidateException("密码为空");
        }

    }
}
