package com.tyc.quartz;

import java.util.Date;

/**
 * @author tangyucong
 * @Title: MyJob
 * @ProjectName common_parent
 * @Description: TODO
 * @date 2018/8/1013:03
 */
public class MyJob {

    public void sendMsg(){
        System.out.println("自定义任务执行了,执行时间为:"+new Date());
    }
}
