package services;

import database.KoneksiDatabase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.EnumKategoriObat;
import utils.EncryptionService;
import model.Obat;

/**
 *
 * @author TUF
 */
public class InventoryObatService {

    private final static Connection conn = KoneksiDatabase.getKoneksi();
    private final static String namaTable = "tb_obat";

    public static void tambahObat(Obat reqObat) throws Exception {
        PreparedStatement p = null;
        try {
            String sql = "INSERT INTO " + namaTable + "(kode_obat, nama_obat, harga_beli, harga_jual, kategori, stok_obat) VALUES (?, ?, ?, ?, ?, ?)";
            System.out.println("QUERY YANG DIPANGGIL " + sql);
            p = conn.prepareStatement(sql);
            p.setString(1, reqObat.getKodeObat());
            p.setString(2, reqObat.getNamaObat());
            p.setDouble(3, reqObat.getHargaBeli());
            p.setDouble(4, reqObat.getHargaJual());
            p.setString(5, reqObat.getKategori().name());
            p.setInt(6, reqObat.getStokObat());
            p.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new Exception("Terjadi kesalahan saat menambahkan data !");
        } finally {
            KoneksiDatabase.closeResources(p);
        }
    }

    public static List<Obat> ambilDataObat() throws Exception {
        List<Obat> result = new ArrayList<>();
        Statement s = null;
        ResultSet r = null;
        try {
            s = conn.createStatement();
            String sql = "SELECT * FROM " + namaTable;
            System.out.println("QUERY YANG DIPANGGIL " + sql);
            r = s.executeQuery(sql);
            while (r.next()) {
                Obat obat = new Obat();
                obat.setKodeObat(r.getString("kode_obat"));
                obat.setNamaObat(r.getString("nama_obat"));
                obat.setHargaBeli(r.getDouble("harga_beli"));
                obat.setHargaJual(r.getDouble("harga_jual"));
                obat.setKategori(EnumKategoriObat.valueOf(r.getString("kategori")));
                obat.setStokObat(r.getInt("stok_obat"));
                result.add(obat);
            }
            return result;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new Exception("Terjadi kesalahan saat mengambil data !");
        } finally {
            KoneksiDatabase.closeResources(s, r);
        }
    }

    public static int ambilStokObat(String reqKodeObat, Integer reqQuantity) throws Exception {
        Statement s = null;
        ResultSet r = null;
        try {
            s = conn.createStatement();
            String sql = "SELECT * FROM " + namaTable + " WHERE kode_obat='" + reqKodeObat + "'";
            System.out.println("QUERY YANG DIPANGGIL " + sql);
            r = s.executeQuery(sql);

            Integer stokSaatIni = null;

            while (r.next()) {
                stokSaatIni = r.getInt("stok_obat");
                
            }

            return stokSaatIni;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new Exception("Terjadi kesalahan saat mengambil data !");
        } finally {
            KoneksiDatabase.closeResources(s, r);
        }
    }

    public static List<Obat> searchObat(String keyword, String selectedCategories) throws Exception {
        List<Obat> result = new ArrayList<>();
        Statement s = null;
        ResultSet r = null;

        try {
            s = conn.createStatement();
            String sql = "SELECT * FROM " + namaTable
                    + " WHERE nama_obat LIKE '%" + keyword + "%'";

            if (selectedCategories.length() > 0) {
                sql += " AND kategori IN (" + selectedCategories + ")";
            }

            System.out.println("QUERY YANG DIPANGGIL " + sql);

            r = s.executeQuery(sql);
            while (r.next()) {
                Obat obat = new Obat();
                obat.setKodeObat(r.getString("kode_obat"));
                obat.setNamaObat(r.getString("nama_obat"));
                obat.setHargaBeli(r.getDouble("harga_beli"));
                obat.setHargaJual(r.getDouble("harga_jual"));
                obat.setKategori(EnumKategoriObat.valueOf(r.getString("kategori")));
                obat.setStokObat(r.getInt("stok_obat"));
                result.add(obat);
            }
            return result;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new Exception("Terjadi kesalahan saat mencari data !");
        } finally {
            KoneksiDatabase.closeResources(s, r);
        }
    }

    public static String ambilKodeObat() throws Exception {
        Statement s = null;
        ResultSet r = null;
        try {
            s = conn.createStatement();
            String sql = "SELECT * FROM " + namaTable + " ORDER by kode_obat desc";
            System.out.println("QUERY YANG DIPANGGIL " + sql);
            r = s.executeQuery(sql);
            if (r.next()) {
                String nofak = r.getString("kode_obat").substring(1);
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
                return "I" + Nol + AN;
            } else {
                return "I0001";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new Exception("Terjadi kesalahan saat mengambil kode terbaru !");
        } finally {
            KoneksiDatabase.closeResources(s, r);
        }
    }

    public static void updateDataObat(Obat updatedData) throws Exception {
        PreparedStatement pstmt = null;
        try {
            System.out.println(updatedData.toString());
            String sql = "UPDATE " + namaTable + " SET nama_obat = ?, harga_beli = ?, harga_jual = ?, kategori = ?, stok_obat = ? WHERE kode_obat = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, updatedData.getNamaObat());
            pstmt.setDouble(2, updatedData.getHargaBeli());
            pstmt.setDouble(3, updatedData.getHargaJual());
            pstmt.setString(4, updatedData.getKategori().name());
            pstmt.setInt(5, updatedData.getStokObat());
            pstmt.setString(6, updatedData.getKodeObat());
            System.out.println("QUERY YANG DIPANGGIL " + sql);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new Exception("Terjadi kesalahan saat mengupdate data !");
        } finally {
            KoneksiDatabase.closeResources(pstmt);
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

}
