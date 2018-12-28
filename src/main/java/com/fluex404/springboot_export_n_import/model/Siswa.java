package com.fluex404.springboot_export_n_import.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@Entity
@Table(name="siswa")
public class Siswa implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String kelas;
    private String jurusan;

    public Siswa() {
    }
}
