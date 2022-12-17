package com.observationclass.utils;

import com.observationclass.entity.Campus;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelHelper {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADER_CAMPUS = {"id", "campus_name"};
    static String SHEET_CAMPUS = "campus";

    public static boolean hasExcelFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return false;
    }
    public static List<Campus> getCampusDataExcel(InputStream ins) {
        List<Campus> listOfCampus = new ArrayList<>();
        try {
            Workbook workbook = new XSSFWorkbook(ins);
            Sheet sheet = workbook.getSheet(SHEET_CAMPUS);
            Iterator<Row> rows = sheet.iterator();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cells = currentRow.iterator();
                Campus campus = new Campus();
                Integer cellIndex = 0;
                while (cells.hasNext()) {
                    Cell currentCell = cells.next();
                    switch (cellIndex) {
                        case 1:
                            campus.setCampusName(currentCell.getStringCellValue());
                            break;
                    }
                    cellIndex++;
                }
                listOfCampus.add(campus);
                rowNumber++;
            }
            workbook.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return listOfCampus;
    }
}
