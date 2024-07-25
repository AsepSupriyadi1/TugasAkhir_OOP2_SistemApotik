/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package popup_frame;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.EnumKategoriObat;
import model.EnumRegister;
import model.ItemPenjualan;
import model.Obat;
import services.InventoryObatService;
import services.PenjualanItemServices;
import MyException.CustomException;
import frame.FormPenjualan;

/**
 *
 * @author TUF
 */
public class FormTambahEntryObat extends javax.swing.JFrame {

    private DefaultTableModel model;
    private Obat obatDipilih = null;

    private String currentKodeTransaksi = null;
    private ItemPenjualan currentEditedItem = null;

    /**
     * Creates new form FormTambahEntryObat
     *
     * @param currentKodeTransaksi
     * @param currentEditedItem
     */
    public FormTambahEntryObat(String currentKodeTransaksi, ItemPenjualan currentEditedItem) {

        this.currentEditedItem = currentEditedItem;
        this.currentKodeTransaksi = currentKodeTransaksi;

        initComponents();
        this.setLocationRelativeTo(null);

        model = new DefaultTableModel();
        tb_inventory_obat.setModel(model);
        model.addColumn("kode_obat");
        model.addColumn("nama_obat");
        model.addColumn("harga_beli");
        model.addColumn("harga_jual");
        model.addColumn("kategori");
        model.addColumn("stok_obat");

        loadData();
    }

