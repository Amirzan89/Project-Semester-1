package com.window.panels;

import com.data.db.Database;
import com.error.InValidUserDataException;
import com.manage.Barang;
import com.manage.Message;
import com.manage.Text;
import com.manage.Waktu;
import com.media.Audio;
import com.media.Gambar;
import com.sun.glass.events.KeyEvent;
import com.users.Supplier;
import com.users.Users;
import com.window.dialogs.KonfirmasiPembayaran;
import java.awt.Color;
import java.awt.Cursor;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gemastik Lightning
 */
public class TransaksiBeli extends javax.swing.JPanel {
    
    private final Database db = new Database();
    
    private final Users user = new Users();
    
    private final Supplier supplier = new Supplier();
    
    private final Barang barang = new Barang();
    
    private final Waktu waktu = new Waktu();
    
    private final com.manage.ManageTransaksiBeli trb = new com.manage.ManageTransaksiBeli();
    
    private final Text text = new Text();
    
    private String keywordSupplier = "", keywordBarang = "", idSelectedSupplier, idSelectedBarang;
    
    private String idTr, namaTr, namaSupplier, namaBarang, idKaryawan, idSupplier, idBarang, metodeBayar, tglNow;
    
    private int jumlah = 0, hargaBeli, totalHarga = 0, stok = 0,keseluruhan = 0;
    
