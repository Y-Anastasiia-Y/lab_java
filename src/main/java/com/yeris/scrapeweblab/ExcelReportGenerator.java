package com.yeris.scrapeweblab;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelReportGenerator {
    public static String getExcelFromProductList(List<TouchProductModel> productList) throws IOException {
// Blank workbook
        HSSFWorkbook workbook = new HSSFWorkbook();
        // Creating a blank Excel sheet
        HSSFSheet sheet = workbook.createSheet("Products");
        // Creating an empty TreeMap of string and Object][] type


        int rownum = 0;
        for (TouchProductModel productModel : productList) {
            // Creating a new row in the sheet
            HSSFRow row = sheet.createRow(rownum++);

            Object[] objArr = new Object[4];
            objArr[0] = productModel.productName;
            objArr[1] = productModel.article;
            objArr[2] = productModel.oldPrice;
            objArr[3] = productModel.productLink;

            int cellnum = 0;
            for (Object obj : objArr) {
                // This line creates a cell in the next column of that row
                HSSFCell cell = row.createCell(cellnum++);
                if (obj instanceof String)
                    cell.setCellValue((String)obj);
                else if (obj instanceof Integer)
                    cell.setCellValue((Integer)obj);
            }
        }
        // Writing the workbook
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentDateTime = dateFormat.format(currentDate);

        String fileName = String.format("products_%s.xls", currentDateTime);
        FileOutputStream out = new FileOutputStream(fileName);
        workbook.write(out);
        // Closing file output connections
        out.close();

        return fileName;
    }
}
