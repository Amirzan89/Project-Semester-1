package com.window.panels;

import com.manage.Barang;
import com.manage.ManageTransaksiJual;
import com.manage.Message;
import com.manage.Text;
import com.manage.Waktu;
import com.media.Audio;
import com.media.Gambar;
import com.sun.glass.events.KeyEvent;
import com.users.Pembeli;
import com.users.Petugas;
import com.window.dialogs.CetakLaporan;
import java.awt.BorderLayout;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
//import java.awt.Color;
//import java.awt.Cursor;
//import java.awt.event.MouseEvent;
import java.sql.SQLException;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JTabbedPane;
//import javax.swing.event.ChangeEvent;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author Gemastik Lightning
 */
public class LaporanJual extends javax.swing.JPanel {
    
    private final ManageTransaksiJual trj = new ManageTransaksiJual();
    
    private final Petugas petugas = new Petugas();
    
    private final Pembeli pembeli = new Pembeli();
    
    private final Barang barang = new Barang();
    
    private final Text text = new Text();
    
    private final Waktu waktu = new Waktu();
    
    private int selectedIndex;
    private String idSelected = "", keyword = "", idTr, idPd, idBg, namaPembeli, namaPetugas, namaBarang, jenis, jumlah, totalHrg, tanggal;
    public LaporanJual() {
        initComponents();
        tabPendapatan.addChangeListener(new javax.swing.event.ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                JTabbedPane tabbedPane = (JTabbedPane) e.getSource();
        selectedIndex = tabbedPane.getSelectedIndex()+1;
//        JOptionPane.showMessageDialog(null, "Selected Index: " + selectedIndex);
            }
        });
