package com.observationclass.utils;

import com.observationclass.entity.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ExcelHelper {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADER_CAMPUS = {"Id", "Campus Name"};
    static String[] HEADER_SEMESTER = {"Id", "Semester Name", "Start Date", "End Date"};
    static String[] HEADER_ROOM = {"Id", "Room Name", "Campus"};
    static String[] HEADER_SUBJECT = {"Id", "Subject Code", "Subject Name", "Department"};
    static String[] HEADER_SLOT = {"Id", "Slot Name", "Slot Time Range"};
    static String SHEET_CAMPUS = "campus";
    static String SHEET_SEMESTER = "semester";
    static String SHEET_ROOM = "room";
    static String SHEET_SUBJECT = "subject";
    static String SHEET_SLOT = "slot";

    public static boolean hasExcelFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return false;
    }

    public static void writeSemesterToExcel(List<Semester> listSemester, Workbook workBook) throws IOException {
        Sheet sheet = workBook.createSheet(SHEET_SEMESTER);
        Row rowHeader = sheet.createRow(0);
        for (int i = 0; i < HEADER_SEMESTER.length; i++) {
            Cell cell1 = rowHeader.createCell(i);
            cell1.setCellValue(HEADER_SEMESTER[i]);
        }
        Integer rowIndex = 1;
        CreationHelper creationHelper = workBook.getCreationHelper();
        CellStyle cellStyle = workBook.createCellStyle();
        cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd hh:mm:ss"));
        for (Semester semester : listSemester) {
            Row row = sheet.createRow(rowIndex++);
            Double id = new Double(semester.getId());
            row.createCell(0).setCellValue((id));
            row.createCell(1).setCellValue(semester.getSemesterName());
            row.createCell(2).setCellValue(new Date(semester.getStartDate().getTime()));
            row.getCell(2).setCellStyle(cellStyle);
            row.createCell(3).setCellValue(new Date(semester.getEndDate().getTime()));
            row.getCell(3).setCellStyle(cellStyle);
        }
    }

    public static void writeCampusToExcel(List<Campus> listCampus, Workbook workBook) {
        Sheet sheet = workBook.createSheet(SHEET_CAMPUS);
        Row rowHeader = sheet.createRow(0);
        for (int i = 0; i < HEADER_CAMPUS.length; i++) {
            Cell cell = rowHeader.createCell(i);
            cell.setCellValue(HEADER_CAMPUS[i]);
        }
        Integer rowIndex = 1;
        for (Campus campus : listCampus) {
            Row row = sheet.createRow(rowIndex++);
            Double id = new Double(campus.getId());
            row.createCell(0).setCellValue((id));
            row.createCell(1).setCellValue(campus.getCampusName());
        }

    }

    public static void writeSubjectToExcel(List<Subject> listSubject, Workbook workBook) {
        Sheet sheet = workBook.createSheet(SHEET_SUBJECT);
        Row rowHeader = sheet.createRow(0);
        for (int i = 0; i < HEADER_SUBJECT.length; i++) {
            Cell cell = rowHeader.createCell(i);
            cell.setCellValue(HEADER_SUBJECT[i]);
        }
        Integer rowIndex = 1;
        for (Subject subject : listSubject) {
            Row row = sheet.createRow(rowIndex++);
            Double subjectId = new Double(subject.getId());
            row.createCell(0).setCellValue((subjectId));
            row.createCell(1).setCellValue(subject.getSubjectCode());
            row.createCell(2).setCellValue(subject.getSubject_name());
            Double campusId = new Double(subject.getDepartmentId());
            row.createCell(3).setCellValue(campusId);
        }
    }

    public static void writeRoomToExcel(List<Room> listRoom, Workbook workBook) {
        Sheet sheet = workBook.createSheet(SHEET_ROOM);
        Row rowHeader = sheet.createRow(0);
        for (int i = 0; i < HEADER_ROOM.length; i++) {
            Cell cell = rowHeader.createCell(i);
            cell.setCellValue(HEADER_ROOM[i]);
        }
        Integer rowIndex = 1;
        for (Room room : listRoom) {
            Row row = sheet.createRow(rowIndex++);
            Double roomId = new Double(room.getId());
            row.createCell(0).setCellValue(roomId);
            row.createCell(1).setCellValue(room.getRoom_name());
            Double campusId = new Double(room.getCampusId());
            row.createCell(2).setCellValue(campusId);
        }
    }

    public static void writeSlotToExcel(List<Slot> listSlot, Workbook workBook) {
        Sheet sheet = workBook.createSheet(SHEET_SLOT);
        Row rowHeader = sheet.createRow(0);
        for (int i = 0; i < HEADER_SLOT.length; i++) {
            Cell cell = rowHeader.createCell(i);
            cell.setCellValue(HEADER_SLOT[i]);
        }
        Integer rowIndex = 1;
        for (Slot slot : listSlot) {
            Row row = sheet.createRow(rowIndex++);
            Double slotId = new Double(slot.getId());
            row.createCell(0).setCellValue(slotId);
            row.createCell(1).setCellValue(slot.getSlotName());
            row.createCell(2).setCellValue(slot.getSlotRange());
        }
    }

    public static ByteArrayInputStream exportData(List<Campus> listCampus, List<Semester> listSemester, List<Room> listRoom,
                                                  List<Subject> listSubject, List<Slot> listSlot) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        writeCampusToExcel(listCampus, workbook);
        writeSemesterToExcel(listSemester, workbook);
        writeSubjectToExcel(listSubject, workbook);
        writeRoomToExcel(listRoom, workbook);
        writeSlotToExcel(listSlot, workbook);
        workbook.write(out);
        return new ByteArrayInputStream(out.toByteArray());
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
                        case 0:
                            Double id = new Double(currentCell.getNumericCellValue());
                            campus.setId(id.intValue());
                            break;
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

    public static List<Semester> getSemesterDataExcel(InputStream ins) {
        List<Semester> listOfSemester = new ArrayList<>();
        try {
            Workbook workBook = new XSSFWorkbook(ins);
            Sheet sheet = workBook.getSheet(SHEET_SEMESTER);
            Iterator<Row> row = sheet.iterator();
            int rowNumber = 0;
            while (row.hasNext()) {
                Row currentRow = row.next();
                // missing header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cells = currentRow.iterator();
                int cellIndex = 0;
                Semester semester = new Semester();
                while (cells.hasNext()) {
                    Cell cellCurrent = cells.next();
                    switch (cellIndex) {
                        case 0:
                            Double id = new Double(cellCurrent.getNumericCellValue());
                            semester.setId(id.intValue());
                            break;
                        case 1:
                            semester.setSemesterName(cellCurrent.getStringCellValue());
                            break;
                        case 2:
                            semester.setStartDate(new Timestamp(cellCurrent.getDateCellValue().getTime()));
                            break;
                        case 3:
                            semester.setEndDate(new Timestamp(cellCurrent.getDateCellValue().getTime()));
                            break;
                    }
                    cellIndex++;
                }
                listOfSemester.add(semester);
                rowNumber++;
            }
            workBook.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return listOfSemester;
    }

    public static List<Subject> getSubjectDataExcel(InputStream ins) throws IOException {
        List<Subject> listOfSubject = new ArrayList<>();
        Workbook workBook = new XSSFWorkbook(ins);
        Sheet sheet = workBook.getSheet(SHEET_SUBJECT);
        Iterator<Row> row = sheet.iterator();
        int rowNumber = 0;
        while (row.hasNext()) {
            Row currentRow = row.next();
            // missing header
            if (rowNumber == 0) {
                rowNumber++;
                continue;
            }
            Iterator<Cell> cells = currentRow.iterator();
            int cellIndex = 0;
            Subject subject = new Subject();
            while (cells.hasNext()) {
                Cell cellCurrent = cells.next();
                switch (cellIndex) {
                    case 0:
                        Double id = new Double(cellCurrent.getNumericCellValue());
                        subject.setId(id.intValue());
                        break;
                    case 1:
                        subject.setSubjectCode(cellCurrent.getStringCellValue());
                        break;
                    case 2:
                        subject.setSubject_name(cellCurrent.getStringCellValue());
                        break;
                    case 3:
                        Double departmentId = new Double(cellCurrent.getNumericCellValue());
                        subject.setDepartmentId(departmentId.intValue());
                        break;
                }
                cellIndex++;
            }
            listOfSubject.add(subject);
            rowNumber++;
        }
        workBook.close();
        return listOfSubject;
    }

    public static List<Room> getRoomDataExcel(InputStream ins) throws IOException {
        List<Room> listOfRoom = new ArrayList<>();
        Workbook workBook = new XSSFWorkbook(ins);
        Sheet sheet = workBook.getSheet(SHEET_ROOM);
        Iterator<Row> row = sheet.iterator();
        int rowNumber = 0;
        while (row.hasNext()) {
            Row currentRow = row.next();
            // missing header
            if (rowNumber == 0) {
                rowNumber++;
                continue;
            }
            Iterator<Cell> cells = currentRow.iterator();
            int cellIndex = 0;
            Room room = new Room();
            while (cells.hasNext()) {
                Cell cellCurrent = cells.next();
                switch (cellIndex) {
                    case 0:
                        Double id = new Double(cellCurrent.getNumericCellValue());
                        room.setId(id.intValue());
                        break;
                    case 1:
                        room.setRoom_name(cellCurrent.getStringCellValue());
                        break;
                    case 2:
                        Double campusId = new Double(cellCurrent.getNumericCellValue());
                        room.setCampusId(campusId.intValue());
                        break;
                }
                cellIndex++;
            }
            listOfRoom.add(room);
            rowNumber++;
        }
        workBook.close();
        return listOfRoom;
    }

    public static List<Slot> getSlotDataExcel(InputStream ins) throws IOException {
        List<Slot> listOfSlot = new ArrayList<>();
        Workbook workBook = new XSSFWorkbook(ins);
        Sheet sheet = workBook.getSheet(SHEET_SLOT);
        Iterator<Row> row = sheet.iterator();
        int rowNumber = 0;
        while (row.hasNext()) {
            Row currentRow = row.next();
            // missing header
            if (rowNumber == 0) {
                rowNumber++;
                continue;
            }
            Iterator<Cell> cells = currentRow.iterator();
            int cellIndex = 0;
            Slot slot = new Slot();
            while (cells.hasNext()) {
                Cell cellCurrent = cells.next();
                switch (cellIndex) {
                    case 0:
                        Double id = new Double(cellCurrent.getNumericCellValue());
                        slot.setId(id.intValue());
                        break;
                    case 1:
                        slot.setSlotName(cellCurrent.getStringCellValue());
                        break;
                    case 2:
                        slot.setSlotRange(cellCurrent.getStringCellValue());
                        break;
                }
                cellIndex++;
            }
            listOfSlot.add(slot);
            rowNumber++;
        }
        workBook.close();
        return listOfSlot;
    }

}
