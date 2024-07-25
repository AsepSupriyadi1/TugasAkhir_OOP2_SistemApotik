/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frame;

import MyException.CustomException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Pegawai;
import model.EnumRegister;
import services.LoginServices;
import services.PegawaiServices;

/**
 *
 * @author TUF
 */
public class DataPegawai extends javax.swing.JFrame {

    private DefaultTableModel model;

    /**
     * Creates new form DataPegawai
     */
    public DataPegawai() {
        initComponents();
        this.setLocationRelativeTo(null);

        model = new DefaultTableModel();
        table_pegawai.setModel(model);
        model.addColumn("kode_pegawai");
        model.addColumn("nama_pegawai");
        model.addColumn("username");
        model.addColumn("tgl_dimasukan");
        loadData();
        kode();

    }

    private void loadData() {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        btn_update.setEnabled(false);
        btn_delete.setEnabled(false);

        try {
            List<Pegawai> result = PegawaiServices.ambilDataPegawai();

            for (Pegawai pegawai : result) {
                // your logic here
                Object[] o = new Object[4];
                o[0] = pegawai.getKodePegawai();
                o[1] = pegawai.getNamaPegawai();
                o[2] = pegawai.getUsername();
                o[3] = pegawai.getTanggalDiMasukan();
                model.addRow(o);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void kode() {
        try {
            String result = PegawaiServices.ambilKodePegawai();
            txt_kodePegawai.setText(result);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void hardReset() {
        txt_kodePegawai.setText("");
        txt_namaPegawai.setText("");
        txt_username.setText("");
        txt_password.setText("");
        txt_CPassword.setText("");

        txt_keyword.setText("");

        table_pegawai.clearSelection();

        loadData();
        kode();

        btn_tambah.setEnabled(true);
        btn_update.setEnabled(false);
        btn_delete.setEnabled(false);
    }

    private void TambahAtauUpdateData(EnumRegister tipeRegister) throws Exception {
        String reqKodePegawai = txt_kodePegawai.getText();
        String reqNamaPegawai = txt_namaPegawai.getText();
        String reqUsername = txt_username.getText();
        String reqPassword = txt_password.getText();
        String reqCPassword = txt_CPassword.getText();

        System.out.println(reqKodePegawai);
        System.out.println(reqNamaPegawai);
        System.out.println(reqUsername);
        System.out.println(reqPassword);
        System.out.println(reqCPassword);

        if (reqNamaPegawai.equals("") || reqUsername.equals("") || reqPassword.equals("") || reqCPassword.equals("")) {
            throw new CustomException("LENGKAPI DATA !");
        } else if (!reqPassword.equals(reqCPassword)) {
            throw new CustomException("Konfirmasi password tidak sesuai !");
        } else {
            try {
                Pegawai reqPegawai = new Pegawai();
                reqPegawai.setKodePegawai(reqKodePegawai);
                reqPegawai.setNamaPegawai(reqNamaPegawai);
                reqPegawai.setUsername(reqUsername);
                reqPegawai.setPassword(reqPassword);

                if (tipeRegister.equals(tipeRegister.ADD)) {
                    PegawaiServices.tambahPegawai(reqPegawai);
                } else if (tipeRegister.equals(tipeRegister.UPDATE)) {
                    PegawaiServices.updateDataPegawai(reqPegawai);
                }

            } catch (Exception e) {
                throw new Exception(e.getMessage());
            } finally {
                hardReset();
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_pegawai = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        txt_namaPegawai = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txt_username = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txt_kodePegawai = new javax.swing.JTextField();
        btn_delete = new javax.swing.JButton();
        btn_tambah = new javax.swing.JButton();
        btn_update = new javax.swing.JButton();
        txt_CPassword = new javax.swing.JPasswordField();
        txt_password = new javax.swing.JPasswordField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txt_keyword = new javax.swing.JTextField();
        dfsa = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        refreshButton = new javax.swing.JButton();
        Navbar = new javax.swing.JMenuBar();
        menuMaster = new javax.swing.JMenu();
        menuObat = new javax.swing.JMenuItem();
        menuPegawai = new javax.swing.JMenuItem();
        menuPelanggan = new javax.swing.JMenuItem();
        menuLogout = new javax.swing.JMenuItem();
        menuTransaksi = new javax.swing.JMenu();
        menuPenjualan = new javax.swing.JMenuItem();
        menuLaporan = new javax.swing.JMenu();
        menuLaporanPenjualan = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(0, 0));
        setPreferredSize(new java.awt.Dimension(1200, 750));

        jPanel1.setPreferredSize(new java.awt.Dimension(1200, 700));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/my-logo.png"))); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel3)
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel3)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        table_pegawai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "kode_pegawai", "nama_pegawai", "username", "tgl_dimasukan"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        table_pegawai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_pegawaiMouseClicked(evt);
            }
        });
        table_pegawai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                table_pegawaiKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(table_pegawai);

        jPanel2.setBackground(new java.awt.Color(204, 255, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Form Pegawai", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 2, 18))); // NOI18N

        txt_namaPegawai.setBackground(new java.awt.Color(253, 253, 236));
        txt_namaPegawai.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txt_namaPegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_namaPegawaiActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 153, 51));
        jLabel5.setText("Nama Pegawai :");

        jLabel11.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 153, 51));
        jLabel11.setText("Password :");

        txt_username.setBackground(new java.awt.Color(253, 253, 236));
        txt_username.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txt_username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_usernameActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 153, 51));
        jLabel15.setText("Kode Pegawai :");

        txt_kodePegawai.setBackground(new java.awt.Color(204, 204, 204));
        txt_kodePegawai.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txt_kodePegawai.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txt_kodePegawai.setEnabled(false);
        txt_kodePegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_kodePegawaiActionPerformed(evt);
            }
        });

        btn_delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/thrash.png"))); // NOI18N
        btn_delete.setText("Delete");
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });

        btn_tambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Save.png"))); // NOI18N
        btn_tambah.setText("Tambah");
        btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahActionPerformed(evt);
            }
        });

        btn_update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Edit.png"))); // NOI18N
        btn_update.setText("Update");
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });

        txt_CPassword.setBackground(new java.awt.Color(253, 253, 236));
        txt_CPassword.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txt_password.setBackground(new java.awt.Color(253, 253, 236));
        txt_password.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txt_password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_passwordActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 153, 51));
        jLabel12.setText("Username :");

        jLabel13.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 153, 51));
        jLabel13.setText("Confirm Password");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_username)
                            .addComponent(txt_namaPegawai)
                            .addComponent(txt_kodePegawai)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel15))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_CPassword))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addGap(102, 102, 102)
                                        .addComponent(jLabel13)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(12, 12, 12))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_tambah)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_update)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_delete)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_kodePegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_namaPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_CPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_tambah)
                    .addComponent(btn_update)
                    .addComponent(btn_delete))
                .addGap(115, 115, 115))
        );

        jPanel4.setBackground(new java.awt.Color(0, 102, 51));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setToolTipText("");
        jPanel4.setAlignmentX(0.0F);
        jPanel4.setAlignmentY(0.0F);
        jPanel4.setPreferredSize(new java.awt.Dimension(0, 40));

        jLabel2.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 255, 204));
        jLabel2.setText("Kelola Data Pegawai");

        jLabel1.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 255, 204));
        jLabel1.setText("24 April 2021 11:01");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 576, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(23, 23, 23))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(204, 255, 204));

        txt_keyword.setBackground(new java.awt.Color(253, 253, 236));
        txt_keyword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_keywordActionPerformed(evt);
            }
        });
        txt_keyword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_keywordKeyReleased(evt);
            }
        });

        dfsa.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        dfsa.setForeground(new java.awt.Color(0, 153, 51));
        dfsa.setText("Cari :");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(dfsa)
                .addGap(18, 18, 18)
                .addComponent(txt_keyword)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dfsa)
                    .addComponent(txt_keyword, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        refreshButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/reset.png"))); // NOI18N
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 975, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(refreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(17, 17, 17))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(refreshButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 426, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        menuMaster.setText("Master");

        menuObat.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        menuObat.setText("Obat");
        menuObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuObatActionPerformed(evt);
            }
        });
        menuMaster.add(menuObat);

        menuPegawai.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        menuPegawai.setText("Pegawai");
        menuPegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPegawaiActionPerformed(evt);
            }
        });
        menuMaster.add(menuPegawai);

        menuPelanggan.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, 0));
        menuPelanggan.setText("Pelanggan");
        menuPelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPelangganActionPerformed(evt);
            }
        });
        menuMaster.add(menuPelanggan);

        menuLogout.setText("Logout");
        menuLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuLogoutActionPerformed(evt);
            }
        });
        menuMaster.add(menuLogout);

        Navbar.add(menuMaster);

        menuTransaksi.setText("Transaksi");

        menuPenjualan.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        menuPenjualan.setText("Penjualan");
        menuPenjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPenjualanActionPerformed(evt);
            }
        });
        menuTransaksi.add(menuPenjualan);

        Navbar.add(menuTransaksi);

        menuLaporan.setText("Laporan");

        menuLaporanPenjualan.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        menuLaporanPenjualan.setText("Laporan Penjualan");
        menuLaporanPenjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuLaporanPenjualanActionPerformed(evt);
            }
        });
        menuLaporan.add(menuLaporanPenjualan);

        Navbar.add(menuLaporan);

        setJMenuBar(Navbar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_namaPegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_namaPegawaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_namaPegawaiActionPerformed

    private void txt_usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_usernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_usernameActionPerformed

    private void txt_kodePegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_kodePegawaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_kodePegawaiActionPerformed

    private void txt_keywordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_keywordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_keywordActionPerformed

    private void txt_passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_passwordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_passwordActionPerformed

    private void menuObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuObatActionPerformed
        // TODO add your handling code here:
        InventoryObat f = new InventoryObat();
        f.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_menuObatActionPerformed

    private void menuPegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPegawaiActionPerformed
        // TODO add your handling code here:
        DataPegawai f = new DataPegawai();
        f.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_menuPegawaiActionPerformed

    private void menuPelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPelangganActionPerformed
        // TODO add your handling code here:
        DataPelanggan f = new DataPelanggan();
        f.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_menuPelangganActionPerformed

    private void menuLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuLogoutActionPerformed
        // TODO add your handling code here:
        Login f = new Login();
        f.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_menuLogoutActionPerformed

    private void menuPenjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPenjualanActionPerformed
        // TODO add your handling code here:
        FormPenjualan f = new FormPenjualan();
        f.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_menuPenjualanActionPerformed

    private void menuLaporanPenjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuLaporanPenjualanActionPerformed
        // TODO add your handling code here:
        FormLaporanPenjualan f = new FormLaporanPenjualan();
        f.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_menuLaporanPenjualanActionPerformed

    private void txt_keywordKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_keywordKeyReleased
        // TODO add your handling code here:
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try {
            List<Pegawai> result = PegawaiServices.searchPegawai(txt_keyword.getText());

            for (Pegawai pegawai : result) {
                // your logic here
                Object[] o = new Object[4];
                o[0] = pegawai.getKodePegawai();
                o[1] = pegawai.getNamaPegawai();
                o[2] = pegawai.getUsername();
                o[3] = pegawai.getTanggalDiMasukan();
                model.addRow(o);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }//GEN-LAST:event_txt_keywordKeyReleased

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        // TODO add your handling code here:
        try {
            TambahAtauUpdateData(EnumRegister.ADD);
            JOptionPane.showMessageDialog(null, "Data berhasil tersimpan", "Apotik Pharmacy - Asep Supriyadi", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }//GEN-LAST:event_btn_tambahActionPerformed

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
        // TODO add your handling code here:
        try {
            TambahAtauUpdateData(EnumRegister.UPDATE);
            JOptionPane.showMessageDialog(null, "Data berhasil diubah", "Apotik Pharmacy - Asep Supriyadi", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }//GEN-LAST:event_btn_updateActionPerformed

    private void table_pegawaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_table_pegawaiKeyPressed

    }//GEN-LAST:event_table_pegawaiKeyPressed

    private void table_pegawaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_pegawaiMouseClicked
        // TODO add your handling code here:
        btn_tambah.setEnabled(false);
        btn_update.setEnabled(true);
        btn_delete.setEnabled(true);

        int i = table_pegawai.getSelectedRow();
        if (i == -1) {
            return;
        }

        String detail_kodePegawai = (String) model.getValueAt(i, 0);
        String detail_namaPegawai = (String) model.getValueAt(i, 1);
        String detail_usernamePegawai = (String) model.getValueAt(i, 2);

        txt_kodePegawai.setText(detail_kodePegawai);
        txt_namaPegawai.setText(detail_namaPegawai);
        txt_username.setText(detail_usernamePegawai);
        txt_password.setText("");
        txt_CPassword.setText("");
    }//GEN-LAST:event_table_pegawaiMouseClicked

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        // TODO add your handling code here:
        hardReset();
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        // TODO add your handling code here:
        try {
            PegawaiServices.deleteData(txt_kodePegawai.getText());
            hardReset();
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus", "Apotik Pharmacy - Asep Supriyadi", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }//GEN-LAST:event_btn_deleteActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DataPegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DataPegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DataPegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DataPegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DataPegawai().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar Navbar;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JButton btn_update;
    private javax.swing.JLabel dfsa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JMenu menuLaporan;
    private javax.swing.JMenuItem menuLaporanPenjualan;
    private javax.swing.JMenuItem menuLogout;
    private javax.swing.JMenu menuMaster;
    private javax.swing.JMenuItem menuObat;
    private javax.swing.JMenuItem menuPegawai;
    private javax.swing.JMenuItem menuPelanggan;
    private javax.swing.JMenuItem menuPenjualan;
    private javax.swing.JMenu menuTransaksi;
    private javax.swing.JButton refreshButton;
    private javax.swing.JTable table_pegawai;
    private javax.swing.JPasswordField txt_CPassword;
    private javax.swing.JTextField txt_keyword;
    private javax.swing.JTextField txt_kodePegawai;
    private javax.swing.JTextField txt_namaPegawai;
    private javax.swing.JPasswordField txt_password;
    private javax.swing.JTextField txt_username;
    // End of variables declaration//GEN-END:variables
}
