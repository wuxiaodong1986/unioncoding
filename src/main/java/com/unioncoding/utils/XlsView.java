package com.unioncoding.utils;

import com.sun.deploy.net.URLEncoder;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by 吴晓冬 on 2017/9/17.
 */
public class XlsView extends AbstractXlsView
{
    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        //下载的xls的文件名
        String fileName = (String) model.get("fileName");
        //各栏目标题的有序键值对，key为数据库名，value为标题名。map为LinkedHashMap
        Map<String, String> titles = (Map<String, String>) model.get("titles");
        //具体内容
        List<Object> objects = (List<Object>) model.get("objects");

        //生成xls的第一个sheet
        Sheet sheet1 = workbook.createSheet("sheet1");

        //生成xls的第一列，标题
        Row titleRow = sheet1.createRow(0);
        Iterator<Map.Entry<String, String>> titleIterable = titles.entrySet().iterator();
        int titleCellNo = 0;
        while (titleIterable.hasNext())
        {
            Map.Entry<String, String> titleEntry = titleIterable.next();
            String titleName = titleEntry.getValue();
            Cell titleCell = titleRow.createCell(titleCellNo);
            titleCell.setCellValue(titleName);
            titleCellNo++;
        }

        //生成xls后续内容
        for (int i = 0; i < objects.size(); i++)
        {
            Row rowi = sheet1.createRow(i+1);
            Object object = objects.get(i);

            Iterator<String> keyIterable = titles.keySet().iterator();
            int valueCellNo = 0;
            while (keyIterable.hasNext())
            {
                Cell valueCell = rowi.createCell(valueCellNo);
                String keyName = keyIterable.next();

                //通过反射获取到对象内对应属性的内容
                Field f = object.getClass().getDeclaredField(keyName);
                f.setAccessible(true);
                Object value = f.get(object);

                if (value instanceof String)
                {
                    valueCell.setCellValue((String) value);
                }
                else if (value instanceof Integer)
                {
                    valueCell.setCellValue((Integer) value);
                }
                else if (value instanceof Long)
                {
                    valueCell.setCellValue((Long) value);
                }
                else if (value instanceof Double)
                {
                    valueCell.setCellValue((Double) value);
                }
                else if (value instanceof Date)
                {
                    valueCell.setCellValue((Date) value);
                }
                else if (value instanceof Boolean)
                {
                    valueCell.setCellValue((Boolean) value);
                }
                else if (null == value)
                {
                    valueCell.setCellValue("");
                }
                else
                {
                    valueCell.setCellValue("未转换的格式");
                }

                valueCellNo++;
            }
        }

        //生成下载文件
        response.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(fileName, "utf-8"));
    }
}
