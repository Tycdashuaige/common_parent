package cn.tyc.bos_fore.web.action;

import cn.tyc.bos_fore.crm.domain.Customer;
import cn.tyc.bos_fore.crm.service.ICustomerService;
import cn.tyc.bos_fore.utils.MailUtils;
import com.aliyuncs.exceptions.ClientException;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class UserAction extends ActionSupport implements ModelDriven<Customer> {

    @Resource
    private JmsTemplate jmsTemplate;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ICustomerService customerProxy;

    private Customer model;

    @Override
    public Customer getModel() {
        if (model == null) {
            model = new Customer();
        }
        return model;
    }

    private String checkcode;

    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }

    private String activeCode;

    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
    }

    @Action(value = "userAction_sendMessage")
    public String sendMSG() throws ClientException {

        String randomNumeric = RandomStringUtils.randomNumeric(6);
        ServletActionContext.getRequest().getSession().setAttribute(model.getTelephone(), randomNumeric);

//        SendSmsResponse sendSmsResponse = SmsUtils.sendSms(model.getTelephone(), randomNumeric);
//        String success = "true";
//        if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
//            success = "true";
//        } else {
//            success = "false";
//        }

        System.out.println(randomNumeric);
        ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
        try {
            ServletActionContext.getResponse().getWriter().print(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return NONE;
    }

    @Action(value = "customerAction_regist", results = {
            @Result(name = "success", location = "/signup-success.html", type = "redirect"),
            @Result(name = "exist", location = "signup-exist.html", type = "redirect"),
            @Result(name = "error", location = "signup-fail.html", type = "redirect")})
    public String regist() {


        String randomNumeric = (String) ServletActionContext.getRequest().getSession().getAttribute(model.getTelephone());

        if (StringUtils.isNotBlank(randomNumeric) && StringUtils.isNotBlank(checkcode) && randomNumeric.equals(checkcode)) {
            boolean regist = customerProxy.regist(model);
            if (!regist) {
                return "exist";
            }

            String activeCode = RandomStringUtils.randomNumeric(36);
            redisTemplate.opsForValue().set(model.getTelephone(), activeCode, 24, TimeUnit.HOURS);
            String content = "尊敬的用户，您好!\n" +
                    "\n" +
                    "您正在提交对速运快递注册的验证，请点击以下链接完成邮箱验证（如果不是您提交的申请，请忽略）。\n" +
                    "\n" +
                    "验证链接：<a href=" + MailUtils.activeUrl + "?telephone=" + model.getTelephone() + "&activeCode=" + activeCode + ">激活账户</a>" +
                    "\n" +
                    "如果以上链接无法点击，可以复制以上链接在浏览器打开。\n";
            MailUtils.sendMail("速运激活邮件", content, model.getEmail());

            jmsTemplate.send("sms_message", new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    MapMessage mapMessage = session.createMapMessage();
                    mapMessage.setString("telephone",model.getTelephone());
                    mapMessage.setString("content","恭喜您注册成功速运快递会员，请到邮箱激活账号！");
                    return mapMessage;
                }
            });
            return SUCCESS;
        } else {
            return ERROR;
        }
    }

    @Action(value = "customerAction_activeMail", results = {@Result(name = "success", location = "/login.html", type = "redirect"),
            @Result(name = "fail", location = "/signup-fail.html", type = "redirect"),
            @Result(name = "already", location = "/active-already.html", type = "redirect")})
    public String activeMail() {

        String telephone = model.getTelephone();

        if (StringUtils.isBlank(activeCode) || StringUtils.isBlank(telephone)) {
            return "fail";
        }

        String redisActiveCode = redisTemplate.opsForValue().get(telephone);
        if (StringUtils.isBlank(redisActiveCode) || !redisActiveCode.equals(activeCode)) {
            return "fail";
        }

        List<Customer> customers = customerProxy.findByTelephone(telephone);

        if (customers == null || customers.size() > 1) {
            return "fail";
        }

        if (customers.get(0).getType() != null && customers.get(0).getType() == 1) {
            return "already";
        }
        try {
            customerProxy.activeMail(telephone);
            redisTemplate.delete(telephone);
            return SUCCESS;
        } catch (Exception e) {
            return "fail";
        }
    }

    @Action(value = "customerAction_login", results = {
            @Result(name = "success", location = "/myhome.html", type = "redirect"),
            @Result(name = "fail", location = "/login.html", type = "redirect"),
            @Result(name = "notActive", location = "/notActive.html", type = "redirect")})
    public String login() {

        String telephone = model.getTelephone();
        String password = model.getPassword();
        String validateCode = (String) ServletActionContext.getRequest().getSession().getAttribute("validateCode");

        if (StringUtils.isNotBlank(telephone) && StringUtils.isNotBlank(password) && StringUtils.isNotBlank(checkcode) && checkcode.equals(validateCode)) {
            Customer customer = customerProxy.login(telephone, password);
            if (customer.getType() == null || customer.getType() != 1) {
                return "notActive";
            }
            if (customer != null) {
                return SUCCESS;
            }
        }
        return "fail";
    }

}
