package com.module.helpers;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelTableGenerator {

    public void writeIntoExcel(List<List<String>> exportData, File file) throws IOException {
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        HSSFSheet hssfSheet = hssfWorkbook.createSheet("Ветераны");

        int rowNum = 0;
        for (List<String> dataType : exportData) {
            Row row = hssfSheet.createRow(rowNum++);
            int colNum = 0;
            for (String field : dataType) {
                Cell cell = row.createCell(colNum++);
                cell.setCellValue(field);
            }
        }
        writeFile(hssfWorkbook, file);
    }

    private void writeFile(HSSFWorkbook hssfWorkbook, File file) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(file);
        hssfWorkbook.write(outputStream);
        hssfWorkbook.close();
        outputStream.close();
    }
}
