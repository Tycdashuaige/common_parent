package com.tyc.bos.web.action.base;

import com.tyc.bos.domain.base.SubArea;
import com.tyc.bos.service.base.ISubareaService;
import com.tyc.bos.utils.Config;
import com.tyc.bos.utils.FileUtil;
import com.tyc.bos.utils.FileUtils;
import com.tyc.bos.web.action.common.CommonAction;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class SubareaAction extends CommonAction<SubArea> {

    @Resource
    private Config config;

    @Resource
    private ISubareaService subareaService;

    @Action(value = "subareaAction_save", results = {@Result(name = "success", location = "pages/base/sub_area.html", type = "redirect")})
    public String save() {
        subareaService.save(getModel());
        return SUCCESS;
    }

    @Action(value = "subareaAction_pageQuery")
    public String pageQuery() throws IOException {
        Pageable pageable = new PageRequest(page - 1, rows);
        Page<SubArea> list = subareaService.pageQuery(pageable);
        this.page2Json(list);
        return NONE;
    }

    @Action(value = "subareaAction_exportXls")
    public String exportXls() throws IOException {

       List<SubArea> subAreaList = subareaService.findAll();

        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        HSSFSheet sheet = hssfWorkbook.createSheet("分区统计数据");
        HSSFRow titleRow = sheet.createRow(0);

        titleRow.createCell(0).setCellValue("编号");
        titleRow.createCell(1).setCellValue("分区起始号");
        titleRow.createCell(2).setCellValue("分区终止号");
        titleRow.createCell(3).setCellValue("分区关键字");
        titleRow.createCell(4).setCellValue("辅助关键字");
        titleRow.createCell(5).setCellValue("区域信息");

        for (SubArea subArea : subAreaList) {
            HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
            dataRow.createCell(0).setCellValue(subArea.getId());
            dataRow.createCell(1).setCellValue(subArea.getStartNum());
            dataRow.createCell(2).setCellValue(subArea.getEndNum());
            dataRow.createCell(3).setCellValue(subArea.getKeyWords());
            dataRow.createCell(4).setCellValue(subArea.getAssistKeyWords());
            dataRow.createCell(5).setCellValue(subArea.getArea().getName());
        }

        String fileName = "分区数据.xls";
        String header = ServletActionContext.getRequest().getHeader("User-Agent");
        String newFileName = FileUtils.encodeDownloadFilename(fileName , header);

        ServletOutputStream outputStream = ServletActionContext.getResponse().getOutputStream();
        ServletActionContext.getResponse().setContentType("application/vnd.ms-excel;charset=UTF-8");
        ServletActionContext.getResponse().setHeader("content-disposition","filename="+newFileName);

        hssfWorkbook.write(outputStream);
        hssfWorkbook.close();

        return NONE;
    }

    @Action(value = "subareaAction_downLoadTemplate")
    public String downLoadTemplate(){

        String realPath = ServletActionContext.getServletContext().getRealPath("/" + config.getTemplateFolder() + File.separator + "subarea_import_template.xls");
        FileUtil.download(realPath,ServletActionContext.getResponse());

        return NONE;
    }

    @Action(value = "subareaAction_showPie")
    public String showPie() throws IOException {

        List<Object> list = subareaService.findBySubareas();
        this.list2Json(list);

        return NONE;
    }
}
