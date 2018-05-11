package com.mycode.demo.service.impl;

import com.mycode.demo.service.IQuerryService;
import com.mycode.framework.annotation.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 蛮小江 on 2018/4/29
 */
@Service
public class QuerryService implements IQuerryService {
    @Override
    public String querry(String name) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
        String json = "{name:\""+name+"\",time:\""+time+"\"}";
        return json;
    }

    public static void main(String[] args) {
        QuerryService querryService = new QuerryService();
        String querry = querryService.querry("陈超");
        System.out.println(querry);
    }


}
