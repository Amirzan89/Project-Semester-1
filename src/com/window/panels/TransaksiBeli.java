package com.window.panels;

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
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gemastik Lightning
 */
public class TransaksiBeli extends javax.swing.JPanel {
    
    
    private final Users user = new Users();
    
    private final Supplier supplier = new Supplier();
    
    private final Barang barang = new Barang();
    
    private final Waktu waktu = new Waktu();
    
    private final com.manage.ManageTransaksiBeli trb = new com.manage.ManageTransaksiBeli();
    
    private final Text text = new Text();
    
    private String keywordSupplier = "", keywordBarang = "", idSelectedSupplier, idSelectedBarang;
    
    private String idTr, namaTr, namaSupplier, namaBarang, idPetugas, idSupplier, idBarang, metodeBayar, tglNow;
    
    private int jumlah = 0, hargaBeli, totalHarga = 0, stok = 0;
    
    public TransaksiBeli() {
        initComponents();
        this.idTr = this.trb.createIDTransaksi();
        
        this.txtTotal.setText(text.toMoneyCase("0"));
        this.inpJumlah.setText("0");
        this.inpTotalHarga.setText(text.toMoneyCase("0"));
        
//        this.inpJumlah.setEditable(false);
        this.inpID.setText("<html><p>:&nbsp;"+this.trb.createIDTransaksi()+"</p></html>");
        this.inpNamaKaryawan.setText("<html><p>:&nbsp;"+this.user.getCurrentLoginName()+"</p></html>");
        
        this.btnAddJumlah.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        this.btnMinJumlah.setUI(new javax.swing.plaf.basic.BasicButtonUI());
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
    private void setrowtotabeldetail(Object[] dataRow) {
        DefaultTableModel model = (DefaultTableModel) tabelData.getModel();
        model.setValueAt(dataRow[0], tabelData.getRowCount() - 1, 0);
        model.setValueAt(dataRow[1], tabelData.getRowCount() - 1, 1);
        model.setValueAt(dataRow[2], tabelData.getRowCount() - 1, 2);
        model.setValueAt(dataRow[3], tabelData.getRowCount() - 1, 3);
        model.setValueAt(dataRow[4], tabelData.getRowCount() - 1, 4);
        model.setValueAt(dataRow[5], tabelData.getRowCount() - 1, 5);
        model.setValueAt(dataRow[6], tabelData.getRowCount() - 1, 6);
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
    private void addrowtotabeldetail(Object[] dataRow,JTable tabel) {
        DefaultTableModel model = (DefaultTableModel) tabel.getModel();
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
    
    private Object[][] getDataBarang(){
        try{
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
                obj[rows][0] = barang.res.getString("id_barang");
                obj[rows][1] = barang.res.getString("nama_barang");
                obj[rows][2] = text.toCapitalize(barang.res.getString("jenis_barang"));
                obj[rows][3] = barang.res.getString("stok");
                obj[rows][4] = text.toMoneyCase(barang.res.getString("harga_beli"));
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
                "ID Barang", "Nama Barang", "Jenis Barang", "Stok", "Harga Beli"
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
    
    private void resetInput(){
        this.inpNamaSupplier.setText("<html><p>:&nbsp;</p></html>");
        this.inpNamaBarang.setText("<html><p>:&nbsp;</p></html>");
        this.inpJumlah.setText("1");
//        this.inpMetode.setSelectedIndex(0);
        this.inpTotalHarga.setText("<html><p>:&nbsp;</p></html>");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        inpCariBarang = new javax.swing.JTextField();
        inpCariSupplier = new javax.swing.JTextField();
        btnAddJumlah = new javax.swing.JButton();
        btnMinJumlah = new javax.swing.JButton();
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

        btnAddJumlah.setBackground(new java.awt.Color(34, 119, 237));
        btnAddJumlah.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnAddJumlah.setForeground(new java.awt.Color(255, 255, 255));
        btnAddJumlah.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnAddJumlah.setOpaque(false);
        btnAddJumlah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAddJumlahMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAddJumlahMouseExited(evt);
            }
        });
        btnAddJumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddJumlahActionPerformed(evt);
            }
        });
        add(btnAddJumlah, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 290, 29, 25));

        btnMinJumlah.setBackground(new java.awt.Color(220, 41, 41));
        btnMinJumlah.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnMinJumlah.setForeground(new java.awt.Color(255, 255, 255));
        btnMinJumlah.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnMinJumlah.setOpaque(false);
        btnMinJumlah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnMinJumlahMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnMinJumlahMouseExited(evt);
            }
        });
        btnMinJumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMinJumlahActionPerformed(evt);
            }
        });
        add(btnMinJumlah, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 290, 27, 25));

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

        inpNamaBarang.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
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
                {"BG001", "Sprite", "Minuman", "12", "Rp. 4.000.00"},
                {"BG002", "Coca Cola", "Minuman", "10", "Rp. 4.000.00"},
                {"BG003", "Teh Pucuk", "Minuman", "10", "Rp. 4.000.00"},
                {"BG004", "Aqua 500ml", "Minuman", "5", "Rp. 5.000.00"},
                {"BG005", "Aqua 1L", "Minuman", "9", "Rp. 5.000.00"},
                {"BG006", "Indomilk", "Minuman", "11", "Rp. 8.000.00"},
                {"BG007", "Kertas Folio", "ATK", "250", "Rp. 250.00"},
                {"BG008", "Kertas HVS", "ATK", "420", "Rp. 250.00"},
                {"BG009", "Pulpen Snowman", "ATK", "23", "Rp. 2.500.00"},
                {"BG010", "Spidol Hitam", "ATK", "19", "Rp. 2.000.00"},
                {"BG011", "Spidol Merah", "ATK", "26", "Rp. 2.500.00"},
                {"BG012", "Spidol Biru", "ATK", "24", "Rp. 2.500.00"},
                {"BG013", "Yupi", "Snack", "45", "Rp. 2.500.00"},
                {"BG014", "Nabati Wafer", "Snack", "30", "Rp. 3.500.00"},
                {"BG015", "Oreo", "Snack", "60", "Rp. 2.000.00"},
                {"BG016", "Roti", "Snack", "27", "Rp. 1.000.00"},
                {"BG017", "Ichi Ocha 350ml", "Minuman", "18", "Rp. 2.000.00"}
            },
            new String [] {
                "ID Barang", "Nama Barang", "Jenis", "Stok", "Harga Jual"
            }
        ));
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
                "ID Log", "Tanggal", "ID Barang", "Nama Barang", "Jumlah Barang", "Harga", "Total Harga"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true
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
                {"SP001", "Asrul Achmad Asrofi", "086653837718", "Nganjuk, Indonesia"},
                {"SP002", "Dimas Abimanyu", "084884582640", "Nganjuk, Indonesia"},
                {"SP003", "Farel Kurniawan", "080548653287", "Nganjuk, Indonesia"},
                {"SP004", "Feby Ayu Dyah Pitaloka", "082203072218", "Jombang, Indonesia"},
                {"SP005", "Afif Fitra Nugroho", "087676072232", "Kediri, Indonesia"},
                {"SP006", "Abdulloh Kafabi", "086134471867", "Nganjuk, Indonesia"},
                {"SP007", "Deni Yuda Mahendra", "083851907614", "Kediri, Indonesia"},
                {"SP008", "Hendro Wawan Setiyo", "085790836149", "Nganjuk, Indonesia"},
                {"SP009", "Mahendra Putra Pratama", "085775502026", "Nganjuk, Indonesia"},
                {"SP010", "Mohamad Rodzikul Hidayatulloh", "0895395057931", "Nganjuk, Indonesia"},
                {"SP011", "Arfan Ardiansyah", "085706595022", "Jombang, Indonesia"},
                {"SP012", "Flavia Reiska Januari Putri", "0881026542584", "Nganjuk, Indonesia"},
                {"SP013", "Kartika Dewi Claudia", "087824060309", "Kediri, Indonesia"},
                {"SP014", "Tegar Pratama Alfianto", "085816555034", "Kediri, Indonesia"},
                {"SP015", "Sultan Yorgi Praba Mahendra", "081336252154", "Nganjuk, Indonesia"},
                {"SP016", "Renaldi Gilang Prasetyo", "087779212229", "Jombang, Indonesia"},
                {"SP017", "Rahmat Aji Wibowo", "082333174750", "Kediri, Indonesia"},
                {"SP018", "Nyofrizal Teguh Santoso", "083440455377", "Nganjuk, Indonesia"},
                {"SP019", "Mohammad Bagas Pratama", "082887145367", "Nganjuk, Indonesia"},
                {"SP020", "Kurniawan Adi Candra", "088404705857", "Nganjuk, Indonesia"},
                {"SP021", "Amanda Rahmawati", "086460356514", "Nganjuk, Indonesia"},
                {"SP022", "Esly Reeka Augustinyo", "085232007805", "Nganjuk, Indonesia"},
                {"SP023", "Mokhammad Mansor Kornianto", "089654770601", "Jombang, Indonesia"}
            },
            new String [] {
                "ID Supplier", "Nama Supplier", "No Telp", "Alamat"
            }
        ));
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
        
        switch(status){
            case JOptionPane.YES_OPTION : {
                // mereset tabel
                this.updateTabelSupplier();
                this.updateTabelBarang();

                // mereset input
                this.resetInput();
                break;
            }
        }
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBayarActionPerformed
        this.idTr = this.trb.createIDTransaksi();
        this.idPetugas = this.user.getCurrentLogin();
        
        // cek apakah user sudah memilih supplier
        if(this.tabelData.getSelectedRow() > -1){
            // mendapatkan data supplier
            this.idSupplier = this.tabelData.getValueAt(this.tabelData.getSelectedRow(), 0).toString();
            this.namaSupplier = this.supplier.getNama(this.idSupplier);
            System.out.println("ID SUPPLIER : " + this.idSupplier);
        }else{
            Message.showWarning(this, "Tidak ada data supplier yang dipilih!");
            return;
        }
        
        // cek apakah user sudah memilih data barang
        if(this.tabelDataBarang.getSelectedRow() > -1){
            // mendapatkan data barang
            this.idBarang = this.tabelDataBarang.getValueAt(this.tabelDataBarang.getSelectedRow(), 0).toString();
            this.namaBarang = this.barang.getNamaBarang(this.idBarang);
            this.jumlah = Integer.parseInt(this.inpJumlah.getText());
            this.hargaBeli = Integer.parseInt(this.barang.getHargaBeli(this.idBarang));
            this.totalHarga = hargaBeli * jumlah;
//            switch(this.inpMetode.getSelectedIndex()){
//                case 1 : this.metodeBayar = "CASH"; break;
//                case 2 : this.metodeBayar = "E-WALLET"; break;
//                default : Message.showWarning(this, "Silahkan pilih metode pembayaran terlebih dahulu!"); return;
//            }
            this.namaTr = String.format("%s membeli %s sebanyak %s dengan total harga %s", this.namaSupplier, this.namaBarang, this.jumlah, text.toMoneyCase(""+this.totalHarga));
        }else{
            Message.showWarning(this, "Tidak ada data barang yang dipilih!");
            return;
        }
        
        // membuka window konfirmasi pembayaran
        Audio.play(Audio.SOUND_INFO);
        KonfirmasiPembayaran konfirmasi = new KonfirmasiPembayaran(null, true, KonfirmasiPembayaran.OPSI_BELI);
        konfirmasi.putValueBeli(namaTr, idPetugas, idSupplier, idBarang, Integer.toString(this.jumlah), Integer.toString(this.totalHarga), this.waktu.getCurrentDate());
        konfirmasi.setVisible(true);
