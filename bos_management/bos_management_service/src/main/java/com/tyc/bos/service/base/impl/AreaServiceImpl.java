package com.tyc.bos.service.base.impl;

import com.tyc.bos.dao.base.IAreaDao;
import com.tyc.bos.domain.base.Area;
import com.tyc.bos.service.IAreaService;
import com.tyc.bos.utils.PinYin4jUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AreaServiceImpl implements IAreaService {

    @Resource
    private IAreaDao areaDao;

    @Override
    public void importXls(File areaFile) throws IOException {

        HSSFWorkbook hs = new HSSFWorkbook(new FileInputStream(areaFile));
        HSSFSheet sheetAt = hs.getSheetAt(0);

        List<Area> list = new ArrayList<>();

        for (Row row : sheetAt) {
            int rowNum = row.getRowNum();
            if (rowNum == 0) {
                continue;
            }

            String id = row.getCell(0).getStringCellValue();
            String province = row.getCell(1).getStringCellValue();
            String city = row.getCell(2).getStringCellValue();
            String district = row.getCell(3).getStringCellValue();
            String postcode = row.getCell(4).getStringCellValue();

            String province1 = province.substring(0, province.length() - 1);
            String city1 = city.substring(0, city.length() - 1);
            String district1 = district.substring(0, district.length() - 1);

            String[] headByString = PinYin4jUtils.getHeadByString(province1 + city1 + district1);
            String shortcode = PinYin4jUtils.stringArrayToString(headByString, "");

            String citycode = PinYin4jUtils.hanziToPinyin(city1, "");

            Area area = new Area(id, province, city, district, postcode, citycode, shortcode, null);
            list.add(area);
        }
        areaDao.save(list);
        hs.close();
    }

    @Override
    public Page<Area> pageQuery(Pageable pageable) {
        return  areaDao.findAll(pageable);
    }

    @Override
    public List<Area> findByQ(String q) {
        return areaDao.findByQ(q);
    }

    @Override
    public List<Area> findAll() {
        return areaDao.findAll();
    }
}
