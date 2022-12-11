package com.window.panels;

import java.util.Date;
import com.manage.Chart;
import com.manage.Message;
import com.manage.Waktu;
import com.data.db.Database;
import com.manage.Barang;
import com.manage.ManageTransaksiBeli;
import com.manage.ManageTransaksiJual;
import com.manage.Text;
import java.awt.BorderLayout;
import java.awt.Color;
//import static java.awt.SystemColor.text;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.statistics.HistogramDataset;

/**
 *
 * @author Amirzan Fikri P
 */
public class Dashboard extends javax.swing.JPanel {
    
    private final Database db = new Database();
    
//    private final Statement stat = db.stat;
    
    private final ManageTransaksiBeli trb = new ManageTransaksiBeli();
    
    private final ManageTransaksiJual trj = new ManageTransaksiJual();
    
    private final Barang barang = new Barang();
    private final Chart chart = new Chart();
    
    private final Waktu waktu = new Waktu();
    
    private final Text text = new Text();
    
    private int hari,bulan, tahun,pMakanan,pMinuman,pSnack,pAtk;
    
    DateFormat tanggalMilis = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private final DateFormat tanggalFull = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss ");
    private final DateFormat date = new SimpleDateFormat("dd-MM-yyyy");
    private final DateFormat date1 = new SimpleDateFormat("yyyy-MM-dd");
    private final DateFormat time = new SimpleDateFormat("hh:mm:ss");
    private final DateFormat timeMillis = new SimpleDateFormat("ss.SSS:mm:hh");
    private String keyword = "";
    public Dashboard() throws ParseException {
        initComponents();
        updateTabel();
        this.hari = waktu.getTanggal();
        this.bulan = waktu.getBulan()+1;
        this.tahun = waktu.getTahun();
        this.tabelData.setRowHeight(30);
        this.tabelData.getTableHeader().setBackground(new java.awt.Color(255,255,255));
        this.tabelData.getTableHeader().setForeground(new java.awt.Color(0, 0, 0));
        
        showMain();
        pMakanan = getJenis("MAKANAN");
        pMinuman = getJenis("MINUMAN");
        pSnack = getJenis("SNACK");
        pAtk = getJenis("ATK");
        this.chart.showPieChart(this.pnlPieChart, "", 40, 20, 15, 25);
//        this.chart.showPieChart(this.pnlPieChart, "", pMakanan, pMinuman, pSnack, pAtk);
        this.chart.lineChartPenjualan(this.pnlLineChart);
        this.showLineChart();
        
        // mengupdate waktu
        new Thread(new Runnable(){
            
            @Override
            public void run(){
                try{
                    while(isVisible()){
//                        System.out.println("update");
                        lblDate.setText(waktu.getUpdateWaktu() + "  ");
                        Thread.sleep(100);
                    }
                }catch(InterruptedException ex){
                    Message.showException(this, "Terjadi Kesalahan Saat Mengupdate Tanggal!\n" + ex.getMessage(), ex, true);
                }
            }
        }).start();
//        this.lblPembeli.setText(Integer.toString(db.getJumlahData("laporan_pendapatan",String.format("WHERE tanggal = '" +waktu.getCurrentDate()+"' "))));
//        updateTabel();
    }
    private int getJenis(String field){
        try {
            Statement stat = getStat();
            int data = 0;
            String sql = "SELECT SUM(jenis_barang = '"+field+"') AS total FROM barang INNER JOIN transaksi_jual ON barang.id_barang = transaksi_jual.id_barang WHERE MONTH(tanggal) = '"+this.bulan+"'";
//            String sql = "SELECT SUM("+kolom+") AS total FROM "+table+" "+kondisi;
            ResultSet res = stat.executeQuery(sql);
            while(res.next()){
                System.out.println("data "+ field +" ditemukan");
                data = res.getInt("total");
                System.out.println("jumlahnya "+data);
            }
            return data;
        } catch (SQLException ex) {
            Logger.getLogger(LaporanJual.class.getName()).log(Level.SEVERE, null, ex);
        }catch(NullPointerException n){
//            n.printStackTrace();
            System.out.println("errorr ");
            return 0;
        }
        return -1;
    }
    private Statement getStat(){
        try{  
            Class.forName("com.mysql.jdbc.Driver");  
            Connection con=DriverManager.getConnection(  
                "jdbc:mysql://localhost:3306/gemastik_lightning","root","");  
            Statement stmt=con.createStatement();
            
//            ResultSet rs=stmt.executeQuery("show databases;");  
            System.out.println("Connected");  
            return stmt;
        }
        catch(Exception e){
            System.out.println(e);
        }
        return null;
    }
    
