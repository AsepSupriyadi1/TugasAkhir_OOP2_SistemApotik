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
public class Transaksi {

    private String kodeTransaksi;
    private Integer idPelanggan;
    private Double totalPembelian;
    private Double totalDibayarkan;
    private Double totalKembalian;
    private Date tglTransaksi;

    public Transaksi() {
    }

    public Transaksi(String kodeTransaksi, Integer idPelanggan, Double totalPembelian, Double totalDibayarkan, Double totalKembalian, Date tglTransaksi) {
        this.kodeTransaksi = kodeTransaksi;
        this.idPelanggan = idPelanggan;
        this.totalPembelian = totalPembelian;
        this.totalDibayarkan = totalDibayarkan;
        this.totalKembalian = totalKembalian;
        this.tglTransaksi = tglTransaksi;
    }

    public String getKodeTransaksi() {
        return kodeTransaksi;
    }

    public void setKodeTransaksi(String kodeTransaksi) {
        this.kodeTransaksi = kodeTransaksi;
    }

    public Integer getIdPelanggan() {
        return idPelanggan;
    }

    public void setIdPelanggan(Integer idPelanggan) {
        this.idPelanggan = idPelanggan;
    }

    public Double getTotalPembelian() {
        return totalPembelian;
    }

    public void setTotalPembelian(Double totalPembelian) {
        this.totalPembelian = totalPembelian;
    }

    public Double getTotalDibayarkan() {
        return totalDibayarkan;
    }

    public void setTotalDibayarkan(Double totalDibayarkan) {
        this.totalDibayarkan = totalDibayarkan;
    }

    public Double getTotalKembalian() {
        return totalKembalian;
    }

    public void setTotalKembalian(Double totalKembalian) {
        this.totalKembalian = totalKembalian;
    }

    public Date getTglTransaksi() {
        return tglTransaksi;
    }

    public void setTglTransaksi(Date tglTransaksi) {
        this.tglTransaksi = tglTransaksi;
    }

    @Override
    public String toString() {
        return "Transaksi{" + "kodeTransaksi=" + kodeTransaksi + ", idPelanggan=" + idPelanggan + ", totalPembelian=" + totalPembelian + ", totalDibayarkan=" + totalDibayarkan + ", totalKembalian=" + totalKembalian + ", tglTransaksi=" + tglTransaksi + '}';
    }

}
