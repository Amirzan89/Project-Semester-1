package com.window.panels;

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
import java.sql.SQLException;
import javax.swing.JOptionPane;

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
    
    private String keywordPembeli = "", keywordBarang = "", idSelectedPembeli, idSelectedBarang;
    
    private String idTr, namaTr, namaPembeli, namaBarang, idPetugas, idPembeli, idBarang, metodeBayar, tglNow;
    
    private int jumlah = 1, hargaJual, totalHarga = 0, stok = 0;
    
    public TransaksiJual() {
        initComponents();
        
        this.idTr = this.trj.createIDTransaksi();
        
        this.inpJumlah.setEditable(false);
        this.inpID.setText("<html><p>:&nbsp;"+this.trj.createIDTransaksi()+"</p></html>");
        this.inpNamaPetugas.setText("<html><p>:&nbsp;"+this.user.getCurrentLoginName()+"</p></html>");
        
        this.btnAddJumlah.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        this.btnMinJumlah.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        this.btnBayar.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        this.btnBatal.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        
        this.tabelDataBarang.setRowHeight(29);
        this.tabelDataBarang.getTableHeader().setBackground(new java.awt.Color(255,255,255));
        this.tabelDataBarang.getTableHeader().setForeground(new java.awt.Color(0, 0, 0));
        this.tabelDataPembeli.setRowHeight(29);
        this.tabelDataPembeli.getTableHeader().setBackground(new java.awt.Color(255,255,255));
        this.tabelDataPembeli.getTableHeader().setForeground(new java.awt.Color(0, 0, 0));
        
        this.updateTabelPembeli();
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
    
    private void updateTabelPembeli(){
        this.tabelDataPembeli.setModel(new javax.swing.table.DefaultTableModel(
            getDataPembeli(),
            new String [] {
                "ID Pembeli", "Nama Pembeli", "No Telephone", "Alamat"
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
    
    private boolean isSelectedPembeli(){
        return this.tabelDataPembeli.getSelectedRow() > - 1;
    }
    
    private boolean isSelectedBarang(){
        return this.tabelDataBarang.getSelectedRow() > - 1;
    }
    
    private void showDataPembeli(){
        
        // cek akapah ada data pembeli yg dipilih
        if(this.isSelectedPembeli()){
            // mendapatkan data pembeli
            this.idPembeli = this.idSelectedPembeli;
            this.namaPembeli = this.pembeli.getNama(this.idPembeli);
            
            // menampilkan data pembeli
            this.inpNamaPembeli.setText("<html><p>:&nbsp;"+this.namaPembeli+"</p></html>");
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
        this.inpNamaPembeli.setText("<html><p>:&nbsp;</p></html>");
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
        inpNamaPembeli = new javax.swing.JLabel();
        inpNamaBarang = new javax.swing.JLabel();
        inpTotalHarga = new javax.swing.JLabel();
        inpTanggal = new javax.swing.JLabel();
        inpJumlah = new javax.swing.JTextField();
        btnBayar = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        btnAddJumlah = new javax.swing.JButton();
        btnMinJumlah = new javax.swing.JButton();
        inpCariBarang = new javax.swing.JTextField();
        inpCariPembeli = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelDataBarang = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabelDataPembeli = new javax.swing.JTable();
        background = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(957, 650));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        inpID.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpID.setForeground(new java.awt.Color(0, 0, 0));
        inpID.setText(":");
        add(inpID, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 135, 200, 55));

        inpNamaPetugas.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpNamaPetugas.setForeground(new java.awt.Color(0, 0, 0));
        inpNamaPetugas.setText(":");
        add(inpNamaPetugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 209, 200, 55));

        inpNamaPembeli.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpNamaPembeli.setForeground(new java.awt.Color(0, 0, 0));
        inpNamaPembeli.setText(":");
        add(inpNamaPembeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 284, 200, 55));

        inpNamaBarang.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpNamaBarang.setForeground(new java.awt.Color(0, 0, 0));
        inpNamaBarang.setText(":");
        add(inpNamaBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 357, 200, 55));

        inpTotalHarga.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpTotalHarga.setForeground(new java.awt.Color(0, 0, 0));
        inpTotalHarga.setText(":");
        add(inpTotalHarga, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 506, 200, 55));

        inpTanggal.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpTanggal.setForeground(new java.awt.Color(0, 0, 0));
        inpTanggal.setText(": 15 Oktober 2022 | 17:55");
        add(inpTanggal, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 580, 200, 55));

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
        add(inpJumlah, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 447, 30, -1));

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
        add(btnBayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(53, 669, 149, 48));

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
        add(btnBatal, new org.netbeans.lib.awtextra.AbsoluteConstraints(242, 669, 149, 48));

        btnAddJumlah.setBackground(new java.awt.Color(34, 119, 237));
        btnAddJumlah.setFont(new java.awt.Font("Dialog", 1, 15)); // NOI18N
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
        add(btnAddJumlah, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 446, 29, 25));

        btnMinJumlah.setBackground(new java.awt.Color(220, 41, 41));
        btnMinJumlah.setFont(new java.awt.Font("Dialog", 1, 15)); // NOI18N
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
        add(btnMinJumlah, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 444, 29, 26));

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

        inpCariPembeli.setBackground(new java.awt.Color(255, 255, 255));
        inpCariPembeli.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        inpCariPembeli.setForeground(new java.awt.Color(0, 0, 0));
        inpCariPembeli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inpCariPembeliKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                inpCariPembeliKeyTyped(evt);
            }
        });
        add(inpCariPembeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 415, 300, 25));

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

        add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 100, 500, 290));

        tabelDataPembeli.setBackground(new java.awt.Color(255, 255, 255));
        tabelDataPembeli.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        tabelDataPembeli.setForeground(new java.awt.Color(0, 0, 0));
        tabelDataPembeli.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID Pembeli", "Nama Pembeli", "Alamat"
            }
        ));
        tabelDataPembeli.setGridColor(new java.awt.Color(0, 0, 0));
        tabelDataPembeli.setSelectionBackground(new java.awt.Color(26, 164, 250));
        tabelDataPembeli.setSelectionForeground(new java.awt.Color(250, 246, 246));
        tabelDataPembeli.getTableHeader().setReorderingAllowed(false);
        tabelDataPembeli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelDataPembeliMouseClicked(evt);
            }
        });
        tabelDataPembeli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabelDataPembeliKeyPressed(evt);
            }
        });
        jScrollPane4.setViewportView(tabelDataPembeli);

        add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 440, 500, 300));

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/gambar/app-transaksi-jual-075.png"))); // NOI18N
        add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        int status;
        Audio.play(Audio.SOUND_INFO);
        status = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin membatalkan transaksi?", "Confirm", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        switch(status){
            case JOptionPane.YES_OPTION : {
                // mereset tabel
                this.updateTabelPembeli();
                this.updateTabelBarang();

                // mereset input
                this.resetInput();
                break;
            }
        }
    }//GEN-LAST:event_btnBatalActionPerformed

    private void inpCariPembeliKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpCariPembeliKeyTyped
        String key = this.inpCariPembeli.getText();
        this.keywordPembeli = "WHERE id_pembeli LIKE '%"+key+"%' OR nama_pembeli LIKE '%"+key+"%'";
        this.updateTabelPembeli();
    }//GEN-LAST:event_inpCariPembeliKeyTyped

    private void btnBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBayarActionPerformed
        this.idTr = this.trj.createIDTransaksi();
        this.idPetugas = this.user.getCurrentLogin();
        
        // cek apakah ada data pembeli yang dipilih
        if(this.tabelDataPembeli.getSelectedRow() > -1){
            // mendapatkan data pembeli
            this.idPembeli = this.tabelDataPembeli.getValueAt(this.tabelDataPembeli.getSelectedRow(), 0).toString();
            this.namaPembeli = this.pembeli.getNama(this.idPembeli);
        }else{
            Message.showWarning(this, "Tidak ada data pembeli yang dipilih!");
            return;
        }
        
        // cek apakah ada data barang yang dipilih
        if(this.tabelDataBarang.getSelectedRow() > -1){
            // mendapatkan data barang
            this.idBarang = this.tabelDataBarang.getValueAt(this.tabelDataBarang.getSelectedRow(), 0).toString();
            this.namaBarang = this.barang.getNamaBarang(this.idBarang);
            this.jumlah = Integer.parseInt(this.inpJumlah.getText());
            this.hargaJual = Integer.parseInt(this.barang.getHargaJual(this.idBarang));
            this.totalHarga = hargaJual * jumlah;
//            switch(this.inpMetode.getSelectedIndex()){
//                case 1 : this.metodeBayar = "CASH"; break;
//                case 2 : this.metodeBayar = "E-WALLET"; break;
//                default : Message.showWarning(this, "Silahkan pilih metode pembayaran terlebih dahulu!"); return;
//            }
            this.namaTr = String.format("%s membeli %s sebanyak %s dengan total harga %s", this.namaPembeli, this.namaBarang, this.jumlah, text.toMoneyCase(""+this.totalHarga));
        }else{
            Message.showWarning(this, "Tidak ada data barang yang dipilih!");
            return;
        }

        // membuka window konfirmasi pembayaran
        Audio.play(Audio.SOUND_INFO);
        KonfirmasiPembayaran konfirmasi = new KonfirmasiPembayaran(null, true, 1);
        konfirmasi.putValueJual(namaTr, idPetugas, idPembeli, idBarang, Integer.toString(this.jumlah), Integer.toString(this.totalHarga), this.waktu.getCurrentDate());
        konfirmasi.setVisible(true);
        
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        // mengecek apakah transaksi jadi menambahkan data atau tidak
        if(konfirmasi.isUpdated()){
            // mengupdate tabel
            this.updateTabelPembeli();
            this.updateTabelBarang();
            this.resetInput();
        }
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_btnBayarActionPerformed

    private void btnAddJumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddJumlahActionPerformed
        // cek apakah ada data yang dipilih
        if(this.isSelectedBarang()){
            // menambahkan jumlah
            this.jumlah++;
            // cek apakah jumlah tidak melebihi stok barang
            if(this.jumlah <= this.stok){
                // mengupdate total harga
                this.totalHarga = 0;
                this.totalHarga = this.hargaJual * this.jumlah;
                
                // menampilkan data jumlah dan total harga
                this.inpJumlah.setText(Integer.toString(this.jumlah));
                this.inpTotalHarga.setText("<html><p>:&nbsp;"+text.toMoneyCase(Integer.toString(this.totalHarga))+"</p></html>");
            }else{
                Message.showWarning(this, String.format("Jumlah barang tidak boleh melebihi stok barang!"));
            }
        }else{
            Message.showWarning(this, "Tidak ada data barang yang dipilih!");
        }
    }//GEN-LAST:event_btnAddJumlahActionPerformed

    private void btnMinJumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMinJumlahActionPerformed
        // cek apakah ada data yang dipilih
        if(this.isSelectedBarang()){
            // mengurangi jumlah
            this.jumlah--;
            // cek apakah jumlah lebih dari 0
            if(this.jumlah > 0){
                // mengupdate total harga
                this.totalHarga = 0;
                this.totalHarga = this.hargaJual * this.jumlah;
                
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

    private void btnBayarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBayarMouseEntered
//        this.btnBayar.setIcon(Gambar.getIcon("ic-pembayaran-pay-entered.png"));
    }//GEN-LAST:event_btnBayarMouseEntered

    private void btnBayarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBayarMouseExited
//        this.btnBayar.setIcon(Gambar.getIcon("ic-pembayaran-pay.png"));
    }//GEN-LAST:event_btnBayarMouseExited

    private void btnBatalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBatalMouseEntered
//        this.btnBatal.setIcon(Gambar.getIcon("ic-data-hapus-entered.png"));
    }//GEN-LAST:event_btnBatalMouseEntered

    private void btnBatalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBatalMouseExited
//        this.btnBatal.setIcon(Gambar.getIcon("ic-data-hapus.png"));
    }//GEN-LAST:event_btnBatalMouseExited

    private void btnAddJumlahMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddJumlahMouseEntered
        this.btnAddJumlah.setForeground(new Color(0, 0, 0));
    }//GEN-LAST:event_btnAddJumlahMouseEntered

    private void btnAddJumlahMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddJumlahMouseExited
        this.btnAddJumlah.setForeground(new Color(255, 255, 255));
    }//GEN-LAST:event_btnAddJumlahMouseExited

    private void btnMinJumlahMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinJumlahMouseEntered
        this.btnMinJumlah.setForeground(new Color(0, 0, 0));
    }//GEN-LAST:event_btnMinJumlahMouseEntered

    private void btnMinJumlahMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinJumlahMouseExited
        this.btnMinJumlah.setForeground(new Color(255, 255, 255));
    }//GEN-LAST:event_btnMinJumlahMouseExited

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

    private void tabelDataPembeliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelDataPembeliMouseClicked
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        // menampilkan data pembeli
        this.idSelectedPembeli = this.tabelDataPembeli.getValueAt(tabelDataPembeli.getSelectedRow(), 0).toString();
        this.showDataPembeli();
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_tabelDataPembeliMouseClicked

    private void tabelDataPembeliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelDataPembeliKeyPressed
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        if(evt.getKeyCode() == KeyEvent.VK_UP){
            this.idSelectedPembeli = this.tabelDataPembeli.getValueAt(tabelDataPembeli.getSelectedRow() - 1, 0).toString();
            this.showDataPembeli();
        }else if(evt.getKeyCode() == KeyEvent.VK_DOWN){
            this.idSelectedPembeli = this.tabelDataPembeli.getValueAt(tabelDataPembeli.getSelectedRow() + 1, 0).toString();
            this.showDataPembeli();
        }
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_tabelDataPembeliKeyPressed

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

    private void inpCariPembeliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpCariPembeliKeyReleased
        String key = this.inpCariPembeli.getText();
        this.keywordPembeli = "WHERE id_pembeli LIKE '%"+key+"%' OR nama_pembeli LIKE '%"+key+"%'";
        this.updateTabelPembeli();
    }//GEN-LAST:event_inpCariPembeliKeyReleased

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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background;
    private javax.swing.JButton btnAddJumlah;
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnBayar;
    private javax.swing.JButton btnMinJumlah;
    private javax.swing.JTextField inpCariBarang;
    private javax.swing.JTextField inpCariPembeli;
    private javax.swing.JLabel inpID;
    private javax.swing.JTextField inpJumlah;
    private javax.swing.JLabel inpNamaBarang;
    private javax.swing.JLabel inpNamaPembeli;
    private javax.swing.JLabel inpNamaPetugas;
    private javax.swing.JLabel inpTanggal;
    private javax.swing.JLabel inpTotalHarga;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable tabelDataBarang;
    private javax.swing.JTable tabelDataPembeli;
    // End of variables declaration//GEN-END:variables
}
