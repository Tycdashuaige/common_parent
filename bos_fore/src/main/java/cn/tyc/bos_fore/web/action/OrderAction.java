package cn.tyc.bos_fore.web.action;

import cn.tyc.bos.domain.Area;
import cn.tyc.bos.domain.Order;
import cn.tyc.bos.service.IOrderService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class OrderAction extends ActionSupport implements ModelDriven<Order> {

    private String sendAreaInfo;

    private String recAreaInfo;

    public void setSendAreaInfo(String sendAreaInfo) {
        this.sendAreaInfo = sendAreaInfo;
    }

    public void setRecAreaInfo(String recAreaInfo) {
        this.recAreaInfo = recAreaInfo;
    }

    @Resource
    private IOrderService orderProxy;

    private Order model;

    @Override
    public Order getModel() {
        if (model == null) {
            model = new Order();
        }
        return model;
    }

    @Action(value = "orderAction_add", results = {
            @Result(name = "success01", location = "/order.html", type = "redirect"),
            @Result(name = "fail", location = "/fail.html", type = "redirect")})
    public String add() {

        try {
            if (StringUtils.isNotBlank(sendAreaInfo)) {
                String[] split = sendAreaInfo.split("/");
                Area area = new Area();
                area.setProvince(split[0]);
                area.setCity(split[1]);
                area.setDistrict(split[2]);
                model.setSendArea(area);
            }
            if (StringUtils.isNotBlank(recAreaInfo)) {
                String[] split = recAreaInfo.split("/");
                Area area = new Area();
                area.setProvince(split[0]);
                area.setCity(split[1]);
                area.setDistrict(split[2]);
                model.setRecArea(area);
            }
            orderProxy.save(model);
        } catch (Exception e) {
            return "fail";
        }
        return "success01";
    }
}