    private void loadData() {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        System.out.println("COOOOOOOOOOOOOOoo" + currentKodeTransaksi);

        if (currentEditedItem != null) {
            entry_judul_form.setText("Form Edit Entry Obat");

            btn_tambah.setVisible(false);
            btn_ubah.setVisible(true);

            tabel_container.setVisible(false);

            label_obatDipilih.setText(currentEditedItem.getNamaBarang());

            txt_quantity.setEnabled(true);
            txt_quantity.setValue(currentEditedItem.getQuantity());
            txt_total.setEnabled(false);
            txt_total.setText(currentEditedItem.getTotal().toString());
        } else {
            entry_judul_form.setText("Form Tambah Entry Obat");

            tabel_container.setVisible(true);
            btn_tambah.setVisible(true);
            btn_ubah.setVisible(false);
        }

        try {
            List<Obat> result = InventoryObatService.ambilDataObat();

            for (Obat obat : result) {
                // your logic here
                Object[] o = new Object[6];
                o[0] = obat.getKodeObat();
                o[1] = obat.getNamaObat();
                o[2] = obat.getHargaBeli();
                o[3] = obat.getHargaJual();
                o[4] = obat.getKategori().name();
                o[5] = obat.getStokObat();

                model.addRow(o);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }

    private void hitungTotal() {
        boolean editMode = currentEditedItem != null;
        if (obatDipilih != null) {
            Integer qty = (Integer) txt_quantity.getValue();
            Double total = obatDipilih.getHargaJual() * qty;
            txt_total.setText(total.toString());
        } else if (editMode && obatDipilih == null) {
            Integer qty = (Integer) txt_quantity.getValue();
            Double total = currentEditedItem.getHargaItem() * qty;
            txt_total.setText(total.toString());
        }
    }

    private void hardReset() {
        obatDipilih = null;
        txt_quantity.setEnabled(false);
        txt_quantity.setValue(0);

        txt_total.setEnabled(false);
        txt_total.setText("");
    }

    private boolean checkStock() throws CustomException {
        Integer qty = (Integer) txt_quantity.getValue();

        boolean editMode = currentEditedItem != null;

        if (!editMode && (qty > obatDipilih.getStokObat())) {
            return true;
        } else if (editMode && obatDipilih == null) {
            try {
                Integer stokSaatIni = InventoryObatService.ambilStokObat(currentEditedItem.getKodeObat(), qty);

                System.out.println(" STOK ------------------------ " + stokSaatIni);

                if (qty > stokSaatIni) {
                    return true;
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }

        return false;
    }

    private void tambahAtauEditData() throws Exception {
        Integer qty = (Integer) txt_quantity.getValue();
        Double total = Double.parseDouble(txt_total.getText());

        System.out.println(total);

        boolean editMode = currentEditedItem != null;

        ItemPenjualan reqItemPenjualan = new ItemPenjualan();
        System.out.println("INII KODE TRANSAKSII " + currentKodeTransaksi);
        reqItemPenjualan.setKodeTransaksi(currentKodeTransaksi);

        if (editMode && obatDipilih == null) {
            reqItemPenjualan.setNamaBarang(currentEditedItem.getNamaBarang());
            reqItemPenjualan.setHargaItem(currentEditedItem.getHargaItem());
            reqItemPenjualan.setKodeObat(currentEditedItem.getKodeObat());
        } else {
            reqItemPenjualan.setNamaBarang(obatDipilih.getNamaObat());
            reqItemPenjualan.setHargaItem(obatDipilih.getHargaJual());
            reqItemPenjualan.setKodeObat(obatDipilih.getKodeObat());
        }

        if (editMode) {
            reqItemPenjualan.setIdEntryTransaksi(currentEditedItem.getIdEntryTransaksi());
            reqItemPenjualan.setKodeTransaksi(currentEditedItem.getKodeTransaksi());
        }

        reqItemPenjualan.setQuantity(qty);
        reqItemPenjualan.setTotal(total);

        System.out.println("DATANYAAA YANG DITAMBAHKAN " + reqItemPenjualan.toString());

        if (checkStock()) {
            throw new CustomException("Stok tidak cukup!");
        }

        try {
            if (editMode) {
                PenjualanItemServices.updateItem(reqItemPenjualan);
                System.out.println("SEDANGGG MENGEDIIITT BOYYYY");
            } else if (!editMode) {
                PenjualanItemServices.tambahItem(reqItemPenjualan);
                System.out.println("SEDANGGG MENAMMBAHHH BOYYYY");
            }
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }

        hardReset();

        FormPenjualan f = new FormPenjualan();
        f.setVisible(true);
        this.setVisible(false);
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
        jPanel3 = new javax.swing.JPanel();
        entry_judul_form = new javax.swing.JLabel();
        btn_back = new javax.swing.JButton();
        dfsa = new javax.swing.JLabel();
        txt_keyword = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txt_total = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        label_obatDipilih = new javax.swing.JLabel();
        txt_quantity = new javax.swing.JSpinner();
        dfsa2 = new javax.swing.JLabel();
        btn_tambah = new javax.swing.JButton();
        btn_ubah = new javax.swing.JButton();
        tabel_container = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_inventory_obat = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));
        jPanel1.setPreferredSize(new java.awt.Dimension(500, 903));

        jPanel3.setBackground(new java.awt.Color(0, 102, 51));

        entry_judul_form.setBackground(new java.awt.Color(204, 255, 204));
        entry_judul_form.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        entry_judul_form.setForeground(new java.awt.Color(204, 255, 204));
        entry_judul_form.setText("Form Entry Obat");

        btn_back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Back.png"))); // NOI18N
        btn_back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_backActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(entry_judul_form)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_back)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(entry_judul_form)
                .addContainerGap(18, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_back)
                .addContainerGap())
        );

        dfsa.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        dfsa.setForeground(new java.awt.Color(0, 153, 51));
        dfsa.setText("Cari :");

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

