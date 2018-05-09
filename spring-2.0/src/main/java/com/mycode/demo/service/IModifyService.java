package com.mycode.demo.service;

/**
 * 增删改业务
 * Created by 江富 on 2018/4/29
 */
public interface IModifyService {
    /**
     * 增加
     * @param name
     * @param addr
     * @return
     */
    String add(String name,String addr);

    /**
     * 修改
     * @param id
     * @param name
     * @return
     */
    String edit(Integer id,String name);

    /**
     * 删除
     * @param id
     * @return
     */
    String remove(Integer id);
}
