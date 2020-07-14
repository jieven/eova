/**
 * Copyright (c) 2019 EOVA.CN. All rights reserved.
 * 
 * Licensed to: Jieven(admin@eova.cn)
 * Licensed under the EPPL license: http://eova.cn/eppl.txt
 * Software copyright registration number:2018SR1012969
 * For authorization, please contact: admin@eova.cn
 */
package com.eova.common.utils.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.eova.common.utils.xx;
import com.eova.common.utils.util.RandomUtil;
import com.eova.model.MetaField;
import com.eova.model.MetaObject;
import com.jfinal.plugin.activerecord.Record;

import jxl.Cell;
import jxl.Range;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Colour;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * <pre>
 * 专业版解决方案:
 * 少于5W用Excel导出
 * 大于5W用Csv导出, 节省服务器资源, 可轻松导出几十万数据
 * 
 * 更新:
 * 2020-07-14 支持导出对时间和日期格式化
 * </pre>
 *
 * @author Jieven
 *
 */
public class ExcelUtil {
    public static void createExcel(OutputStream os, List<Record> list, List<MetaField> items, MetaObject object) throws WriteException, IOException {
        // 创建工作薄
        WritableWorkbook workbook = Workbook.createWorkbook(os);
        // 创建新的一页
        WritableSheet sheet = workbook.createSheet("Sheet1", 0);

        int row = 0;// 当前行索引
        // 写入标题
        for (int i = 0; i < items.size(); i++) {
            MetaField item = items.get(i);
            WritableCellFormat format = new WritableCellFormat();
            format.setBackground(Colour.GRAY_25);// 设置灰色背景
            sheet.addCell(new Label(i, row, item.getCn(), format));
            // 设置列宽度(列宽px值/10)
            sheet.setColumnView(i, item.getInt("width") / 10);
        }
        row++;
        // 写入数据行
        for (; row <= list.size(); row++) {
        	// 写入数据列
			Record e = list.get(row - 1);
			for (int i = 0; i < items.size(); i++) {
				MetaField f = items.get(i);
				Object value = e.get(f.getEn());
				if (value == null) {
	                continue;
	            }
				
				sheet.addCell(new Label(i, row, formatValue(f, value)));
			}
			
        }

        // 把创建的内容写入到输出流中，并关闭输出流
        workbook.write();
        workbook.close();

    }

	/**
	 * 格式化单元格值
	 * @param f
	 * @param value
	 * @return
	 */
	private static String formatValue(MetaField f, Object value) {
		String val = value.toString();
		switch (f.getType()) {
		case "时间框":
			val = DateTime.now().toString("yyyy-MM-dd HH:mm:ss");
			break;
		case "日期框":
			val = DateTime.now().toString("yyyy-MM-dd");
			break;
		default:
			break;
		}
		return val;
	}

    /**
     * 获取数据行
     *
     * @param names
     * @param record
     * @return
     */
	public static String[] getValues(List<MetaField> items, Record record) {
        String[] values = new String[items.size()];
        int i = -1;
        for (MetaField item : items) {
            i++;
            Object value = record.get(item.getEn());
            if (value == null) {
                continue;
            }
            values[i] = value.toString();
        }
        return values;
    }

	/**
	 * 导入Excel
	 * 
	 * @param is
	 * @param items
	 * @return
	 */
    public static List<Record> importExcel(InputStream is, List<MetaField> items) {

        List<Record> list = new ArrayList<Record>();

        Workbook workbook = null;
        try {
            // 从读取流创建只读Workbook对象
            workbook = Workbook.getWorkbook(is);
            // 获取第一张Sheet表
            Sheet readsheet = workbook.getSheet(0);

            // 总列数
            int colSum = readsheet.getColumns();
            // 总行数
            int rowSum = readsheet.getRows();

            // 表头中文信息
            String[] headers = new String[colSum];
            // CN -> EN
            Map<String, String> field = getKeyValue(items);

            int row = 0;

            // 读取表头
            for (int i = 0; i < colSum; i++) {
                Cell cell = readsheet.getCell(i, 0);
                headers[i] = cell.getContents();
            }
            row++;

            // 获取指定单元格的对象引用
            for (; row < rowSum; row++) {
                Record record = new Record();
                for (int i = 0; i < colSum; i++) {
                    Cell cell = readsheet.getCell(i, row);
                    String s = cell.getContents();
                    // 空值不参与导入持久化
                    if (!xx.isEmpty(s)) {
                        // 根据表头CN获取EN
                        record.set(field.get(headers[i]), s);
                    }
                }
                // 忽略空行
                if(!record.getColumns().isEmpty())
                	list.add(record);
            }
		} catch (BiffException e) {
			throw new RuntimeException("读取文件异常,只能选择经典版(Office97-2003)XlS文件");
		} catch (Exception e) {
            e.printStackTrace();
        } finally {
            workbook.close();
        }

        return list;
    }

