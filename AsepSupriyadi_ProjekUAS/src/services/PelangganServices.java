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
import model.Pelanggan;

/**
 *
 * @author TUF
 */
public class PelangganServices {

    private final static Connection conn = KoneksiDatabase.getKoneksi();
    private final static String namaTable = "tb_pelanggan";

    public static void tambahPelanggan(Pelanggan reqPelanggan) throws Exception {
        PreparedStatement p = null;
        try {
            String sql = "INSERT INTO " + namaTable + "(nama_pelanggan, email, no_telp, alamat) VALUES (?, ?, ?, ?)";
            System.out.println("QUERY YANG DIPANGGIL " + sql);
            p = conn.prepareStatement(sql);
            p.setString(1, reqPelanggan.getNama_pelanggan());
            p.setString(2, reqPelanggan.getEmail());
            p.setString(3, reqPelanggan.getNo_telp());
            p.setString(4, reqPelanggan.getAlamat());
            p.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new Exception("Terjadi kesalahan saat menambahkan data !");
        } finally {
            KoneksiDatabase.closeResources(p);
        }
    }

    public static List<Pelanggan> ambilDataPelanggan() throws Exception {
        List<Pelanggan> result = new ArrayList<>();
        Statement s = null;
        ResultSet r = null;
        try {
            s = conn.createStatement();
            String sql = "SELECT * FROM " + namaTable;
            System.out.println("QUERY YANG DIPANGGIL " + sql);
            r = s.executeQuery(sql);
            while (r.next()) {
                Pelanggan pelanggan = new Pelanggan();
                pelanggan.setId_pelanggan(r.getInt("id_pelanggan"));
                pelanggan.setNama_pelanggan(r.getString("nama_pelanggan"));
                pelanggan.setEmail(r.getString("email"));
                pelanggan.setNo_telp(r.getString("no_telp"));
                pelanggan.setAlamat(r.getString("alamat"));
                pelanggan.setTgl_dibuat(r.getDate("tgl_dibuat"));
                result.add(pelanggan);
            }
            return result;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new Exception("Terjadi kesalahan saat mengambil data !");
        } finally {
            KoneksiDatabase.closeResources(s, r);
        }
    }

    public static List<Pelanggan> searchPelanggan(String keyword) throws Exception {
        List<Pelanggan> result = new ArrayList<>();
        Statement s = null;
        ResultSet r = null;

        try {
            s = conn.createStatement();
            String sql = "SELECT * FROM " + namaTable + " WHERE nama_pelanggan LIKE '%" + keyword + "%' "
                    + "OR email LIKE '%" + keyword + "%' "
                    + "OR no_telp LIKE '%" + keyword + "%' ";

            System.out.println("QUERY YANG DIPANGGIL " + sql);

            r = s.executeQuery(sql);
            while (r.next()) {
                Pelanggan pelanggan = new Pelanggan();
                pelanggan.setId_pelanggan(r.getInt("id_pelanggan"));
                pelanggan.setNama_pelanggan(r.getString("nama_pelanggan"));
                pelanggan.setEmail(r.getString("email"));
                pelanggan.setNo_telp(r.getString("no_telp"));
                pelanggan.setAlamat(r.getString("alamat"));
                pelanggan.setTgl_dibuat(r.getDate("tgl_dibuat"));
                result.add(pelanggan);
            }
            return result;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new Exception("Terjadi kesalahan saat mencari data !");
        } finally {
            KoneksiDatabase.closeResources(s, r);
        }
    }

    public static void updateDataPelanggan(Pelanggan updatedData) throws Exception {
        PreparedStatement pstmt = null;
        try {
            String sql = "UPDATE " + namaTable + " SET nama_pelanggan = ?, email = ?, no_telp = ?, alamat = ? WHERE id_pelanggan = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, updatedData.getNama_pelanggan());
            pstmt.setString(2, updatedData.getEmail());
            pstmt.setString(3, updatedData.getNo_telp());
            pstmt.setString(4, updatedData.getAlamat());
            pstmt.setInt(5, updatedData.getId_pelanggan());
            System.out.println("QUERY YANG DIPANGGIL " + sql);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new Exception("Terjadi kesalahan saat mengupdate data !");
        } finally {
            KoneksiDatabase.closeResources(pstmt);
        }
    }

    public static void deleteData(int reqIdPelanggan) throws Exception {
        PreparedStatement pst = null;
        try {
            String sql = "DELETE from " + namaTable + " where id_pelanggan=" + reqIdPelanggan;
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
