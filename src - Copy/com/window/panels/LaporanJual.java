package com.window.panels;

import com.data.db.Database;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.event.*;
//import java.awt.Color;
//import java.awt.Cursor;
//import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private final Database db = new Database();
    
    private final ManageTransaksiJual trj = new ManageTransaksiJual();
    
    private final Petugas petugas = new Petugas();
    
    private final Pembeli pembeli = new Pembeli();
    
    private final Barang barang = new Barang();
    
    private final Text text = new Text();
    
    DateFormat tanggalMilis = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private final DateFormat tanggalFull = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss ");
    private final DateFormat date = new SimpleDateFormat("dd-MM-yyyy");
    private final DateFormat date1 = new SimpleDateFormat("yyyy-MM-dd");
    private final DateFormat time = new SimpleDateFormat("ss:mm:hh");
    private final DateFormat timeMillis = new SimpleDateFormat("ss.SSS:s=mm:hh");
        
    private int hari,bulan, tahun;
    
    private String minggu[];
    private final Waktu waktu = new Waktu();
    private String tPemasukan;
    private int selectedIndex;
    private String idSelected = "", keyword = "", idTr, idPd, idBg, namaPembeli, namaPetugas, namaBarang, jenis, jumlah, totalHrg, tanggal;
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
    public LaporanJual() throws ParseException {
        initComponents();
        int total = getTotal("transaksi_jual", "total_hrg","");
        System.out.println("total data adalah "+ total);
        this.hari = waktu.getTanggal();
        this.minggu = waktu.getMinggu1(this.hari, -1);
        this.bulan = waktu.getBulan()+1;
        this.tahun = waktu.getTahun();
        tPemasukan = text.toMoneyCase(Integer.toString(getTotal("transaksi_jual", "total_hrg","")));
        System.out.println("Pemasukan adalah "+tPemasukan);
        valTotalS.setText(tPemasukan);
        tabPendapatan.addChangeListener(new javax.swing.event.ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                JTabbedPane tabbedPane = (JTabbedPane) e.getSource();
                selectedIndex = tabbedPane.getSelectedIndex()+1;
                switch(selectedIndex){
                    case 1:
                        System.out.println("Menampilkan Panel semua");
                        tPemasukan = text.toMoneyCase(Integer.toString(getTotal("transaksi_jual", "total_hrg","")));
                        valTotalS.setText(tPemasukan);
                        break;
                    case 2:
                        System.out.println("Menampilkan Panel harian");
                        tPemasukan = text.toMoneyCase(Integer.toString(getTotal("transaksi_jual", "total_hrg","WHERE YEAR(tanggal) = '"+tahun+"' AND MONTH(tanggal) = '"+bulan+"' AND DAY(tanggal) = '"+hari+"'")));
                        valTotalH.setText(tPemasukan);
                        break;
                    case 3:
                        System.out.println("Menampilkan Panel mingguan");
                        tPemasukan = text.toMoneyCase(Integer.toString(getTotal("transaksi_jual", "total_hrg","WHERE (tanggal BETWEEN '"+minggu[0]+"' AND '"+minggu[1]+"')")));
                        valTotalM.setText(tPemasukan);
                        break;
                    case 4:
                        System.out.println("Menampilkan Panel bulanan");
                        tPemasukan = text.toMoneyCase(Integer.toString(getTotal("transaksi_jual", "total_hrg","WHERE MONTH(tanggal) = '"+bulan+"'")));
                        valTotalB.setText(tPemasukan);
                        break;
                    case 5:
                        System.out.println("Menampilkan Panel tahunan");
                        tPemasukan = text.toMoneyCase(Integer.toString(getTotal("transaksi_jual", "total_hrg","WHERE YEAR(tanggal) = '"+tahun+"'")));
                        valTotalT.setText(tPemasukan);
                        break;
                }
//        JOptionPane.showMessageDialog(null, "Selected Index: " + selectedIndex);
            }
        });
