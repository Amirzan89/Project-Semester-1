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
import com.users.Pembeli;
import com.users.Users;
import com.window.dialogs.KonfirmasiPembayaran;
import java.awt.Color;
import java.awt.Cursor;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gemastik Lightning
 */
public class TransaksiJual extends javax.swing.JPanel {

    private final Users user = new Users();
    
    private final Pembeli pembeli = new Pembeli();
    
    private final Barang barang = new Barang();
    
    private final com.manage.ManageTransaksiJual trj = new com.manage.ManageTransaksiJual();
    
    private final Text text = new Text();
    
    private final Waktu waktu = new Waktu();
    
    private final Database db = new Database();
    
    private String keywordPembeli = "", keywordBarang = "", idSelectedPembeli, idSelectedBarang;
    
    private String idTr, namaTr, namaPembeli, namaBarang, idPetugas, idPembeli, idBarang, metodeBayar, tglNow;
    
    private int jumlah = 1, hargaJual, totalHarga = 0, stok = 0;
    
    public TransaksiJual() {
        initComponents();
        
        this.idTr = this.trj.createIDTransaksi();
        this.inpID.setText("<html><p>:&nbsp;"+this.trj.createIDTransaksi()+"</p></html>");
        this.inpNamaPetugas.setText("<html><p>:&nbsp;"+this.user.getCurrentLoginName()+"</p></html>");
        
        this.btnBayar.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        this.btnBatal.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        
        this.tabelDataBarang.setRowHeight(29);
        this.tabelDataBarang.getTableHeader().setBackground(new java.awt.Color(255,255,255));
        this.tabelDataBarang.getTableHeader().setForeground(new java.awt.Color(0, 0, 0));
        
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
    private Object[][] getDataPembeli(){
        try{
            Object[][] obj;
            int rows = 0;
            String sql = "SELECT id_pembeli, nama_pembeli, no_telp, alamat FROM pembeli " + keywordPembeli;
            // mendefinisikan object berdasarkan total rows dan cols yang ada didalam tabel
            obj = new Object[pembeli.getJumlahData("pembeli", keywordPembeli)][4];
            // mengeksekusi query
            pembeli.res = pembeli.stat.executeQuery(sql);
            // mendapatkan semua data yang ada didalam tabel
            while(pembeli.res.next()){
                // menyimpan data dari tabel ke object
                obj[rows][0] = pembeli.res.getString("id_pembeli");
                obj[rows][1] = pembeli.res.getString("nama_pembeli");
                obj[rows][2] = pembeli.res.getString("no_telp");
                obj[rows][3] = pembeli.res.getString("alamat");
                rows++; // rows akan bertambah 1 setiap selesai membaca 1 row pada tabel
            }
            return obj;
        }catch(SQLException ex){
            Message.showException(this, "Terjadi kesalahan saat mengambil data dari database\n" + ex.getMessage(), ex, true);
        }
        return null;
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
    private void addrowtotabeldetail(Object[] dataRow) {
        DefaultTableModel model = (DefaultTableModel) this.tabelData.getModel();
        model.addRow(dataRow);
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
    
    private boolean isSelectedBarang(){
        return this.tabelDataBarang.getSelectedRow() > - 1;
    }
    
    private void updateTabelData(){
        DefaultTableModel model = (DefaultTableModel) tabelData.getModel();
        while(tabelData.getRowCount()>0){
            model.removeRow(0);
        }
    }
    private void showDataBarang(){
        
        // cek apakah ada data barang yang dipilih
        if(this.isSelectedBarang()){
            // mendapatkan data barang
            this.idBarang = this.idSelectedBarang;
            this.namaBarang = text.toCapitalize(this.barang.getNamaBarang(this.idBarang));
            this.jumlah = 1;
            this.stok = Integer.parseInt(this.barang.getStok(this.idBarang));
            this.hargaJual = Integer.parseInt(this.barang.getHargaJual(this.idBarang));
            this.totalHarga = this.hargaJual;
            
            // menampilkan data barang
            this.inpNamaBarang.setText("<html><p>:&nbsp;"+this.namaBarang+"</p></html>");
            this.inpJumlah.setText(Integer.toString(this.jumlah));
            this.inpTotalHarga.setText("<html><p>:&nbsp;"+text.toMoneyCase(Integer.toString(this.totalHarga))+"</p></html>");
        }
    }
    
    private void resetInput(){
        this.inpNamaBarang.setText("<html><p>:&nbsp;</p></html>");
        this.inpJumlah.setText("1");
//        this.inpMetode.setSelectedIndex(0);
        this.inpTotalHarga.setText("<html><p>:&nbsp;</p></html>");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        inpID = new javax.swing.JLabel();
        inpNamaPetugas = new javax.swing.JLabel();
        inpIDBarang = new javax.swing.JLabel();
        inpNamaBarang = new javax.swing.JLabel();
        inpHarga = new javax.swing.JLabel();
        inpTotalHarga = new javax.swing.JLabel();
        inpTanggal = new javax.swing.JLabel();
        txtTotal = new javax.swing.JLabel();
        inpJumlah = new javax.swing.JTextField();
        inpCariBarang = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelDataBarang = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelData = new javax.swing.JTable();
        btnSimpan = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnBayar = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        background = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(957, 650));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        inpID.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpID.setForeground(new java.awt.Color(0, 0, 0));
        inpID.setText(":");
        add(inpID, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 68, 200, 32));

        inpNamaPetugas.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpNamaPetugas.setForeground(new java.awt.Color(0, 0, 0));
        inpNamaPetugas.setText(":");
        add(inpNamaPetugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 115, 200, 32));

        inpIDBarang.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpIDBarang.setForeground(new java.awt.Color(0, 0, 0));
        inpIDBarang.setText(": ");
        add(inpIDBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 162, 200, 32));

