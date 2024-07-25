/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frame;

import MyException.CustomException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.EnumKategoriObat;
import model.EnumRegister;
import model.Obat;
import services.InventoryObatService;

/**
 *
 * @author TUF
 */
public class InventoryObat extends javax.swing.JFrame {

    private DefaultTableModel model;
    private List<JCheckBox> checkBoxes;

    /**
     * Creates new form Inventory
     */
    public InventoryObat() {
        initComponents();
        this.setLocationRelativeTo(null);
        select_kategoriObat.setSelectedItem(null);

        model = new DefaultTableModel();
        tb_inventory_obat.setModel(model);
        model.addColumn("kode_obat");
        model.addColumn("nama_obat");
        model.addColumn("harga_beli");
        model.addColumn("harga_jual");
        model.addColumn("kategori");
        model.addColumn("stok_obat");

        checkBoxes = new ArrayList<>();
        checkBoxes.add(checkbox_antiDiare);
        checkBoxes.add(checkbox_antiSeptik);
        checkBoxes.add(checkbox_antibiotics);
        checkBoxes.add(checkbox_antivirus);
        checkBoxes.add(checkbox_obatBatuk);

        loadData();
        kode();
        initiateSelectOptions();
    }

    private void initiateSelectOptions() {
        for (EnumKategoriObat kategoryObat : EnumKategoriObat.values()) {
            select_kategoriObat.addItem(kategoryObat.name());
        }
    }

