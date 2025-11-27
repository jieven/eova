/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.common.utils.excel;

import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.eova.model.MetaField;
import cn.eova.tools.x;
import com.jfinal.plugin.activerecord.Record;
import org.ttzero.excel.entity.Column;
import org.ttzero.excel.entity.SimpleSheet;
import org.ttzero.excel.entity.Workbook;
import org.ttzero.excel.entity.style.Fill;
import org.ttzero.excel.entity.style.PatternType;
import org.ttzero.excel.entity.style.Styles;

/**
 * Excel文件处理
 * 支持Excel 2007 xlsx和csv
 *
 * @author Jieven
 */
public class ExceUtil {


    /**
     * Xlsx写入文件
     * @param fields 导出字段
     * @param data 导出数据
     * @param filePath 文件路径
     * @throws IOException
     */
    public static void export(List<MetaField> fields, List<Record> data, String filePath) throws IOException {
        Workbook book = createWorkBook(fields, data);
        book.writeTo(Paths.get(filePath));
    }

    /**
     * Xlsx写入输出流
     * @param fields 导出字段
     * @param data 导出数据
     * @param os 输出流
     * @throws IOException
     */
    public static void export(List<MetaField> fields, List<Record> data, OutputStream os) throws IOException {
        Workbook book = createWorkBook(fields, data);
        book.writeTo(os);
    }

    /**
     * CSV写入输出流
     * @param fields 导出字段
     * @param data 导出数据
     * @param os 输出流
     * @throws IOException
     */
    public static void exportCsv(List<MetaField> fields, List<Record> data, OutputStream os) throws IOException {
        Workbook book = createWorkBook(fields, data);
        book.saveAsCSV();
        book.writeTo(os);
    }

    /**
     * 创建Excel WorkBook
     *
     * @param fields
     * @param data
     * @throws IOException
     */
    public static Workbook createWorkBook(List<MetaField> fields, List<Record> data) throws IOException {

        SimpleSheet<Object> sheet = new SimpleSheet<>();

        Workbook book = new Workbook();
        book.addSheet(sheet);
        book.setAutoSize(true);// 自适应列宽
        book.bestSpeed(); // 启用性能模式(性能提升60%～100%，文件大小略增加5%～10%)

        // 样式设置参考 https://github.com/wangguanquan/eec/wiki/4-静态设置样式
        // 修改默认样式
        Styles styles = book.getStyles();
        int style = sheet.defaultHeadStyle();
        // 修改填充色
        int styleColor = styles.modifyFill(style, new Fill(PatternType.solid, new Color(238, 238, 238)));

        // 设置表头与字段
        List<Column> heads = new ArrayList<>();
        for (MetaField f : fields) {
            // 设置表头名称, 字段 宽度 样式
            heads.add(new Column(f.getCn(), f.getEn()).setHeaderStyle(styleColor));
            // .setHeaderHeight(200) 设置表头行高
            // .setNumFmt("###0") 数字格式化
            // .setWidth(20.0D) 自定义列宽
            // .setNumFmt("yyyy-mm-dd hh:mm:ss")
            // .setHorizontal(Horizontals.CENTER) // <-- 设置水平居中
        }
        sheet.setColumns(heads);

        // 添加表格数据
        List<Object> rows = new ArrayList<>();
        for (Record r : data) {
            List<Object> cols = new ArrayList<>();
            for (MetaField f : fields) {
                Object value = r.get(f.getEn());
                String val = formatValue(f, value);
                cols.add(val);
            }
            rows.add(cols);
        }
        sheet.setData(rows);

        return book;
    }

    private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * 格式化单元格值
     * @param f
     * @param value
     * @return
     */
    private static String formatValue(MetaField f, Object value) {
        if (x.isEmpty(value)) {
            return "";
        }
        String val = value.toString();
        long ms = 0;
        switch (f.getType()) {
            case "时间框":
                if (value instanceof Timestamp) {
                    return x.time.formatTime(((Timestamp) value).getTime());
                }
                if (value instanceof Date) {
                    return x.time.formatTime(((Date) value).getTime());
                }
                if (value instanceof LocalDateTime) {
                    return ((LocalDateTime) value).format(TIME);
                }
                break;
            case "日期框":
                if (value instanceof Timestamp) {
                    return x.time.formatDate(((Timestamp) value).getTime());
                }
                if (value instanceof Date) {
                    return x.time.formatDate(((Date) value).getTime());
                }
                if (value instanceof LocalDateTime) {
                    return ((LocalDateTime) value).format(DATE);
                }
                break;
            default:
                break;
        }
        return val;
    }

}