    public TransaksiBeli() {
        initComponents();
        this.idTr = this.trb.createIDTransaksi();
        this.idKaryawan = this.user.getCurrentLogin();
        this.txtTotal.setText(text.toMoneyCase("0"));
        this.inpJumlah.setText("0");
        this.inpTotalHarga.setText(text.toMoneyCase("0"));
        
//        this.inpJumlah.setEditable(false);
        this.inpID.setText("<html><p>:&nbsp;"+this.trb.createIDTransaksi()+"</p></html>");
        this.inpNamaKaryawan.setText("<html><p>:&nbsp;"+this.user.getCurrentLoginName()+"</p></html>");
        
        this.btnBayar.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        this.btnBatal.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        
        this.tabelDataBarang.setRowHeight(29);
        this.tabelDataBarang.getTableHeader().setBackground(new java.awt.Color(255,255,255));
        this.tabelDataBarang.getTableHeader().setForeground(new java.awt.Color(0, 0, 0));
        
        this.tabelDataSupplier.setRowHeight(29);
        this.tabelDataSupplier.getTableHeader().setBackground(new java.awt.Color(255,255,255));
        this.tabelDataSupplier.getTableHeader().setForeground(new java.awt.Color(0, 0, 0));
        
        this.tabelData.setRowHeight(29);
        this.tabelData.getTableHeader().setBackground(new java.awt.Color(255,255,255));
        this.tabelData.getTableHeader().setForeground(new java.awt.Color(0, 0, 0));
        
        this.updateTabelSupplier();
        this.updateTabelBarang();
        
        // mengupdate waktu
        new Thread(new Runnable(){
            
            @Override
            public void run(){
                try{
                    while(isVisible()){
                        tglNow = waktu.getUpdateTime();
                        inpTanggal.setText("<html><p>:&nbsp;"+tglNow+"</p></html>");
                        Thread.sleep(100);
                    }
                }catch(InterruptedException ex){
                    Message.showException(this, "Terjadi Kesalahan Saat Mengupdate Tanggal!\n" + ex.getMessage(), ex, true);
                }
            }
        }).start();
    }
    private void setrowtotabeldetail(Object[] dataRow,int baris) {
        DefaultTableModel model = (DefaultTableModel) tabelData.getModel();
        model.setValueAt(dataRow[0], baris, 0);
        model.setValueAt(dataRow[1], baris, 1);
        model.setValueAt(dataRow[2], baris, 2);
        model.setValueAt(dataRow[3], baris, 3);
        model.setValueAt(dataRow[4], baris, 4);
        model.setValueAt(dataRow[5], baris, 5);
        model.setValueAt(dataRow[6], baris, 6);
//        model.addRow(dataRow);
    }
    private Object[][] getDataSupplier(){
        try{
            Object[][] obj;
            int rows = 0;
            String sql = "SELECT id_supplier, nama_supplier, no_telp, alamat FROM supplier " + keywordSupplier;
            // mendefinisikan object berdasarkan total rows dan cols yang ada didalam tabel
            obj = new Object[supplier.getJumlahData("supplier", keywordSupplier)][4];
            // mengeksekusi query
            supplier.res = supplier.stat.executeQuery(sql);
            // mendapatkan semua data yang ada didalam tabel
            while(supplier.res.next()){
                // menyimpan data dari tabel ke object
                obj[rows][0] = supplier.res.getString("id_supplier");
                obj[rows][1] = supplier.res.getString("nama_supplier");
                obj[rows][2] = supplier.res.getString("no_telp");
                obj[rows][3] = supplier.res.getString("alamat");
                rows++; // rows akan bertambah 1 setiap selesai membaca 1 row pada tabel
            }
            return obj;
        }catch(SQLException ex){
            Message.showException(this, "Terjadi kesalahan saat mengambil data dari database\n" + ex.getMessage(), ex, true);
        }
        return null;
    }
    private void addrowtotabeldetail(Object[] dataRow) {
        DefaultTableModel model = (DefaultTableModel) this.tabelData.getModel();
        model.addRow(dataRow);
    }
    private void updateTabelSupplier(){
        this.tabelDataSupplier.setModel(new javax.swing.table.DefaultTableModel(
            getDataSupplier(),
            new String [] {
                "ID Supplier", "Nama Supplier", "No Telephone", "Alamat"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
    }
    
    private void TabelBarang(){
        try{
            Object data[] = new Object[5];
            DefaultTableModel model = (DefaultTableModel) tabelDataBarang.getModel();
            Object obj[][];
            int rows = 0;
            String sql = "SELECT id_barang, nama_barang, jenis_barang, stok, harga_beli FROM barang " + keywordBarang;
            // mendefinisikan object berdasarkan total rows dan cols yang ada didalam tabel
            obj = new Object[barang.getJumlahData("barang", keywordBarang)][5];
            // mengeksekusi query
            barang.res = barang.stat.executeQuery(sql);
            // mendapatkan semua data yang ada didalam tabel
            while(barang.res.next()){
                // menyimpan data dari tabel ke object
                data[0] = barang.res.getString("id_barang");
                data[1] = barang.res.getString("nama_barang");
                data[2] = text.toCapitalize(barang.res.getString("jenis_barang"));
                data[3] = barang.res.getInt("stok");
                data[4] = text.toMoneyCase(barang.res.getString("harga_beli"));
                model.addRow(data);
            }
        }catch(SQLException ex){
            Message.showException(this, "Terjadi kesalahan saat mengambil data dari database\n" + ex.getMessage(), ex, true);
        }
    }
    private Object[][] getDataBarang(){
        try{
            Object obj[][];
            int rows = 0;
            String sql = "SELECT id_barang, nama_barang, jenis_barang, stok, harga_jual FROM barang " + keywordBarang;
            // mendefinisikan object berdasarkan total rows dan cols yang ada didalam tabel
            obj = new Object[barang.getJumlahData("barang", keywordBarang)][5];
            // mengeksekusi query
            barang.res = barang.stat.executeQuery(sql);
            // mendapatkan semua data yang ada didalam tabel
            while(barang.res.next()){
                // menyimpan data dari tabel ke object
                obj[rows][0] = barang.res.getString("id_barang");
                obj[rows][1] = barang.res.getString("nama_barang");
                obj[rows][2] = text.toCapitalize(barang.res.getString("jenis_barang"));
                obj[rows][3] = barang.res.getString("stok");
                obj[rows][4] = text.toMoneyCase(barang.res.getString("harga_jual"));
                rows++; // rows akan bertambah 1 setiap selesai membaca 1 row pada tabel
            }
            return obj;
        }catch(SQLException ex){
            Message.showException(this, "Terjadi kesalahan saat mengambil data dari database\n" + ex.getMessage(), ex, true);
        }
        return null;
    }
    
    private void updateTabelBarang(){
        this.tabelDataBarang.setModel(new javax.swing.table.DefaultTableModel(
            getDataBarang(),
            new String [] {
                "ID Barang", "Nama Barang", "Jenis Barang", "Stok", "Harga"
            }
        ){
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
    }
    
    private boolean isSelectedSupplier(){
        return this.tabelDataSupplier.getSelectedRow() > - 1;
    }
    
    private boolean isSelectedBarang(){
        return this.tabelDataBarang.getSelectedRow() > - 1;
    }
    
    private void showDataSupplier(){
        
        // cek akapah ada data supplier yg dipilih
        if(this.isSelectedSupplier()){
            // mendapatkan data supplier
            this.idSupplier = this.idSelectedSupplier;
            this.namaSupplier = this.supplier.getNama(this.idSupplier);
            System.out.println(namaSupplier);
            // menampilkan data barang
            this.inpNamaSupplier.setText("<html><p>:&nbsp;"+this.namaSupplier+"</p></html>");
        }
    }
    
    private void showDataBarang(){
        
        // cek apakah ada data barang yang dipilih
        if(this.isSelectedBarang()){
            // mendapatkan data barang
            this.idBarang = this.idSelectedBarang;
            this.namaBarang = text.toCapitalize(this.barang.getNamaBarang(this.idBarang));
            this.stok = Integer.parseInt(this.barang.getStok(this.idBarang));
            this.hargaBeli = Integer.parseInt(this.barang.getHargaBeli(this.idBarang));
//            this.totalHarga = this.hargaBeli;
            
            // menampilkan data barang
            this.inpIDBarang.setText("<html><p>:&nbsp;"+this.idBarang+"</p></html>");
            this.inpNamaBarang.setText("<html><p>:&nbsp;"+this.namaBarang+"</p></html>");
            this.inpHarga.setText("<html><p>:&nbsp;"+text.toMoneyCase(Integer.toString(this.hargaBeli))+"</p></html>");
        }
    }
    private void updateTabelData(){
        DefaultTableModel model = (DefaultTableModel) tabelData.getModel();
        while(tabelData.getRowCount()>0){
            model.removeRow(0);
        }
    }
    private void resetInput(){
        this.inpNamaSupplier.setText("<html><p>:&nbsp;</p></html>");
        this.inpNamaBarang.setText("<html><p>:&nbsp;</p></html>");
        this.inpJumlah.setText("1");
        this.inpTotalHarga.setText("<html><p>:&nbsp;</p></html>");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        inpCariBarang = new javax.swing.JTextField();
        inpCariSupplier = new javax.swing.JTextField();
        btnBayar = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        txtTotal = new javax.swing.JLabel();
        inpID = new javax.swing.JLabel();
        inpNamaKaryawan = new javax.swing.JLabel();
        inpIDBarang = new javax.swing.JLabel();
        inpNamaSupplier = new javax.swing.JLabel();
        inpNamaBarang = new javax.swing.JLabel();
        inpJumlah = new javax.swing.JTextField();
        inpTotalHarga = new javax.swing.JLabel();
        inpHarga = new javax.swing.JLabel();
        inpTanggal = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelDataBarang = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelData = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabelDataSupplier = new javax.swing.JTable();
        background = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(957, 650));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        inpCariBarang.setBackground(new java.awt.Color(255, 255, 255));
        inpCariBarang.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        inpCariBarang.setForeground(new java.awt.Color(0, 0, 0));
        inpCariBarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inpCariBarangKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                inpCariBarangKeyTyped(evt);
            }
        });
        add(inpCariBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 53, 345, 26));

        inpCariSupplier.setBackground(new java.awt.Color(255, 255, 255));
        inpCariSupplier.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        inpCariSupplier.setForeground(new java.awt.Color(0, 0, 0));
        inpCariSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inpCariSupplierActionPerformed(evt);
            }
        });
        inpCariSupplier.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inpCariSupplierKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                inpCariSupplierKeyTyped(evt);
            }
        });
        add(inpCariSupplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 252, 345, 26));

        btnBayar.setBackground(new java.awt.Color(34, 119, 237));
        btnBayar.setForeground(new java.awt.Color(255, 255, 255));
        btnBayar.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnBayar.setOpaque(false);
        btnBayar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBayarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnBayarMouseExited(evt);
            }
        });
        btnBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBayarActionPerformed(evt);
            }
        });
        add(btnBayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(558, 712, 150, 48));

        btnBatal.setBackground(new java.awt.Color(220, 41, 41));
        btnBatal.setForeground(new java.awt.Color(255, 255, 255));
        btnBatal.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnBatal.setOpaque(false);
        btnBatal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBatalMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBatalMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnBatalMouseExited(evt);
            }
        });
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });
        add(btnBatal, new org.netbeans.lib.awtextra.AbsoluteConstraints(737, 712, 150, 48));

        btnSimpan.setBackground(new java.awt.Color(34, 119, 237));
        btnSimpan.setForeground(new java.awt.Color(255, 255, 255));
        btnSimpan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnSimpan.setOpaque(false);
        btnSimpan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSimpanMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSimpanMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSimpanMouseExited(evt);
            }
        });
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });
        add(btnSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 712, 150, 48));

        btnHapus.setBackground(new java.awt.Color(34, 119, 237));
        btnHapus.setForeground(new java.awt.Color(255, 255, 255));
        btnHapus.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnHapus.setOpaque(false);
        btnHapus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHapusMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnHapusMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnHapusMouseExited(evt);
            }
        });
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });
        add(btnHapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(378, 712, 150, 48));

        btnEdit.setBackground(new java.awt.Color(34, 119, 237));
        btnEdit.setForeground(new java.awt.Color(255, 255, 255));
        btnEdit.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnEdit.setOpaque(false);
        btnEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEditMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEditMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEditMouseExited(evt);
            }
        });
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        add(btnEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(197, 712, 150, 48));
        add(txtTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 666, 160, 22));

        inpID.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpID.setForeground(new java.awt.Color(0, 0, 0));
        inpID.setText(": ");
        add(inpID, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 68, 200, 28));

        inpNamaKaryawan.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        inpNamaKaryawan.setForeground(new java.awt.Color(0, 0, 0));
        inpNamaKaryawan.setText(": ");
        add(inpNamaKaryawan, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 151, 200, 28));

        inpIDBarang.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpIDBarang.setForeground(new java.awt.Color(0, 0, 0));
        inpIDBarang.setText(": ");
        add(inpIDBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 110, 200, 28));

        inpNamaSupplier.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        inpNamaSupplier.setForeground(new java.awt.Color(0, 0, 0));
        inpNamaSupplier.setText(":");
        add(inpNamaSupplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 192, 200, 28));

        inpNamaBarang.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        inpNamaBarang.setForeground(new java.awt.Color(0, 0, 0));
        inpNamaBarang.setText(":");
        add(inpNamaBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 233, 200, 28));

        inpJumlah.setBackground(new java.awt.Color(255, 255, 255));
        inpJumlah.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpJumlah.setForeground(new java.awt.Color(0, 0, 0));
        inpJumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inpJumlahActionPerformed(evt);
            }
        });
        inpJumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                inpJumlahKeyTyped(evt);
            }
        });
        add(inpJumlah, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 315, 50, 32));

        inpTotalHarga.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpTotalHarga.setForeground(new java.awt.Color(0, 0, 0));
        inpTotalHarga.setText(":");
        add(inpTotalHarga, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 357, 200, 28));

        inpHarga.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpHarga.setForeground(new java.awt.Color(0, 0, 0));
        inpHarga.setText(":");
        add(inpHarga, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 275, 200, 28));

        inpTanggal.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        inpTanggal.setForeground(new java.awt.Color(0, 0, 0));
        inpTanggal.setText(": 15 Oktober 2022 | 17:55");
        add(inpTanggal, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 398, 200, 28));

        tabelDataBarang.setBackground(new java.awt.Color(255, 255, 255));
        tabelDataBarang.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        tabelDataBarang.setForeground(new java.awt.Color(0, 0, 0));
        tabelDataBarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Barang", "Nama Barang", "Jenis", "Stok", "Harga Jual"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelDataBarang.setGridColor(new java.awt.Color(0, 0, 0));
        tabelDataBarang.setSelectionBackground(new java.awt.Color(26, 164, 250));
        tabelDataBarang.setSelectionForeground(new java.awt.Color(250, 246, 246));
        tabelDataBarang.getTableHeader().setReorderingAllowed(false);
        tabelDataBarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelDataBarangMouseClicked(evt);
            }
        });
        tabelDataBarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabelDataBarangKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(tabelDataBarang);

        add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(435, 80, 500, 155));

        tabelData.setBackground(new java.awt.Color(255, 255, 255));
        tabelData.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        tabelData.setForeground(new java.awt.Color(0, 0, 0));
        tabelData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tanggal", "ID Log", "ID Supplier", "Nama Supplier", "ID Barang", "Nama Barang", "Harga", "Jumlah Barang", "Total Harga"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelData.setGridColor(new java.awt.Color(0, 0, 0));
        tabelData.setSelectionBackground(new java.awt.Color(26, 164, 250));
        tabelData.setSelectionForeground(new java.awt.Color(250, 246, 246));
        tabelData.getTableHeader().setReorderingAllowed(false);
        tabelData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelDataMouseClicked(evt);
            }
        });
        tabelData.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabelDataKeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(tabelData);

        add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 460, 930, 200));

        tabelDataSupplier.setBackground(new java.awt.Color(255, 255, 255));
        tabelDataSupplier.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        tabelDataSupplier.setForeground(new java.awt.Color(0, 0, 0));
        tabelDataSupplier.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Supplier", "Nama Supplier", "No Telp", "Alamat"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelDataSupplier.setGridColor(new java.awt.Color(0, 0, 0));
        tabelDataSupplier.setSelectionBackground(new java.awt.Color(26, 164, 250));
        tabelDataSupplier.setSelectionForeground(new java.awt.Color(250, 246, 246));
        tabelDataSupplier.getTableHeader().setReorderingAllowed(false);
        tabelDataSupplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelDataSupplierSupplier1MouseClicked(evt);
            }
        });
        tabelDataSupplier.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabelDataSupplierSupplier1KeyPressed(evt);
            }
        });
        jScrollPane4.setViewportView(tabelDataSupplier);

        add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(435, 280, 500, 155));

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/gambar/app-transaksi-beli-new-075.png"))); // NOI18N
        add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        int status;
        Audio.play(Audio.SOUND_INFO);
        status = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin membatalkan transaksi?", "Confirm", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);
        System.out.println("status option" +status);
        switch(status){
            case JOptionPane.YES_OPTION : {
                // mereset tabel
                this.updateTabelSupplier();
                this.updateTabelBarang();
                this.updateTabelData();
                // mereset input
                this.resetInput();
                txtTotal.setText(text.toMoneyCase("0"));
                break;
            }
        }
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBayarActionPerformed
        // membuka window konfirmasi pembayaran
        PreparedStatement pst1;
        PreparedStatement pst2;
        String idbarang,namabarang,idsupplier,namasupplier,hbarang,jbarang,totalharga;
        try{
            if(tabelData.getRowCount()>0){
                
                int status;
                Audio.play(Audio.SOUND_INFO);
                status = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin melakukan pembayaran ?", "Confirm", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);
                System.out.println("status option" +status);
                switch(status){
                    case JOptionPane.YES_OPTION : {
                        pst1 = db.conn.prepareStatement("INSERT INTO transaksi_beli VALUES (?, ?, ?, ?)");
                        pst1.setString(1, idTr);
                        pst1.setString(2, idKaryawan);
                        pst1.setInt(3, text.toIntCase(txtTotal.getText()));
                        pst1.setString(4, waktu.getCurrentDateTime());
                        if(pst1.executeUpdate()>0){
                            System.out.println("Sudah membuat Transaksi Beli");
                        }
                        //id_tr_beli,id_supplier,nama_supplier,id_barang,_nama_barang,jenis_barang,harga,jumlah,total_harga
                        pst2 = db.conn.prepareStatement("INSERT INTO detail_transaksi_beli VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
                        
                        for(int i = 0; i < tabelData.getRowCount(); i++){
                            idbarang = tabelData.getValueAt(i,4).toString();
                            pst1.setString(1, this.idTr);
                            pst1.setString(2, tabelData.getValueAt(i, 2).toString());
                            pst1.setString(3, tabelData.getValueAt(i, 3).toString());
                            pst1.setString(4, idbarang);
                            pst1.setString(5, tabelData.getValueAt(i, 5).toString());
                            pst1.setString(6, barang.getJenis(idbarang));
                            pst1.setInt(7, Integer.parseInt(tabelData.getValueAt(i, 6).toString()));
                            pst1.setInt(8, Integer.parseInt(tabelData.getValueAt(i, 7).toString()));
                            pst1.setInt(9, Integer.parseInt(tabelData.getValueAt(i, 8).toString()));
                            if(pst1.executeUpdate()>0){
                                System.out.println("Sudah membuat Detail Transaksi Beli ke "+ i);
                            }
                        }
                        Message.showInformation(this, "Transaksi berhasil!");
                        // mereset tabel
                        this.updateTabelSupplier();
                        this.updateTabelBarang();
                        this.updateTabelData();
                        // mereset input
                        this.resetInput();
                        this.idTr = this.trb.createIDTransaksi();
                        break;
                    }
                    case JOptionPane.NO_OPTION : {
                        System.out.println("Transaksi Dibatalkan");
                        Message.showInformation(this, "Transaksi Dibatalkan!");
                        break;
                    }
                }
            }else{
                Message.showWarning(this, "Tabel Data Transaksi Tidak Boleh Kosong !"); 
            }
        } catch (SQLException | InValidUserDataException ex) {
            ex.printStackTrace();
            System.out.println("Error Message : " + ex.getMessage());
        }
    }//GEN-LAST:event_btnBayarActionPerformed

    private void btnBayarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBayarMouseEntered
