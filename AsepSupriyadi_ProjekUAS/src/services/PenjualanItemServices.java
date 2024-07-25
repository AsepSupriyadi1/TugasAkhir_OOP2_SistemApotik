/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import MyException.CustomException;
import database.KoneksiDatabase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.ItemPenjualan;
import utils.EncryptionService;

/**
 *
 * @author TUF
 */
public class PenjualanItemServices {

    private final static Connection conn = KoneksiDatabase.getKoneksi();
    private final static String namaTable = "tb_entry_transaksi";

    public static void tambahItem(ItemPenjualan reqNewItem) throws Exception {
        PreparedStatement p = null;
        ResultSet rs = null;

        try {
            // Periksa apakah item sudah ada dalam transaksi
            String query = "SELECT * FROM " + namaTable
                    + " WHERE kode_obat=? AND kode_transaksi=?";
            p = conn.prepareStatement(query);
            p.setString(1, reqNewItem.getKodeObat());
            p.setString(2, reqNewItem.getKodeTransaksi());
            rs = p.executeQuery();

            if (rs.next()) {
                // Jika item sudah ada, lakukan update
                int existingQuantity = rs.getInt("quantity");
                double existingTotal = rs.getDouble("total");

                int newQuantity = existingQuantity + reqNewItem.getQuantity();
                double newTotal = existingTotal + reqNewItem.getTotal();

                String updateSQL = "UPDATE " + namaTable
                        + " SET quantity=?, total=?"
                        + " WHERE kode_obat=? AND kode_transaksi=?";
                p = conn.prepareStatement(updateSQL);
                p.setInt(1, newQuantity);
                p.setDouble(2, newTotal);
                p.setString(3, reqNewItem.getKodeObat());
                p.setString(4, reqNewItem.getKodeTransaksi());
                p.executeUpdate();
            } else {
                // Jika item belum ada, lakukan insert
                String insertSQL = "INSERT INTO " + namaTable
                        + " (kode_transaksi, kode_obat, nama_obat, quantity, harga_item, total)"
                        + " VALUES (?, ?, ?, ?, ?, ?)";
                p = conn.prepareStatement(insertSQL);
                p.setString(1, reqNewItem.getKodeTransaksi());
                p.setString(2, reqNewItem.getKodeObat());
                p.setString(3, reqNewItem.getNamaBarang());
                p.setInt(4, reqNewItem.getQuantity());
                p.setDouble(5, reqNewItem.getHargaItem());
                p.setDouble(6, reqNewItem.getTotal());
                p.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new Exception("Terjadi kesalahan saat menambahkan atau memperbarui data!");
        } finally {
            KoneksiDatabase.closeResources(p, rs);
        }

    }

    public static void updateItem(ItemPenjualan updatedData) throws Exception {
        PreparedStatement pstmt = null;
        try {
            System.out.println("REQUEST" + updatedData.toString());
            String sql = "UPDATE " + namaTable + " SET kode_obat = ?, nama_obat = ?, quantity = ?, harga_item = ?, total = ? WHERE id_entry_transaksi = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, updatedData.getKodeObat());
            pstmt.setString(2, updatedData.getNamaBarang());
            pstmt.setInt(3, updatedData.getQuantity());
            pstmt.setDouble(4, updatedData.getHargaItem());
            pstmt.setDouble(5, updatedData.getTotal());
            pstmt.setInt(6, updatedData.getIdEntryTransaksi());
            System.out.println("QUERY YANG DIPANGGIL " + sql);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new Exception("Terjadi kesalahan saat mengupdate data !");
        } finally {
            KoneksiDatabase.closeResources(pstmt);
        }
    }

    public static void deleteData(Integer idTransaksi) throws Exception {
        PreparedStatement pst = null;
        try {
            String sql = "DELETE from " + namaTable + " where id_entry_transaksi=" + idTransaksi;
            pst = conn.prepareStatement(sql);
            pst.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new Exception("Terjadi kesalahan saat menghapus data !");
        } finally {
            KoneksiDatabase.closeResources(pst);
        }
    }

    public static void resetEntryData(String kodeTransaksi) throws Exception {
        PreparedStatement pst = null;
        try {
            String deleteSql = "DELETE FROM " + namaTable + " WHERE kode_transaksi ='" + kodeTransaksi + "'";
            pst = conn.prepareStatement(deleteSql);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new Exception("Terjadi kesalahan saat menghapus data dan mereset auto increment !");
        } finally {
            KoneksiDatabase.closeResources(pst);
        }
    }

    public static List<ItemPenjualan> ambilDataItemPenjualan(String kodeTransaksi) throws Exception {
        List<ItemPenjualan> result = new ArrayList<>();
        Statement s = null;
        ResultSet r = null;
        try {
            s = conn.createStatement();
            String sql = "SELECT * FROM " + namaTable + " WHERE kode_transaksi='" + kodeTransaksi + "'";
            System.out.println("QUERY YANG DIPANGGIL " + sql);
            r = s.executeQuery(sql);
            while (r.next()) {
                ItemPenjualan itemPenjualan = new ItemPenjualan();
                itemPenjualan.setIdEntryTransaksi(r.getInt("id_entry_transaksi"));
                itemPenjualan.setKodeTransaksi(r.getString("kode_transaksi"));
                itemPenjualan.setKodeObat(r.getString("kode_obat"));
                itemPenjualan.setNamaBarang(r.getString("nama_obat"));
                itemPenjualan.setQuantity(r.getInt("quantity"));
                itemPenjualan.setHargaItem(r.getDouble("harga_item"));
                itemPenjualan.setTotal(r.getDouble("total"));
                result.add(itemPenjualan);
            }
            return result;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new Exception("Terjadi kesalahan saat mengambil data !");
        } finally {
            KoneksiDatabase.closeResources(s, r);
        }
    }

}