//        konfirmasi.
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        // mengecek apakah transaksi jadi menambahkan data atau tidak
        if(konfirmasi.isUpdated()){
            // mengupdate tabel
            this.updateTabelSupplier();
            this.updateTabelBarang();
            this.resetInput();
        }
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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
        // TODO add your handling code here:
        //
        boolean error = false;
        System.out.println("simpan");
        String total, thargaLama, thargaBaru;
        int tharga = 0, idProduk, totalProduk = 0, stokLama = 0, stokBaru = 0;
        String idProdukBaru = inpIDBarang.getText();
        if (inpTanggal.getText().equals("")) {
            error = true;
            JOptionPane.showMessageDialog(null, "Tanggal Harus Di isi");
        } else if (inpIDBarang.getText().equals("")) {
            error = true;
            JOptionPane.showMessageDialog(null, "Id Produk Harus Di isi");
        } else if (inpNamaBarang.getText().equals("")) {
            error = true;
            JOptionPane.showMessageDialog(null, "Nama Produk Harus Di isi");
//        } else if (txt_namaprd.getText().equals("")) {
//            error = true;
//            JOptionPane.showMessageDialog(null, "Jenis Harus Di isi");
        } else if (inpJumlah.getText().equals("")) {
            error = true;
            JOptionPane.showMessageDialog(null, "Stok Harus Di isi");
        } else if (inpJumlah.getText().equals("0")) {
            error = true;
            JOptionPane.showMessageDialog(null, "Stok tidak boleh 0");
        } else if (inpHarga.getText().equals("")) {
            error = true;
            JOptionPane.showMessageDialog(null, "Harga Jual Harus Di isi");
        } else if (inpTotalHarga.getText().equals("")) {
            error = true;
            JOptionPane.showMessageDialog(null, "Harga Total Harus Di isi");
        }
        if (!error) {
            if (tabelData.getRowCount() >= 1) { //jika tabel ada isinya
                if (this.idBarang.equals(idProdukBaru)) {
                    String idBarangBaru = tabelData.getValueAt(tabelData.getRowCount()-1, 2).toString();
                    stokLama = Integer.parseInt(tabelData.getValueAt(tabelData.getRowCount()-1, 4).toString());
                    stokBaru = stokLama + this.stok;
                    idProduk = Integer.parseInt(idBarangBaru.substring(3));
                    thargaLama = tabelData.getValueAt(tabelData.getRowCount()-1, 6).toString();
                    thargaBaru = inpTotalHarga.getText();
                    tharga = Integer.parseInt(thargaLama) + Integer.parseInt(thargaBaru);
                    System.out.println(idBarangBaru);
                    System.out.println("total produk " + totalProduk);
                    if (stokBaru > this.stok) { // jika jumlah baru lebih dari jumlah lama
                        JOptionPane.showMessageDialog(null, "Jumlah Lebih Dari Stok Yang Tersedia");
                    } else { //jika data duplikasi 
                        //ubah tabel data sementara
                        setrowtotabeldetail(new Object[]{
                            this.idTr,
                            waktu.getCurrentDate(),
                            idBarangBaru,
                            inpNamaBarang,
                            stokBaru,
                            totalHarga,
                            tharga
                        });
                        //update table barang
                        DefaultTableModel model = (DefaultTableModel) tabelData.getModel();
                        model.setValueAt(this.stok - stokBaru, tabelData.getRowCount() - 1, 3); 
                        //update total harga keseluruhan
                        for (int i = 0; i < tabelData.getRowCount(); i++) {
                            total = tabelData.getValueAt(i, 6).toString();
                            totalHarga += Integer.parseInt(total);
                        }
                        System.out.println(totalHarga);
                        this.txtTotal.setText(text.toMoneyCase(Integer.toString(totalHarga)));
                    }
                } else { //jika data tidak ada duplikasi
                    if (stokBaru > this.stok) { // jika jumlah baru lebih dari jumlah lama
                        JOptionPane.showMessageDialog(null, "Jumlah Lebih Dari Stok Yang Tersedia");
                    }else{
                        System.out.println("data baru ");
                        System.out.println("id : "+this.idBarang + "stok : "+this.stok);
                        addrowtotabeldetail(new Object[]{
                            this.idTr,
                            waktu.getCurrentDate(),
                            this.idBarang,
                            this.namaBarang,
                            stokBaru,
                            this.hargaBeli,
                            this.totalHarga
                        },tabelData);
                    }
                }
                DefaultTableModel model = (DefaultTableModel) tabelData.getModel();
                model.setValueAt(this.stok - stokBaru, tabelData.getRowCount() - 1, 3); 
                //update total harga keseluruhan
                for (int i = 0; i < tabelData.getRowCount(); i++) {
                    total = tabelData.getValueAt(i, 6).toString();
                    totalHarga += Integer.parseInt(total);
                }
                System.out.println(totalHarga);
                this.txtTotal.setText(text.toMoneyCase(Integer.toString(totalHarga)));
            } else { //jika tabel kosong
                System.out.println("data kosong");
                stokBaru = Integer.parseInt(inpJumlah.getText());
                if (stokBaru > this.stok) {
                    JOptionPane.showMessageDialog(null, "Jumlah Lebih Dari Stok Yang Tersedia");
                } else {
                    addrowtotabeldetail(new Object[]{
                        this.idTr,
                        waktu.getCurrentDate(),
                        this.idBarang,
                        this.namaBarang,
                        stokBaru,
                        this.hargaBeli,
                        this.totalHarga
                    },tabelData);
                    //update tabel barang
                    DefaultTableModel model = (DefaultTableModel) tabelData.getModel();
                    model.setValueAt(this.stok - stokBaru, tabelData.getRowCount() - 1, 3); 
                    //update total harga keseluruhan
                    for (int i = 0; i < tabelData.getRowCount(); i++) {
                        total = tabelData.getValueAt(i, 6).toString();
                        totalHarga += Integer.parseInt(total);
                    }
                    System.out.println(totalHarga);
                    this.txtTotal.setText(text.toMoneyCase(Integer.toString(totalHarga)));
                }
            }
        }
    }//GEN-LAST:event_btnSimpanMouseClicked

    private void btnMinJumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMinJumlahActionPerformed
        // cek apakah ada data yang dipilih
        if(this.isSelectedBarang()){
            // mengurangi jumlah
            this.jumlah--;
            // cek apakah jumlah lebih dari 0
            if(this.jumlah > 0){
                // mengupdate total harga
                this.totalHarga = 0;
                this.totalHarga = this.hargaBeli * this.jumlah;

                // menampilkan data jumlah dan total harga
                this.inpJumlah.setText(Integer.toString(this.jumlah));
                this.inpTotalHarga.setText("<html><p>:&nbsp;"+text.toMoneyCase(Integer.toString(this.totalHarga))+"</p></html>");
            }else{
                Message.showWarning(this, String.format("Jumlah barang tidak boleh kurang dari 1!", jumlah, stok));
            }
        }else{
            Message.showWarning(this, "Tidak ada data barang yang dipilih!");
        }
    }//GEN-LAST:event_btnMinJumlahActionPerformed

    private void btnMinJumlahMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinJumlahMouseExited
        this.btnMinJumlah.setForeground(new Color(255, 255, 255));
    }//GEN-LAST:event_btnMinJumlahMouseExited

    private void btnMinJumlahMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinJumlahMouseEntered
        this.btnMinJumlah.setForeground(new Color(0, 0, 0));
    }//GEN-LAST:event_btnMinJumlahMouseEntered

    private void btnAddJumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddJumlahActionPerformed
        // cek apakah ada data yang dipilih
        if(this.isSelectedBarang()){
            // menambahkan jumlah
            this.jumlah++;
            // mengupdate total harga
            this.totalHarga = 0;
            this.totalHarga = this.hargaBeli * this.jumlah;

            // menampilkan data jumlah dan total harga
            this.inpJumlah.setText(Integer.toString(this.jumlah));
            this.inpTotalHarga.setText("<html><p>:&nbsp;"+text.toMoneyCase(Integer.toString(this.totalHarga))+"</p></html>");
        }else{
            Message.showWarning(this, "Tidak ada data barang yang dipilih!");
        }
    }//GEN-LAST:event_btnAddJumlahActionPerformed

    private void btnAddJumlahMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddJumlahMouseExited
        this.btnAddJumlah.setForeground(new Color(255, 255, 255));
    }//GEN-LAST:event_btnAddJumlahMouseExited

    private void btnAddJumlahMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddJumlahMouseEntered
        this.btnAddJumlah.setForeground(new Color(0, 0, 0));
    }//GEN-LAST:event_btnAddJumlahMouseEntered

    private void inpJumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inpJumlahActionPerformed
        // TODO add your handling code here:text.toMoneyCase(Integer.toString(Integer.parseInt(inpJumlah.getText())*hargaBeli))
        this.totalHarga = Integer.parseInt(inpJumlah.getText())*hargaBeli;
        inpTotalHarga.setText(text.toMoneyCase(Integer.toString(this.totalHarga)));
    }//GEN-LAST:event_inpJumlahActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background;
    private javax.swing.JButton btnAddJumlah;
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnBayar;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnMinJumlah;
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