//        this.btnBeli.setIcon(Gambar.getIcon("ic-pembayaran-pay-entered.png"));
    }//GEN-LAST:event_btnBayarMouseEntered

    private void btnBayarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBayarMouseExited
//        this.btnBeli.setIcon(Gambar.getIcon("ic-pembayaran-pay.png"));
    }//GEN-LAST:event_btnBayarMouseExited

    private void btnBatalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBatalMouseEntered
//        this.btnBatal.setIcon(Gambar.getIcon("ic-data-hapus-entered.png"));
    }//GEN-LAST:event_btnBatalMouseEntered

    private void btnBatalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBatalMouseExited
//        this.btnBatal.setIcon(Gambar.getIcon("ic-data-hapus.png"));
    }//GEN-LAST:event_btnBatalMouseExited

    private void tabelDataBarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelDataBarangMouseClicked
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        // menampilkan data barang
        this.idSelectedBarang = this.tabelDataBarang.getValueAt(tabelDataBarang.getSelectedRow(), 0).toString();
        this.showDataBarang();
        this.totalHarga = Integer.parseInt(inpJumlah.getText())*hargaBeli;
        inpTotalHarga.setText(text.toMoneyCase(Integer.toString(this.totalHarga)));
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_tabelDataBarangMouseClicked

    private void tabelDataBarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelDataBarangKeyPressed
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        if(evt.getKeyCode() == KeyEvent.VK_UP){
            this.idSelectedBarang = this.tabelDataBarang.getValueAt(tabelDataBarang.getSelectedRow() - 1, 0).toString();
            this.showDataBarang();
            this.totalHarga = Integer.parseInt(inpJumlah.getText())*hargaBeli;
            inpTotalHarga.setText(text.toMoneyCase(Integer.toString(this.totalHarga)));
        }else if(evt.getKeyCode() == KeyEvent.VK_DOWN){
            this.idSelectedBarang = this.tabelDataBarang.getValueAt(tabelDataBarang.getSelectedRow() + 1, 0).toString();
            this.showDataBarang();
            this.totalHarga = Integer.parseInt(inpJumlah.getText())*hargaBeli;
            inpTotalHarga.setText(text.toMoneyCase(Integer.toString(this.totalHarga)));
        }
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_tabelDataBarangKeyPressed

    private void tabelDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelDataMouseClicked
//        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        int dipilih = tabelData.getSelectedRow();
        System.out.println("baris yg dipilih "+dipilih);
//        // menampilkan data supplier
//        this.idSelectedSupplier = this.tabelData.getValueAt(tabelData.getSelectedRow(), 0).toString();
//        this.showDataSupplier();
//        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_tabelDataMouseClicked

    private void tabelDataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelDataKeyPressed
//        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
//        if(evt.getKeyCode() == KeyEvent.VK_UP){
//            this.idSelectedSupplier = this.tabelData.getValueAt(tabelData.getSelectedRow() - 1, 0).toString();
//            this.showDataSupplier();
//        }else if(evt.getKeyCode() == KeyEvent.VK_DOWN){
//            this.idSelectedSupplier = this.tabelData.getValueAt(tabelData.getSelectedRow() + 1, 0).toString();
//            this.showDataSupplier();
//        }
//        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_tabelDataKeyPressed

    private void inpCariBarangKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpCariBarangKeyTyped
        String key = this.inpCariBarang.getText();
        this.keywordBarang = "WHERE id_barang LIKE '%"+key+"%' OR nama_barang LIKE '%"+key+"%'";
        this.updateTabelBarang();
    }//GEN-LAST:event_inpCariBarangKeyTyped

    private void inpCariBarangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpCariBarangKeyReleased
        String key = this.inpCariBarang.getText();
        this.keywordBarang = "WHERE id_barang LIKE '%"+key+"%' OR nama_barang LIKE '%"+key+"%'";
        this.updateTabelBarang();
    }//GEN-LAST:event_inpCariBarangKeyReleased

    private void inpCariSupplierKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpCariSupplierKeyTyped
        String key = this.inpCariSupplier.getText();
        this.keywordSupplier = "WHERE id_supplier LIKE '%"+key+"%' OR nama_supplier LIKE '%"+key+"%'";
        this.updateTabelSupplier();
    }//GEN-LAST:event_inpCariSupplierKeyTyped

    private void inpCariSupplierKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpCariSupplierKeyReleased
        String key = this.inpCariSupplier.getText();
        this.keywordSupplier = "WHERE id_supplier LIKE '%"+key+"%' OR nama_supplier LIKE '%"+key+"%'";
        this.updateTabelSupplier();
    }//GEN-LAST:event_inpCariSupplierKeyReleased

    private void inpCariSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inpCariSupplierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inpCariSupplierActionPerformed

    private void tabelDataSupplierSupplier1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelDataSupplierSupplier1MouseClicked
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        // menampilkan data supplier
        this.idSelectedSupplier = this.tabelDataSupplier.getValueAt(tabelDataSupplier.getSelectedRow(), 0).toString();
        this.showDataSupplier();
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_tabelDataSupplierSupplier1MouseClicked

    private void tabelDataSupplierSupplier1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelDataSupplierSupplier1KeyPressed
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        if(evt.getKeyCode() == KeyEvent.VK_UP){
            this.idSelectedSupplier = this.tabelDataSupplier.getValueAt(tabelDataSupplier.getSelectedRow() - 1, 0).toString();
            this.showDataSupplier();
        }else if(evt.getKeyCode() == KeyEvent.VK_DOWN){
            this.idSelectedSupplier = this.tabelDataSupplier.getValueAt(tabelDataSupplier.getSelectedRow() + 1, 0).toString();
            this.showDataSupplier();
        }
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_tabelDataSupplierSupplier1KeyPressed

    private void btnSimpanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSimpanMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSimpanMouseEntered

    private void btnSimpanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSimpanMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSimpanMouseExited

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnHapusMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHapusMouseEntered

    private void btnHapusMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHapusMouseExited

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnEditMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditMouseEntered

    private void btnEditMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditMouseExited

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnSimpanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSimpanMouseClicked
        DefaultTableModel modelData = (DefaultTableModel) tabelData.getModel();
        DefaultTableModel modelBarang = (DefaultTableModel) tabelDataBarang.getModel();
        boolean error = false, cocokBarang = false,cocokSupplier = false;
        System.out.println("simpan");
        int tharga = 0,total = 0, stokSekarang = 0, totalProduk = 0, sisaStok = 0, jumlahB = 0,thargaLama = 0, thargaBaru = 0,baris = -1;
