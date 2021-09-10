package com.github.analysis;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.annotation.ExcelCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

/**
 * excel解析
 *
 * @author 杨中肖
 * @date 2021/09/05
 */
public class ExcelAnalysis {
    private ExcelAnalysis() {
    }

    /**
     * 解析
     *
     * @param path         excel文件路径
     * @param c       excel对应实体类对象
     * @param headerLength 行头高度
     * @return {@link List<T> }
     * @throws IOException ioexception
     * @author 杨中肖
     * @date 2021/09/10
     */
    public static <T> List<T> analysis(String path, Class<T> c, int headerLength) throws IOException {
        ArrayList<T> list = new ArrayList<>();
        // 获取行头索引与行头值对应map
        Map<Integer, String> indexValueMap = HeaderAnalysis.getIndexValueMap(path, headerLength);

        Field[] declaredFields = c.getDeclaredFields();
        int mapSize = declaredFields.length;

        // 实体类字段名与行头值对应map
        HashMap<String, String> fieldNameHeadNameMap = new HashMap<>(mapSize);

        // 行头索引与实体类字段名对应map
        HashMap<Integer, String> indexFieldNameMap = new HashMap<>(mapSize);

        // 获取实体类字段名与行头值对应map
        for (Field field : declaredFields) {
            ExcelCell annotation = field.getAnnotation(ExcelCell.class);
            String[] headerName = annotation.headerName();
            StringBuilder stringBuilder = new StringBuilder();
            for (int j = 0; j < headerName.length; j++) {
                stringBuilder.append(headerName[j]);
            }
            String headName = stringBuilder.toString();
            fieldNameHeadNameMap.put(field.getName(), headName);
        }

        Set<Map.Entry<Integer, String>> indexValueEntrySet = indexValueMap.entrySet();
        Set<Map.Entry<String, String>> fieldNameHeadNameEntrySet = fieldNameHeadNameMap.entrySet();

        // 获取行头索引与实体类字段名对应map
        for (Map.Entry<Integer, String> entry : indexValueEntrySet) {
            Integer key = entry.getKey();
            String value = entry.getValue();
            for (Map.Entry<String, String> stringEntry : fieldNameHeadNameEntrySet) {
                String headName = stringEntry.getValue();
                String fieldName = stringEntry.getKey();
                if (value.equals(headName)) {
                    indexFieldNameMap.put(key, fieldName);
                    break;
                }
            }
        }

        // 遍历sheet、row
        try (HSSFWorkbook sheets = new HSSFWorkbook(new FileInputStream(path))) {
            HSSFSheet sheet = sheets.getSheetAt(0);
            int cellCount = 0;
            for (Row cells : sheet) {
                // 跳过行头
                if (cellCount < headerLength) {
                    cellCount++;
                    continue;
                }
                // 获取实体类字段名与字段值对应map,用于反序列化对象
                HashMap<String, String> jsonMap = new HashMap<>(mapSize);
                for (Cell cell : cells) {
                    // 获取单元格值
                    String value = cell.toString();
                    // 获取单元格所在列索引
                    int index = cell.getColumnIndex();
                    // 根据索引从indexFieldNameMap获取单元格值对应的字段
                    String fieldName = indexFieldNameMap.get(index);
                    if (fieldName != null) {
                        jsonMap.put(fieldName, value);
                    }
                }
                // 反序列化实体类对象
                ObjectMapper objectMapper = new ObjectMapper();
                String json = objectMapper.writeValueAsString(jsonMap);
                T t = objectMapper.readValue(json, c);
                list.add(t);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
