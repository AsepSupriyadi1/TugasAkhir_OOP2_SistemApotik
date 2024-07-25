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
import utils.EncryptionService;
import model.Pegawai;

/**
 *
 * @author TUF
 */
public class PegawaiServices {

    private final static Connection conn = KoneksiDatabase.getKoneksi();
    private final static String namaTable = "tb_pegawai";

    public static void tambahPegawai(Pegawai reqPegawai) throws Exception {
        PreparedStatement p = null;
        try {
            String sql = "INSERT INTO " + namaTable + "(kode_pegawai, nama_pegawai, username, password) VALUES (?, ?, ?, ?)";
            System.out.println("QUERY YANG DIPANGGIL " + sql);
            p = conn.prepareStatement(sql);
            p.setString(1, reqPegawai.getKodePegawai());
            p.setString(2, reqPegawai.getNamaPegawai());
            p.setString(3, reqPegawai.getUsername());
            p.setString(4, EncryptionService.encrypt(reqPegawai.getPassword()));
            p.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new Exception("Terjadi kesalahan saat menambahkan data !");
        } finally {
            KoneksiDatabase.closeResources(p);
        }
    }

    public static List<Pegawai> ambilDataPegawai() throws Exception {
        List<Pegawai> result = new ArrayList<>();
        Statement s = null;
        ResultSet r = null;
        try {
            s = conn.createStatement();
            String sql = "SELECT * FROM " + namaTable;
            System.out.println("QUERY YANG DIPANGGIL " + sql);
            r = s.executeQuery(sql);
            while (r.next()) {
                Pegawai pegawai = new Pegawai();
                pegawai.setKodePegawai(r.getString("kode_pegawai"));
                pegawai.setNamaPegawai(r.getString("nama_pegawai"));
                pegawai.setUsername(r.getString("username"));
                pegawai.setPassword(r.getString("password"));
                pegawai.setTanggalDiMasukan(r.getDate("tgl_masuk"));
                result.add(pegawai);
            }
            return result;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new Exception("Terjadi kesalahan saat mengambil data !");
        } finally {
            KoneksiDatabase.closeResources(s, r);
        }
    }

    public static List<Pegawai> searchPegawai(String keyword) throws Exception {
        List<Pegawai> result = new ArrayList<>();
        Statement s = null;
        ResultSet r = null;

        try {
            s = conn.createStatement();
            String sql = "SELECT * FROM "+ namaTable +" WHERE kode_pegawai LIKE '%" + keyword + "%' "
                    + "OR nama_pegawai LIKE '%" + keyword + "%' "
                    + "OR username LIKE '%" + keyword + "%' ";
            System.out.println("QUERY YANG DIPANGGIL " + sql);

            r = s.executeQuery(sql);
            while (r.next()) {
                Pegawai pegawai = new Pegawai();
                pegawai.setKodePegawai(r.getString("kode_pegawai"));
                pegawai.setNamaPegawai(r.getString("nama_pegawai"));
                pegawai.setUsername(r.getString("username"));
                pegawai.setPassword(r.getString("password"));
                pegawai.setTanggalDiMasukan(r.getDate("tgl_masuk"));
                result.add(pegawai);
            }
            return result;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new Exception("Terjadi kesalahan saat mencari data !");
        } finally {
            KoneksiDatabase.closeResources(s, r);
        }
    }

    public static String ambilKodePegawai() throws Exception {
        Statement s = null;
        ResultSet r = null;
        try {
            s = conn.createStatement();
            String sql = "SELECT * FROM " + namaTable + " ORDER by kode_pegawai desc";
            System.out.println("QUERY YANG DIPANGGIL " + sql);
            r = s.executeQuery(sql);
            if (r.next()) {
                String nofak = r.getString("kode_pegawai").substring(1);
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
                return "P" + Nol + AN;
            } else {
                return "P0001";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new Exception("Terjadi kesalahan saat mengambil kode terbaru !");
        } finally {
            KoneksiDatabase.closeResources(s, r);
        }
    }

    public static void updateDataPegawai(Pegawai updatedData) throws Exception {
        PreparedStatement pstmt = null;
        try {
            String sql = "UPDATE " + namaTable + " SET nama_pegawai = ?, username = ?, password = ? WHERE kode_pegawai = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, updatedData.getNamaPegawai());
            pstmt.setString(2, updatedData.getUsername());
            pstmt.setString(3, EncryptionService.encrypt(updatedData.getPassword()));
            pstmt.setString(4, updatedData.getKodePegawai());
            System.out.println("QUERY YANG DIPANGGIL " + sql);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new Exception("Terjadi kesalahan saat mengupdate data !");
        } finally {
            KoneksiDatabase.closeResources(pstmt);
        }
    }

    public static void deleteData(String reqKodePegawai) throws Exception {
        PreparedStatement pst = null;
        try {
            String sql = "DELETE from " + namaTable + " where kode_pegawai='" + reqKodePegawai + "'";
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