//        String idProdukBaru = inpIDBarang.getText();
        if (inpTanggal.getText().equals("")) {
            error = true;
            Message.showWarning(this, "Tanggal harus Di isi !");
        } else if (inpID.getText().equals("")) {
            error = true;
            Message.showWarning(this, "ID Transaksi harus Di isi !");
        } else if (inpNamaSupplier.getText().equals(":")) {
            error = true;
            Message.showWarning(this, "Nama Supplier harus Di isi !");
        } else if (inpIDBarang.getText().equals("")) {
            error = true;
            Message.showWarning(this, "ID Barang harus Di isi !");
        } else if (inpNamaBarang.getText().equals("")) {
            error = true;
            Message.showWarning(this, "Nama Barang harus Di isi !");
        } else if (inpJumlah.getText().equals("")) {
            error = true;
            Message.showWarning(this, "Jumlah Barang harus Di isi !");
        } else if (inpJumlah.getText().equals("0")) {
            error = true;
            Message.showWarning(this, "Jumlah Barang tidak boleh 0 !");
        } else if (inpHarga.getText().equals("")) {
            error = true;
            Message.showWarning(this, "Harga Harus di isi!");
        } else if (inpTotalHarga.getText().equals("")) {
            error = true;
            Message.showWarning(this, "Harga Total Harus di isi!");
        }
        //
        if (!error) {
            for(int i = 0; i<tabelDataBarang.getRowCount(); i++){
                if(tabelDataBarang.getValueAt(i, 0).equals(this.idBarang)){
                    stokSekarang = Integer.parseInt(tabelDataBarang.getValueAt(i,3).toString());
                    break;
                }
            }
            if (tabelData.getRowCount() >= 1) { //jika tabel ada isinya
                String idBarangLama = "",idSupplierLama = "",idbarang = "",idsupplier = "",namaSupplierLama = "";
                int stokLama = 0, stokBaru = 0;
                System.out.println("id supplier di tabel "+this.idSupplier);
                System.out.println("id barang di tabel "+this.idBarang);
                for(int i = 0; i<tabelData.getRowCount(); i++){
                    //mencari id barang dan id supplier di tabel data
                    idsupplier = tabelData.getValueAt(i, 2).toString();
                    System.out.println("id supplier di baris "+(i+1)+" : "+ idsupplier);
                    idbarang = tabelData.getValueAt(i, 4).toString();
                    System.out.println("id barang di baris "+(i+1)+" : "+ idbarang);
                    if(this.idBarang.equals(idbarang) && this.idSupplier.equals(idsupplier)){
                        baris = i;
                    }
                    if(this.idBarang.equals(idbarang)){
                        cocokBarang = true;
                        System.out.println("barang cocok");
                        idBarangLama = idbarang;
                        stokLama = Integer.parseInt(tabelData.getValueAt(i,7).toString());
                        thargaLama = Integer.parseInt(tabelData.getValueAt(i, 8).toString());
                    }
                    if(this.idSupplier.equals(idsupplier)){
                        cocokSupplier = true;
                        System.out.println("supplier cocok");
                        idSupplierLama = idsupplier;
//                        namaSupplierLama = tabelData.getValueAt(i, 3).toString();
                    }
                }
                //mengecek jika id barang dan id supplier ada yg sama
                if (cocokBarang && cocokSupplier){
                    System.out.println("data barang dan supplier sama");
                    System.out.println("baris ke "+(baris+1));
                    jumlahB = Integer.parseInt(inpJumlah.getText());
                    stokBaru =  jumlahB + stokLama;
                    thargaBaru = text.toIntCase(inpTotalHarga.getText());
                    tharga = thargaLama + thargaBaru;
                    System.out.println(idBarangLama);
                    System.out.println("total barang lama " + stokLama);
                    System.out.println("total barang sekarang " + jumlahB);
                    System.out.println("total barang baru " + stokBaru);
                    System.out.println("total harga lama " + thargaLama);
                    System.out.println("total harga sekarang " + thargaBaru);
                    System.out.println("total harga baru " + tharga);
                    // jika jumlah baru lebih dari jumlah lama
                    if (jumlahB > this.stok) { 
                        System.out.println("Jumlah Lebih Dari Stok Yang Tersedia !");
                        Message.showWarning(this, "Jumlah Lebih Dari Stok Yang Tersedia !");   
                    } else { //jika data duplikasi 
                        sisaStok = this.stok - jumlahB;
                        System.out.println("stok di dalam tabel "+this.stok);
                        System.out.println("sisa stok sekarang "+sisaStok);
                        this.stok = sisaStok;
                        //update tabel data
                        
                        modelData.setValueAt(stokBaru, baris, 7);
                        modelData.setValueAt(tharga, baris, 8);
                        //update table barang
                        modelBarang.setValueAt(sisaStok, tabelDataBarang.getSelectedRow(), 3); 
                        //update total harga keseluruhan
                        for (int i = 0; i < tabelData.getRowCount(); i++) {
                            total += Integer.parseInt(tabelData.getValueAt(i, 8).toString());
                        }
                        System.out.println(total);
                        this.txtTotal.setText(text.toMoneyCase(Integer.toString(total)));
                    }
                }else if (cocokSupplier || cocokBarang){
                    System.out.println("data supplier atau data barang sama");
                    jumlahB = Integer.parseInt(inpJumlah.getText());
                    if (jumlahB > this.stok) { // jika jumlah baru lebih dari jumlah lama
                        System.out.println("Jumlah Lebih Dari Stok Yang Tersedia !");
                        Message.showWarning(this, "Jumlah Lebih Dari Stok Yang Tersedia !");   
                    } else { 
                        //jika data barang atau supplier ada yg sama
                        sisaStok = this.stok - jumlahB;
                        //ubah tabel data sementara
                        addrowtotabeldetail(new Object[]{
                            waktu.getCurrentDate(),
                            this.idTr,
                            this.idSupplier,
                            this.namaSupplier,
                            this.idBarang,
                            this.namaBarang,
                            this.hargaBeli,
                            jumlahB,
                            this.totalHarga
                        });
                        this.stok = sisaStok;
                        //update table barang
                        modelBarang.setValueAt(sisaStok, tabelDataBarang.getSelectedRow(), 3); 
                        //update total harga keseluruhan
                        for (int i = 0; i < tabelData.getRowCount(); i++) {
                            total += Integer.parseInt(tabelData.getValueAt(i, 8).toString());
                        }
                        System.out.println(total);
                        this.txtTotal.setText(text.toMoneyCase(Integer.toString(total)));
                    }
                    
                    //mengecek jika id barang dan id supplier tidak ada yg sama
                }else if(!cocokBarang && !cocokSupplier){ //jika data baru tidak ada duplikasi
                    System.out.println("data baris baru");
                    jumlahB = Integer.parseInt(inpJumlah.getText());
                    if (jumlahB > this.stok) { // jika jumlah baru lebih dari jumlah lama
                        System.out.println("Jumlah Lebih Dari Stok Yang Tersedia !");
                        Message.showWarning(this, "Jumlah Lebih Dari Stok Yang Tersedia !");   
                    }else{
                        System.out.println("data baru ");
                        addrowtotabeldetail(new Object[]{
                            waktu.getCurrentDate(),
                            this.idTr,
                            this.idSupplier,
                            this.namaSupplier,
                            this.idBarang,
                            this.namaBarang,
                            this.hargaBeli,
                            jumlahB,
                            this.totalHarga
                        });
                        sisaStok = stokSekarang + jumlahB;
                        System.out.println("sisa stok "+ sisaStok);
                        this.stok = sisaStok;
                    //update total harga keseluruhan
                        modelBarang.setValueAt(sisaStok, tabelDataBarang.getSelectedRow(), 3); 
                        for (int i = 0; i < tabelData.getRowCount(); i++) {
                            total += Integer.parseInt(tabelData.getValueAt(i, 8).toString());
                        }
                        System.out.println(total);
                        this.txtTotal.setText(text.toMoneyCase(Integer.toString(total)));
                    }
                }
                //
            } else { 
                //jika tabel kosong
                System.out.println("data kosong");
                jumlahB = Integer.parseInt(inpJumlah.getText());
                if (jumlahB > this.stok) {
                    Message.showWarning(this, "Jumlah Lebih Dari Stok Yang Tersedia !");   
                } else {
                    addrowtotabeldetail(new Object[]{
                        waktu.getCurrentDate(),
                        this.idTr,
                        this.idSupplier,
                        this.namaSupplier,
                        this.idBarang,
                        this.namaBarang,
                        this.hargaBeli,
                        jumlahB,
                        this.totalHarga
                    });
                    //
                    //update tabel barang
                    System.out.println("stok tabel "+this.stok);
                    System.out.println("jumlah pesanan "+jumlahB);
                    sisaStok = stokSekarang + jumlahB;
                    System.out.println("sisa stok "+ sisaStok);
//                    DefaultTableModel model = (DefaultTableModel) tabelDataBarang.getModel();
                    modelBarang.setValueAt(sisaStok, tabelDataBarang.getSelectedRow(), 3);
                    //update total harga keseluruhan
                    for (int i = 0; i < tabelData.getRowCount(); i++) {
                        total += Integer.parseInt(tabelData.getValueAt(i, 8).toString());
                    }
                    System.out.println(total);
                    this.txtTotal.setText(text.toMoneyCase(Integer.toString(total)));
//                    String idBarangLama = tabelData.getValueAt(tabelData.getSelectedRow()-1, 4).toString();
//                    System.out.println("id barang lama "+idBarangLama);
                }
            }
        }
    }//GEN-LAST:event_btnSimpanMouseClicked

    private void inpJumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inpJumlahActionPerformed
        try{
            int jumlahbarang = Integer.parseInt(inpJumlah.getText());
            this.totalHarga = Integer.parseInt(inpJumlah.getText())*hargaBeli;
            inpTotalHarga.setText(text.toMoneyCase(Integer.toString(this.totalHarga)));
        }catch(NumberFormatException e){
            System.out.println("harus angka ");
        }
    }//GEN-LAST:event_inpJumlahActionPerformed

    private void inpJumlahKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpJumlahKeyTyped
//        if(evt.getKeyCode() == KeyEvent.VK_NUMPAD0 || evt.getKeyCode() == KeyEvent.VK_NUMPAD1 || evt.getKeyCode() == KeyEvent.VK_NUMPAD2 || evt.getKeyCode() == KeyEvent.VK_NUMPAD3 || evt.getKeyCode() == KeyEvent.VK_NUMPAD4 || evt.getKeyCode() == KeyEvent.VK_NUMPAD5 || evt.getKeyCode() == KeyEvent.VK_NUMPAD6 || evt.getKeyCode() == KeyEvent.VK_NUMPAD7 || evt.getKeyCode() == KeyEvent.VK_NUMPAD8 || evt.getKeyCode() == KeyEvent.VK_NUMPAD9){
//            this.totalHarga = Integer.parseInt(inpJumlah.getText())*hargaBeli;
//            inpTotalHarga.setText(text.toMoneyCase(Integer.toString(this.totalHarga)));
//        }
    }//GEN-LAST:event_inpJumlahKeyTyped

    private void btnHapusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusMouseClicked
        DefaultTableModel modelData = (DefaultTableModel) tabelData.getModel();
        DefaultTableModel modelBarang = (DefaultTableModel) tabelDataBarang.getModel();
        boolean cocok = false;
        String idbarang = "";
        int stok = 0,total = 0, totalSisa = 0,sisaBarang = 0,totalkeseluruhan = text.toIntCase(txtTotal.getText());
        System.out.println("total keseluruhan "+totalkeseluruhan);
        if(tabelData.getSelectedRow()<0){
            Message.showWarning(this, "Tidak ada data yang dipilih!");
        }else{
            idbarang = tabelData.getValueAt(tabelData.getSelectedRow(),4).toString();
            stok = Integer.parseInt(tabelData.getValueAt(tabelData.getSelectedRow(),7).toString());
            total = Integer.parseInt(tabelData.getValueAt(tabelData.getSelectedRow(),8).toString());
            totalSisa = totalkeseluruhan - total;
            txtTotal.setText(text.toMoneyCase(Integer.toString(totalSisa)));
            for(int i = 0; i<tabelDataBarang.getRowCount(); i++){
                cocok = tabelDataBarang.getValueAt(i, 0).toString().equals(idbarang);
                if(tabelDataBarang.getValueAt(i, 0).toString().equals(idbarang)){
//                    System.out.println("match data barang di edit");
                    sisaBarang = Integer.parseInt(tabelDataBarang.getValueAt(i, 3).toString()) + stok;
                    modelBarang.setValueAt(sisaBarang,i,3);
                }
            }
                // mereset input
                this.resetInput();
            modelData.removeRow(tabelData.getSelectedRow());
        }
    }//GEN-LAST:event_btnHapusMouseClicked

    private void btnEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditMouseClicked
        DefaultTableModel model = (DefaultTableModel) tabelData.getModel();
        String idSupplierBaru = tabelDataBarang.getValueAt(tabelDataBarang.getSelectedRow()-1, 2).toString();
        String idSuppliertabel = "";
        String idBarangBaru = tabelDataBarang.getValueAt(tabelDataBarang.getSelectedRow()-1, 4).toString();
        String idBarangtabel = "";
        String idbarang = "";
        int stok = 0,total = 0, totalSisa = 0,sisaBarang = 0,totalkeseluruhan = text.toIntCase(txtTotal.getText());
        if(tabelData.getSelectedRow()<0){
            Message.showWarning(this, "Tidak ada data yang dipilih!");
        }else{
            idbarang = tabelData.getValueAt(tabelData.getSelectedRow(),4).toString();
            stok = Integer.parseInt(tabelData.getValueAt(tabelData.getSelectedRow(),7).toString());
            total = Integer.parseInt(tabelData.getValueAt(tabelData.getSelectedRow(),8).toString());
            totalSisa = totalkeseluruhan - total;
            txtTotal.setText(text.toMoneyCase(Integer.toString(totalSisa)));
            int hargaSekarang = Integer.parseInt(tabelData.getValueAt(tabelData.getSelectedRow(), 8).toString());
//            if(hargaSekarang>thargaBaru){
//                
//            }else if(hargaSekarang<thargaBaru){
//                
//            }else if(hargaSekarang == thargaBaru){
//                
////                this.txtTotal.setText(text.toMoneyCase());
//            }
            for(int i = 0; i<tabelDataBarang.getRowCount(); i++){
//                cocok = tabelDataBarang.getValueAt(i, 0).toString().equals(idbarang);
                if(tabelDataBarang.getValueAt(i, 0).toString().equals(idbarang)){
//                    System.out.println("match data barang di edit");
                    sisaBarang = Integer.parseInt(tabelDataBarang.getValueAt(i, 3).toString()) + stok;
//                    modelBarang.setValueAt(sisaBarang,i,3);
                }
            }
            setrowtotabeldetail(new Object[]{
                waktu.getCurrentDate(),
                this.idTr,
                idBarangBaru,
                namaBarang,
//                stokBaru,
                this.hargaBeli,
                totalHarga,
            },tabelData.getSelectedRow());
        }
    }//GEN-LAST:event_btnEditMouseClicked

    private void btnBatalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBatalMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBatalMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background;
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnBayar;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JTextField inpCariBarang;
    private javax.swing.JTextField inpCariSupplier;
    private javax.swing.JLabel inpHarga;
    private javax.swing.JLabel inpID;
    private javax.swing.JLabel inpIDBarang;
    private javax.swing.JTextField inpJumlah;
    private javax.swing.JLabel inpNamaBarang;
    private javax.swing.JLabel inpNamaKaryawan;
    private javax.swing.JLabel inpNamaSupplier;
    private javax.swing.JLabel inpTanggal;
    private javax.swing.JLabel inpTotalHarga;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable tabelData;
    private javax.swing.JTable tabelDataBarang;
    private javax.swing.JTable tabelDataSupplier;
    private javax.swing.JLabel txtTotal;
    // End of variables declaration//GEN-END:variables
}
