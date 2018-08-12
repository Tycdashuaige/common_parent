package com.tyc.bos.jobs;

import com.tyc.bos.dao.take_delivery.IWorkBillDao;
import com.tyc.bos.domain.base.Courier;
import com.tyc.bos.domain.take_delivery.WorkBill;
import com.tyc.bos.utils.MailUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author tangyucong
 * @Title: WorkBillJobs
 * @ProjectName common_parent
 * @Description: TODO
 * @date 2018/8/1013:33
 */
public class WorkBillJobs {

    private Logger logger = LoggerFactory.getLogger(WorkBillJobs.class);

    @Resource
    private IWorkBillDao workBillDao;

    public void sendMail() {

        List<WorkBill> all = workBillDao.findAll();

        if (all != null && all.size() > 0) {
            String content = "<table border='1px'><tr><td>工单id</td><td>工单类型 </td><td>取件状态 </td><td>快递员</td></tr>";
            for (WorkBill workBill : all) {
                Courier courier = workBill.getCourier();
                String cname = "";
                if (courier != null) {
                    cname = courier.getName();
                }
                content += "<tr><td>" + workBill.getId() + "</td><td>" + workBill.getType() + "</td><td>"
                        + workBill.getPickstate() + "</td><td>" + cname + "</td></tr>";
            }
            content += "</table>";

            String subject = "统计所有工单";
            String to = "tangyucong716@qq.com";

            logger.info("邮件发送内容");
            MailUtils.sendMail(subject, content, to);

            logger.info("发送邮件成功");
        } else {
            logger.warn("没有工单数据");
        }

    }
}
