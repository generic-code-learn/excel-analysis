package com.github.analysis;


import com.github.model.CellModel;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 行头解析
 *
 * @author 杨中肖
 * @date 2021/09/09
 */
public class HeaderAnalysis {
    public static List<CellModel> getMergedHeaderList(InputStream inputStream, int headerLength) throws IOException {
        return getMergedHeaderList(new HSSFWorkbook(inputStream), headerLength);
    }

    public static List<CellModel> getMergedHeaderList(HSSFWorkbook workbook, int headerLength) throws IOException {
        return getMergedHeaderList(workbook.getSheetAt(0), headerLength);
    }

    private HeaderAnalysis() {
    }

    public static List<CellModel> getMergedHeaderList(HSSFSheet sheet, int headerLength) throws IOException {
        List<CellRangeAddress> mergedRegions = sheet.getMergedRegions();
        ArrayList<CellModel> list = new ArrayList<>();
        for (CellRangeAddress mergedRegion : mergedRegions) {
            int firstColumn = mergedRegion.getFirstColumn();
            int lastColumn = mergedRegion.getLastColumn();
            int firstRow = mergedRegion.getFirstRow();
            int lastRow = mergedRegion.getLastRow();
            HSSFCell cell = sheet.getRow(firstRow).getCell(firstColumn);
            String value = cell.toString();

            for (int i = firstColumn; i <= lastColumn; i++) {
                for (int j = firstRow; j <= lastRow; j++) {
                    CellModel cellModel = new CellModel(i, j, value);
                    list.add(cellModel);
                }
            }

        }
        return list;
    }


    public static List<CellModel> getMergedHeaderList(String path, int headerLength) throws IOException {
        return getMergedHeaderList(new FileInputStream(path), headerLength);
    }

    public static List<CellModel> getHeaderList(InputStream inputStream, int headerLength) {
        try (HSSFWorkbook sheets = new HSSFWorkbook(inputStream)) {
            HSSFSheet sheet = sheets.getSheetAt(0);
            ArrayList<CellModel> list = new ArrayList<>();
            int cellCount = 0;
            for (Row cells : sheet) {
                if (cellCount == headerLength) {
                    break;
                }
                for (Cell cell : cells) {
                    CellModel cellModel = new CellModel(cell.getColumnIndex(), cell.getRowIndex(), cell.toString());
                    if (!list.contains(cellModel)) {
                        list.add(cellModel);
                    }
                }
                cellCount++;
            }
            List<CellModel> mergedHeaderList = getMergedHeaderList(sheet, headerLength);
            list.addAll(mergedHeaderList);
            return list.stream().distinct().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<CellModel> getHeaderList(String path, int headerLength) throws IOException {
        return getHeaderList(new FileInputStream(path), headerLength);
    }

    public static Map<Integer, String> getIndexValueMap(String path, int headerLength) throws IOException {
        List<CellModel> headerList = getHeaderList(path, headerLength);
        Map<Integer, List<CellModel>> indexMap = headerList.stream().collect(Collectors.groupingBy(CellModel::getX));
        HashMap<Integer, String> map = new HashMap<>(indexMap.size());
        indexMap.forEach((k, v) -> {
            StringBuilder builder = new StringBuilder();
            v.sort(Comparator.comparingInt(CellModel::getY));
            v.forEach(x -> builder.append(x.getValue()));
            map.put(k, builder.toString());
        });
        return map;
    }
}
