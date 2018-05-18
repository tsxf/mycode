package com.mycode.transaction.service;

import com.mycode.model.Member;
import com.mycode.transaction.dao.MemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.Name;
import java.util.List;

/**
 * 蛮小江
 * 2018/5/13 19:35
 */
@Service(value = "memberService1")
public class MemberService {
    @Autowired
    private MemberDao memberDao;


    public List<Member> queryAll() throws Exception {
        return memberDao.selectAll();
    }

//    public boolean addOne(Member member) throws Exception{
//        boolean r = memberDao.insert(member);
////        throw new Exception("自定义异常");
//        return r;
//    }


    //没有把异常抛出去，事务不回滚
    //把异常抛出去以后，事务就自动回滚
    public boolean add(Member member) throws Exception {
        boolean r = memberDao.insert(member);
         throw new Exception("自定义异常");
       // return r;
    }

    public boolean remove(long id) throws Exception {
        boolean r = memberDao.delete(id);
        throw new Exception("自定义异常");
//		return r;
    }

    public boolean modify(long id, String name) throws Exception {
        return memberDao.update(id, name);
    }

    public boolean login(long id, String name) throws Exception {
        boolean modify = this.modify(id, name);
//		throw new Exception("测试无事务");
        return modify;
    }
}
