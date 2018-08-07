package cn.tyc.consumer;

import cn.tyc.utils.SmsUtils;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

public class SmsConsumer implements MessageListener {
    @Override
    public void onMessage(Message message) {
        MapMessage mapMessage = (MapMessage) message;
        try {
            String telephone = mapMessage.getString("telephone");
            String content = mapMessage.getString("content");
            System.out.println("手机号码:"+telephone+"短信内容:"+content);
            SmsUtils.sendSms(telephone,content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