        jLabel5.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 153, 51));
        jLabel5.setText("Total:");

        txt_total.setEditable(false);
        txt_total.setBackground(new java.awt.Color(204, 204, 204));
        txt_total.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txt_total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_totalActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 153, 51));
        jLabel6.setText("Quantity :");

        label_obatDipilih.setFont(new java.awt.Font("Trebuchet MS", 1, 36)); // NOI18N
        label_obatDipilih.setForeground(new java.awt.Color(0, 153, 51));
        label_obatDipilih.setText("-");

        txt_quantity.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        txt_quantity.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                txt_quantityStateChanged(evt);
            }
        });

        dfsa2.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        dfsa2.setForeground(new java.awt.Color(0, 153, 51));
        dfsa2.setText("Obat Dipilih");

        btn_tambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Save.png"))); // NOI18N
        btn_tambah.setText("Tambah");
        btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahActionPerformed(evt);
            }
        });

        btn_ubah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Edit.png"))); // NOI18N
        btn_ubah.setText("Ubah");
        btn_ubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ubahActionPerformed(evt);
            }
        });

        tb_inventory_obat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tb_inventory_obat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_inventory_obatMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tb_inventory_obat);

        javax.swing.GroupLayout tabel_containerLayout = new javax.swing.GroupLayout(tabel_container);
        tabel_container.setLayout(tabel_containerLayout);
        tabel_containerLayout.setHorizontalGroup(
            tabel_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        tabel_containerLayout.setVerticalGroup(
            tabel_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(dfsa)
                        .addGap(18, 18, 18)
                        .addComponent(txt_keyword))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_quantity, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(136, 136, 136))
                    .addComponent(label_obatDipilih, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btn_ubah, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(dfsa2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(tabel_container, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dfsa)
                    .addComponent(txt_keyword, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(dfsa2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(label_obatDipilih)
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_quantity, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(tabel_container, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_tambah, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                    .addComponent(btn_ubah, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 552, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_keywordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_keywordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_keywordActionPerformed

    private void txt_totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_totalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_totalActionPerformed

    private void btn_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_backActionPerformed
        // TODO add your handling code here:
        hardReset();
        FormPenjualan f = new FormPenjualan();
        f.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btn_backActionPerformed

    private void txt_keywordKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_keywordKeyReleased
        // TODO add your handling code here:
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        try {
            List<Obat> result = InventoryObatService.searchObat(txt_keyword.getText(), "");

            for (Obat obat : result) {
                // your logic here
                Object[] o = new Object[6];
                o[0] = obat.getKodeObat();
                o[1] = obat.getNamaObat();
                o[2] = obat.getHargaBeli();
                o[3] = obat.getHargaJual();
                o[4] = obat.getKategori().name();
                o[5] = obat.getStokObat();

                model.addRow(o);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }//GEN-LAST:event_txt_keywordKeyReleased

    private void tb_inventory_obatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_inventory_obatMouseClicked
        // TODO add your handling code here:
        int i = tb_inventory_obat.getSelectedRow();
        if (i == -1) {
            return;
        }

        String detail_kodeObat = (String) model.getValueAt(i, 0);
        String detail_namaObat = (String) model.getValueAt(i, 1);
        Double detail_hargaBeli = (Double) model.getValueAt(i, 2);
        Double detail_hargaJual = (Double) model.getValueAt(i, 3);
        String detail_kategori = (String) model.getValueAt(i, 4);
        Integer detail_stokObat = (Integer) model.getValueAt(i, 5);

        obatDipilih = new Obat(detail_kodeObat, detail_namaObat, detail_hargaBeli, detail_hargaJual, EnumKategoriObat.valueOf(detail_kategori), detail_stokObat);

        label_obatDipilih.setText(detail_namaObat);
        txt_quantity.setValue(1);
        hitungTotal();

        txt_quantity.setEnabled(true);
        btn_tambah.setEnabled(true);
    }//GEN-LAST:event_tb_inventory_obatMouseClicked

    private void txt_quantityStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_txt_quantityStateChanged
        // TODO add your handling code here:
        Integer currentValue = (Integer) txt_quantity.getValue();

        if (currentValue <= 0) {
            txt_quantity.setValue(1);
        }

        hitungTotal();
    }//GEN-LAST:event_txt_quantityStateChanged

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        // TODO add your handling code here:
        try {
            tambahAtauEditData();
            JOptionPane.showMessageDialog(null, "Data berhasil tersimpan", "Apotik Pharmacy - Asep Supriyadi", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }//GEN-LAST:event_btn_tambahActionPerformed

    private void btn_ubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ubahActionPerformed
        // TODO add your handling code here:
        try {
            tambahAtauEditData();
            JOptionPane.showMessageDialog(null, "Data berhasil tersimpan", "Apotik Pharmacy - Asep Supriyadi", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }//GEN-LAST:event_btn_ubahActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(FormTambahEntryObat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(FormTambahEntryObat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(FormTambahEntryObat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(FormTambahEntryObat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new FormTambahEntryObat().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_back;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JButton btn_ubah;
    private javax.swing.JLabel dfsa;
    private javax.swing.JLabel dfsa2;
    private javax.swing.JLabel entry_judul_form;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel label_obatDipilih;
    private javax.swing.JPanel tabel_container;
    private javax.swing.JTable tb_inventory_obat;
    private javax.swing.JTextField txt_keyword;
    private javax.swing.JSpinner txt_quantity;
    private javax.swing.JTextField txt_total;
    // End of variables declaration//GEN-END:variables
}