    private void loadData() {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        btn_update.setEnabled(false);
        btn_delete.setEnabled(false);

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

    private void kode() {
        try {
            String result = InventoryObatService.ambilKodeObat();
            txt_kodeObat.setText(result);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void hardReset() {
        // RESET SEARCH BY KEYWORD FILTER
        txt_keyword.setText("");

        // RESET FORM
        txt_kodeObat.setText("");
        txt_namaObat.setText("");
        txt_hargaBeli.setText("");
        txt_hargaJual.setText("");
        select_kategoriObat.setSelectedItem(null);
        txt_stokObat.setText("");

        tb_inventory_obat.clearSelection();

        loadData();
        kode();

        // RESET BTN STATE
        btn_tambah.setEnabled(true);
        btn_update.setEnabled(false);
        btn_delete.setEnabled(false);

        // RESET CHECKBOX
        checkbox_allCategory.setSelected(false);
        checkbox_antiDiare.setSelected(false);
        checkbox_antiSeptik.setSelected(false);
        checkbox_antibiotics.setSelected(false);
        checkbox_antivirus.setSelected(false);
        checkbox_obatBatuk.setSelected(false);
    }

    private void TambahAtauUpdateData(EnumRegister tipeRegister) throws Exception {
        String reqKodeObat = txt_kodeObat.getText();
        String reqNamaObat = txt_namaObat.getText();
        String reqHargaBeli = txt_hargaBeli.getText();
        String reqHargaJual = txt_hargaJual.getText();
        String reqKategoriObat = select_kategoriObat.getSelectedItem().toString();
        String reqStokObat = txt_stokObat.getText();

        System.out.println(reqKodeObat);
        System.out.println(reqNamaObat);
        System.out.println(reqHargaBeli);
        System.out.println(reqHargaJual);
        System.out.println(reqKategoriObat);
        System.out.println(reqStokObat);

        if (reqKodeObat.equals("") || reqNamaObat.equals("")
                || reqHargaBeli.equals("") || reqHargaJual.equals("")
                || reqKategoriObat.equals("") || reqStokObat.equals("")) {
            throw new CustomException("LENGKAPI DATA !");
        } else {
            try {
                Obat reqObat = new Obat();
                reqObat.setKodeObat(reqKodeObat);
                reqObat.setNamaObat(reqNamaObat);
                reqObat.setHargaBeli(Double.parseDouble(reqHargaBeli));
                reqObat.setHargaJual(Double.parseDouble(reqHargaJual));
                reqObat.setKategori(EnumKategoriObat.valueOf(reqKategoriObat));
                reqObat.setStokObat(Integer.parseInt(reqStokObat));

                if (tipeRegister.equals(tipeRegister.ADD)) {
                    InventoryObatService.tambahObat(reqObat);
                } else if (tipeRegister.equals(tipeRegister.UPDATE)) {
                    InventoryObatService.updateDataObat(reqObat);
                }

            } catch (Exception e) {
                throw new Exception(e.getMessage());
            } finally {
                hardReset();
            }
        }
    }

    private String getSelectedValues() {
        List<String> selectedValues = new ArrayList<>();
        for (JCheckBox checkBox : checkBoxes) {
            if (checkBox.isSelected()) {
                selectedValues.add(checkBox.getText());
            }
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < selectedValues.size(); i++) {
            result.append("'").append(selectedValues.get(i)).append("'");
            if (i < selectedValues.size() - 1) {
                result.append(", ");
            }
        }

        return result.toString();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        groupStatus = new javax.swing.ButtonGroup();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txt_keyword = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        checkbox_antibiotics = new javax.swing.JCheckBox();
        checkbox_antivirus = new javax.swing.JCheckBox();
        checkbox_obatBatuk = new javax.swing.JCheckBox();
        checkbox_antiSeptik = new javax.swing.JCheckBox();
        checkbox_antiDiare = new javax.swing.JCheckBox();
        checkbox_allCategory = new javax.swing.JCheckBox();
        btn_filter = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txt_namaObat = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txt_hargaJual = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txt_stokObat = new javax.swing.JTextField();
        txt_hargaBeli = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        select_kategoriObat = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        txt_kodeObat = new javax.swing.JTextField();
        btn_delete = new javax.swing.JButton();
        btn_tambah = new javax.swing.JButton();
        btn_update = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_inventory_obat = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
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

        jPanel3.setPreferredSize(new java.awt.Dimension(1200, 700));

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));
        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel5.setBackground(new java.awt.Color(0, 102, 51));
        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel6.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(204, 255, 204));
        jLabel6.setText("Filter");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txt_keyword.setBackground(new java.awt.Color(245, 245, 245));
        txt_keyword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_keywordActionPerformed(evt);
            }
        });

        jPanel6.setBackground(new java.awt.Color(0, 102, 51));

        jLabel7.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(204, 255, 204));
        jLabel7.setText("Cari");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(0, 102, 51));
        jPanel7.setForeground(new java.awt.Color(0, 102, 51));

        jLabel8.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(204, 255, 204));
        jLabel8.setText("Kategori");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        checkbox_antibiotics.setText("Antibiotics");
        checkbox_antibiotics.setContentAreaFilled(false);
        checkbox_antibiotics.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkbox_antibioticsActionPerformed(evt);
            }
        });

        checkbox_antivirus.setText("Antivirus");
        checkbox_antivirus.setContentAreaFilled(false);
        checkbox_antivirus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkbox_antivirusActionPerformed(evt);
            }
        });

        checkbox_obatBatuk.setText("Obat_Batuk");
        checkbox_obatBatuk.setContentAreaFilled(false);

        checkbox_antiSeptik.setText("Antiseptik");
        checkbox_antiSeptik.setContentAreaFilled(false);
        checkbox_antiSeptik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkbox_antiSeptikActionPerformed(evt);
            }
        });

        checkbox_antiDiare.setText("Antidiare");
        checkbox_antiDiare.setContentAreaFilled(false);
        checkbox_antiDiare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkbox_antiDiareActionPerformed(evt);
            }
        });

        checkbox_allCategory.setText("Semua kategori");
        checkbox_allCategory.setContentAreaFilled(false);
        checkbox_allCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkbox_allCategoryActionPerformed(evt);
            }
        });

        btn_filter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Search 24 h p8.png"))); // NOI18N
        btn_filter.setText("Filter Data");
        btn_filter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_filterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(8, 8, 8)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(checkbox_antiSeptik)
                                            .addComponent(checkbox_antiDiare)
                                            .addComponent(checkbox_antivirus)
                                            .addComponent(checkbox_obatBatuk)
                                            .addComponent(checkbox_antibiotics)))
                                    .addComponent(checkbox_allCategory))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_filter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(txt_keyword, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 15, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_keyword, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(checkbox_allCategory)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(checkbox_antibiotics)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkbox_obatBatuk)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkbox_antivirus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkbox_antiDiare)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkbox_antiSeptik)
                .addGap(18, 18, 18)
                .addComponent(btn_filter, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(204, 255, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Form Obat", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 2, 18))); // NOI18N

        txt_namaObat.setBackground(new java.awt.Color(253, 253, 236));
        txt_namaObat.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txt_namaObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_namaObatActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 153, 51));
        jLabel5.setText("Nama Obat :");

        jLabel10.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 153, 51));
        jLabel10.setText("Harga Beli :");

        txt_hargaJual.setBackground(new java.awt.Color(253, 253, 236));
        txt_hargaJual.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txt_hargaJual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_hargaJualActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 153, 51));
        jLabel11.setText("Stok");

        txt_stokObat.setBackground(new java.awt.Color(253, 253, 236));
        txt_stokObat.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txt_stokObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_stokObatActionPerformed(evt);
            }
        });

        txt_hargaBeli.setBackground(new java.awt.Color(253, 253, 236));
        txt_hargaBeli.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txt_hargaBeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_hargaBeliActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 153, 51));
        jLabel12.setText("Harga jual :");

        jLabel13.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 153, 51));
        jLabel13.setText("Kategori");

        select_kategoriObat.setBackground(new java.awt.Color(253, 253, 236));
        select_kategoriObat.setForeground(new java.awt.Color(253, 253, 236));
        select_kategoriObat.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        select_kategoriObat.setPreferredSize(new java.awt.Dimension(64, 40));

        jLabel15.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 153, 51));
        jLabel15.setText("Kode Obat :");

        txt_kodeObat.setBackground(new java.awt.Color(204, 204, 204));
        txt_kodeObat.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txt_kodeObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_kodeObatActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(select_kategoriObat, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_stokObat)
                            .addComponent(txt_namaObat)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txt_hargaBeli, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(18, 18, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(txt_hargaJual, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(txt_kodeObat)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(btn_tambah)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btn_update)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btn_delete)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_kodeObat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_namaObat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_hargaJual, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_hargaBeli, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(select_kategoriObat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_stokObat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_tambah)
                    .addComponent(btn_update)
                    .addComponent(btn_delete))
                .addGap(57, 57, 57))
        );

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

        jPanel4.setBackground(new java.awt.Color(0, 102, 51));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setToolTipText("");
        jPanel4.setAlignmentX(0.0F);
        jPanel4.setAlignmentY(0.0F);
        jPanel4.setPreferredSize(new java.awt.Dimension(0, 40));

        jLabel2.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 255, 204));
        jLabel2.setText("Inventory Obat");

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 645, Short.MAX_VALUE)
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

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/my-logo.png"))); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        refreshButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/reset.png"))); // NOI18N
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(refreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 986, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 521, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(refreshButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1)))
                .addContainerGap(26, Short.MAX_VALUE))
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
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_keywordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_keywordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_keywordActionPerformed

    private void checkbox_antibioticsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkbox_antibioticsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkbox_antibioticsActionPerformed

    private void checkbox_antivirusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkbox_antivirusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkbox_antivirusActionPerformed

    private void checkbox_antiSeptikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkbox_antiSeptikActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkbox_antiSeptikActionPerformed

    private void checkbox_antiDiareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkbox_antiDiareActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkbox_antiDiareActionPerformed

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

    private void checkbox_allCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkbox_allCategoryActionPerformed
        // TODO add your handling code here:
        boolean isSelected = checkbox_allCategory.isSelected();
        checkbox_antiDiare.setSelected(isSelected);
        checkbox_antiSeptik.setSelected(isSelected);
        checkbox_antibiotics.setSelected(isSelected);
        checkbox_antivirus.setSelected(isSelected);
        checkbox_obatBatuk.setSelected(isSelected);
    }//GEN-LAST:event_checkbox_allCategoryActionPerformed

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        // TODO add your handling code here:
        hardReset();
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void btn_filterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_filterActionPerformed
        // TODO add your handling code here:
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        System.out.println(getSelectedValues().length());

        try {
            List<Obat> result = InventoryObatService.searchObat(txt_keyword.getText(), getSelectedValues());

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
    }//GEN-LAST:event_btn_filterActionPerformed

    private void tb_inventory_obatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_inventory_obatMouseClicked
        // TODO add your handling code here:
        btn_tambah.setEnabled(false);
        btn_update.setEnabled(true);
        btn_delete.setEnabled(true);

        int i = tb_inventory_obat.getSelectedRow();
        if (i == -1) {
            return;
        }

        String detail_kodeBarang = (String) model.getValueAt(i, 0);
        String detail_namaObat = (String) model.getValueAt(i, 1);
        Double detail_hargaBeli = (Double) model.getValueAt(i, 2);
        Double detail_hargaJual = (Double) model.getValueAt(i, 3);
        String detail_kategori = (String) model.getValueAt(i, 4);
        Integer detail_stokObat = (Integer) model.getValueAt(i, 5);

        txt_kodeObat.setText(detail_kodeBarang);
        txt_namaObat.setText(detail_namaObat);
        txt_hargaBeli.setText(detail_hargaBeli.toString());
        txt_hargaJual.setText(detail_hargaJual.toString());
        select_kategoriObat.setSelectedItem(detail_kategori);
        txt_stokObat.setText(detail_stokObat.toString());
    }//GEN-LAST:event_tb_inventory_obatMouseClicked

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
        // TODO add your handling code here:
        try {
            TambahAtauUpdateData(EnumRegister.UPDATE);
            JOptionPane.showMessageDialog(null, "Data berhasil diubah", "Apotik Pharmacy - Asep Supriyadi", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }//GEN-LAST:event_btn_updateActionPerformed

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        // TODO add your handling code here:
        try {
            TambahAtauUpdateData(EnumRegister.ADD);
            JOptionPane.showMessageDialog(null, "Data berhasil tersimpan", "Apotik Pharmacy - Asep Supriyadi", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }//GEN-LAST:event_btn_tambahActionPerformed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        // TODO add your handling code here:
        try {
            InventoryObatService.deleteData(txt_kodeObat.getText());
            hardReset();
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus", "Apotik Pharmacy - Asep Supriyadi", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void txt_kodeObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_kodeObatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_kodeObatActionPerformed

    private void txt_hargaBeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_hargaBeliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_hargaBeliActionPerformed

    private void txt_stokObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_stokObatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_stokObatActionPerformed

    private void txt_hargaJualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_hargaJualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_hargaJualActionPerformed

    private void txt_namaObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_namaObatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_namaObatActionPerformed

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
            java.util.logging.Logger.getLogger(InventoryObat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InventoryObat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InventoryObat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InventoryObat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InventoryObat().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar Navbar;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_filter;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JButton btn_update;
    private javax.swing.JCheckBox checkbox_allCategory;
    private javax.swing.JCheckBox checkbox_antiDiare;
    private javax.swing.JCheckBox checkbox_antiSeptik;
    private javax.swing.JCheckBox checkbox_antibiotics;
    private javax.swing.JCheckBox checkbox_antivirus;
    private javax.swing.JCheckBox checkbox_obatBatuk;
    private javax.swing.ButtonGroup groupStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
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
    private javax.swing.JComboBox<String> select_kategoriObat;
    private javax.swing.JTable tb_inventory_obat;
    private javax.swing.JTextField txt_hargaBeli;
    private javax.swing.JTextField txt_hargaJual;
    private javax.swing.JTextField txt_keyword;
    private javax.swing.JTextField txt_kodeObat;
    private javax.swing.JTextField txt_namaObat;
    private javax.swing.JTextField txt_stokObat;
    // End of variables declaration//GEN-END:variables
}
