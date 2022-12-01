package com.window.panels;

import com.manage.Barang;
import com.manage.ManageTransaksiBeli;
import com.manage.Message;
import com.manage.Text;
import com.manage.Waktu;
import com.media.Audio;
import com.media.Gambar;
import com.sun.glass.events.KeyEvent;
import com.users.Petugas;
import com.users.Supplier;
import com.window.dialogs.CetakLaporan;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.ParseException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.statistics.HistogramDataset;

/**
 *
 * @author Gemastik Lightning
 */
public class LaporanBeli extends javax.swing.JPanel {

    private final ManageTransaksiBeli trb = new ManageTransaksiBeli();
    
    private final Petugas petugas = new Petugas();
    
    private final Supplier supplier = new Supplier();
    
    private final Barang barang = new Barang();
    
    private final Text text = new Text();
    
    private final Waktu waktu = new Waktu();
    
    private String idSelected = "", keyword = "", idTr, idPd, idBg, namaSupplier, namaPetugas, namaBarang, jenis, jumlah, totalHrg, tanggal;
    
    /**
     * Creates new form Dashboard
     */
    public LaporanBeli() throws ParseException {
        initComponents();
        
//        this.btnPrint.setUI(new javax.swing.plaf.basic.BasicButtonUI());
//        this.btnEdit.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        this.btnDel.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        
        this.tabelDataS.setRowHeight(29);
        this.tabelDataS.getTableHeader().setBackground(new java.awt.Color(255,255,255));
        this.tabelDataS.getTableHeader().setForeground(new java.awt.Color(0, 0, 0));
        
        this.tabelDataH.setRowHeight(29);
        this.tabelDataH.getTableHeader().setBackground(new java.awt.Color(255,255,255));
        this.tabelDataH.getTableHeader().setForeground(new java.awt.Color(0, 0, 0));
        
        this.tabelDataM.setRowHeight(29);
        this.tabelDataM.getTableHeader().setBackground(new java.awt.Color(255,255,255));
        this.tabelDataM.getTableHeader().setForeground(new java.awt.Color(0, 0, 0));
        
        this.tabelDataB.setRowHeight(29);
        this.tabelDataB.getTableHeader().setBackground(new java.awt.Color(255,255,255));
        this.tabelDataB.getTableHeader().setForeground(new java.awt.Color(0, 0, 0));
        
//        this.tabelDataT.setRowHeight(29);
//        this.tabelDataT.getTableHeader().setBackground(new java.awt.Color(255,255,255));
//        this.tabelDataT.getTableHeader().setForeground(new java.awt.Color(0, 0, 0));
        
        JLabel[] lbls = new JLabel[]{
            this.valIDPengeluaran, this.valIDTransaksi, this.valNamaBarang, this.valNamaKaryawan, this.valNamaSupplier, 
            this.valJenisBrg, this.valJumlahBrg, this.valTanggal, this.valHarga
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
        
//        this.showLineChart1();
//        this.showLineChart2();
        int hari = waktu.getTanggal();
        String minggu[] = waktu.getMinggu(hari, -1);
        System.out.println("hari awal "+minggu[0]);
        System.out.println("hari akhir "+minggu[1]);
        int bulan = waktu.getBulan()+1;
        int tahun = waktu.getTahun();
        System.out.println("hari "+hari);
        System.out.println("bulan "+bulan);
        System.out.println("tahun "+tahun);
        this.updateTabel(tabelDataS);
        keyword = "WHERE tanggal = '"+tahun+"-"+bulan+"-"+hari+"'";
//        SELECT * FROM gemastik_lightning.laporan_pengeluaran WHERE tanggal = '2022-10-22'
        this.updateTabel(tabelDataH);
        keyword = "WHERE (tanggal BETWEEN '"+minggu[0]+"' AND '"+minggu[1]+"')";
//        SELECT * FROM gemastik_lightning.laporan_pengeluaran WHERE (tanggal BETWEEN '2022-10-22' AND '2022-10-23');
        this.updateTabel(tabelDataM);
        keyword = "WHERE MONTH(tanggal) = '"+bulan+"'";
//        SELECT * FROM gemastik_lightning.laporan_pengeluaran WHERE MONTH(tanggal) = '11'
        this.updateTabel(tabelDataB);
        keyword = "WHERE YEAR(tanggal) = '"+tahun+"'";
//        SELECT * FROM gemastik_lightning.laporan_pengeluaran WHERE YEAR(tanggal) = '2022'
        this.updateTabel(tabelDataT);
    }
    
    public void showPieChart(){
        
        //create dataset
      DefaultPieDataset barDataset = new DefaultPieDataset( );
      barDataset.setValue( "Makanan" , new Double( 20 ) );  
      barDataset.setValue( "Minuman" , new Double( 20 ) );   
      barDataset.setValue( "Snack" , new Double( 40 ) );    
      barDataset.setValue( "ATK" , new Double( 10 ) );  
      
      //create chart
      JFreeChart piechart = ChartFactory.createPieChart("Penbelian Seminggu Terakhir",barDataset, false,true,false);//explain
      
        PiePlot piePlot =(PiePlot) piechart.getPlot();
      
       //changing pie chart blocks colors
       piePlot.setSectionPaint("Makanan", new Color(255,255,0));
       piePlot.setSectionPaint("Minuman", new Color(51,255,0));
       piePlot.setSectionPaint("Snack", new Color(255,0,255));
       piePlot.setSectionPaint("ATK", new Color(0,204,204));
      
       
        piePlot.setBackgroundPaint(Color.white);
        
        //create chartPanel to display chart(graph)
        ChartPanel barChartPanel = new ChartPanel(piechart);
//        chartPane.removeAll();
//        chartPane.add(barChartPanel, BorderLayout.CENTER);
//        chartPane.validate();
    }

    public void showLineChart1(){
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
        JFreeChart linechart = ChartFactory.createLineChart("Pengeluaran Minggu Ini","Hari","Jumlah", 
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
//        tabChartMinggu.removeAll();
//        tabChartMinggu.add(lineChartPanel, BorderLayout.CENTER);
//        tabChartMinggu.validate();
    }
    
    public void showLineChart2(){
        //create dataset for the graph
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.setValue(410, "Amount", "Minggu 1");
        dataset.setValue(390, "Amount", "Minggu 2");
        dataset.setValue(198, "Amount", "Minggu 3");
        dataset.setValue(420, "Amount", "Minggu 4");
        dataset.setValue(396, "Amount", "Minggu 5");
        
        //create chart
        JFreeChart linechart = ChartFactory.createLineChart("Pengeluaran Bulan Ini","Hari","Jumlah", 
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
//        tabChartBulan.removeAll();
//        tabChartBulan.add(lineChartPanel, BorderLayout.CENTER);
//        tabChartBulan.validate();
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

        
        
//        ChartPanel barpChartPanel2 = new ChartPanel(chart);
//        tabChartBulan.removeAll();
//        tabChartBulan.add(barpChartPanel2, BorderLayout.CENTER);
//        tabChartBulan.validate();
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
//        chartPane.removeAll();
//        chartPane.add(barpChartPanel, BorderLayout.CENTER);
//        chartPane.validate();
    }
    
    private Object[][] getData(){
        try{
            Object obj[][];
            int rows = 0;
            String sql = "SELECT id_tr_beli, id_barang, jumlah_brg, total_hrg, tanggal FROM transaksi_beli " + keyword + " ORDER BY id_tr_beli DESC";
            // mendefinisikan object berdasarkan total rows dan cols yang ada didalam tabel
            System.out.println(sql);
            obj = new Object[trb.getJumlahData("transaksi_beli", keyword)][5];
            // mengeksekusi query
            trb.res = trb.stat.executeQuery(sql);
            // mendapatkan semua data yang ada didalam tabel
            while(trb.res.next()){
                // menyimpan data dari tabel ke object
                obj[rows][0] = trb.res.getString("id_tr_beli").replace("TRB", "LPG");
                obj[rows][1] = barang.getNamaBarang(trb.res.getString("id_barang"));
                obj[rows][2] = trb.res.getString("jumlah_brg");
                obj[rows][3] = text.toMoneyCase(trb.res.getString("total_hrg"));
                obj[rows][4] = text.toDateCase(trb.res.getString("tanggal"));
                rows++; // rows akan bertambah 1 setiap selesai membaca 1 row pada tabel
            }
            return obj;
        }catch(SQLException ex){
            ex.printStackTrace();
            Message.showException(this, "Terjadi kesalahan saat mengambil data dari database\n" + ex.getMessage(), ex, true);
        }
        return null;
    }
    
    private void updateTabel(JTable tabelData){
        tabelData.setModel(new javax.swing.table.DefaultTableModel(
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
        this.idTr = tabel.getValueAt(tabel.getSelectedRow(), 0).toString().replace("LPG", "TRB");
        this.idPd = this.idTr.replace("TRB", "LPG");
        this.namaSupplier = this.supplier.getNama(this.trb.getIdSupplier(this.idTr));
        this.namaPetugas = this.petugas.getNama(this.trb.getIdPetugas(this.idTr));
        this.namaBarang = this.barang.getNamaBarang(this.trb.getIdBarang(this.idTr));
        this.jenis = text.toCapitalize(this.barang.getJenis(this.trb.getIdBarang(this.idTr)));
        this.jumlah = Integer.toString(this.trb.getJumlahBarang(this.idTr));
        this.totalHrg = text.toMoneyCase(this.trb.getTotalHarga(this.idTr));
        this.tanggal = this.text.toDateCase(this.trb.getTanggal(this.idTr));
        
        // menampilkan data-data
        this.valIDTransaksi.setText("<html><p>:&nbsp;"+this.idTr+"</p></html>");
        this.valIDPengeluaran.setText("<html><p>:&nbsp;"+this.idPd+"</p></html>");
        this.valNamaSupplier.setText("<html><p>:&nbsp;"+this.namaSupplier+"</p></html>");
        this.valNamaKaryawan.setText("<html><p>:&nbsp;"+this.namaPetugas+"</p></html>");
        this.valNamaBarang.setText("<html><p>:&nbsp;"+this.namaBarang+"</p></html>");
        this.valJumlahBrg.setText("<html><p>:&nbsp;"+this.jumlah+"</p></html>");
        this.valJenisBrg.setText("<html><p>:&nbsp;"+this.jenis+"</p></html>");
        this.valHarga.setText("<html><p>:&nbsp;"+this.totalHrg+"</p></html>");
        this.valTanggal.setText("<html><p>:&nbsp;"+this.tanggal+"</p></html>");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        valIDPengeluaran = new javax.swing.JLabel();
        valIDTransaksi = new javax.swing.JLabel();
        valNamaKaryawan = new javax.swing.JLabel();
        valNamaSupplier = new javax.swing.JLabel();
        valNamaBarang = new javax.swing.JLabel();
        valJenisBrg = new javax.swing.JLabel();
        valJumlahBrg = new javax.swing.JLabel();
        valHarga = new javax.swing.JLabel();
        valTanggal = new javax.swing.JLabel();
        btnDel = new javax.swing.JButton();
        inpCari = new javax.swing.JTextField();
        tabLaporan = new javax.swing.JTabbedPane();
        LPSEMUA = new javax.swing.JPanel();
        lpSemua = new javax.swing.JScrollPane();
        tabelDataS = new javax.swing.JTable();
        LPHARIAN = new javax.swing.JPanel();
        lpHarian = new javax.swing.JScrollPane();
        tabelDataH = new javax.swing.JTable();
        LPMINGGUAN = new javax.swing.JPanel();
        lpMingguan = new javax.swing.JScrollPane();
        tabelDataM = new javax.swing.JTable();
        LPBULANAN = new javax.swing.JPanel();
        lpBulanan = new javax.swing.JScrollPane();
        tabelDataB = new javax.swing.JTable();
        LPTAHUNAN = new javax.swing.JPanel();
        lpTahunan = new javax.swing.JScrollPane();
        tabelDataT = new javax.swing.JTable();
        background = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(957, 650));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        valIDPengeluaran.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        valIDPengeluaran.setForeground(new java.awt.Color(0, 0, 0));
        valIDPengeluaran.setText(":");
        add(valIDPengeluaran, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 100, 200, 36));

        valIDTransaksi.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        valIDTransaksi.setForeground(new java.awt.Color(0, 0, 0));
        valIDTransaksi.setText(":");
        add(valIDTransaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 164, 200, 36));

        valNamaKaryawan.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        valNamaKaryawan.setForeground(new java.awt.Color(0, 0, 0));
        valNamaKaryawan.setText(":");
        add(valNamaKaryawan, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 228, 200, 36));

        valNamaSupplier.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        valNamaSupplier.setForeground(new java.awt.Color(0, 0, 0));
        valNamaSupplier.setText(":");
        add(valNamaSupplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 292, 200, 37));

        valNamaBarang.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        valNamaBarang.setForeground(new java.awt.Color(0, 0, 0));
        valNamaBarang.setText(":");
        valNamaBarang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        add(valNamaBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 357, 200, 36));