    private int getTotal(String table, String kolom, String kondisi){
        try {
            Statement stat = getStat();
            int data = 0;
            String sql = "SELECT SUM("+kolom+") AS total FROM "+table+" "+kondisi;
            System.out.println("sql sum "+sql);
            ResultSet res = stat.executeQuery(sql);
            while(res.next()){
                System.out.println("data ditemukan");
                data = res.getInt("total");
                System.out.println("jumlahnya "+data);
            }
            return data;
        } catch (SQLException ex) {
            Logger.getLogger(LaporanJual.class.getName()).log(Level.SEVERE, null, ex);
        }catch(NullPointerException n){
//            n.printStackTrace();
            System.out.println("errorr ");
            return 0;
        }
        return -1;
    }
    public int getJumlahData(String tabel, String kondisi){
        try{
            Statement stat = getStat();
            String query = "SELECT COUNT(*) AS total FROM " + tabel + " " + kondisi;
            System.out.println("hitung " + query);
            ResultSet res = stat.executeQuery(query);
            if(res.next()){
                return res.getInt("total");
            }
        }catch(SQLException ex){
            Message.showException(this, "Terjadi Kesalahan!\n\nError message : "+ex.getMessage(), ex, true);
        }catch(NullPointerException n){
//            n.printStackTrace();
            System.out.println("errorr ");
            return 0;
        }
        return -1;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblDate = new javax.swing.JLabel();
        lblSaldo = new javax.swing.JLabel();
        lblPemasukan = new javax.swing.JLabel();
        lblPengeluaran = new javax.swing.JLabel();
        lblPembeli = new javax.swing.JLabel();
        lblMakanan = new javax.swing.JLabel();
        lblMinuman = new javax.swing.JLabel();
        lblAtk = new javax.swing.JLabel();
        lblSnack = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelData = new javax.swing.JTable();
        pnlLineChart = new javax.swing.JPanel();
        pnlPieChart = new javax.swing.JPanel();
        background = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(957, 650));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblDate.setText("-");
        add(lblDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 140, 260, 20));

        lblSaldo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        add(lblSaldo, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 160, 20));

        lblPemasukan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        add(lblPemasukan, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 80, 155, 20));

        lblPengeluaran.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        add(lblPengeluaran, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 80, 170, 20));

        lblPembeli.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        add(lblPembeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 80, 160, 20));

        lblMakanan.setBackground(new java.awt.Color(10, 255, 108));
        lblMakanan.setOpaque(true);
        add(lblMakanan, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 350, 40, 35));

        lblMinuman.setBackground(new java.awt.Color(2, 99, 255));
        lblMinuman.setOpaque(true);
        add(lblMinuman, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 195, 40, 35));

        lblAtk.setBackground(new java.awt.Color(255, 233, 35));
        lblAtk.setOpaque(true);
        add(lblAtk, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 250, 40, 35));

        lblSnack.setBackground(new java.awt.Color(255, 51, 102));
        lblSnack.setOpaque(true);
        add(lblSnack, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 300, 40, 35));

        tabelData.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        tabelData.setForeground(new java.awt.Color(0, 0, 0));
        tabelData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "No", "Nama Transaksi", "Total Harga", "Jenis Transaksi", "Tanggal", "Waktu"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
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
                tabelDatatablDataKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(tabelData);
        if (tabelData.getColumnModel().getColumnCount() > 0) {
            tabelData.getColumnModel().getColumn(0).setMinWidth(35);
            tabelData.getColumnModel().getColumn(0).setMaxWidth(35);
            tabelData.getColumnModel().getColumn(2).setMinWidth(100);
            tabelData.getColumnModel().getColumn(2).setMaxWidth(100);
            tabelData.getColumnModel().getColumn(3).setMinWidth(100);
            tabelData.getColumnModel().getColumn(3).setMaxWidth(100);
            tabelData.getColumnModel().getColumn(4).setMinWidth(80);
            tabelData.getColumnModel().getColumn(4).setMaxWidth(80);
            tabelData.getColumnModel().getColumn(5).setMinWidth(80);
            tabelData.getColumnModel().getColumn(5).setMaxWidth(80);
        }

        add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 480, 940, 260));

        pnlLineChart.setBackground(new java.awt.Color(255, 255, 255));
        pnlLineChart.setForeground(new java.awt.Color(226, 226, 0));
        pnlLineChart.setLayout(new java.awt.BorderLayout());
        add(pnlLineChart, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 200, 430, 210));

        pnlPieChart.setBackground(new java.awt.Color(255, 255, 255));
        pnlPieChart.setForeground(new java.awt.Color(255, 255, 0));
        pnlPieChart.setLayout(new java.awt.BorderLayout());
        add(pnlPieChart, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 190, 260, 210));

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/gambar/app-dashboard-075.png"))); // NOI18N
        add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 973, 768));
    }// </editor-fold>//GEN-END:initComponents

    private void tabelDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelDataMouseClicked
//
    }//GEN-LAST:event_tabelDataMouseClicked

    private void tabelDatatablDataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelDatatablDataKeyPressed
//
    }//GEN-LAST:event_tabelDatatablDataKeyPressed
    private void showMain(){
        String tanggal = waktu.getCurrentDate();
        System.out.println("tanggal "+ tanggal);
        String tSaldo = text.toMoneyCase(Integer.toString(getTotal("saldo", "jumlah_saldo", "WHERE id_saldo = 'S001'")));
        String tPemasukan = text.toMoneyCase(Integer.toString(getTotal("transaksi_jual", "total_hrg", "WHERE YEAR(tanggal) = '"+tahun+"' AND MONTH(tanggal) = '"+bulan+"' AND DAY(tanggal) = '"+hari+"'")));
        String tPengeluaran = text.toMoneyCase(Integer.toString(getTotal("transaksi_beli", "total_hrg", "WHERE YEAR(tanggal) = '"+tahun+"' AND MONTH(tanggal) = '"+bulan+"' AND DAY(tanggal) = '"+hari+"'")));
        String tPembeli = Integer.toString(getJumlahData("transaksi_jual", "WHERE YEAR(tanggal) = '"+tahun+"' AND MONTH(tanggal) = '"+bulan+"' AND DAY(tanggal) = '"+hari+"'"));
        lblSaldo.setText(tSaldo);
        lblPemasukan.setText(tPemasukan);
        lblPengeluaran.setText(tPengeluaran);
        lblPembeli.setText(tPembeli);
        System.out.println("saldo "+tSaldo);
        System.out.println("pemasukan "+tPemasukan);
        System.out.println("pengeluaran "+tPengeluaran);
        System.out.println("pembeli "+tPembeli);
    }
    private void updateTabel() throws ParseException{
        try{
            Date tanggalData = null;
            String kolom[] = {"No","Nama Transaksi", "Total Harga", "Jenis Transaksi", "Tanggal","Waktu"},tanggalPenuh,total,ID,data[] = new String[6];
            TableModel model;
//            model.
            DefaultTableModel tabelModel = new DefaultTableModel(kolom,0){
                boolean[] canEdit = new boolean [] {
                false, false, false, false
                };
                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit[columnIndex];
            }
            };
            Statement stat = getStat();
            String sql = "SELECT nama_tr_beli AS nama,total_hrg AS total, id_tr_beli AS jenis, tanggal FROM transaksi_beli UNION SELECT nama_tr_jual,total_hrg,id_tr_jual,tanggal FROM transaksi_jual ORDER BY tanggal DESC";
            System.out.println(sql);
            ResultSet res = stat.executeQuery(sql);
            int rows = 1;
            while(res.next()){
                System.out.println("data ditemukan");
                data[0] = Integer.toString(rows);
                rows++;
                data[1] = res.getString("nama");
                System.out.println("data ke 1 : "+ data[1]);
                total = res.getString("total");
                System.out.println("data ke 2 : "+ data[2]);
                ID = res.getString("jenis");
                System.out.println("data ke 3 : "+ ID);
                if(ID.substring(0, 3).equals("TRJ")){
                    data[2] = text.toMoneyCase(total);
                    data[3] = "Penjualan";
                }else if(ID.substring(0, 3).equals("TRB")){
                    data[2] = text.toMoneyCase("-"+total);
                    data[3] = "Pembelian";
                }
                tanggalData = tanggalMilis.parse(res.getString("tanggal"));
                System.out.println("tanggal nya "+ tanggalData);
                data[4] = date.format(tanggalData);
                System.out.println("data ke 4 : "+ data[4]);
                data[5] = time.format(tanggalData);
                System.out.println("data ke 5 : "+ data[5]);
                tabelModel.addRow(data);
            }
            tabelData.setModel(tabelModel);
        }catch(SQLException ex){
            Message.showException(this, "Terjadi kesalahan saat mengambil data dari database\n" + ex.getMessage(), ex, true);
        }
    }
    public void showPieChart(){
        
        //create dataset
      DefaultPieDataset barDataset = new DefaultPieDataset( );
      barDataset.setValue( "Makanan" , new Double( 20 ) );  
      barDataset.setValue( "Minuman" , new Double( 20 ) );   
      barDataset.setValue( "Snack" , new Double( 40 ) );    
      barDataset.setValue( "ATK" , new Double( 10 ) );  
      
      //create chart
      JFreeChart piechart = ChartFactory.createPieChart("Penjualan Produk",barDataset, false,true,false);//explain
      piechart.setTitle(new TextTitle("Pie Chart", new java.awt.Font("Ebrima", 1, 22)));
      
        PiePlot piePlot =(PiePlot) piechart.getPlot();
      
       //changing pie chart blocks colors
       piePlot.setSectionPaint("Makanan", new Color(226,226,0));
       piePlot.setSectionPaint("Minuman", new Color(52,200,38));
       piePlot.setSectionPaint("Snack", new Color(255,43,237));
       piePlot.setSectionPaint("ATK", new Color(49,165,192));
      
       
        piePlot.setBackgroundPaint(Color.white);
        
        //create chartPanel to display chart(graph)
        ChartPanel barChartPanel = new ChartPanel(piechart);
//        pnlPieChart.removeAll();
//        pnlPieChart.add(barChartPanel, BorderLayout.CENTER);
//        pnlPieChart.validate();
    }
    
    public void showLineChart(){
        //create dataset for the graph
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.setValue(170, "Amount", "Kamis");
        dataset.setValue(150, "Amount", "Jumat");
        dataset.setValue(80, "Amount", "Sabtu");
        dataset.setValue(50, "Amount", "Minggu");
        dataset.setValue(180, "Amount", "Senin");
        dataset.setValue(200, "Amount", "Selasa");
        dataset.setValue(200, "Amount", "Rabu");
        //
        //create chart
        JFreeChart linechart = ChartFactory.createLineChart("","","", 
                dataset, PlotOrientation.VERTICAL, false,true,false);
//        linechart.setTitle(new TextTitle("Penjualan Produk Minggu Ini", new java.awt.Font("Ebrima", 1, 21)));
        
        //create plot object
         CategoryPlot lineCategoryPlot = linechart.getCategoryPlot();
        lineCategoryPlot.setRangeGridlinePaint(Color.BLUE);
        lineCategoryPlot.setBackgroundPaint(Color.WHITE);
        
        //create render object to change the moficy the line properties like color
        LineAndShapeRenderer lineRenderer = (LineAndShapeRenderer) lineCategoryPlot.getRenderer();
        Color lineChartColor = new Color(255,2,9);
        lineRenderer.setSeriesPaint(0, lineChartColor);
        
         //create chartPanel to display chart(graph)
        ChartPanel lineChartPanel = new ChartPanel(linechart);
//        pnlLineChart.removeAll();
//        pnlLineChart.add(lineChartPanel, BorderLayout.CENTER);
//        pnlLineChart.validate();
    }

    public void showHistogram(){
        
         double[] values = { 95, 49, 14, 59, 50, 66, 47, 40, 1, 67,
                            12, 58, 28, 63, 14, 9, 31, 17, 94, 71,
                            49, 64, 73, 97, 15, 63, 10, 12, 31, 62,
                            93, 49, 74, 90, 59, 14, 15, 88, 26, 57,
                            77, 44, 58, 91, 10, 67, 57, 19, 88, 84                                
                          };
 
 
        HistogramDataset dataset = new HistogramDataset();
        dataset.addSeries("key", values, 20);
        
         JFreeChart chart = ChartFactory.createHistogram("JFreeChart Histogram","Data", "Frequency", dataset,PlotOrientation.VERTICAL, false,true,false);
            XYPlot plot= chart.getXYPlot();
        plot.setBackgroundPaint(Color.WHITE);

        
        
        ChartPanel barpChartPanel2 = new ChartPanel(chart);
//        pnlLineChart.removeAll();
//        pnlLineChart.add(barpChartPanel2, BorderLayout.CENTER);
//        pnlLineChart.validate();
    }

    public void showBarChart(){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.setValue(200, "Amount", "january");
        dataset.setValue(150, "Amount", "february");
        dataset.setValue(18, "Amount", "march");
        dataset.setValue(100, "Amount", "april");
        dataset.setValue(80, "Amount", "may");
        dataset.setValue(250, "Amount", "june");
        
        JFreeChart chart = ChartFactory.createBarChart("contribution","monthly","amount", 
                dataset, PlotOrientation.VERTICAL, false,true,false);
        
        CategoryPlot categoryPlot = chart.getCategoryPlot();
        //categoryPlot.setRangeGridlinePaint(Color.BLUE);
        categoryPlot.setBackgroundPaint(Color.WHITE);
        BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();
        Color clr3 = new Color(204,0,51);
        renderer.setSeriesPaint(0, clr3);
        
        ChartPanel barpChartPanel = new ChartPanel(chart);
//        pnlLineChart.removeAll();
//        pnlLineChart.add(barpChartPanel, BorderLayout.CENTER);
//        pnlLineChart.validate();
        
        
    }
    


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAtk;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblMakanan;
    private javax.swing.JLabel lblMinuman;
    private javax.swing.JLabel lblPemasukan;
    private javax.swing.JLabel lblPembeli;
    private javax.swing.JLabel lblPengeluaran;
    private javax.swing.JLabel lblSaldo;
    private javax.swing.JLabel lblSnack;
    private javax.swing.JPanel pnlLineChart;
    private javax.swing.JPanel pnlPieChart;
    private javax.swing.JTable tabelData;
    // End of variables declaration//GEN-END:variables
}