	/**
	 * 导入有合并单元格的Excel:先将合并单元格自动拆分
	 * 
	 * @param is
	 * @param items
	 * @return
	 * @throws IOException
	 * @throws WriteException
	 */
	public static List<Record> importExcelByMergedCell(InputStream is, List<MetaField> items) {

		String tempDir = System.getProperty("java.io.tmpdir");
		String tempName = System.currentTimeMillis() + RandomUtil.nextIntAsStringByLength(3) + ".xls";
		String tempPath = tempDir + File.separator + tempName;
		File tempXls = new File(tempPath);

		Workbook wb = null;
		WritableWorkbook wwb = null;

		try {
			wb = Workbook.getWorkbook(is);
			wwb = Workbook.createWorkbook(tempXls);

			Sheet sheet = wb.getSheet(0);
			WritableSheet sheet1 = wwb.createSheet("temp", 0);

			Range[] rangeCell = sheet.getMergedCells(); // 获取合并单元格的数组
			// 将合并单元部分全部展示出来
			for (int i = 0; i < sheet.getRows(); i++) {
				for (int j = 0; j < sheet.getColumns(); j++) {
					String str = sheet.getCell(j, i).getContents();
					for (Range r : rangeCell) {
						if (i > r.getTopLeft().getRow() && i <= r.getBottomRight().getRow() && j >= r.getTopLeft().getColumn() && j <= r.getBottomRight().getColumn()) {
							str = sheet.getCell(r.getTopLeft().getColumn(), r.getTopLeft().getRow()).getContents();
						}
					}
					Label label = new Label(j, i, str);
					sheet1.addCell(label);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				wwb.write();
				wwb.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			wb.close();
		}

		InputStream tempIs = null;
		try {
			// 导入Excel
			tempIs = new FileInputStream(tempXls);
			return importExcel(tempIs, items);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (tempIs != null)
				try {
					tempIs.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			// 自动删除废弃临时文件
			if (tempXls.exists())
				tempXls.delete();
		}

		return null;
	}

	private static Map<String, String> getKeyValue(List<MetaField> items) {
        HashMap<String, String> map = new HashMap<String, String>();
        for (MetaField item : items) {
			// 跳过虚拟字段
			if (item.isVirtual()) {
				continue;
			}
			map.put(item.getCn(), item.getEn());
        }
        return map;
    }

    public static void writeExcel(InputStream is, OutputStream os, HashMap<String, String> data) throws Exception {


        WritableWorkbook wwb = null;
        WorkbookSettings settings = new WorkbookSettings();
        settings.setEncoding("UTF-8"); // 关键代码，解决中文乱码:GB18030，UTF-8，ISO-8859-1 等
        settings.setWriteAccess("eovaxls");
        // 读取模版
        Workbook in = Workbook.getWorkbook(is, settings);

        // 创建写表格
        wwb = Workbook.createWorkbook(os, in);
        // 取表格第一页
        WritableSheet sheet = wwb.getSheet(1);

        // 总列数
        int colSum = sheet.getColumns();
        // 总行数
        int rowSum = sheet.getRows();
        System.out.println(colSum);
        System.out.println(rowSum);

        sheet.addCell(new Label(1, 1, "6666666"));

        Cell cell = sheet.getCell(1, 1);
        System.out.println(cell.getContents());
        
        // 把创建的内容写入到输出流中，并关闭输出流
        wwb.write();
        wwb.close();
    }

}