//        this.btnPrint.setUI(new javax.swing.plaf.basic.BasicButtonUI());
//        this.btnEdit.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        
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
        
        this.tabelDataT.setRowHeight(29);
        this.tabelDataT.getTableHeader().setBackground(new java.awt.Color(255,255,255));
        this.tabelDataT.getTableHeader().setForeground(new java.awt.Color(0, 0, 0));
        
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
//        this.showLineChart1();
//        this.showPieChart();
        System.out.println("hari "+hari);
        System.out.println("bulan "+bulan);
        System.out.println("tahun "+tahun);
        System.out.println("Minggu ke 1 "+ minggu[0]);
        System.out.println("Minggu ke 2 "+ minggu[1]);
        String hari1 = minggu[0].substring(8, 10);
        String hari2 = minggu[1].substring(8, 10);
        String bulan1 = minggu[0].substring(5, 7);
        String bulan2 = minggu[1].substring(5, 7);
        String tahun1 = minggu[0].substring(0, 4);
        String tahun2 = minggu[1].substring(0, 4);
        System.out.println("hari awal "+hari1+" "+bulan1+" "+tahun1);
        System.out.println("hari akhir "+hari2+" "+bulan2+" "+tahun2);
        this.updateTabel(tabelDataS);
//        SELECT * FROM gemastik_lightning.laporan_pengeluaran WHERE tanggal = '2022-10-22'
        keyword = "WHERE YEAR(tanggal) = '"+tahun+"' AND MONTH(tanggal) = '"+bulan+"' AND DAY(tanggal) = '"+hari+"'";
        System.out.println("isi keyword :"+keyword);
        this.updateTabel(tabelDataH);
        keyword = "WHERE (tanggal BETWEEN '"+minggu[0]+"' AND '" +minggu[1]+"')";
//        SELECT * FROM gemastik_lightning.laporan_pengeluaran WHERE (tanggal BETWEEN '2022-10-22' AND '2022-10-23');
        System.out.println("isi keyword :"+keyword);
//        keyword = "";
        this.updateTabel(tabelDataM);
        keyword = "WHERE MONTH(tanggal) = '"+bulan+"'";
        System.out.println("isi keyword :"+keyword);
//        SELECT * FROM gemastik_lightning.laporan_pengeluaran WHERE MONTH(tanggal) = '11'
        this.updateTabel(tabelDataB);
        keyword = "WHERE YEAR(tanggal) = '"+tahun+"'";
        System.out.println("isi keyword :"+keyword);
