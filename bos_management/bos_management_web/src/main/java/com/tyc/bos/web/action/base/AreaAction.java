package com.tyc.bos.web.action.base;

import com.tyc.bos.domain.base.Area;
import com.tyc.bos.service.base.IAreaService;
import com.tyc.bos.utils.FileUtils;
import com.tyc.bos.web.action.common.CommonAction;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class AreaAction extends CommonAction<Area> {

    @Resource
    private DataSource dataSource;

    @Resource
    private IAreaService areaService;

    private File areaFile;

    public void setAreaFile(File areaFile) {
        this.areaFile = areaFile;
    }

    private String q;

    public void setQ(String q) {
        this.q = q;
    }

    @Action(value = "areaAction_importXls")
    public String importXls() throws IOException {

        String rs = "success";
        LOG.info(areaFile.getName());
        try {
            areaService.importXls(areaFile);
        } catch (Exception e) {
            e.printStackTrace();
            rs = "file";
        }
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().print(rs);
        return NONE;
    }

    @Action(value = "areaAction_pageQuery")
    public String pageQuery() throws IOException {

        Pageable pageable = new PageRequest(page - 1, rows);
        Page<Area> page = areaService.pageQuery(pageable);

        this.page2Json(page);

        return NONE;
    }

    @Action(value = "areaAction_findAll")
    public String findAll() throws IOException {

        List<Area> list = null;
        if (StringUtils.isNotBlank(q)) {
            list = areaService.findByQ("%" + q.toUpperCase() + "%");
        } else {
            list = areaService.findAll();
        }
        list2Json(list);
        return NONE;
    }

    @Action(value = "areaAction_exportPDF")
    public String exportPDF() throws JRException {

        String realPath = ServletActionContext.getServletContext().getRealPath("/jr/test.jrxml");
        Map<String, Object> map = new HashMap<>();
        map.put("company","阿里巴巴");

        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(realPath);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, dataSource.getConnection());

            HttpServletResponse response = ServletActionContext.getResponse();
            ServletOutputStream outputStream = response.getOutputStream();

            response.setContentType("application/pdf");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + FileUtils.encodeDownloadFilename("工作单.pdf",
                    ServletActionContext.getRequest().getHeader("user-agent"))
            );

            JRPdfExporter jrPdfExporter = new JRPdfExporter();
            jrPdfExporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
            jrPdfExporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outputStream);
            jrPdfExporter.exportReport();
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return NONE;
    }

}
