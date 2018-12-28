package com.fluex404.springboot_export_n_import.dao;

import com.fluex404.springboot_export_n_import.model.Siswa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiswaDao extends JpaRepository<Siswa, Integer> {
}