//        SELECT * FROM gemastik_lightning.laporan_pengeluaran WHERE YEAR(tanggal) = '2022'
        this.updateTabel(tabelDataT);
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
    
    private Object[][] getData() throws ParseException{
        try{
            Date tanggalData = new Date();
            String data;
            Object obj[][];
            int rows = 0;
            String sql = "SELECT id_tr_jual, id_barang, jumlah_brg, total_hrg, tanggal FROM transaksi_jual " + keyword + " ORDER BY id_tr_jual DESC";
            // mendefinisikan object berdasarkan total rows dan cols yang ada didalam tabel
            System.out.println(sql);
            obj = new Object[trj.getJumlahData("transaksi_jual", keyword)][6];
            // mengeksekusi query
            trj.res = trj.stat.executeQuery(sql);
            // mendapatkan semua data yang ada didalam tabel
            while(trj.res.next()){
                // menyimpan data dari tabel ke object
                obj[rows][0] = trj.res.getString("id_tr_jual").replace("TRJ", "LPD");
                obj[rows][1] = barang.getNamaBarang(trj.res.getString("id_barang"));
                obj[rows][2] = trj.res.getString("jumlah_brg");
                obj[rows][3] = text.toMoneyCase(trj.res.getString("total_hrg"));
                tanggalData = tanggalMilis.parse(trj.res.getString("tanggal"));
                obj[rows][4] = date.format(tanggalData);
                obj[rows][5] = time.format(tanggalData);
                rows++; // rows akan bertambah 1 setiap selesai membaca 1 row pada tabel
            }
            return obj;
        }catch(SQLException ex){
            ex.printStackTrace();
            Message.showException(this, "Terjadi kesalahan saat mengambil data dari database\n" + ex.getMessage(), ex, true);
        }
        return null;
    }
    
    private void updateTabel(JTable tabel) throws ParseException{
        tabel.setModel(new javax.swing.table.DefaultTableModel(
            getData(),
            new String [] {
                "ID Pendapatan", "Nama Barang", "Jumlah Barang", "Total Pendapatan", "Tanggal","Waktu"
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
    
    private void showData(JTable tabel) throws ParseException{
        // mendapatkan data-data
        this.idTr = tabel.getValueAt(tabel.getSelectedRow(), 0).toString().replace("LPD", "TRJ");
        System.out.println(this.idTr);
        this.idPd = this.idTr.replace("TRJ", "LPD");
        this.namaPembeli = this.pembeli.getNama(this.trj.getIdPembeli(this.idTr));
        this.namaPetugas = this.petugas.getNama(this.trj.getIdPetugas(this.idTr));
        this.namaBarang = this.barang.getNamaBarang(this.trj.getIdBarang(this.idTr));
        this.jenis = text.toCapitalize(this.barang.getJenis(this.trj.getIdBarang(this.idTr)));
        this.jumlah = Integer.toString(this.trj.getJumlahBarang(this.idTr));
        this.totalHrg = text.toMoneyCase(this.trj.getTotalHarga(this.idTr));
//        this.tanggal = this.text.toDateCase(this.trj.getTanggal(this.idTr));
        String tanggal1 = this.trj.getTanggal(this.idTr);
        Date d = tanggalMilis.parse(tanggal1);
        this.tanggal = date.format(d);
//        System.out.println(tanggal2[1]);

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
        tabPendapatan = new javax.swing.JTabbedPane();
        LPSEMUA = new javax.swing.JPanel();
        lpSemua1 = new javax.swing.JScrollPane();
        tabelDataS = new javax.swing.JTable();
        valTotalS = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        LPHARIAN = new javax.swing.JPanel();
        lpHarian1 = new javax.swing.JScrollPane();
        tabelDataH = new javax.swing.JTable();
        valTotalH = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        LPMNGGUAN = new javax.swing.JPanel();
        lpMingguan1 = new javax.swing.JScrollPane();
        tabelDataM = new javax.swing.JTable();
        valTotalM = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        LPBULANAN = new javax.swing.JPanel();
        lpBulanan1 = new javax.swing.JScrollPane();
        tabelDataB = new javax.swing.JTable();
        valTotalB = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        LPTAHUNAN = new javax.swing.JPanel();
        lpTahunan1 = new javax.swing.JScrollPane();
        tabelDataT = new javax.swing.JTable();
        valTotalT = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
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

        LPSEMUA.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lpSemua1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lpSemua1MouseClicked(evt);
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
        lpSemua1.setViewportView(tabelDataS);

        LPSEMUA.add(lpSemua1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 500, 520));

        valTotalS.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        valTotalS.setForeground(new java.awt.Color(0, 0, 0));
        valTotalS.setText(":");
        LPSEMUA.add(valTotalS, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 530, 290, 36));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/gambar_icon/laporan-pemasukan-075.png"))); // NOI18N
        jLabel1.setText("lbll");
        LPSEMUA.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 530, 490, -1));

        tabPendapatan.addTab("Semua", LPSEMUA);

        LPHARIAN.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        lpHarian1.setViewportView(tabelDataH);

        LPHARIAN.add(lpHarian1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 500, 520));

        valTotalH.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        valTotalH.setForeground(new java.awt.Color(0, 0, 0));
        valTotalH.setText(":");
        LPHARIAN.add(valTotalH, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 530, 290, 36));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/gambar_icon/laporan-pemasukan-075.png"))); // NOI18N
        jLabel2.setText("lbll");
        LPHARIAN.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 530, 490, -1));

        tabPendapatan.addTab("Harian", LPHARIAN);

        LPMNGGUAN.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        lpMingguan1.setViewportView(tabelDataM);

        LPMNGGUAN.add(lpMingguan1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 500, 520));

        valTotalM.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        valTotalM.setForeground(new java.awt.Color(0, 0, 0));
        valTotalM.setText(":");
        LPMNGGUAN.add(valTotalM, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 530, 290, 36));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/gambar_icon/laporan-pemasukan-075.png"))); // NOI18N
        jLabel3.setText("lbll");
        LPMNGGUAN.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 530, 490, -1));

        tabPendapatan.addTab("Mingguan", LPMNGGUAN);

        LPBULANAN.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        lpBulanan1.setViewportView(tabelDataB);

        LPBULANAN.add(lpBulanan1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 500, 520));

        valTotalB.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        valTotalB.setForeground(new java.awt.Color(0, 0, 0));
        valTotalB.setText(":");
        LPBULANAN.add(valTotalB, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 530, 290, 36));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/gambar_icon/laporan-pemasukan-075.png"))); // NOI18N
        jLabel4.setText("lbll");
        LPBULANAN.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 530, 490, -1));

        tabPendapatan.addTab("Bulanan", LPBULANAN);

        LPTAHUNAN.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabelDataT.setBackground(new java.awt.Color(255, 255, 255));
        tabelDataT.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        tabelDataT.setForeground(new java.awt.Color(0, 0, 0));
        tabelDataT.setModel(new javax.swing.table.DefaultTableModel(
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
        lpTahunan1.setViewportView(tabelDataT);

        LPTAHUNAN.add(lpTahunan1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 500, 520));

        valTotalT.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        valTotalT.setForeground(new java.awt.Color(0, 0, 0));
        valTotalT.setText(":");
        LPTAHUNAN.add(valTotalT, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 530, 290, 36));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/gambar_icon/laporan-pemasukan-075.png"))); // NOI18N
        jLabel5.setText("lbll");
        LPTAHUNAN.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 530, 490, -1));

        tabPendapatan.addTab("Tahunan", LPTAHUNAN);

        add(tabPendapatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 90, 505, 600));

        bacground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/gambar/app-laporan-pemasukan-075.png"))); // NOI18N
        add(bacground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void tabelDataSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelDataSMouseClicked
        try {
            this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            this.idSelected = this.tabelDataS.getValueAt(tabelDataS.getSelectedRow(), 0).toString();
            this.showData(this.tabelDataS);
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } catch (ParseException ex) {
            Logger.getLogger(LaporanJual.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tabelDataSMouseClicked

    private void tabelDataSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelDataSKeyPressed
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        if(evt.getKeyCode() == KeyEvent.VK_UP){
            try {
                this.idSelected = this.tabelDataS.getValueAt(tabelDataS.getSelectedRow() - 1, 0).toString();
                this.showData(this.tabelDataS);
            } catch (ParseException ex) {
                Logger.getLogger(LaporanJual.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(evt.getKeyCode() == KeyEvent.VK_DOWN){
            try {
                this.idSelected = this.tabelDataS.getValueAt(tabelDataS.getSelectedRow() + 1, 0).toString();
                this.showData(this.tabelDataS);
            } catch (ParseException ex) {
                Logger.getLogger(LaporanJual.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_tabelDataSKeyPressed

    private void inpCariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpCariKeyTyped
        try {
            String key = this.inpCari.getText();
            String data = "";
            if(key.contains("TRJ")|| key.contains("LPD")|| key.contains("trj")|| key.contains("lpd")){
                data = "TRJ"+key.substring(3, key.length());
            }
            System.out.println("id "+ data);
            this.keyword = "WHERE id_tr_jual LIKE '%"+data+"%' OR id_barang LIKE '%"+data+"%'";
            JTable tabel = new JTable();
            switch(selectedIndex){
                case 1:
                    tabel = this.tabelDataS;
                    break;
                case 2:
                    tabel = this.tabelDataH;
                    break;
                case 3:
                    tabel = this.tabelDataM;
                    break;
                case 4:
                    tabel = this.tabelDataB;
                    break;
                case 5:
                    tabel = this.tabelDataT;
                    break;
            }
            this.updateTabel(tabel);
        } catch (ParseException ex) {
            Logger.getLogger(LaporanJual.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_inpCariKeyTyped

    private void inpCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpCariKeyReleased
        try {
            String key = this.inpCari.getText();
            String data = "";
            if(key.contains("TRJ")|| key.contains("LPD")|| key.contains("trj")|| key.contains("lpd")){
                data = "TRJ"+key.substring(3, key.length());
            }
            System.out.println("id "+ data);
            this.keyword = "WHERE id_tr_jual LIKE '%"+data+"%' OR id_barang LIKE '%"+data+"%'";
            JTable tabel = new JTable();
            switch(selectedIndex){
                case 1:
                    tabel = this.tabelDataS;
                    break;
                case 2:
                    tabel = this.tabelDataH;
                    break;
                case 3:
                    tabel = this.tabelDataM;
                    break;
                case 4:
                    tabel = this.tabelDataB;
                    break;
                case 5:
                    tabel = this.tabelDataT;
                    break;
            }
            this.updateTabel(tabel);
        } catch (ParseException ex) {
            Logger.getLogger(LaporanJual.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_inpCariKeyReleased

    private void tabelDataHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelDataHMouseClicked
        try {
            // TODO add your handling code here:
            this.idSelected = this.tabelDataH.getValueAt(tabelDataH.getSelectedRow(), 0).toString();
            this.showData(this.tabelDataH);
        } catch (ParseException ex) {
            Logger.getLogger(LaporanJual.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tabelDataHMouseClicked

    private void tabelDataHKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelDataHKeyPressed
        try{
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        if(evt.getKeyCode() == KeyEvent.VK_UP){
                this.idSelected = this.tabelDataH.getValueAt(tabelDataH.getSelectedRow() - 1, 0).toString();
                this.showData(this.tabelDataH);
        }else if(evt.getKeyCode() == KeyEvent.VK_DOWN){
            this.idSelected = this.tabelDataH.getValueAt(tabelDataH.getSelectedRow() + 1, 0).toString();
            this.showData(this.tabelDataH);
        }
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } catch (ParseException ex) {
            Logger.getLogger(LaporanJual.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tabelDataHKeyPressed

    private void tabelDataMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelDataMMouseClicked
        try {
            // TODO add your handling code here:
            this.idSelected = this.tabelDataM.getValueAt(tabelDataM.getSelectedRow(), 0).toString();
            this.showData(this.tabelDataM);
        } catch (ParseException ex) {
            Logger.getLogger(LaporanJual.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tabelDataMMouseClicked

    private void tabelDataMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelDataMKeyPressed
        try {
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        if(evt.getKeyCode() == KeyEvent.VK_UP){
            this.idSelected = this.tabelDataM.getValueAt(tabelDataM.getSelectedRow() - 1, 0).toString();
            this.showData(this.tabelDataM);
        }else if(evt.getKeyCode() == KeyEvent.VK_DOWN){
            this.idSelected = this.tabelDataM.getValueAt(tabelDataM.getSelectedRow() + 1, 0).toString();
            this.showData(this.tabelDataM);
        }
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } catch (ParseException ex) {
            Logger.getLogger(LaporanJual.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tabelDataMKeyPressed

    private void inpCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inpCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inpCariActionPerformed

    private void lpSemua1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lpSemua1MouseClicked
//        // TODO add your handling code here:
//        JTabbedPane tabSource = (JTabbedPane) evt.getSource();
//        String tab = tabSource.getTitleAt(tabSource.getSelectedIndex());
        
    }//GEN-LAST:event_lpSemua1MouseClicked

    private void tabelDataTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelDataTMouseClicked
        try {
            // TODO add your handling code here:
            this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            this.idSelected = this.tabelDataT.getValueAt(tabelDataT.getSelectedRow(), 0).toString();
            this.showData(this.tabelDataT);
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } catch (ParseException ex) {
            Logger.getLogger(LaporanJual.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tabelDataTMouseClicked

    private void tabelDataTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelDataTKeyPressed
        try {
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        if(evt.getKeyCode() == KeyEvent.VK_UP){
            this.idSelected = this.tabelDataT.getValueAt(tabelDataT.getSelectedRow() - 1, 0).toString();
            this.showData(this.tabelDataT);
        }else if(evt.getKeyCode() == KeyEvent.VK_DOWN){
            this.idSelected = this.tabelDataT.getValueAt(tabelDataT.getSelectedRow() + 1, 0).toString();
            this.showData(this.tabelDataT);
        }
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } catch (ParseException ex) {
            Logger.getLogger(LaporanJual.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tabelDataTKeyPressed

    private void tabelDataBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelDataBKeyPressed
        try {
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        if(evt.getKeyCode() == KeyEvent.VK_UP){
            this.idSelected = this.tabelDataB.getValueAt(tabelDataB.getSelectedRow() - 1, 0).toString();
            this.showData(this.tabelDataB);
        }else if(evt.getKeyCode() == KeyEvent.VK_DOWN){
            this.idSelected = this.tabelDataB.getValueAt(tabelDataB.getSelectedRow() + 1, 0).toString();
            this.showData(this.tabelDataB);
        }
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } catch (ParseException ex) {
            Logger.getLogger(LaporanJual.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tabelDataBKeyPressed

    private void tabelDataBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelDataBMouseClicked
        try {
            this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            this.idSelected = this.tabelDataB.getValueAt(tabelDataB.getSelectedRow(), 0).toString();
            this.showData(this.tabelDataB);
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } catch (ParseException ex) {
            Logger.getLogger(LaporanJual.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tabelDataBMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel LPBULANAN;
    private javax.swing.JPanel LPHARIAN;
    private javax.swing.JPanel LPMNGGUAN;
    private javax.swing.JPanel LPSEMUA;
    private javax.swing.JPanel LPTAHUNAN;
    private javax.swing.JLabel bacground;
    private javax.swing.JTextField inpCari;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane lpBulanan1;
    private javax.swing.JScrollPane lpHarian1;
    private javax.swing.JScrollPane lpMingguan1;
    private javax.swing.JScrollPane lpSemua1;
    private javax.swing.JScrollPane lpTahunan1;
    private javax.swing.JTabbedPane tabPendapatan;
    private javax.swing.JTable tabelDataB;
    private javax.swing.JTable tabelDataH;
    private javax.swing.JTable tabelDataM;
    private javax.swing.JTable tabelDataS;
    private javax.swing.JTable tabelDataT;
    private javax.swing.JLabel valIDPendapatan;
    private javax.swing.JLabel valIDTransaksi;
    private javax.swing.JLabel valJenisBarang;
    private javax.swing.JLabel valJumlah;
    private javax.swing.JLabel valNamaBarang;
    private javax.swing.JLabel valNamaKaryawan;
    private javax.swing.JLabel valNamaPembeli;
    private javax.swing.JLabel valTanggal;
    private javax.swing.JLabel valTotalB;
    private javax.swing.JLabel valTotalH;
    private javax.swing.JLabel valTotalHarga;
    private javax.swing.JLabel valTotalM;
    private javax.swing.JLabel valTotalS;
    private javax.swing.JLabel valTotalT;
    // End of variables declaration//GEN-END:variables
}