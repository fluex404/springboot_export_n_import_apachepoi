package com.fluex404.springboot_export_n_import.controller;

import com.fluex404.springboot_export_n_import.dao.SiswaDao;
import com.fluex404.springboot_export_n_import.model.Siswa;
import com.fluex404.springboot_export_n_import.myapachepoi.ExcelGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
public class HttpMappingRestController {

    private SiswaDao siswaDao;
    private ExcelGenerator excel;

    @Autowired
    public HttpMappingRestController(SiswaDao siswaDao, ExcelGenerator excel) {
        this.siswaDao = siswaDao;
        this.excel = excel;
    }

    @GetMapping("/export")
    public ResponseEntity<InputStreamResource> excelSiswaReport() throws Exception {
        List<Siswa> siswas = siswaDao.findAll();

        ByteArrayInputStream in = excel.exportExcel(siswas);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=siswa.xlsx");

        return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));

    }


}
