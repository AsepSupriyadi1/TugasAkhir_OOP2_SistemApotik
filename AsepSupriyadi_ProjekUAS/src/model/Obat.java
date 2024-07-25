/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author TUF
 */
public class Obat {

    private String kodeObat;
    private String namaObat;
    private Double hargaBeli;
    private Double hargaJual;
    private EnumKategoriObat kategori;
    private int stokObat;

    public Obat() {
    }

    public Obat(String kodeObat, String namaObat, Double hargaBeli, Double hargaJual, EnumKategoriObat kategori, int stokObat) {
        this.kodeObat = kodeObat;
        this.namaObat = namaObat;
        this.hargaBeli = hargaBeli;
        this.hargaJual = hargaJual;
        this.kategori = kategori;
        this.stokObat = stokObat;
    }

    public String getKodeObat() {
        return kodeObat;
    }

    public void setKodeObat(String kodeObat) {
        this.kodeObat = kodeObat;
    }

    public String getNamaObat() {
        return namaObat;
    }

    public void setNamaObat(String namaObat) {
        this.namaObat = namaObat;
    }

    public Double getHargaBeli() {
        return hargaBeli;
    }

    public void setHargaBeli(Double hargaBeli) {
        this.hargaBeli = hargaBeli;
    }

    public Double getHargaJual() {
        return hargaJual;
    }

    public void setHargaJual(Double hargaJual) {
        this.hargaJual = hargaJual;
    }

    public EnumKategoriObat getKategori() {
        return kategori;
    }

    public void setKategori(EnumKategoriObat kategori) {
        this.kategori = kategori;
    }

    public int getStokObat() {
        return stokObat;
    }

    public void setStokObat(int stokObat) {
        this.stokObat = stokObat;
    }

    @Override
    public String toString() {
        return "InventoryObat{" + "kodeObat=" + kodeObat + ", namaObat=" + namaObat + ", hargaBeli=" + hargaBeli + ", hargaJual=" + hargaJual + ", kategori=" + kategori + ", stokObat=" + stokObat + '}';
    }

}
