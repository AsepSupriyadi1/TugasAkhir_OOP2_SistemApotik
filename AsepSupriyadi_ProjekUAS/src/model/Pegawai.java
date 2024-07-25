/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 *
 * @author TUF
 */
public class Pegawai {

    private String kodePegawai;
    private String namaPegawai;
    private String username;
    private String password;
    private Date tanggalDiMasukan;

    public Pegawai() {
    }

    public Pegawai(String kodePegawai, String namaPegawai, String username, String password, Date tanggalDiMasukan) {
        this.kodePegawai = kodePegawai;
        this.namaPegawai = namaPegawai;
        this.username = username;
        this.password = password;
        this.tanggalDiMasukan = tanggalDiMasukan;
    }

    public String getKodePegawai() {
        return kodePegawai;
    }

    public void setKodePegawai(String kodePegawai) {
        this.kodePegawai = kodePegawai;
    }

    public String getNamaPegawai() {
        return namaPegawai;
    }

    public void setNamaPegawai(String namaPegawai) {
        this.namaPegawai = namaPegawai;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getTanggalDiMasukan() {
        return tanggalDiMasukan;
    }

    public void setTanggalDiMasukan(Date tanggalDiMasukan) {
        this.tanggalDiMasukan = tanggalDiMasukan;
    }

    @Override
    public String toString() {
        return "Pegawai{" + "kodePegawai=" + kodePegawai + ", namaPegawai=" + namaPegawai + ", username=" + username + ", password=" + password + ", tanggalDiMasukan=" + tanggalDiMasukan + '}';
    }

}
