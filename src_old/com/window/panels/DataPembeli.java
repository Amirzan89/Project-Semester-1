package com.window.panels;

import com.data.db.DatabaseTables;
import com.manage.Barang;
import com.manage.Chart;
import com.manage.Internet;
import com.manage.Message;
import com.manage.Text;
import com.media.Audio;
import com.media.Gambar;
import com.sun.glass.events.KeyEvent;
import com.users.Pembeli;
import com.window.dialogs.InputPembeli;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Amirzan FIkri
 */
public class DataPembeli extends javax.swing.JPanel {
    
    private final Barang barang = new Barang();
    
    private final Pembeli pembeli = new Pembeli();
    
    private final Chart chart = new Chart();
    
    private final Internet net = new Internet();
    
    private final Text text = new Text();

    private String idSelected = "", keyword = "", namaPembeli, noTelp, alamat, favorite, ttlPem, ttlUang, last;
    
    DateFormat tanggalMilis = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private final DateFormat tanggalFull = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss ");
    private final DateFormat date = new SimpleDateFormat("dd-MM-yyyy");
    private final DateFormat date1 = new SimpleDateFormat("yyyy-MM-dd");
    private final DateFormat time = new SimpleDateFormat("ss:mm:hh");
    private final DateFormat timeMillis = new SimpleDateFormat("ss.SSS:mm:hh");
    