//        this.btnPrint.setUI(new javax.swing.plaf.basic.BasicButtonUI());
//        this.btnEdit.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        this.btnDel.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        
        this.tabelDataS.setRowHeight(29);
        this.tabelDataS.getTableHeader().setBackground(new java.awt.Color(255,255,255));
        this.tabelDataS.getTableHeader().setForeground(new java.awt.Color(0, 0, 0));
        
        JLabel[] lbls = new JLabel[]{
            this.valIDPendapatan, this.valIDTransaksi, this.valNamaBarang, this.valNamaKaryawan, this.valNamaPembeli, 
            this.valJenisBarang, this.valJumlah, this.valTanggal, this.valTotalHarga
        };
        
        for(JLabel lbl : lbls){
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
//        String selected = lpSemua.ge
//        this.showLineChart1();
//        this.showPieChart();
        System.out.println(waktu.getTanggal());
        System.out.println("bulan "+ waktu.getBulan());
        System.out.println("rahun "+ waktu.getTahun());
        this.updateTabel(this.tabelDataS);
        keyword = "WHERE DAY(tanggal) = DAY(CURRENT_DATE()) AND MONTH(tanggal) = MONTH(CURRENT_DATE()) AND YEAR(tanggal) = YEAR(CURRENT_DATE())";
        this.updateTabel(this.tabelDataH);
        
        this.updateTabel(this.tabelDataM);
        keyword = "WHERE MONTH(tanggal) = MONTH(CURRENT_DATE()) AND YEAR(tanggal) = YEAR(CURRENT_DATE())";
        this.updateTabel(this.tabelDataB);
        keyword = "WHERE YEAR(tanggal) = YEAR(CURRENT_DATE())";
//        this.updateTabel(this.tabelDataT);
    }
    private void hitungMinggu(int awal, int akhir){
//        waktu
//        if(awal == waktu. && akhir == waktu.)
    }
    public void showPieChart(){
        
        //create dataset
      DefaultPieDataset barDataset = new DefaultPieDataset( );
      barDataset.setValue( "Nabati Wafer" , new Double( 18 ) );  
      barDataset.setValue( "Aqua 500ml" , new Double( 15 ) );   
      barDataset.setValue( "Coca Cola" , new Double( 14 ) );    
      barDataset.setValue( "Oreo" , new Double( 12 ) );  
      barDataset.setValue( "Ichi Ocha 350ml" , new Double( 10 ) );  
      barDataset.setValue( "Aqua 1L" , new Double( 10 ) );   
      barDataset.setValue( "Pulpen Snowman" , new Double( 8 ) );    
      barDataset.setValue( "Teh Pucuk" , new Double( 7 ) );    
      barDataset.setValue( "Lainya" , new Double( 6 ) );  
      
      //create chart
      JFreeChart piechart = ChartFactory.createPieChart("Penjualan Produk",barDataset, false,true,false);//explain
      piechart.setTitle(new TextTitle("Produk Terlaris", new java.awt.Font("Ebrima", 1, 22)));
      
        PiePlot piePlot =(PiePlot) piechart.getPlot();
      
       //changing pie chart blocks colors
       piePlot.setSectionPaint("Nabati Wafer", new Color(226,226,0));
       piePlot.setSectionPaint("Aqua 500ml", new Color(52,200,38));
       piePlot.setSectionPaint("Coca Cola", new Color(255,43,237));
       piePlot.setSectionPaint("Oreo", new Color(49,165,192));
       piePlot.setSectionPaint("Ichi Ocha 350ml", new Color(255,0,153));
       piePlot.setSectionPaint("Aqua 1L", new Color(51,255,0));
       piePlot.setSectionPaint("Pulpen Snowman", new Color(255,102,51));
       piePlot.setSectionPaint("Teh Pucuk", new Color(51,0,204));
       piePlot.setSectionPaint("Lainya", new Color(0,102,102));
      
       
        piePlot.setBackgroundPaint(Color.white);
        
        //create chartPanel to display chart(graph)
//        ChartPanel barChartPanel = new ChartPanel(piechart);
//        paneProduk.removeAll();
//        paneProduk.add(barChartPanel, BorderLayout.CENTER);
//        paneProduk.validate();
    }

    public void showLineChart1(){
        //create dataset for the graph
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.setValue(120, "Amount", "Minggu 1");
        dataset.setValue(70, "Amount", "Minggu 2");
        dataset.setValue(60, "Amount", "Minggu 3");
        dataset.setValue(120, "Amount", "Minggu 4");
        dataset.setValue(120, "Amount", "Minggu 5");
        
        //create chart
        JFreeChart linechart = ChartFactory.createLineChart("Pendapatan Bulan Ini","Hari","Jumlah", 
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
//        tabGrafikPendapatan.removeAll();
//        tabGrafikPendapatan.add(lineChartPanel, BorderLayout.CENTER);
//        tabGrafikPendapatan.validate();
    }
    private Object[][] getData(){
        try{
            Object obj[][];
            int rows = 0;
            String sql = "SELECT id_tr_jual, id_barang, jumlah_brg, total_hrg, tanggal FROM transaksi_jual " + keyword + " ORDER BY id_tr_jual DESC";
            // mendefinisikan object berdasarkan total rows dan cols yang ada didalam tabel
            obj = new Object[trj.getJumlahData("transaksi_jual", keyword)][5];
            // mengeksekusi query
            trj.res = trj.stat.executeQuery(sql);
            // mendapatkan semua data yang ada didalam tabel
            while(trj.res.next()){
                // menyimpan data dari tabel ke object
                obj[rows][0] = trj.res.getString("id_tr_jual").replace("TRJ", "LPD");
                obj[rows][1] = barang.getNamaBarang(trj.res.getString("id_barang"));
                obj[rows][2] = trj.res.getString("jumlah_brg");
                obj[rows][3] = text.toMoneyCase(trj.res.getString("total_hrg"));
                obj[rows][4] = text.toDateCase(trj.res.getString("tanggal"));
                rows++; // rows akan bertambah 1 setiap selesai membaca 1 row pada tabel
            }
            return obj;
        }catch(SQLException ex){
            ex.printStackTrace();
            Message.showException(this, "Terjadi kesalahan saat mengambil data dari database\n" + ex.getMessage(), ex, true);
        }
        return null;
    }
    
    private void updateTabel(JTable tabel){
        tabel.setModel(new javax.swing.table.DefaultTableModel(
            getData(),
            new String [] {
                "ID Pendapatan", "Nama Barang", "Jumlah Barang", "Total Pendapatan", "Tanggal"
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
    
    private void showData(JTable tabel){
        // mendapatkan data-data
        this.idTr = tabel.getValueAt(tabel.getSelectedRow(), 0).toString().replace("LPD", "TRJ");
        this.idPd = this.idTr.replace("TRJ", "LPD");
        this.namaPembeli = this.pembeli.getNama(this.trj.getIdPembeli(this.idTr));
        this.namaPetugas = this.petugas.getNama(this.trj.getIdPetugas(this.idTr));
        this.namaBarang = this.barang.getNamaBarang(this.trj.getIdBarang(this.idTr));
        this.jenis = text.toCapitalize(this.barang.getJenis(this.trj.getIdBarang(this.idTr)));
        this.jumlah = Integer.toString(this.trj.getJumlahBarang(this.idTr));
        this.totalHrg = text.toMoneyCase(this.trj.getTotalHarga(this.idTr));
        this.tanggal = this.text.toDateCase(this.trj.getTanggal(this.idTr));
        
        // menampilkan data-data
        this.valIDTransaksi.setText("<html><p>:&nbsp;"+this.idTr+"</p></html>");
        this.valIDPendapatan.setText("<html><p>:&nbsp;"+this.idPd+"</p></html>");
        this.valNamaPembeli.setText("<html><p>:&nbsp;"+this.namaPembeli+"</p></html>");
        this.valNamaKaryawan.setText("<html><p>:&nbsp;"+this.namaPetugas+"</p></html>");
        this.valNamaBarang.setText("<html><p>:&nbsp;"+this.namaBarang+"</p></html>");
        this.valJumlah.setText("<html><p>:&nbsp;"+this.jumlah+"</p></html>");
        this.valJenisBarang.setText("<html><p>:&nbsp;"+this.jenis+"</p></html>");
        this.valTotalHarga.setText("<html><p>:&nbsp;"+this.totalHrg+"</p></html>");
        this.valTanggal.setText("<html><p>:&nbsp;"+this.tanggal+"</p></html>");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        valIDPendapatan = new javax.swing.JLabel();
        valIDTransaksi = new javax.swing.JLabel();
        valNamaKaryawan = new javax.swing.JLabel();
        valNamaPembeli = new javax.swing.JLabel();
        valNamaBarang = new javax.swing.JLabel();
        valJenisBarang = new javax.swing.JLabel();
        valJumlah = new javax.swing.JLabel();
        valTotalHarga = new javax.swing.JLabel();
        valTanggal = new javax.swing.JLabel();
        inpCari = new javax.swing.JTextField();
        btnDel = new javax.swing.JButton();
        tabPendapatan = new javax.swing.JTabbedPane();
        lpSemua = new javax.swing.JScrollPane();
        tabelDataS = new javax.swing.JTable();
        lpHarian = new javax.swing.JScrollPane();
        tabelDataH = new javax.swing.JTable();
        lpMingguan = new javax.swing.JScrollPane();
        tabelDataM = new javax.swing.JTable();
        lpBulanan = new javax.swing.JScrollPane();
        tabelDataB = new javax.swing.JTable();
        bacground = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(957, 650));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        valIDPendapatan.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        valIDPendapatan.setForeground(new java.awt.Color(0, 0, 0));
        valIDPendapatan.setText(":");
        add(valIDPendapatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 98, 200, 37));

        valIDTransaksi.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        valIDTransaksi.setForeground(new java.awt.Color(0, 0, 0));
        valIDTransaksi.setText(":");
        add(valIDTransaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 162, 200, 37));

        valNamaKaryawan.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        valNamaKaryawan.setForeground(new java.awt.Color(0, 0, 0));
        valNamaKaryawan.setText(":");
        add(valNamaKaryawan, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 227, 200, 37));

        valNamaPembeli.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        valNamaPembeli.setForeground(new java.awt.Color(0, 0, 0));
        valNamaPembeli.setText(":");
        add(valNamaPembeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 292, 200, 37));

        valNamaBarang.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        valNamaBarang.setForeground(new java.awt.Color(0, 0, 0));
        valNamaBarang.setText(":");
        add(valNamaBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 356, 200, 37));

        valJenisBarang.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        valJenisBarang.setForeground(new java.awt.Color(0, 0, 0));
        valJenisBarang.setText(":");
        add(valJenisBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 420, 200, 37));

        valJumlah.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        valJumlah.setForeground(new java.awt.Color(0, 0, 0));
        valJumlah.setText(":");
        add(valJumlah, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 485, 200, 37));

        valTotalHarga.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        valTotalHarga.setForeground(new java.awt.Color(0, 0, 0));
        valTotalHarga.setText(":");
        add(valTotalHarga, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 550, 200, 36));

        valTanggal.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        valTanggal.setForeground(new java.awt.Color(0, 0, 0));
        valTanggal.setText(":");
        add(valTanggal, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 614, 200, 37));

        inpCari.setBackground(new java.awt.Color(255, 255, 255));
        inpCari.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        inpCari.setForeground(new java.awt.Color(0, 0, 0));
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
        add(inpCari, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 66, 255, -1));

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
        add(btnDel, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 714, 149, 47));

        lpSemua.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lpSemuaMouseClicked(evt);
            }
        });

        tabelDataS.setBackground(new java.awt.Color(255, 255, 255));
        tabelDataS.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        tabelDataS.setForeground(new java.awt.Color(0, 0, 0));
        tabelDataS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"PD0001", "Teh Pucuk", "3", "Rp. 9.000.00", "01 Oktober 2022"},
                {"PD0002", "Aqua 500ml", "2", "Rp. 10.000.00", "01 Oktober 2022"},
                {"PD0003", "Indomilk", "1", "Rp. 8.000.00", "01 Oktober 2022"},
                {"PD0004", "Nabati Wafer", "5", "Rp. 17.500.00", "01 Oktober 2022"},
                {"PD0005", "Coca Cola", "4", "Rp. 16.000.00", "01 Oktober 2022"},
                {"PD0006", "Oreo", "4", "Rp. 8.000.00", "01 Oktober 2022"},
                {"PD0007", "Teh Pucuk", "2", "Rp. 8.000.00", "01 Oktober 2022"},
                {"PD0008", "Coca Cola", "1", "Rp. 4.000.00", "02 Oktober 2022"},
                {"PD0009", "Oreo", "6", "Rp. 12.000.00", "02 Oktober 2022"},
                {"PD0010", "Ichi Ocha 350ml", "1", "Rp. 2.000.00", "02 Oktober 2022"},
                {"PD0011", "Aqua 500ml", "1", "Rp. 5.000.00", "02 Oktober 2022"},
                {"PD0012", "Coca Cola", "1", "Rp. 4.000.00", "02 Oktober 2022"},
                {"PD0013", "Spidol Merah", "2", "Rp. 5.000.00", "02 Oktober 2022"},
                {"PD0014", "Kertas Folio", "50", "Rp. 12.500.00", "03 Oktober 2022"},
                {"PD0015", "Yupi", "12", "Rp. 30.000.00", "03 Oktober 2022"},
                {"PD0016", "Nabati Wafer", "8", "Rp. 28.000.00", "03 Oktober 2022"},
                {"PD0017", "Nabati Wafer", "3", "Rp. 10.500.00", "03 Oktober 2022"},
                {"PD0018", "Coca Cola", "1", "Rp. 4.000.00", "03 Oktober 2022"},
                {"PD0019", "Ichi Ocha 350ml", "1", "Rp. 2.000.00", "03 Oktober 2022"},
                {"PD0020", "Indomilk", "1", "Rp. 8.000.00", "04 Oktober 2022"},
                {"PD0021", "Aqua 500ml", "3", "Rp. 15.000.00", "04 Oktober 2022"},
                {"PD0022", "Teh Pucuk", "2", "Rp. 8.000.00", "04 Oktober 2022"},
                {"PD0023", "Indomilk", "4", "Rp. 36.000.00", "04 Oktober 2022"}
            },
            new String [] {
                "ID Pendapatan", "Nama Barang", "Jumlah Barang", "Total Pendapatan", "Tanggal"
            }
        ));
        tabelDataS.setGridColor(new java.awt.Color(0, 0, 0));
        tabelDataS.setSelectionBackground(new java.awt.Color(26, 164, 250));
        tabelDataS.setSelectionForeground(new java.awt.Color(250, 246, 246));
        tabelDataS.getTableHeader().setReorderingAllowed(false);
        tabelDataS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelDataSMouseClicked(evt);
            }
        });
        tabelDataS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabelDataSKeyPressed(evt);
            }
        });
        lpSemua.setViewportView(tabelDataS);

        tabPendapatan.addTab("Semua", lpSemua);

        tabelDataH.setBackground(new java.awt.Color(255, 255, 255));
        tabelDataH.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        tabelDataH.setForeground(new java.awt.Color(0, 0, 0));
        tabelDataH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"PD0001", "Teh Pucuk", "3", "Rp. 9.000.00", "01 Oktober 2022"},
                {"PD0002", "Aqua 500ml", "2", "Rp. 10.000.00", "01 Oktober 2022"},
                {"PD0003", "Indomilk", "1", "Rp. 8.000.00", "01 Oktober 2022"},
                {"PD0004", "Nabati Wafer", "5", "Rp. 17.500.00", "01 Oktober 2022"},
                {"PD0005", "Coca Cola", "4", "Rp. 16.000.00", "01 Oktober 2022"},
                {"PD0006", "Oreo", "4", "Rp. 8.000.00", "01 Oktober 2022"},
                {"PD0007", "Teh Pucuk", "2", "Rp. 8.000.00", "01 Oktober 2022"},
                {"PD0008", "Coca Cola", "1", "Rp. 4.000.00", "02 Oktober 2022"},
                {"PD0009", "Oreo", "6", "Rp. 12.000.00", "02 Oktober 2022"},
                {"PD0010", "Ichi Ocha 350ml", "1", "Rp. 2.000.00", "02 Oktober 2022"},
                {"PD0011", "Aqua 500ml", "1", "Rp. 5.000.00", "02 Oktober 2022"},
                {"PD0012", "Coca Cola", "1", "Rp. 4.000.00", "02 Oktober 2022"},
                {"PD0013", "Spidol Merah", "2", "Rp. 5.000.00", "02 Oktober 2022"},
                {"PD0014", "Kertas Folio", "50", "Rp. 12.500.00", "03 Oktober 2022"},
                {"PD0015", "Yupi", "12", "Rp. 30.000.00", "03 Oktober 2022"},
                {"PD0016", "Nabati Wafer", "8", "Rp. 28.000.00", "03 Oktober 2022"},
                {"PD0017", "Nabati Wafer", "3", "Rp. 10.500.00", "03 Oktober 2022"},
                {"PD0018", "Coca Cola", "1", "Rp. 4.000.00", "03 Oktober 2022"},
                {"PD0019", "Ichi Ocha 350ml", "1", "Rp. 2.000.00", "03 Oktober 2022"},
                {"PD0020", "Indomilk", "1", "Rp. 8.000.00", "04 Oktober 2022"},
                {"PD0021", "Aqua 500ml", "3", "Rp. 15.000.00", "04 Oktober 2022"},
                {"PD0022", "Teh Pucuk", "2", "Rp. 8.000.00", "04 Oktober 2022"},
                {"PD0023", "Indomilk", "4", "Rp. 36.000.00", "04 Oktober 2022"}
            },
            new String [] {
                "ID Pendapatan", "Nama Barang", "Jumlah Barang", "Total Pendapatan", "Tanggal"
            }
        ));
        tabelDataH.setGridColor(new java.awt.Color(0, 0, 0));
        tabelDataH.setSelectionBackground(new java.awt.Color(26, 164, 250));
        tabelDataH.setSelectionForeground(new java.awt.Color(250, 246, 246));
        tabelDataH.getTableHeader().setReorderingAllowed(false);
        tabelDataH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelDataHMouseClicked(evt);
            }
        });
        tabelDataH.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabelDataHKeyPressed(evt);
            }
        });
        lpHarian.setViewportView(tabelDataH);

        tabPendapatan.addTab("Harian", lpHarian);

        tabelDataM.setBackground(new java.awt.Color(255, 255, 255));
        tabelDataM.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        tabelDataM.setForeground(new java.awt.Color(0, 0, 0));
        tabelDataM.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"PD0001", "Teh Pucuk", "3", "Rp. 9.000.00", "01 Oktober 2022"},
                {"PD0002", "Aqua 500ml", "2", "Rp. 10.000.00", "01 Oktober 2022"},
                {"PD0003", "Indomilk", "1", "Rp. 8.000.00", "01 Oktober 2022"},
                {"PD0004", "Nabati Wafer", "5", "Rp. 17.500.00", "01 Oktober 2022"},
                {"PD0005", "Coca Cola", "4", "Rp. 16.000.00", "01 Oktober 2022"},
                {"PD0006", "Oreo", "4", "Rp. 8.000.00", "01 Oktober 2022"},
                {"PD0007", "Teh Pucuk", "2", "Rp. 8.000.00", "01 Oktober 2022"},
                {"PD0008", "Coca Cola", "1", "Rp. 4.000.00", "02 Oktober 2022"},
                {"PD0009", "Oreo", "6", "Rp. 12.000.00", "02 Oktober 2022"},
                {"PD0010", "Ichi Ocha 350ml", "1", "Rp. 2.000.00", "02 Oktober 2022"},
                {"PD0011", "Aqua 500ml", "1", "Rp. 5.000.00", "02 Oktober 2022"},
                {"PD0012", "Coca Cola", "1", "Rp. 4.000.00", "02 Oktober 2022"},
                {"PD0013", "Spidol Merah", "2", "Rp. 5.000.00", "02 Oktober 2022"},
                {"PD0014", "Kertas Folio", "50", "Rp. 12.500.00", "03 Oktober 2022"},
                {"PD0015", "Yupi", "12", "Rp. 30.000.00", "03 Oktober 2022"},
                {"PD0016", "Nabati Wafer", "8", "Rp. 28.000.00", "03 Oktober 2022"},
                {"PD0017", "Nabati Wafer", "3", "Rp. 10.500.00", "03 Oktober 2022"},
                {"PD0018", "Coca Cola", "1", "Rp. 4.000.00", "03 Oktober 2022"},
                {"PD0019", "Ichi Ocha 350ml", "1", "Rp. 2.000.00", "03 Oktober 2022"},
                {"PD0020", "Indomilk", "1", "Rp. 8.000.00", "04 Oktober 2022"},
                {"PD0021", "Aqua 500ml", "3", "Rp. 15.000.00", "04 Oktober 2022"},
                {"PD0022", "Teh Pucuk", "2", "Rp. 8.000.00", "04 Oktober 2022"},
                {"PD0023", "Indomilk", "4", "Rp. 36.000.00", "04 Oktober 2022"}
            },
            new String [] {
                "ID Pendapatan", "Nama Barang", "Jumlah Barang", "Total Pendapatan", "Tanggal"
            }
        ));
        tabelDataM.setGridColor(new java.awt.Color(0, 0, 0));
        tabelDataM.setSelectionBackground(new java.awt.Color(26, 164, 250));
        tabelDataM.setSelectionForeground(new java.awt.Color(250, 246, 246));
        tabelDataM.getTableHeader().setReorderingAllowed(false);
        tabelDataM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelDataMMouseClicked(evt);
            }
        });
        tabelDataM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabelDataMKeyPressed(evt);
            }
        });
        lpMingguan.setViewportView(tabelDataM);

        tabPendapatan.addTab("Mingguan", lpMingguan);

        tabelDataB.setBackground(new java.awt.Color(255, 255, 255));
        tabelDataB.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        tabelDataB.setForeground(new java.awt.Color(0, 0, 0));
        tabelDataB.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"PD0001", "Teh Pucuk", "3", "Rp. 9.000.00", "01 Oktober 2022"},
                {"PD0002", "Aqua 500ml", "2", "Rp. 10.000.00", "01 Oktober 2022"},
                {"PD0003", "Indomilk", "1", "Rp. 8.000.00", "01 Oktober 2022"},
                {"PD0004", "Nabati Wafer", "5", "Rp. 17.500.00", "01 Oktober 2022"},
                {"PD0005", "Coca Cola", "4", "Rp. 16.000.00", "01 Oktober 2022"},
                {"PD0006", "Oreo", "4", "Rp. 8.000.00", "01 Oktober 2022"},
                {"PD0007", "Teh Pucuk", "2", "Rp. 8.000.00", "01 Oktober 2022"},
                {"PD0008", "Coca Cola", "1", "Rp. 4.000.00", "02 Oktober 2022"},
                {"PD0009", "Oreo", "6", "Rp. 12.000.00", "02 Oktober 2022"},
                {"PD0010", "Ichi Ocha 350ml", "1", "Rp. 2.000.00", "02 Oktober 2022"},
                {"PD0011", "Aqua 500ml", "1", "Rp. 5.000.00", "02 Oktober 2022"},
                {"PD0012", "Coca Cola", "1", "Rp. 4.000.00", "02 Oktober 2022"},
                {"PD0013", "Spidol Merah", "2", "Rp. 5.000.00", "02 Oktober 2022"},
                {"PD0014", "Kertas Folio", "50", "Rp. 12.500.00", "03 Oktober 2022"},
                {"PD0015", "Yupi", "12", "Rp. 30.000.00", "03 Oktober 2022"},
                {"PD0016", "Nabati Wafer", "8", "Rp. 28.000.00", "03 Oktober 2022"},
                {"PD0017", "Nabati Wafer", "3", "Rp. 10.500.00", "03 Oktober 2022"},
                {"PD0018", "Coca Cola", "1", "Rp. 4.000.00", "03 Oktober 2022"},
                {"PD0019", "Ichi Ocha 350ml", "1", "Rp. 2.000.00", "03 Oktober 2022"},
                {"PD0020", "Indomilk", "1", "Rp. 8.000.00", "04 Oktober 2022"},
                {"PD0021", "Aqua 500ml", "3", "Rp. 15.000.00", "04 Oktober 2022"},
                {"PD0022", "Teh Pucuk", "2", "Rp. 8.000.00", "04 Oktober 2022"},
                {"PD0023", "Indomilk", "4", "Rp. 36.000.00", "04 Oktober 2022"}
            },
            new String [] {
                "ID Pendapatan", "Nama Barang", "Jumlah Barang", "Total Pendapatan", "Tanggal"
            }
        ));
        tabelDataB.setGridColor(new java.awt.Color(0, 0, 0));
        tabelDataB.setSelectionBackground(new java.awt.Color(26, 164, 250));
        tabelDataB.setSelectionForeground(new java.awt.Color(250, 246, 246));
        tabelDataB.getTableHeader().setReorderingAllowed(false);
        tabelDataB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelDataBMouseClicked(evt);
            }
        });
        tabelDataB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabelDataBKeyPressed(evt);
            }
        });
        lpBulanan.setViewportView(tabelDataB);

        tabPendapatan.addTab("Bulanan", lpBulanan);

        add(tabPendapatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 90, 505, 600));

        bacground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/gambar/app-laporan-pemasukan-075.png"))); // NOI18N
        add(bacground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void btnDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelActionPerformed
        int status;
        boolean delete;
        // mengecek apakah ada data yang dipilih atau tidak
        if(tabelDataS.getSelectedRow() > -1){
            // membuka confirm dialog untuk menghapus data
            Audio.play(Audio.SOUND_INFO);
            status = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus '" + this.idTr + "' ?", "Confirm", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);
            // mengecek pilihan dari barang
            switch(status){
                // jika yes maka data akan dihapus
                case JOptionPane.YES_OPTION : 
                    // menghapus data pembeli
                    this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
//                    switch(selectedIndex){
//                        case 1:
//                            
//                    }
                    delete = this.trj.deleteTransaksiJual(this.idTr);
                    // mengecek apakah data pembeli berhasil terhapus atau tidak
                    if(delete){
                        Message.showInformation(this, "Data berhasil dihapus!");
                        // mengupdate tabel
                        switch(selectedIndex){
                            case 1:
                                this.updateTabel(this.tabelDataS);
                                break;
                            case 2:
                                this.updateTabel(this.tabelDataH);
                                break;
                            case 3:
                                this.updateTabel(this.tabelDataM);
                                break;
                            case 4:
                                this.updateTabel(this.tabelDataB);
                                break;
                        }
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

    private void btnDelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDelMouseEntered
//        this.btnDel.setIcon(Gambar.getIcon("ic-data-hapus-entered.png"));
    }//GEN-LAST:event_btnDelMouseEntered

    private void btnDelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDelMouseExited
//        this.btnDel.setIcon(Gambar.getIcon("ic-data-hapus.png"));
    }//GEN-LAST:event_btnDelMouseExited

    private void tabelDataSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelDataSMouseClicked
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        // menampilkan data pembeli
//        switch(selectedIndex){
//            case 1:
//                this.idSelected = this.tabelDataS.getValueAt(tabelDataS.getSelectedRow(), 0).toString();
//                this.showData(this.tabelDataS);
//                break;
//            case 2:
//                this.idSelected = this.tabelDataH.getValueAt(tabelDataH.getSelectedRow(), 0).toString();
//                this.showData(this.tabelDataH);
//                break;
//            case 3:
//                this.idSelected = this.tabelDataM.getValueAt(tabelDataM.getSelectedRow(), 0).toString();
//                this.showData(this.tabelDataM);
//                break;
//            case 4:
//                this.idSelected = this.tabelDataB.getValueAt(tabelDataB.getSelectedRow(), 0).toString();
//                this.showData(this.tabelDataB);
//                break;
////            case 5:
////                this.idSelected = this.tabelDataT.getValueAt(tabelDataT.getSelectedRow(), 0).toString();
////                this.showData(this.tabelDataT);
////                break;
//        }
        this.idSelected = this.tabelDataS.getValueAt(tabelDataS.getSelectedRow(), 0).toString();
        this.showData(this.tabelDataS);
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_tabelDataSMouseClicked

    private void tabelDataSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelDataSKeyPressed
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        if(evt.getKeyCode() == KeyEvent.VK_UP){
//            switch(selectedIndex){
//            case 1:
//                this.idSelected = this.tabelDataS.getValueAt(tabelDataS.getSelectedRow() - 1, 0).toString();
//                this.showData(this.tabelDataS);
//                break;
//            case 2:
//                this.idSelected = this.tabelDataH.getValueAt(tabelDataH.getSelectedRow() - 1, 0).toString();
//                this.showData(this.tabelDataH);
//                break;
//            case 3:
//                this.idSelected = this.tabelDataM.getValueAt(tabelDataM.getSelectedRow() - 1, 0).toString();
//                this.showData(this.tabelDataM);
//                break;
//            case 4:
//                this.idSelected = this.tabelDataB.getValueAt(tabelDataB.getSelectedRow() - 1, 0).toString();
//                this.showData(this.tabelDataB);
//                break;
////            case 5:
////                this.idSelected = this.tabelDataT.getValueAt(tabelDataT.getSelectedRow() - 1, 0).toString();
////                this.showData(this.tabelDataT);
////                break;
//            }
            this.idSelected = this.tabelDataS.getValueAt(tabelDataS.getSelectedRow() - 1, 0).toString();
            this.showData(this.tabelDataS);
        }else if(evt.getKeyCode() == KeyEvent.VK_DOWN){
//            switch(selectedIndex){
//            case 1:
//                this.idSelected = this.tabelDataS.getValueAt(tabelDataS.getSelectedRow() + 1, 0).toString();
//                this.showData(this.tabelDataS);
//                break;
//            case 2:
//                this.idSelected = this.tabelDataH.getValueAt(tabelDataH.getSelectedRow() + 1, 0).toString();
//                this.showData(this.tabelDataH);
//                break;
//            case 3:
//                this.idSelected = this.tabelDataM.getValueAt(tabelDataM.getSelectedRow() + 1, 0).toString();
//                this.showData(this.tabelDataM);
//                break;
//            case 4:
//                this.idSelected = this.tabelDataB.getValueAt(tabelDataB.getSelectedRow() + 1, 0).toString();
//                this.showData(this.tabelDataB);
//                break;
////            case 5:
////                this.idSelected = this.tabelDataT.getValueAt(tabelDataT.getSelectedRow() - 1, 0).toString();
////                this.showData(this.tabelDataT);
////                break;
//            }
            this.idSelected = this.tabelDataS.getValueAt(tabelDataS.getSelectedRow() + 1, 0).toString();
            this.showData(this.tabelDataS);
        }
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_tabelDataSKeyPressed

    private void inpCariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpCariKeyTyped
        String key = this.inpCari.getText();
        this.keyword = "WHERE id_tr_jual LIKE '%"+key+"%' OR id_barang LIKE '%"+key+"%'";
        switch(selectedIndex){
            case 1:
                this.updateTabel(this.tabelDataS);
                break;
            case 2:
                this.updateTabel(this.tabelDataH);
                break;
            case 3:
                this.updateTabel(this.tabelDataM);
                break;
            case 4:
                this.updateTabel(this.tabelDataB);
                break;
//            case 5:
//                this.updateTabel(this.tabelDataT);
//                break;
        }
//        this.updateTabel(this.tabelDataS);
    }//GEN-LAST:event_inpCariKeyTyped

    private void inpCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpCariKeyReleased
        String key = this.inpCari.getText();
        this.keyword = "WHERE id_tr_jual LIKE '%"+key+"%' OR id_barang LIKE '%"+key+"%'";
        switch(selectedIndex){
            case 1:
                this.updateTabel(this.tabelDataS);
                break;
            case 2:
                this.updateTabel(this.tabelDataH);
                break;
            case 3:
                this.updateTabel(this.tabelDataM);
                break;
            case 4:
                this.updateTabel(this.tabelDataB);
                break;
//            case 5:
//                this.updateTabel(this.tabelDataT);
//                break;
        }
//        this.updateTabel(this.tabelDataS);
    }//GEN-LAST:event_inpCariKeyReleased

    private void tabelDataHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelDataHMouseClicked
        // TODO add your handling code here:
        this.idSelected = this.tabelDataH.getValueAt(tabelDataH.getSelectedRow(), 0).toString();
        this.showData(this.tabelDataH);
    }//GEN-LAST:event_tabelDataHMouseClicked

    private void tabelDataHKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelDataHKeyPressed
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        if(evt.getKeyCode() == KeyEvent.VK_UP){
            this.idSelected = this.tabelDataH.getValueAt(tabelDataH.getSelectedRow() - 1, 0).toString();
            this.showData(this.tabelDataH);
        }else if(evt.getKeyCode() == KeyEvent.VK_DOWN){
            this.idSelected = this.tabelDataH.getValueAt(tabelDataH.getSelectedRow() + 1, 0).toString();
            this.showData(this.tabelDataH);
        }
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_tabelDataHKeyPressed

    private void tabelDataMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelDataMMouseClicked
        // TODO add your handling code here:
        this.idSelected = this.tabelDataM.getValueAt(tabelDataM.getSelectedRow(), 0).toString();
        this.showData(this.tabelDataM);
    }//GEN-LAST:event_tabelDataMMouseClicked

    private void tabelDataMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelDataMKeyPressed
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        if(evt.getKeyCode() == KeyEvent.VK_UP){
            this.idSelected = this.tabelDataM.getValueAt(tabelDataM.getSelectedRow() - 1, 0).toString();
            this.showData(this.tabelDataM);
        }else if(evt.getKeyCode() == KeyEvent.VK_DOWN){
            this.idSelected = this.tabelDataM.getValueAt(tabelDataM.getSelectedRow() + 1, 0).toString();
            this.showData(this.tabelDataM);
        }
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_tabelDataMKeyPressed

    private void tabelDataBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelDataBMouseClicked
        // TODO add your handling code here:
        this.idSelected = this.tabelDataB.getValueAt(tabelDataB.getSelectedRow(), 0).toString();
        this.showData(this.tabelDataB);
    }//GEN-LAST:event_tabelDataBMouseClicked

    private void tabelDataBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelDataBKeyPressed
        // TODO add your handling code here:
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        if(evt.getKeyCode() == KeyEvent.VK_UP){
            this.idSelected = this.tabelDataB.getValueAt(tabelDataB.getSelectedRow() - 1, 0).toString();
            this.showData(this.tabelDataB);
        }else if(evt.getKeyCode() == KeyEvent.VK_DOWN){
            this.idSelected = this.tabelDataB.getValueAt(tabelDataB.getSelectedRow() + 1, 0).toString();
            this.showData(this.tabelDataB);
        }
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_tabelDataBKeyPressed

    private void inpCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inpCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inpCariActionPerformed

    private void lpSemuaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lpSemuaMouseClicked
