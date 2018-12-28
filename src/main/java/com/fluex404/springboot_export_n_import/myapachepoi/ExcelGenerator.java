package com.fluex404.springboot_export_n_import.myapachepoi;

import com.fluex404.springboot_export_n_import.dao.SiswaDao;
import com.fluex404.springboot_export_n_import.model.Siswa;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.formula.udf.UDFFinder;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Iterator;
import java.util.List;

@Component
public class ExcelGenerator {

    @Autowired
    private SiswaDao siswaDao;

    /* export */
    public ByteArrayInputStream exportExcel(List<Siswa> siswas) throws Exception{
        String[] columns = {"Id", "Name", "Kelas", "Jurusan"};
        try(
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream out = new ByteArrayOutputStream()
                )
        {
            CreationHelper creationHelper = workbook.getCreationHelper();
            Sheet sheet = workbook.createSheet("Data Siswa");
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.BLUE.getIndex());
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            //Row ofor Header
            Row headerRow = sheet.createRow(0);

            //Header
            for(int i=0;i<columns.length;i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerCellStyle);
            }


            int rowIdx = 1;
            for(Siswa siswa : siswas) {
                Row row = sheet.createRow(rowIdx);

                row.createCell(0).setCellValue(siswa.getId());
                row.createCell(1).setCellValue(siswa.getName());
                row.createCell(2).setCellValue(siswa.getKelas());
                row.createCell(3).setCellValue(siswa.getJurusan());
                rowIdx++;
            }

            workbook.write(out);
            workbook.close();
            return new ByteArrayInputStream(out.toByteArray());
        }catch(Exception e) {

        }
        return null;
    }

    /* Import */
    public void importExcel(MultipartFile file) throws Exception{

        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        for(int i=0;i<(CoutRowExcel(sheet.rowIterator()));i++) {
            if(i == 0) {
                continue;
            }

            Row row = sheet.getRow(i);

            String nama = row.getCell(1).getStringCellValue();
            String kelas = row.getCell(2).getStringCellValue();
            String jurusan = row.getCell(3).getStringCellValue();

            siswaDao.save(new Siswa(0, nama, kelas, jurusan));
        }

    }

    /* Cout Row of Excel Table */
    public static int CoutRowExcel(Iterator<Row> iterator) {
        int size = 0;
        while(iterator.hasNext()) {
            Row row = iterator.next();
            size++;
        }
        return size;
    }
}
