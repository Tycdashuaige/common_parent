package com.tyc.bos.web.action.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.ServletActionContext;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonAction<T> extends ActionSupport implements ModelDriven<T> {

    protected Integer page;
    protected Integer rows;

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    protected T model;

    public CommonAction() {

        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        Class<T> entityClass = (Class) actualTypeArguments[0];

        try {
            model = entityClass.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public T getModel() {
        return model;
    }

    public void page2Json(Page<T> page) throws IOException {
        long total = page.getTotalElements();
        List<T> rows = page.getContent();
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", rows);

        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/json;charset=utf-8");

        response.getWriter().print(JSON.toJSON(map));
    }


    public void list2Json(List list) throws IOException {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().print(JSON.toJSON(list));
    }

    protected void list2JsonIncludes(List<T> list, String[] includes) throws IOException {
        SimplePropertyPreFilter simplePropertyPreFilter = new SimplePropertyPreFilter(model.getClass(), includes);
        String data = JSON.toJSONString(list, simplePropertyPreFilter);
        System.out.println(data);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().print(data);
    }
}
