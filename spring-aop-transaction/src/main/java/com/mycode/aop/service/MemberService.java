package com.mycode.aop.service;

import com.mycode.model.Member;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 蛮小江
 * 2018/5/13 16:29
 */
//注解业务操作Service
@Service
public class MemberService {
    private final static Logger logger = Logger.getLogger(MemberService.class);
    public Member get(long id){
        logger.info("getMemberById method .....");
        return  new Member();
    }
    public Member get(){
        logger.info("getMember method ....");
        return  new Member();
    }

    public void save(Member member){
        logger.info("save member method .....");
    }

    public  boolean delete(long id) throws  Exception{
        logger.info("delete method .....");
        throw new Exception("spring aop ThrowAdvice 报错 .......");
    }
}