        inpNamaBarang.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpNamaBarang.setForeground(new java.awt.Color(0, 0, 0));
        inpNamaBarang.setText(":");
        add(inpNamaBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 207, 200, 32));

        inpHarga.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpHarga.setForeground(new java.awt.Color(0, 0, 0));
        inpHarga.setText(":");
        add(inpHarga, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 255, 200, 32));

        inpTotalHarga.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpTotalHarga.setForeground(new java.awt.Color(0, 0, 0));
        inpTotalHarga.setText(":");
        add(inpTotalHarga, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 347, 200, 32));

        inpTanggal.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpTanggal.setForeground(new java.awt.Color(0, 0, 0));
        inpTanggal.setText(": 15 Oktober 2022 | 17:55");
        add(inpTanggal, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 393, 200, 32));
        add(txtTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 666, 160, 22));

        inpJumlah.setBackground(new java.awt.Color(255, 255, 255));
        inpJumlah.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpJumlah.setForeground(new java.awt.Color(0, 0, 0));
        inpJumlah.setText("1");
        inpJumlah.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        inpJumlah.setEnabled(false);
        inpJumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inpJumlahActionPerformed(evt);
            }
        });
        inpJumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inpJumlahKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                inpJumlahKeyTyped(evt);
            }
        });
        add(inpJumlah, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 301, 50, 31));

        inpCariBarang.setBackground(new java.awt.Color(255, 255, 255));
        inpCariBarang.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        inpCariBarang.setForeground(new java.awt.Color(0, 0, 0));
        inpCariBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inpCariBarangActionPerformed(evt);
            }
        });
        inpCariBarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inpCariBarangKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                inpCariBarangKeyTyped(evt);
            }
        });
        add(inpCariBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 75, 310, 25));
        inpCariBarang.getAccessibleContext().setAccessibleDescription("");

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

        add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 100, 500, 330));

        tabelData.setBackground(new java.awt.Color(255, 255, 255));
        tabelData.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        tabelData.setForeground(new java.awt.Color(0, 0, 0));
        tabelData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tanggal", "ID Log", "ID Barang", "Nama Barang", "Harga", "Jumlah Barang", "Total Harga"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
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

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/gambar/app-transaksi-jual-new-075.png"))); // NOI18N
        add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void tabelDataBarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelDataBarangMouseClicked
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        // menampilkan data pembeli
        this.idSelectedBarang = this.tabelDataBarang.getValueAt(tabelDataBarang.getSelectedRow(), 0).toString();
        this.showDataBarang();
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_tabelDataBarangMouseClicked

    private void tabelDataBarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelDataBarangKeyPressed
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        if(evt.getKeyCode() == KeyEvent.VK_UP){
            this.idSelectedBarang = this.tabelDataBarang.getValueAt(tabelDataBarang.getSelectedRow() - 1, 0).toString();
            this.showDataBarang();
        }else if(evt.getKeyCode() == KeyEvent.VK_DOWN){
            this.idSelectedBarang = this.tabelDataBarang.getValueAt(tabelDataBarang.getSelectedRow() + 1, 0).toString();
            this.showDataBarang();
        }
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_tabelDataBarangKeyPressed

    private void inpCariBarangKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpCariBarangKeyTyped
        String key = this.inpCariBarang.getText();
        this.keywordBarang = "WHERE id_barang LIKE '%"+key+"%' OR nama_barang LIKE '%"+key+"%'";
        this.updateTabelBarang();
    }//GEN-LAST:event_inpCariBarangKeyTyped

    private void getInputJumlah(){
        String jml = this.inpJumlah.getText();
        // cek apakah ada data yang dipilih
        if(this.isSelectedBarang()){
            // cek apakah jumlah kosong atau tidak
//            if(!jml.equals("")){
                // mengecek apakah yang diinputkan user number atau tidak
                if(text.isNumber(jml)){
                    // cek apakah jumlah kurang dari 0
                    if(this.jumlah > 0){
                        // cek apakah jumlah lebih dari stok
                        if(this.jumlah <= stok){
                            this.jumlah = Integer.parseInt(jml);
                            // mengupdate total harga
                            this.totalHarga = 0;
                            this.totalHarga = this.hargaJual * this.jumlah;

                            // menampilkan data jumlah dan total harga
                            this.inpJumlah.setText(Integer.toString(this.jumlah));
                            this.inpTotalHarga.setText(text.toMoneyCase(Integer.toString(this.totalHarga)));
                        }else{
                            Message.showWarning(this, String.format("Jumlah barang tidak boleh melebihi stok barang!"));
                            this.inpJumlah.setText(Integer.toString(this.jumlah));
                        }
                    }else{
                        Message.showWarning(this, "Jumlah barang tidak boleh kurang dari 1!");
                        this.inpJumlah.setText(Integer.toString(this.jumlah));
                    }
                }else{
                    Message.showWarning(this, "Jumlah barang harus berupa angka!");
                    this.inpJumlah.setText(Integer.toString(this.jumlah));
                }
                
//            }
//            else{
//                Message.showWarning(this, "Input jumlah barang tidak boleh kosong!");
//                this.inpJumlah.setText(Integer.toString(jumlah));
//            }
        }else{
            Message.showWarning(this, "Tidak ada data barang yang dipilih!");
        }
    }
    
    private void inpJumlahKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpJumlahKeyTyped
//        this.getInputJumlah();
    }//GEN-LAST:event_inpJumlahKeyTyped

    private void inpJumlahKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpJumlahKeyReleased
//        this.getInputJumlah();
    }//GEN-LAST:event_inpJumlahKeyReleased

    private void inpCariBarangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpCariBarangKeyReleased
        String key = this.inpCariBarang.getText();
        this.keywordBarang = "WHERE id_barang LIKE '%"+key+"%' OR nama_barang LIKE '%"+key+"%'";
        this.updateTabelBarang();
    }//GEN-LAST:event_inpCariBarangKeyReleased

    private void inpCariBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inpCariBarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inpCariBarangActionPerformed
//
    private void inpJumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inpJumlahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inpJumlahActionPerformed

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

    private void btnSimpanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSimpanMouseClicked
        DefaultTableModel modelData = (DefaultTableModel) tabelData.getModel();
        DefaultTableModel modelBarang = (DefaultTableModel) tabelDataBarang.getModel();
        boolean error = false, cocok = false;
        System.out.println("simpan");
        int tharga = 0,total = 0, idProduk, totalProduk = 0, sisaStok = 0, jumlahB = 0,thargaLama = 0, thargaBaru = 0,baris = -1;
        //        String idProdukBaru = inpIDBarang.getText();
        if (inpTanggal.getText().equals("")) {
            error = true;
            Message.showWarning(this, "Tanggal harus Di isi !");
        } else if (inpID.getText().equals("")) {
            error = true;
            Message.showWarning(this, "ID Transaksi harus Di isi !");
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
            if (tabelData.getRowCount() >= 1) { //jika tabel ada isinya
                String idBarangLama = "",idbarang = "";
                int stokLama = 0, stokBaru = 0;
                System.out.println("id barang di tabel "+this.idBarang);
                for(int i = 0; i<tabelData.getRowCount(); i++){
                    idbarang = tabelData.getValueAt(i, 4).toString();
                    System.out.println("id barang di baris "+(i+1)+" : "+ idbarang);
                    if(this.idBarang.equals(idbarang)){
                        cocok = true;
                        System.out.println("barang cocok");
                        idBarangLama = idbarang;
                        stokLama = Integer.parseInt(tabelData.getValueAt(i,7).toString());
                        thargaLama = Integer.parseInt(tabelData.getValueAt(i, 8).toString());
                    }
                }
                //mengecek jika id barang dan id supplier ada yg sama
                if (cocok){
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
                }
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
                        this.idBarang,
                        this.namaBarang,
                        this.hargaJual,
                        jumlahB,
                        this.totalHarga
                    });
                    //
                    //update tabel barang
                    System.out.println("stok tabel "+this.stok);
                    System.out.println("jumlah pesanan "+jumlahB);
                    sisaStok = this.stok - jumlahB;
                    this.stok = sisaStok;
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

    private void btnSimpanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSimpanMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSimpanMouseEntered

    private void btnSimpanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSimpanMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSimpanMouseExited

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditMouseClicked
        DefaultTableModel model = (DefaultTableModel) tabelData.getModel();
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
                this.hargaJual,
                totalHarga,
            },tabelData.getSelectedRow());
        }
    }//GEN-LAST:event_btnEditMouseClicked

    private void btnEditMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditMouseEntered

    private void btnEditMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditMouseExited

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditActionPerformed

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

    private void btnHapusMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHapusMouseEntered

    private void btnHapusMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHapusMouseExited

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnBayarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBayarMouseEntered
        //        this.btnBeli.setIcon(Gambar.getIcon("ic-pembayaran-pay-entered.png"));
    }//GEN-LAST:event_btnBayarMouseEntered

    private void btnBayarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBayarMouseExited
        //        this.btnBeli.setIcon(Gambar.getIcon("ic-pembayaran-pay.png"));
    }//GEN-LAST:event_btnBayarMouseExited

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
                        pst1.setString(2, idPetugas);
                        pst1.setInt(3, text.toIntCase(txtTotal.getText()));
                        pst1.setString(4, waktu.getCurrentDateTime());
                        if(pst1.executeUpdate()>0){
                            System.out.println("Sudah membuat Transaksi Beli");
                        }
                        //id_tr_beli,id_supplier,nama_supplier,id_barang,_nama_barang,jenis_barang,harga,jumlah,total_harga
                        pst2 = db.conn.prepareStatement("INSERT INTO detail_transaksi_beli VALUES (?, ?, ?, ?, ?)");

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
                        this.updateTabelBarang();
                        this.updateTabelData();
                        // mereset input
                        this.resetInput();
                        this.idTr = this.trj.createIDTransaksi();
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

    private void btnBatalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBatalMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBatalMouseClicked

    private void btnBatalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBatalMouseEntered
        //        this.btnBatal.setIcon(Gambar.getIcon("ic-data-hapus-entered.png"));
    }//GEN-LAST:event_btnBatalMouseEntered

    private void btnBatalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBatalMouseExited
        //        this.btnBatal.setIcon(Gambar.getIcon("ic-data-hapus.png"));
    }//GEN-LAST:event_btnBatalMouseExited

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        int status;
        Audio.play(Audio.SOUND_INFO);
        status = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin membatalkan transaksi?", "Confirm", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);
        System.out.println("status option" +status);
        switch(status){
            case JOptionPane.YES_OPTION : {
                // mereset tabel
                this.updateTabelBarang();
                this.updateTabelData();
                // mereset input
                this.resetInput();
                txtTotal.setText(text.toMoneyCase("0"));
                break;
            }
        }
    }//GEN-LAST:event_btnBatalActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background;
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnBayar;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JTextField inpCariBarang;
    private javax.swing.JLabel inpHarga;
    private javax.swing.JLabel inpID;
    private javax.swing.JLabel inpIDBarang;
    private javax.swing.JTextField inpJumlah;
    private javax.swing.JLabel inpNamaBarang;
    private javax.swing.JLabel inpNamaPetugas;
    private javax.swing.JLabel inpTanggal;
    private javax.swing.JLabel inpTotalHarga;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tabelData;
    private javax.swing.JTable tabelDataBarang;
    private javax.swing.JLabel txtTotal;
    // End of variables declaration//GEN-END:variables
}
