package com.window.panels;

import com.data.db.DatabaseTables;
import com.manage.Message;
import com.manage.Text;
import com.media.Audio;
import com.media.Gambar;
import com.sun.glass.events.KeyEvent;
import com.manage.Barang;
import com.manage.Chart;
import com.manage.Validation;
import com.window.dialogs.InputBarang;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;

import java.sql.SQLException;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Amirzan Fikri 
 */
public class DataBarang extends javax.swing.JPanel {
    
    private final Barang barang = new Barang();
    
    private final Chart chart = new Chart();
    
    private final Text text = new Text();

    private String idSelected = "", keyword = "", namaBarang, jenis, stok, hargaBeli, hargaJual, ttlPenjulan, penjMing, penghasilan;
    
    /**
     * Creates new form Dashboard
     */
    public DataBarang() {
        initComponents();
//        this.showPieChart();
        
        this.btnAdd.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        this.btnEdit.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        this.btnDel.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        
        this.tabelData.setRowHeight(29);
        this.tabelData.getTableHeader().setBackground(new java.awt.Color(255,255,255));
        this.tabelData.getTableHeader().setForeground(new java.awt.Color(0, 0, 0));
        
        JLabel[] values = {
          this.valIDBarang, this.valNamaBarang, this.valJenis, this.valStok, 
          this.valHargaBeli, this.valHargaJual, this.valPjln, this.valPjlnMinggu, this.valPenghasilan
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
        
        this.showLineChart();
        
        this.updateTabel();
    }
    
    private String gachaMinggu(){
        int max = barang.sumData(DatabaseTables.TRANSAKSI_JUAL.name(), "jumlah_brg", String.format("where id_barang = '%s'", this.idSelected));
        System.out.println("jumlah barangg "+max);
        if(max <=1){
            return "0";
        }else{
            return Integer.toString(new Random().nextInt(--max));
        }
    }
    
    private void showData(){
        // mendapatkan data
        this.namaBarang = text.toCapitalize(barang.getNamaBarang(this.idSelected));
        this.jenis = text.toCapitalize(barang.getJenis(this.idSelected));
        this.stok = barang.getStok(this.idSelected);
        this.hargaBeli = text.toMoneyCase(barang.getHargaBeli(this.idSelected));
        this.hargaJual = text.toMoneyCase(barang.getHargaJual(this.idSelected));
        this.ttlPenjulan = ""+this.barang.sumData(DatabaseTables.TRANSAKSI_JUAL.name(), "jumlah_brg", String.format("where id_barang = '%s'", this.idSelected));
        this.penghasilan = text.toMoneyCase(""+this.barang.sumData(DatabaseTables.TRANSAKSI_JUAL.name(), "total_hrg", String.format("where id_barang = '%s'", this.idSelected)));
        
        // menampilkan data
        this.valIDBarang.setText("<html><p>:&nbsp;"+idSelected+"</p></html>");
        this.valNamaBarang.setText("<html><p>:&nbsp;"+namaBarang+"</p></html>");
        this.valJenis.setText("<html><p>:&nbsp;"+jenis+"</p></html>");
        this.valStok.setText("<html><p>:&nbsp;"+stok+" Stok</p></html>");
        this.valHargaJual.setText("<html><p>:&nbsp;"+hargaJual+"</p></html>");
        this.valHargaBeli.setText("<html><p>:&nbsp;"+hargaBeli+"</p></html>");
        this.valPjln.setText("<html><p>:&nbsp;"+ttlPenjulan+" Penjualan</p></html>");
        this.valPjlnMinggu.setText("<html><p>:&nbsp;"+gachaMinggu()+" Penjualan</p></html>");
        this.valPenghasilan.setText("<html><p>:&nbsp;"+penghasilan+"</p></html>");
    }
    
    private Object[][] getData(){
        try{
            Object obj[][];
            int rows = 0;
            String sql = "SELECT id_barang, nama_barang, jenis_barang, stok, harga_beli, harga_jual FROM barang " + keyword;
            // mendefinisikan object berdasarkan total rows dan cols yang ada didalam tabel
            obj = new Object[barang.getJumlahData("barang", keyword)][6];
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
                obj[rows][5] = text.toMoneyCase(barang.res.getString("harga_jual"));
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
                "ID Barang", "Nama Barang", "Jenis Barang", "Stok", "Harga Beli", "Harga Jual"
            }
        ){
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
    }
    
    public void showLineChart(){
        //create dataset for the graph
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.setValue(200, "Amount", "Kamis");
        dataset.setValue(150, "Amount", "Jumat");
        dataset.setValue(58, "Amount", "Sabtu");
        dataset.setValue(30, "Amount", "Minggu");
        dataset.setValue(180, "Amount", "Senin");
        dataset.setValue(250, "Amount", "Selasa");
        dataset.setValue(250, "Amount", "Rabu");
        
        //create chart
        JFreeChart linechart = ChartFactory.createLineChart("Penjualan Minggu Ini","Hari","Jumlah", 
                dataset, PlotOrientation.VERTICAL, false,true,false);
        
        //create plot object
         CategoryPlot lineCategoryPlot = linechart.getCategoryPlot();
        lineCategoryPlot.setRangeGridlinePaint(Color.BLUE);
        lineCategoryPlot.setBackgroundPaint(Color.WHITE);
        
        //create render object to change the moficy the line properties like color
        LineAndShapeRenderer lineRenderer = (LineAndShapeRenderer) lineCategoryPlot.getRenderer();
        Color lineChartColor = new Color(255,2,9);
        lineRenderer.setSeriesPaint(0, lineChartColor);
        
         //create chartPanel to display chart(graph)
//        ChartPanel lineChartPanel = new ChartPanel(linechart);
//        lineChart.removeAll();
//        lineChart.add(lineChartPanel, BorderLayout.CENTER);
//        lineChart.validate();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        valIDBarang = new javax.swing.JLabel();
        valPenghasilan = new javax.swing.JLabel();
        valPjln = new javax.swing.JLabel();
        valJenis = new javax.swing.JLabel();
        valPjlnMinggu = new javax.swing.JLabel();
        valHargaJual = new javax.swing.JLabel();
        valHargaBeli = new javax.swing.JLabel();
        valStok = new javax.swing.JLabel();
        valNamaBarang = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelData = new javax.swing.JTable();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JToggleButton();
        btnDel = new javax.swing.JToggleButton();
        inpCari = new javax.swing.JTextField();
        background = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(957, 650));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        add(valIDBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 117, 190, 37));
        add(valPenghasilan, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 633, 190, 37));
        add(valPjln, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 504, 190, 37));
        add(valJenis, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 247, 190, 37));
        add(valPjlnMinggu, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 569, 190, 37));
        add(valHargaJual, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 440, 190, 37));
        add(valHargaBeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 375, 190, 37));
        add(valStok, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 311, 190, 37));
        add(valNamaBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 182, 190, 37));
        valNamaBarang.getAccessibleContext().setAccessibleName(":");

        tabelData.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        tabelData.setForeground(new java.awt.Color(0, 0, 0));
        tabelData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID Barang", "Nama Barang", "Jenis", "Stok"
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
                tablDataKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(tabelData);

        add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 100, 510, 580));

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
        add(btnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 712, 155, 47));

        btnEdit.setBackground(new java.awt.Color(34, 119, 237));
        btnEdit.setForeground(new java.awt.Color(255, 255, 255));
        btnEdit.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnEdit.setMaximumSize(new java.awt.Dimension(109, 25));
        btnEdit.setMinimumSize(new java.awt.Dimension(109, 25));
        btnEdit.setOpaque(false);
        btnEdit.setPreferredSize(new java.awt.Dimension(109, 25));
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
        add(btnEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(209, 713, 150, 47));

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
        add(btnDel, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 712, 149, 47));

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
        add(inpCari, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 76, 185, 23));

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/gambar/app-dataBarang-075.png"))); // NOI18N
        background.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents
        

    private void tabelDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelDataMouseClicked
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        // menampilkan data pembeli
        this.idSelected = this.tabelData.getValueAt(tabelData.getSelectedRow(), 0).toString();
        this.showData();
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_tabelDataMouseClicked

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // mengecek apakah ada data yang dipilih atau tidak
        if(tabelData.getSelectedRow() > -1){
            // membuka window input pembeli
            Audio.play(Audio.SOUND_INFO);
            InputBarang tbh = new InputBarang(null, true, this.idSelected);
            tbh.setVisible(true);

            this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            // mengecek apakah barang jadi mengedit data atau tidak
            if(tbh.isUpdated()){
                // mengupdate tabel dan menampilkan ulang data
                this.updateTabel();
                this.showData();
            }
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }else{
                Message.showWarning(this, "Tidak ada data yang dipilih!!", true);
            }
    }//GEN-LAST:event_btnEditActionPerformed

    private void tablDataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablDataKeyPressed
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        if(evt.getKeyCode() == KeyEvent.VK_UP){
            this.idSelected = this.tabelData.getValueAt(tabelData.getSelectedRow() - 1, 0).toString();
            this.showData();
        }else if(evt.getKeyCode() == KeyEvent.VK_DOWN){
            this.idSelected = this.tabelData.getValueAt(tabelData.getSelectedRow() + 1, 0).toString();
            this.showData();
        }
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_tablDataKeyPressed

    private void btnEditMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditMouseEntered
//        this.btnEdit.setIcon(Gambar.getIcon("ic-data-edit-entered.png"));
    }//GEN-LAST:event_btnEditMouseEntered

    private void btnEditMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditMouseExited
//        this.btnEdit.setIcon(Gambar.getIcon("ic-data-edit.png"));
    }//GEN-LAST:event_btnEditMouseExited

    private void inpCariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpCariKeyTyped
        String key = this.inpCari.getText();
        this.keyword = "WHERE id_barang LIKE '%"+key+"%' OR nama_barang LIKE '%"+key+"%'";
        this.updateTabel();
    }//GEN-LAST:event_inpCariKeyTyped

    private void inpCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpCariKeyReleased
        String key = this.inpCari.getText();
        this.keyword = "WHERE id_barang LIKE '%"+key+"%' OR nama_barang LIKE '%"+key+"%'";
        this.updateTabel();
    }//GEN-LAST:event_inpCariKeyReleased

    private void btnDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelActionPerformed
        int status;
        boolean delete;
        // mengecek apakah ada data yang dipilih atau tidak
        if(tabelData.getSelectedRow() > -1){
            // membuka confirm dialog untuk menghapus data
            Audio.play(Audio.SOUND_INFO);
            status = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus '" + this.namaBarang + "' ?", "Confirm", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);
            // mengecek pilihan dari barang
            switch(status){
                // jika yes maka data akan dihapus
                case JOptionPane.YES_OPTION :
                // menghapus data pembeli
                this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                delete = this.barang.deleteBarang(this.idSelected);
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

    private void btnDelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDelMouseExited
//        this.btnDel.setIcon(Gambar.getIcon("ic-data-hapus.png"));
    }//GEN-LAST:event_btnDelMouseExited

    private void btnDelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDelMouseEntered
//        this.btnDel.setIcon(Gambar.getIcon("ic-data-hapus-entered.png"));
    }//GEN-LAST:event_btnDelMouseEntered

    private void inpCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inpCariActionPerformed
        String key = this.inpCari.getText();
        this.keyword = "WHERE id_barang LIKE '%"+key+"%' OR nama_barang LIKE '%"+key+"%'";
        this.updateTabel();
    }//GEN-LAST:event_inpCariActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // membuka window input pembeli
        Audio.play(Audio.SOUND_INFO);
        InputBarang tbh = new InputBarang(null, true, null);
        tbh.setVisible(true);

        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        // mengecek apakah barang jadi menambahkan data atau tidak
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
//UPDATE pembukuan.barang1 SET nama_barang = 'susu' WHERE id_barang = 'BG002'
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background;
    private javax.swing.JButton btnAdd;
    private javax.swing.JToggleButton btnDel;
    private javax.swing.JToggleButton btnEdit;
    private javax.swing.JTextField inpCari;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tabelData;
    private javax.swing.JLabel valHargaBeli;
    private javax.swing.JLabel valHargaJual;
    private javax.swing.JLabel valIDBarang;
    private javax.swing.JLabel valJenis;
    private javax.swing.JLabel valNamaBarang;
    private javax.swing.JLabel valPenghasilan;
    private javax.swing.JLabel valPjln;
    private javax.swing.JLabel valPjlnMinggu;
    private javax.swing.JLabel valStok;
    // End of variables declaration//GEN-END:variables
}