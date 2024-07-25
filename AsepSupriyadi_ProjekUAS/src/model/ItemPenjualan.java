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
public class ItemPenjualan {

    private Integer idEntryTransaksi;
    private String kodeTransaksi;
    private String kodeObat;
    private String namaBarang;
    private Integer quantity;
    private Double hargaItem;
    private Double total;

    public ItemPenjualan() {
    }

    public ItemPenjualan(Integer idEntryTransaksi, String kodeTransaksi, String kodeObat, String namaBarang, Integer quantity, Double hargaItem, Double total) {
        this.idEntryTransaksi = idEntryTransaksi;
        this.kodeTransaksi = kodeTransaksi;
        this.kodeObat = kodeObat;
        this.namaBarang = namaBarang;
        this.quantity = quantity;
        this.hargaItem = hargaItem;
        this.total = total;
    }

    public Integer getIdEntryTransaksi() {
        return idEntryTransaksi;
    }

    public void setIdEntryTransaksi(Integer idEntryTransaksi) {
        this.idEntryTransaksi = idEntryTransaksi;
    }

    public String getKodeTransaksi() {
        return kodeTransaksi;
    }

    public void setKodeTransaksi(String kodeTransaksi) {
        this.kodeTransaksi = kodeTransaksi;
    }

    public String getKodeObat() {
        return kodeObat;
    }

    public void setKodeObat(String kodeObat) {
        this.kodeObat = kodeObat;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getHargaItem() {
        return hargaItem;
    }

    public void setHargaItem(Double hargaItem) {
        this.hargaItem = hargaItem;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "ItemPenjualan{" + "idEntryTransaksi=" + idEntryTransaksi + ", kodeTransaksi=" + kodeTransaksi + ", kodeObat=" + kodeObat + ", namaBarang=" + namaBarang + ", quantity=" + quantity + ", hargaItem=" + hargaItem + ", total=" + total + '}';
    }

}
