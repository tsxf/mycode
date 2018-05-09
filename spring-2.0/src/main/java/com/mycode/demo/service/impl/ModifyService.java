package com.mycode.demo.service.impl;

import com.mycode.demo.service.IModifyService;
import com.mycode.framework.annotation.Service;

/**
 * Created by 江富 on 2018/4/29
 */
@Service
public class ModifyService implements IModifyService{
    /**
     * 增加
     * @param name
     * @param addr
     * @return
     */
    public String add(String name, String addr) {
        return "modifyService add,name="+name+",addr:"+addr;
    }

    /**
     * 编辑
     * @param id
     * @param name
     * @return
     */
    public String edit(Integer id, String name) {
        return "modifyService edit,id="+id+",name:"+name;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    public String remove(Integer id) {
        return "modifyService remove,id="+id;
    }
}
