/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import MyException.CustomException;
import database.KoneksiDatabase;
import frame.FormPenjualan;
import frame.Login;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.Pelanggan;
import utils.EncryptionService;

/**
 *
 * @author TUF
 */
public class LoginServices {

    private final static Connection conn = KoneksiDatabase.getKoneksi();
    private static boolean isLogin = true;

    public static void login(String reqUsername, String reqPassword) throws CustomException {

        try {
            Statement stm = conn.createStatement();
            ResultSet sql = stm.executeQuery("SELECT * FROM tb_pegawai WHERE username='" + reqUsername + "'");

            if (sql.next()) {
                try {
                    String actualPassword = EncryptionService.decrypt(sql.getString("password"));
                    System.out.println(sql.getString("password"));
                    System.out.println(EncryptionService.decrypt(sql.getString("password")));
                    System.out.println("ACTUAL " + actualPassword);

                    if (reqPassword.equals(actualPassword)) {
                        isLogin = true;
                    } else {
                        throw new CustomException("Username atau password salah!");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    throw new CustomException("Terjadi kesalahan enkripsi !");
                }
            } else {
                throw new CustomException("Username tidak diketahui!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new CustomException("Terjadi kesalahan!");
        }
    }

}
