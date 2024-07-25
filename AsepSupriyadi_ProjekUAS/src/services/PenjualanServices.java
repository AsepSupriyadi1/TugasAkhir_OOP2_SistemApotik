package services;

import database.KoneksiDatabase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.ItemPenjualan;
import model.Transaksi;

/**
 *
 * @author TUF
 */
public class PenjualanServices {

    private final static Connection conn = KoneksiDatabase.getKoneksi();
    private final static String namaTable = "tb_transaksi";

    public static void tambahDataTransaksi(Transaksi reqTransaksi) throws Exception {
        PreparedStatement p = null;
        try {
            String sql = "INSERT INTO " + namaTable
                    + " (kode_transaksi , id_pelanggan, total_pembelian, total_dibayarkan, total_kembalian)"
                    + " VALUES (?, ?, ?, ?, ?)";

            System.out.println("QUERY YANG DIPANGGIL " + sql);
            p = conn.prepareStatement(sql);
            p.setString(1, reqTransaksi.getKodeTransaksi());
            p.setInt(2, reqTransaksi.getIdPelanggan());
            p.setDouble(3, reqTransaksi.getTotalPembelian());
            p.setDouble(4, reqTransaksi.getTotalDibayarkan());
            p.setDouble(5, reqTransaksi.getTotalKembalian());
            p.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new Exception("Terjadi kesalahan saat menambahkan data !");
        } finally {
            KoneksiDatabase.closeResources(p);
        }
    }

    public static void tambahItemPenjualan(List<ItemPenjualan> reqItemPenjualan) throws Exception {
        PreparedStatement p = null;
        try {
            String sql = "INSERT INTO" + namaTable
                    + " (kode_transaksi , id_pelanggan, total_pembelian, total_dibayarkan, total_kembalian, metode_pembayaran)"
                    + " VALUES (?, ?, ?, ?, ?, ?)";

            System.out.println("QUERY YANG DIPANGGIL " + sql);
            p = conn.prepareStatement(sql);

            for (ItemPenjualan item : reqItemPenjualan) {
                p.setString(1, item.getKodeTransaksi());
                p.setString(2, item.getNamaBarang());
                p.setInt(3, item.getQuantity());
                p.setDouble(4, item.getTotal());
                p.addBatch();
            }

            int[] affectedRecords = p.executeBatch();
            System.out.println("Rows inserted: " + affectedRecords.length);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new Exception("Terjadi kesalahan saat menambahkan data !");
        } finally {
            KoneksiDatabase.closeResources(p);
        }
    }

    public static List<Transaksi> ambilDataTransaksi() throws Exception {
        List<Transaksi> result = new ArrayList<>();
        Statement s = null;
        ResultSet r = null;
        try {
            s = conn.createStatement();
            String sql = "SELECT * FROM " + namaTable;
            System.out.println("QUERY YANG DIPANGGIL " + sql);
            r = s.executeQuery(sql);
            while (r.next()) {
                Transaksi transaksi = new Transaksi();
                transaksi.setKodeTransaksi(r.getString("kode_transaksi"));
                transaksi.setIdPelanggan(r.getInt("id_pelanggan"));
                transaksi.setTotalPembelian(r.getDouble("total_pembelian"));
                transaksi.setTotalDibayarkan(r.getDouble("total_dibayarkan"));
                transaksi.setTotalKembalian(r.getDouble("total_kembalian"));
                transaksi.setTglTransaksi(r.getDate("tgl_transaksi"));
                result.add(transaksi);
            }
            return result;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new Exception("Terjadi kesalahan saat mengambil data !");
        } finally {
            KoneksiDatabase.closeResources(s, r);
        }
    }

    public static String ambilKodeTransaksi() throws Exception {
        Statement s = null;
        ResultSet r = null;
        try {
            s = conn.createStatement();
            String sql = "SELECT * FROM " + namaTable + " ORDER by kode_transaksi desc";
            System.out.println("QUERY YANG DIPANGGIL " + sql);
            r = s.executeQuery(sql);
            if (r.next()) {
                String nofak = r.getString("kode_transaksi").substring(1);
                String AN = "" + (Integer.parseInt(nofak) + 1);
                String Nol = "";
                switch (AN.length()) {
                    case 1:
                        Nol = "000";
                        break;
                    case 2:
                        Nol = "00";
                        break;
                    case 3:
                        Nol = "0";
                        break;
                    case 4:
                        Nol = "";
                        break;
                    default:
                        break;
                }
                return "T" + Nol + AN;
            } else {
                return "T0001";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new Exception("Terjadi kesalahan saat mengambil kode terbaru !");
        } finally {
            KoneksiDatabase.closeResources(s, r);
        }
    }

    public static void deleteData(String reqKodeObat) throws Exception {
        PreparedStatement pst = null;
        try {
            String sql = "DELETE from " + namaTable + " where kode_obat='" + reqKodeObat + "'";
            pst = conn.prepareStatement(sql);
            pst.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new Exception("Terjadi kesalahan saat menghapus data !");
        } finally {
            KoneksiDatabase.closeResources(pst);
        }
    }

//    public static List<Transaksi> searchTransaksi(String keyword, String selectedCategories) throws Exception {
//        List<Obat> result = new ArrayList<>();
//        Statement s = null;
//        ResultSet r = null;
//
//        try {
//            s = conn.createStatement();
//            String sql = "SELECT * FROM " + namaTable
//                    + " WHERE nama_obat LIKE '%" + keyword + "%'";
//
//            if (selectedCategories.length() > 0) {
//                sql += " AND kategori IN (" + selectedCategories + ")";
//            }
//
//            System.out.println("QUERY YANG DIPANGGIL " + sql);
//
//            r = s.executeQuery(sql);
//            while (r.next()) {
//                Obat obat = new Obat();
//                obat.setKodeObat(r.getString("kode_obat"));
//                obat.setNamaObat(r.getString("nama_obat"));
//                obat.setHargaBeli(r.getDouble("harga_beli"));
//                obat.setHargaJual(r.getDouble("harga_jual"));
//                obat.setKategori(EnumKategoriObat.valueOf(r.getString("kategori")));
//                obat.setStokObat(r.getInt("stok_obat"));
//                result.add(obat);
//            }
//            return result;
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//            throw new Exception("Terjadi kesalahan saat mencari data !");
//        } finally {
//            KoneksiDatabase.closeResources(s, r);
//        }
//    }
}
