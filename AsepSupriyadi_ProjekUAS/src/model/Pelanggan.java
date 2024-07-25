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
public class Pelanggan {

    private Integer id_pelanggan;
    private String nama_pelanggan;
    private String email;
    private String no_telp;
    private String alamat;
    private Date tgl_dibuat;

    public Pelanggan() {
    }

    public Pelanggan(Integer id_pelanggan, String nama_pelanggan, String email, String no_telp, String alamat, Date tgl_dibuat) {
        this.id_pelanggan = id_pelanggan;
        this.nama_pelanggan = nama_pelanggan;
        this.email = email;
        this.no_telp = no_telp;
        this.alamat = alamat;
        this.tgl_dibuat = tgl_dibuat;
    }

    public Integer getId_pelanggan() {
        return id_pelanggan;
    }

    public void setId_pelanggan(Integer id_pelanggan) {
        this.id_pelanggan = id_pelanggan;
    }

    public String getNama_pelanggan() {
        return nama_pelanggan;
    }

    public void setNama_pelanggan(String nama_pelanggan) {
        this.nama_pelanggan = nama_pelanggan;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNo_telp() {
        return no_telp;
    }

    public void setNo_telp(String no_telp) {
        this.no_telp = no_telp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public Date getTgl_dibuat() {
        return tgl_dibuat;
    }

    public void setTgl_dibuat(Date tgl_dibuat) {
        this.tgl_dibuat = tgl_dibuat;
    }

    @Override
    public String toString() {
        return "Pelanggan{" + "id_pelanggan=" + id_pelanggan + ", nama_pelanggan=" + nama_pelanggan + ", email=" + email + ", no_telp=" + no_telp + ", alamat=" + alamat + ", tgl_dibuat=" + tgl_dibuat + '}';
    }

}