    public DataPembeli() {
        initComponents();
//        this.showPieChart();
        
//        this.chart.showPieChart(this.pieChart, "Produk yang dibeli Achmad Baihaqi", new Font("Ebrima", 1, 20), 15, 20, 60, 0);
        
        this.btnAdd.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        this.btnEdit.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        this.btnDel.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        
        this.tabelData.setRowHeight(29);
        this.tabelData.getTableHeader().setBackground(new java.awt.Color(255,255,255));
        this.tabelData.getTableHeader().setForeground(new java.awt.Color(0, 0, 0));
        
        JLabel[] values = {
          this.valIDPembeli, this.valNamaPembeli, this.valNoTelp, this.valAlamat, 
          this.valFavorite, this.valPembelian, this.valLast, this.valUang
        };
        
        for(JLabel lbl : values){
            lbl.addMouseListener(new java.awt.event.MouseListener() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                    lbl.setForeground(new Color(15,98,230));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    lbl.setForeground(new Color(0,0,0));
                }
            });
        }
        
        this.updateTabel();
    }

    private Object[][] getData(){
        try{
            Object[][] obj;
            int rows = 0;
            String sql = "SELECT DISTINCT id_pembeli, nama_pembeli, no_telp, alamat FROM pembeli " + keyword;
            // mendefinisikan object berdasarkan total rows dan cols yang ada didalam tabel
            System.out.println(sql);
            obj = new Object[pembeli.getJumlahData("pembeli", keyword)][4];
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
    
    private void updateTabel(){
        this.tabelData.setModel(new javax.swing.table.DefaultTableModel(
            getData(),
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

    
    private void gachaPie(){
        Random rand = new Random();
        int[] values = new int[4];
        values[0] = 0; values[1] = 0; values[2] = 0;values[3] = 0;
        int persen = 0, buff;
        while(true){
            if(persen >= 100){
                break;
            }
            for (int i = 0; i < values.length; i++) {
                buff = rand.nextInt(37);
                persen += buff;
                values[i] += buff;
            }            
        }
        
//        this.chart.showPieChart(this.pieChart, "Produk yang dibeli " + this.namaPembeli, new Font("Ebrima", 1, 18), values[0], values[1], values[2], values[3]);
    }
    
    private String gachaFavorite(){
        Random rand = new Random();
        return this.barang.getNamaBarang(String.format("BG%03d", rand.nextInt(16)+1));
    }
    
    private void showData() throws ParseException{
        // mendapatkan data
        String tanggalPenuh;
        Date tanggal = new Date();
        this.namaPembeli = pembeli.getNama(this.idSelected);
        this.noTelp = text.toTelephoneCase(pembeli.getNoTelp(this.idSelected));
        this.alamat = pembeli.getAlamat(this.idSelected);
        this.ttlPem = ""+this.pembeli.getJumlahData(DatabaseTables.TRANSAKSI_JUAL.name(), "WHERE id_pembeli = '" + this.idSelected + "'");
        this.ttlUang = text.toMoneyCase("" + this.pembeli.sumData(DatabaseTables.TRANSAKSI_JUAL.name(), "total_hrg", String.format("where id_pembeli = '%s'", this.idSelected)));
        tanggalPenuh = this.pembeli.getData(DatabaseTables.TRANSAKSI_JUAL.name(), "tanggal", "WHERE id_pembeli = '" + this.idSelected + "'  ORDER BY tanggal DESC");
        this.favorite = this.gachaFavorite();
        System.out.println("tanggal "+ tanggalPenuh);
        if(tanggalPenuh.equals("null")){
            this.last = "Belum Pernah Melakukan Transaksi";
        }else{
            tanggal = tanggalMilis.parse(tanggalPenuh);
            this.last = date.format(tanggal);
        }
        
        // menampilkan data
        this.valIDPembeli.setText("<html><p>:&nbsp;"+idSelected+"</p></html>");
        this.valNamaPembeli.setText("<html><p>:&nbsp;"+namaPembeli+"</p></html>");
        this.valNoTelp.setText("<html><p style=\"text-decoration:underline; color:rgb(0,0,0);\">:&nbsp;"+noTelp+"</p></html>");
        this.valAlamat.setText("<html><p>:&nbsp;"+alamat+"</p></html>");
        this.valPembelian.setText("<html><p>:&nbsp;"+ttlPem+" Pembelian</p></html>");
        this.valUang.setText("<html><p>:&nbsp;"+ttlUang+"</p></html>");
        this.valLast.setText("<html><p>:&nbsp;"+last+"</p></html>");
        this.valFavorite.setText("<html><p>:&nbsp;"+favorite+"</p></html>");
        
        // menampilkan chart
        this.gachaPie();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        tabelData = new javax.swing.JTable();
        inpCari = new javax.swing.JTextField();
        valIDPembeli = new javax.swing.JLabel();
        valNamaPembeli = new javax.swing.JLabel();
        valNoTelp = new javax.swing.JLabel();
        valAlamat = new javax.swing.JLabel();
        valFavorite = new javax.swing.JLabel();
        valPembelian = new javax.swing.JLabel();
        valUang = new javax.swing.JLabel();
        valLast = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDel = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(957, 650));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabelData.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        tabelData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID Pembeli", "Nama Pembeli", "No Telp", "Alamat"
            }
        ));
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
        jScrollPane2.setViewportView(tabelData);

        add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 110, 500, 570));

        inpCari.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        inpCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inpCariActionPerformed(evt);
            }
        });
        inpCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inpCariKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                inpCariKeyTyped(evt);
            }
        });
        add(inpCari, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 73, 200, 35));

        valIDPembeli.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        valIDPembeli.setText(":");
        add(valIDPembeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 116, 200, 44));

        valNamaPembeli.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        valNamaPembeli.setText(":");
        add(valNamaPembeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 190, 200, 44));

        valNoTelp.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        valNoTelp.setText(":");
        valNoTelp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                valNoTelpMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                valNoTelpMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                valNoTelpMouseExited(evt);
            }
        });
        add(valNoTelp, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 263, 200, 44));

        valAlamat.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        valAlamat.setText(":");
        add(valAlamat, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 336, 200, 45));

        valFavorite.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        valFavorite.setText(":");
        add(valFavorite, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 410, 200, 44));

        valPembelian.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        valPembelian.setText(":");
        add(valPembelian, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 483, 200, 45));

        valUang.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        valUang.setText(":");
        add(valUang, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 557, 200, 44));

        valLast.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        valLast.setText(":");
        add(valLast, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 630, 200, 45));

        btnAdd.setBackground(new java.awt.Color(41, 180, 50));
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnAdd.setOpaque(false);
        btnAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAddMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAddMouseExited(evt);
            }
        });
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        add(btnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 712, 152, 47));

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
        add(btnEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 712, 150, 49));

        btnDel.setBackground(new java.awt.Color(220, 41, 41));
        btnDel.setForeground(new java.awt.Color(255, 255, 255));
        btnDel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnDel.setOpaque(false);
        btnDel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDelMouseExited(evt);
            }
        });
        btnDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelActionPerformed(evt);
            }
        });
        add(btnDel, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 712, 150, 49));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/gambar/app-dataPembeli-075.png"))); // NOI18N
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void btnDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelActionPerformed
        int status;
        boolean delete;
        
        // mengecek apakah ada data yang dipilih atau tidak
        if(tabelData.getSelectedRow() > -1){
            // membuka confirm dialog untuk menghapus data
            Audio.play(Audio.SOUND_INFO);
            status = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus '" + this.namaPembeli + "' ?", "Confirm", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);

            // mengecek pilihan dari user
            switch(status){
                // jika yes maka data akan dihapus
                case JOptionPane.YES_OPTION : 
                    // menghapus data pembeli
                    this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                    delete = this.pembeli.deletePembeli(this.idSelected);
                    // mengecek apakah data pembeli berhasil terhapus atau tidak
                    if(delete){
                        Message.showInformation(this, "Data berhasil dihapus!");
                        // mengupdate tabel
                        this.updateTabel();
                    }else{
                        Message.showInformation(this, "Data gagal dihapus!");
                    }
                    this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    break;
            }            
        }else{
            Message.showWarning(this, "Tidak ada data yang dipilih!!", true);
        }
        
    }//GEN-LAST:event_btnDelActionPerformed

    private void inpCariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpCariKeyTyped
        String key = this.inpCari.getText();
        this.keyword = "WHERE id_pembeli LIKE '%"+key+"%' OR nama_pembeli LIKE '%"+key+"%'";
        this.updateTabel();
    }//GEN-LAST:event_inpCariKeyTyped

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // mengecek apakah ada data yang dipilih atau tidak
        if(tabelData.getSelectedRow() > -1){
            // membuka window input pembeli
            Audio.play(Audio.SOUND_INFO);
            InputPembeli tbh = new InputPembeli(null, true, this.idSelected);
            tbh.setVisible(true);

            this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            // mengecek apakah user jadi mengedit data atau tidak
            if(tbh.isUpdated()){
                try {
                    // mengupdate tabel dan menampilkan ulang data
                    this.updateTabel();
                    this.showData();
                } catch (ParseException ex) {
                    Logger.getLogger(DataPembeli.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }else{
                Message.showWarning(this, "Tidak ada data yang dipilih!!", true);
            }  
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnEditMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditMouseEntered
//        this.btnEdit.setIcon(Gambar.getIcon("ic-data-edit-entered.png"));
    }//GEN-LAST:event_btnEditMouseEntered

    private void btnEditMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditMouseExited
//        this.btnEdit.setIcon(Gambar.getIcon("ic-data-edit.png"));
    }//GEN-LAST:event_btnEditMouseExited

    private void btnDelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDelMouseEntered
//        this.btnDel.setIcon(Gambar.getIcon("ic-data-hapus-entered.png"));
    }//GEN-LAST:event_btnDelMouseEntered

    private void btnDelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDelMouseExited
//        this.btnDel.setIcon(Gambar.getIcon("ic-data-hapus.png"));
    }//GEN-LAST:event_btnDelMouseExited

    private void tabelDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelDataMouseClicked
        try {
            this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            // menampilkan data pembeli
            this.idSelected = this.tabelData.getValueAt(tabelData.getSelectedRow(), 0).toString();
            this.showData();
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } catch (ParseException ex) {
            Logger.getLogger(DataPembeli.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tabelDataMouseClicked

    private void tabelDataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelDataKeyPressed
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        if(evt.getKeyCode() == KeyEvent.VK_UP){
            try {
                this.idSelected = this.tabelData.getValueAt(tabelData.getSelectedRow() - 1, 0).toString();
                this.showData();
            } catch (ParseException ex) {
                Logger.getLogger(DataPembeli.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(evt.getKeyCode() == KeyEvent.VK_DOWN){
            try {
                this.idSelected = this.tabelData.getValueAt(tabelData.getSelectedRow() + 1, 0).toString();
                this.showData();
            } catch (ParseException ex) {
                Logger.getLogger(DataPembeli.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_tabelDataKeyPressed

    private void valNoTelpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_valNoTelpMouseClicked
        String nomor = this.noTelp.substring(1).replaceAll(" ", "").replaceAll("-", "");
        if(net.isConnectInternet()){
            try {
                net.openLink("https://wa.me/"+nomor);
            } catch (IOException | URISyntaxException ex) {
                Message.showException(this, ex, true);
            }
        }else{
            Message.showWarning(this, "Tidak terhubung ke Internet!", true);
        }
    }//GEN-LAST:event_valNoTelpMouseClicked

    private void valNoTelpMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_valNoTelpMouseEntered
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.valNoTelp.setText("<html><p style=\"text-decoration:underline; color:rgb(15,98,230);\">:&nbsp;"+noTelp+"</p></html>");
    }//GEN-LAST:event_valNoTelpMouseEntered

    private void valNoTelpMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_valNoTelpMouseExited
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        this.valNoTelp.setText("<html><p style=\"text-decoration:underline; color:rgb(0,0,0);\">:&nbsp;"+noTelp+"</p></html>");
    }//GEN-LAST:event_valNoTelpMouseExited

    private void inpCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inpCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inpCariActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // membuka window input pembeli
        Audio.play(Audio.SOUND_INFO);
        InputPembeli tbh = new InputPembeli(null, true, null);
        tbh.setVisible(true);

        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        // mengecek apakah user jadi menambahkan data atau tidak
        if(tbh.isUpdated()){
            // mengupdate tabel
            this.updateTabel();
            this.tabelData.setRowSelectionInterval(this.tabelData.getRowCount()-1, this.tabelData.getRowCount()-1);
        }
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnAddMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddMouseExited
//        this.btnAdd.setIcon(Gambar.getIcon("ic-data-tambah.png"));
    }//GEN-LAST:event_btnAddMouseExited

    private void btnAddMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddMouseEntered
//        this.btnAdd.setIcon(Gambar.getIcon("ic-data-tambah-entered.png"));
    }//GEN-LAST:event_btnAddMouseEntered

    private void inpCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpCariKeyReleased
        String key = this.inpCari.getText();
        this.keyword = "WHERE id_pembeli LIKE '%"+key+"%' OR nama_pembeli LIKE '%"+key+"%'";
        this.updateTabel();
    }//GEN-LAST:event_inpCariKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDel;
    private javax.swing.JButton btnEdit;
    private javax.swing.JTextField inpCari;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tabelData;
    private javax.swing.JLabel valAlamat;
    private javax.swing.JLabel valFavorite;
    private javax.swing.JLabel valIDPembeli;
    private javax.swing.JLabel valLast;
    private javax.swing.JLabel valNamaPembeli;
    private javax.swing.JLabel valNoTelp;
    private javax.swing.JLabel valPembelian;
    private javax.swing.JLabel valUang;
    // End of variables declaration//GEN-END:variables
}