        valJenisBrg.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        valJenisBrg.setForeground(new java.awt.Color(0, 0, 0));
        valJenisBrg.setText(":");
        add(valJenisBrg, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 422, 200, 36));

        valJumlahBrg.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        valJumlahBrg.setForeground(new java.awt.Color(0, 0, 0));
        valJumlahBrg.setText(":");
        add(valJumlahBrg, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 486, 200, 37));

        valHarga.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        valHarga.setForeground(new java.awt.Color(0, 0, 0));
        valHarga.setText(":");
        valHarga.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        add(valHarga, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 550, 200, 37));

        valTanggal.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        valTanggal.setForeground(new java.awt.Color(0, 0, 0));
        valTanggal.setText(":");
        add(valTanggal, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 615, 200, 37));

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
        add(btnDel, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 712, 150, 49));

        inpCari.setBackground(new java.awt.Color(255, 255, 255));
        inpCari.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        inpCari.setForeground(new java.awt.Color(0, 0, 0));
        inpCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inpCariKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                inpCariKeyTyped(evt);
            }
        });
        add(inpCari, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 66, 255, -1));

        LPSEMUA.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabelDataS.setBackground(new java.awt.Color(255, 255, 255));
        tabelDataS.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        tabelDataS.setForeground(new java.awt.Color(0, 0, 0));
        tabelDataS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"PG0001", "Aqua 1L", "25", "Rp. 125.000.00", "1 Oktober 2022"},
                {"PG0002", "Coca Cola", "20", "Rp. 80.000.00", "1 Oktober 2022"},
                {"PG0003", "Aqua 500ml", "30", "Rp. 100.000.00", "1 Oktober 2022"},
                {"PG0004", "Pulpen Snowman", "30", "Rp. 75.000.00", "1 Oktober 2022"},
                {"PG0005", "Sprite", "15", "Rp. 48.000.00", "1 Oktober 2022"},
                {"PG0006", "Indomilk", "5", "Rp. 40.000.00", "1 Oktober 2022"},
                {"PG0007", "Nabati Wafer", "15", "Rp. 52.000.00", "1 Oktober 2022"},
                {"PG0008", "Ichi Ocha 350ml", "15", "Rp. 30.000.00", "1 Oktober 2022"},
                {"PG0009", "Oreo", "15", "Rp. 30.000.00", "1 Oktober 2022"},
                {"PG0010", "Kertas HVS", "800", "Rp. 200.000.00", "1 Oktober 2022"},
                {"PG0011", "Kertas Folio", "900", "Rp. 237.000.00", "1 Oktober 2022"},
                {"PG0012", "Spidol Hitam", "15", "Rp. 30.000.00", "9 Oktober 2022"},
                {"PG0013", "Spidol Merah", "15", "Rp. 37.500.00", "9 Oktober 2022"},
                {"PG0014", "Spidol Biru", "15", "Rp. 37.500.00", "9 Oktober 2022"},
                {"PG0015", "Nabati Wafer", "15", "Rp. 52.500.00", "9 Oktober 2022"},
                {"PG0016", "Roti", "20", "Rp. 20.000.00", "9 Oktober 2022"},
                {"PG0017", "Sprite", "20", "Rp. 60.000.00", "9 Oktober 2022"},
                {"PG0018", "Yupi", "15", "Rp. 37.500.00", "9 Oktober 2022"},
                {"PG0019", "Teh Pucuk", "15", "Rp. 60.000.00", "9 Oktober 2022"},
                {"PG0020", "Coca Cola", "15", "Rp. 60.000.00", "9 Oktober 2022"},
                {"PG0021", "Ichi Ocha 350ml", "15", "Rp. 30.000.00", "9 Oktober 2022"},
                {"PG0022", "Yupi", "10", "Rp. 25.000.00", "9 Oktober 2022"},
                {"PG0023", "Aqua 500ml", "15", "Rp. 75.000.00", "9 Oktober 2022"}
            },
            new String [] {
                "ID Pengeluaran", "Nama Barang", "Jumlah Barang", "Total Pengeluaran", "Tanggal"
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

        LPSEMUA.add(lpSemua, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 500, 540));

        tabLaporan.addTab("Semua", LPSEMUA);

        LPHARIAN.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabelDataH.setBackground(new java.awt.Color(255, 255, 255));
        tabelDataH.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        tabelDataH.setForeground(new java.awt.Color(0, 0, 0));
        tabelDataH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"PG0001", "Aqua 1L", "25", "Rp. 125.000.00", "1 Oktober 2022"},
                {"PG0002", "Coca Cola", "20", "Rp. 80.000.00", "1 Oktober 2022"},
                {"PG0003", "Aqua 500ml", "30", "Rp. 100.000.00", "1 Oktober 2022"},
                {"PG0004", "Pulpen Snowman", "30", "Rp. 75.000.00", "1 Oktober 2022"},
                {"PG0005", "Sprite", "15", "Rp. 48.000.00", "1 Oktober 2022"},
                {"PG0006", "Indomilk", "5", "Rp. 40.000.00", "1 Oktober 2022"},
                {"PG0007", "Nabati Wafer", "15", "Rp. 52.000.00", "1 Oktober 2022"},
                {"PG0008", "Ichi Ocha 350ml", "15", "Rp. 30.000.00", "1 Oktober 2022"},
                {"PG0009", "Oreo", "15", "Rp. 30.000.00", "1 Oktober 2022"},
                {"PG0010", "Kertas HVS", "800", "Rp. 200.000.00", "1 Oktober 2022"},
                {"PG0011", "Kertas Folio", "900", "Rp. 237.000.00", "1 Oktober 2022"},
                {"PG0012", "Spidol Hitam", "15", "Rp. 30.000.00", "9 Oktober 2022"},
                {"PG0013", "Spidol Merah", "15", "Rp. 37.500.00", "9 Oktober 2022"},
                {"PG0014", "Spidol Biru", "15", "Rp. 37.500.00", "9 Oktober 2022"},
                {"PG0015", "Nabati Wafer", "15", "Rp. 52.500.00", "9 Oktober 2022"},
                {"PG0016", "Roti", "20", "Rp. 20.000.00", "9 Oktober 2022"},
                {"PG0017", "Sprite", "20", "Rp. 60.000.00", "9 Oktober 2022"},
                {"PG0018", "Yupi", "15", "Rp. 37.500.00", "9 Oktober 2022"},
                {"PG0019", "Teh Pucuk", "15", "Rp. 60.000.00", "9 Oktober 2022"},
                {"PG0020", "Coca Cola", "15", "Rp. 60.000.00", "9 Oktober 2022"},
                {"PG0021", "Ichi Ocha 350ml", "15", "Rp. 30.000.00", "9 Oktober 2022"},
                {"PG0022", "Yupi", "10", "Rp. 25.000.00", "9 Oktober 2022"},
                {"PG0023", "Aqua 500ml", "15", "Rp. 75.000.00", "9 Oktober 2022"}
            },
            new String [] {
                "ID Pengeluaran", "Nama Barang", "Jumlah Barang", "Total Pengeluaran", "Tanggal"
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

        LPHARIAN.add(lpHarian, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 500, 520));

        tabLaporan.addTab("Harian", LPHARIAN);

        LPMINGGUAN.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabelDataM.setBackground(new java.awt.Color(255, 255, 255));
        tabelDataM.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        tabelDataM.setForeground(new java.awt.Color(0, 0, 0));
        tabelDataM.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"PG0001", "Aqua 1L", "25", "Rp. 125.000.00", "1 Oktober 2022"},
                {"PG0002", "Coca Cola", "20", "Rp. 80.000.00", "1 Oktober 2022"},
                {"PG0003", "Aqua 500ml", "30", "Rp. 100.000.00", "1 Oktober 2022"},
                {"PG0004", "Pulpen Snowman", "30", "Rp. 75.000.00", "1 Oktober 2022"},
                {"PG0005", "Sprite", "15", "Rp. 48.000.00", "1 Oktober 2022"},
                {"PG0006", "Indomilk", "5", "Rp. 40.000.00", "1 Oktober 2022"},
                {"PG0007", "Nabati Wafer", "15", "Rp. 52.000.00", "1 Oktober 2022"},
                {"PG0008", "Ichi Ocha 350ml", "15", "Rp. 30.000.00", "1 Oktober 2022"},
                {"PG0009", "Oreo", "15", "Rp. 30.000.00", "1 Oktober 2022"},
                {"PG0010", "Kertas HVS", "800", "Rp. 200.000.00", "1 Oktober 2022"},
                {"PG0011", "Kertas Folio", "900", "Rp. 237.000.00", "1 Oktober 2022"},
                {"PG0012", "Spidol Hitam", "15", "Rp. 30.000.00", "9 Oktober 2022"},
                {"PG0013", "Spidol Merah", "15", "Rp. 37.500.00", "9 Oktober 2022"},
                {"PG0014", "Spidol Biru", "15", "Rp. 37.500.00", "9 Oktober 2022"},
                {"PG0015", "Nabati Wafer", "15", "Rp. 52.500.00", "9 Oktober 2022"},
                {"PG0016", "Roti", "20", "Rp. 20.000.00", "9 Oktober 2022"},
                {"PG0017", "Sprite", "20", "Rp. 60.000.00", "9 Oktober 2022"},
                {"PG0018", "Yupi", "15", "Rp. 37.500.00", "9 Oktober 2022"},
                {"PG0019", "Teh Pucuk", "15", "Rp. 60.000.00", "9 Oktober 2022"},
                {"PG0020", "Coca Cola", "15", "Rp. 60.000.00", "9 Oktober 2022"},
                {"PG0021", "Ichi Ocha 350ml", "15", "Rp. 30.000.00", "9 Oktober 2022"},
                {"PG0022", "Yupi", "10", "Rp. 25.000.00", "9 Oktober 2022"},
                {"PG0023", "Aqua 500ml", "15", "Rp. 75.000.00", "9 Oktober 2022"}
            },
            new String [] {
                "ID Pengeluaran", "Nama Barang", "Jumlah Barang", "Total Pengeluaran", "Tanggal"
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

        LPMINGGUAN.add(lpMingguan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 500, 530));

        tabLaporan.addTab("Mingguan", LPMINGGUAN);

        LPBULANAN.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabelDataB.setBackground(new java.awt.Color(255, 255, 255));
        tabelDataB.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        tabelDataB.setForeground(new java.awt.Color(0, 0, 0));
        tabelDataB.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"PG0001", "Aqua 1L", "25", "Rp. 125.000.00", "1 Oktober 2022"},
                {"PG0002", "Coca Cola", "20", "Rp. 80.000.00", "1 Oktober 2022"},
                {"PG0003", "Aqua 500ml", "30", "Rp. 100.000.00", "1 Oktober 2022"},
                {"PG0004", "Pulpen Snowman", "30", "Rp. 75.000.00", "1 Oktober 2022"},
                {"PG0005", "Sprite", "15", "Rp. 48.000.00", "1 Oktober 2022"},
                {"PG0006", "Indomilk", "5", "Rp. 40.000.00", "1 Oktober 2022"},
                {"PG0007", "Nabati Wafer", "15", "Rp. 52.000.00", "1 Oktober 2022"},
                {"PG0008", "Ichi Ocha 350ml", "15", "Rp. 30.000.00", "1 Oktober 2022"},
                {"PG0009", "Oreo", "15", "Rp. 30.000.00", "1 Oktober 2022"},
                {"PG0010", "Kertas HVS", "800", "Rp. 200.000.00", "1 Oktober 2022"},
                {"PG0011", "Kertas Folio", "900", "Rp. 237.000.00", "1 Oktober 2022"},
                {"PG0012", "Spidol Hitam", "15", "Rp. 30.000.00", "9 Oktober 2022"},
                {"PG0013", "Spidol Merah", "15", "Rp. 37.500.00", "9 Oktober 2022"},
                {"PG0014", "Spidol Biru", "15", "Rp. 37.500.00", "9 Oktober 2022"},
                {"PG0015", "Nabati Wafer", "15", "Rp. 52.500.00", "9 Oktober 2022"},
                {"PG0016", "Roti", "20", "Rp. 20.000.00", "9 Oktober 2022"},
                {"PG0017", "Sprite", "20", "Rp. 60.000.00", "9 Oktober 2022"},
                {"PG0018", "Yupi", "15", "Rp. 37.500.00", "9 Oktober 2022"},
                {"PG0019", "Teh Pucuk", "15", "Rp. 60.000.00", "9 Oktober 2022"},
                {"PG0020", "Coca Cola", "15", "Rp. 60.000.00", "9 Oktober 2022"},
                {"PG0021", "Ichi Ocha 350ml", "15", "Rp. 30.000.00", "9 Oktober 2022"},
                {"PG0022", "Yupi", "10", "Rp. 25.000.00", "9 Oktober 2022"},
                {"PG0023", "Aqua 500ml", "15", "Rp. 75.000.00", "9 Oktober 2022"}
            },
            new String [] {
                "ID Pengeluaran", "Nama Barang", "Jumlah Barang", "Total Pengeluaran", "Tanggal"
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

        LPBULANAN.add(lpBulanan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 500, 540));

        tabLaporan.addTab("Bulanan", LPBULANAN);

        LPTAHUNAN.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabelDataT.setBackground(new java.awt.Color(255, 255, 255));
        tabelDataT.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        tabelDataT.setForeground(new java.awt.Color(0, 0, 0));
        tabelDataT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"PG0001", "Aqua 1L", "25", "Rp. 125.000.00", "1 Oktober 2022"},
                {"PG0002", "Coca Cola", "20", "Rp. 80.000.00", "1 Oktober 2022"},
                {"PG0003", "Aqua 500ml", "30", "Rp. 100.000.00", "1 Oktober 2022"},
                {"PG0004", "Pulpen Snowman", "30", "Rp. 75.000.00", "1 Oktober 2022"},
                {"PG0005", "Sprite", "15", "Rp. 48.000.00", "1 Oktober 2022"},
                {"PG0006", "Indomilk", "5", "Rp. 40.000.00", "1 Oktober 2022"},
                {"PG0007", "Nabati Wafer", "15", "Rp. 52.000.00", "1 Oktober 2022"},
                {"PG0008", "Ichi Ocha 350ml", "15", "Rp. 30.000.00", "1 Oktober 2022"},
                {"PG0009", "Oreo", "15", "Rp. 30.000.00", "1 Oktober 2022"},
                {"PG0010", "Kertas HVS", "800", "Rp. 200.000.00", "1 Oktober 2022"},
                {"PG0011", "Kertas Folio", "900", "Rp. 237.000.00", "1 Oktober 2022"},
                {"PG0012", "Spidol Hitam", "15", "Rp. 30.000.00", "9 Oktober 2022"},
                {"PG0013", "Spidol Merah", "15", "Rp. 37.500.00", "9 Oktober 2022"},
                {"PG0014", "Spidol Biru", "15", "Rp. 37.500.00", "9 Oktober 2022"},
                {"PG0015", "Nabati Wafer", "15", "Rp. 52.500.00", "9 Oktober 2022"},
                {"PG0016", "Roti", "20", "Rp. 20.000.00", "9 Oktober 2022"},
                {"PG0017", "Sprite", "20", "Rp. 60.000.00", "9 Oktober 2022"},
                {"PG0018", "Yupi", "15", "Rp. 37.500.00", "9 Oktober 2022"},
                {"PG0019", "Teh Pucuk", "15", "Rp. 60.000.00", "9 Oktober 2022"},
                {"PG0020", "Coca Cola", "15", "Rp. 60.000.00", "9 Oktober 2022"},
                {"PG0021", "Ichi Ocha 350ml", "15", "Rp. 30.000.00", "9 Oktober 2022"},
                {"PG0022", "Yupi", "10", "Rp. 25.000.00", "9 Oktober 2022"},
                {"PG0023", "Aqua 500ml", "15", "Rp. 75.000.00", "9 Oktober 2022"}
            },
            new String [] {
                "ID Pengeluaran", "Nama Barang", "Jumlah Barang", "Total Pengeluaran", "Tanggal"
            }
        ));
        tabelDataT.setGridColor(new java.awt.Color(0, 0, 0));
        tabelDataT.setSelectionBackground(new java.awt.Color(26, 164, 250));
        tabelDataT.setSelectionForeground(new java.awt.Color(250, 246, 246));
        tabelDataT.getTableHeader().setReorderingAllowed(false);
        tabelDataT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelDataTMouseClicked(evt);
            }
        });
        tabelDataT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabelDataTKeyPressed(evt);
            }
        });
        lpTahunan.setViewportView(tabelDataT);

        LPTAHUNAN.add(lpTahunan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 500, 570));

        tabLaporan.addTab("Tahunan", LPTAHUNAN);

        add(tabLaporan, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 90, 505, 600));

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/gambar/app-laporan-pengeluaran-075.png"))); // NOI18N
        background.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
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
                    delete = this.trb.deleteTransaksiBeli(this.idTr);
                    // mengecek apakah data pembeli berhasil terhapus atau tidak
                    if(delete){
                        Message.showInformation(this, "Data berhasil dihapus!");
                        // mengupdate tabel
                        this.updateTabel(tabelDataS);
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
        this.btnDel.setIcon(Gambar.getIcon("ic-data-hapus-entered.png"));
    }//GEN-LAST:event_btnDelMouseEntered

    private void btnDelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDelMouseExited
        this.btnDel.setIcon(Gambar.getIcon("ic-data-hapus.png"));
    }//GEN-LAST:event_btnDelMouseExited

    private void inpCariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpCariKeyTyped
        String key = this.inpCari.getText();
        this.keyword = "WHERE id_tr_beli LIKE '%"+key+"%' OR id_barang LIKE '%"+key+"%'";
        this.updateTabel(tabelDataS);
    }//GEN-LAST:event_inpCariKeyTyped

    private void inpCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpCariKeyReleased
        String key = this.inpCari.getText();
        this.keyword = "WHERE id_tr_beli LIKE '%"+key+"%' OR id_barang LIKE '%"+key+"%'";
        this.updateTabel(tabelDataS);
    }//GEN-LAST:event_inpCariKeyReleased

    private void tabelDataBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelDataBKeyPressed
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        if(evt.getKeyCode() == KeyEvent.VK_UP){
            this.idSelected = this.tabelDataB.getValueAt(tabelDataB.getSelectedRow() - 1, 0).toString();
            this.showData(tabelDataB);
        }else if(evt.getKeyCode() == KeyEvent.VK_DOWN){
            this.idSelected = this.tabelDataB.getValueAt(tabelDataB.getSelectedRow() + 1, 0).toString();
            this.showData(tabelDataB);
        }
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_tabelDataBKeyPressed

    private void tabelDataBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelDataBMouseClicked
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        // menampilkan data pembeli
        this.idSelected = this.tabelDataB.getValueAt(tabelDataB.getSelectedRow(), 0).toString();
        this.showData(tabelDataB);
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_tabelDataBMouseClicked

    private void tabelDataMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelDataMKeyPressed
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        if(evt.getKeyCode() == KeyEvent.VK_UP){
            this.idSelected = this.tabelDataM.getValueAt(tabelDataM.getSelectedRow() - 1, 0).toString();
            this.showData(tabelDataM);
        }else if(evt.getKeyCode() == KeyEvent.VK_DOWN){
            this.idSelected = this.tabelDataM.getValueAt(tabelDataM.getSelectedRow() + 1, 0).toString();
            this.showData(tabelDataM);
        }
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_tabelDataMKeyPressed

    private void tabelDataMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelDataMMouseClicked
        // TODO add your handling code here:
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        // menampilkan data pembeli
        this.idSelected = this.tabelDataM.getValueAt(tabelDataM.getSelectedRow(), 0).toString();
        this.showData(tabelDataM);
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_tabelDataMMouseClicked

    private void tabelDataHKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelDataHKeyPressed
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        if(evt.getKeyCode() == KeyEvent.VK_UP){
            this.idSelected = this.tabelDataH.getValueAt(tabelDataH.getSelectedRow() - 1, 0).toString();
            this.showData(tabelDataH);
        }else if(evt.getKeyCode() == KeyEvent.VK_DOWN){
            this.idSelected = this.tabelDataH.getValueAt(tabelDataH.getSelectedRow() + 1, 0).toString();
            this.showData(tabelDataH);
        }
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_tabelDataHKeyPressed

    private void tabelDataHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelDataHMouseClicked
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        // menampilkan data pembeli
        this.idSelected = this.tabelDataH.getValueAt(tabelDataH.getSelectedRow(), 0).toString();
        this.showData(tabelDataH);
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_tabelDataHMouseClicked

    private void tabelDataSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelDataSKeyPressed
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        if(evt.getKeyCode() == KeyEvent.VK_UP){
            this.idSelected = this.tabelDataS.getValueAt(tabelDataS.getSelectedRow() - 1, 0).toString();
            this.showData(tabelDataS);
        }else if(evt.getKeyCode() == KeyEvent.VK_DOWN){
            this.idSelected = this.tabelDataS.getValueAt(tabelDataS.getSelectedRow() + 1, 0).toString();
            this.showData(tabelDataS);
        }
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_tabelDataSKeyPressed

    private void tabelDataSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelDataSMouseClicked
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        // menampilkan data pembeli
        this.idSelected = this.tabelDataS.getValueAt(tabelDataS.getSelectedRow(), 0).toString();
        this.showData(tabelDataS);
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_tabelDataSMouseClicked

    private void tabelDataTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelDataTMouseClicked
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        // menampilkan data pembeli
        this.idSelected = this.tabelDataT.getValueAt(tabelDataT.getSelectedRow(), 0).toString();
        this.showData(tabelDataT);
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_tabelDataTMouseClicked

    private void tabelDataTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelDataTKeyPressed
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        if(evt.getKeyCode() == KeyEvent.VK_UP){
            this.idSelected = this.tabelDataT.getValueAt(tabelDataT.getSelectedRow() - 1, 0).toString();
            this.showData(tabelDataT);
        }else if(evt.getKeyCode() == KeyEvent.VK_DOWN){
            this.idSelected = this.tabelDataT.getValueAt(tabelDataT.getSelectedRow() + 1, 0).toString();
            this.showData(tabelDataT);
        }
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_tabelDataTKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel LPBULANAN;
    private javax.swing.JPanel LPHARIAN;
    private javax.swing.JPanel LPMINGGUAN;
    private javax.swing.JPanel LPSEMUA;
    private javax.swing.JPanel LPTAHUNAN;
    private javax.swing.JLabel background;
    private javax.swing.JButton btnDel;
    private javax.swing.JTextField inpCari;
    private javax.swing.JScrollPane lpBulanan;
    private javax.swing.JScrollPane lpHarian;
    private javax.swing.JScrollPane lpMingguan;
    private javax.swing.JScrollPane lpSemua;
    private javax.swing.JScrollPane lpTahunan;
    private javax.swing.JTabbedPane tabLaporan;
    private javax.swing.JTable tabelDataB;
    private javax.swing.JTable tabelDataH;
    private javax.swing.JTable tabelDataM;
    private javax.swing.JTable tabelDataS;
    private javax.swing.JTable tabelDataT;
    private javax.swing.JLabel valHarga;
    private javax.swing.JLabel valIDPengeluaran;
    private javax.swing.JLabel valIDTransaksi;
    private javax.swing.JLabel valJenisBrg;
    private javax.swing.JLabel valJumlahBrg;
    private javax.swing.JLabel valNamaBarang;
    private javax.swing.JLabel valNamaKaryawan;
    private javax.swing.JLabel valNamaSupplier;
    private javax.swing.JLabel valTanggal;
    // End of variables declaration//GEN-END:variables
}