//        // TODO add your handling code here:
//        JTabbedPane tabSource = (JTabbedPane) evt.getSource();
//        String tab = tabSource.getTitleAt(tabSource.getSelectedIndex());
        
    }//GEN-LAST:event_lpSemuaMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bacground;
    private javax.swing.JButton btnDel;
    private javax.swing.JTextField inpCari;
    private javax.swing.JScrollPane lpBulanan;
    private javax.swing.JScrollPane lpHarian;
    private javax.swing.JScrollPane lpMingguan;
    private javax.swing.JScrollPane lpSemua;
    private javax.swing.JTabbedPane tabPendapatan;
    private javax.swing.JTable tabelDataB;
    private javax.swing.JTable tabelDataH;
    private javax.swing.JTable tabelDataM;
    private javax.swing.JTable tabelDataS;
    private javax.swing.JLabel valIDPendapatan;
    private javax.swing.JLabel valIDTransaksi;
    private javax.swing.JLabel valJenisBarang;
    private javax.swing.JLabel valJumlah;
    private javax.swing.JLabel valNamaBarang;
    private javax.swing.JLabel valNamaKaryawan;
    private javax.swing.JLabel valNamaPembeli;
    private javax.swing.JLabel valTanggal;
    private javax.swing.JLabel valTotalHarga;
    // End of variables declaration//GEN-END:variables
}