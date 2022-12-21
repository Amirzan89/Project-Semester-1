package com.window.panels;

import com.data.db.Database;
import com.manage.Barang;
import com.manage.ManageTransaksiBeli;
import com.manage.Message;
import com.manage.Text;
import com.manage.Waktu;
import com.media.Audio;
import com.media.Gambar;
import com.sun.glass.events.KeyEvent;
import com.users.Karyawan;
import com.users.Supplier;
import com.window.dialogs.CetakLaporan;
import java.awt.BorderLayout;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
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
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JTable;

/**
 *
 * @author Gemastik Lightning
 */
public class LaporanBeli extends javax.swing.JPanel {
    private final Database db = new Database();
    private final String namadb = Database.DB_NAME;
    private final ManageTransaksiBeli trb = new ManageTransaksiBeli();
    
    private final Karyawan karyawan = new Karyawan();
    
    private final Supplier supplier = new Supplier();
    
    private final Barang barang = new Barang();
    
    private final Text text = new Text();
    
    DateFormat tanggalMilis = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private final DateFormat tanggalFull = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss ");
    private final DateFormat date = new SimpleDateFormat("dd-MM-yyyy");
    private final DateFormat date1 = new SimpleDateFormat("dd-MM-yyyy");
    private final DateFormat time = new SimpleDateFormat("hh:mm:ss");
    private final DateFormat timeMillis = new SimpleDateFormat("ss.SSS:mm:hh");
        
    private int hari, hari1,bulan,bulan1, tahun,tahun1,bulanan,tahunan;
    
    private Object minggu[];
    private final Waktu waktu = new Waktu();
    private String tPengeluaran;
    private int selectedIndex,totalHrg;
    private String idSelected = "", keyword = "", idTr, idPd,IDKaryawan, namaKaryawan, tanggal,tanggalSekarang,tanggalDipilih;
    private String minggu1 = "", minggu2 = "";
    
    public LaporanBeli() throws ParseException {
        initComponents();
        this.tanggalSekarang = waktu.getCurrentDate();
        this.tanggalDipilih = this.tanggalSekarang;
        minggu1 = this.tanggalSekarang;
        minggu2 = this.tanggalSekarang;
        this.hari = Integer.parseInt(tanggalDipilih.substring(8));
        this.bulan = Integer.parseInt(tanggalDipilih.substring(5,7));
        this.tahun = Integer.parseInt(tanggalDipilih.substring(0,4));
        
        this.hari1 = Integer.parseInt(minggu2.substring(8));
        this.bulan1 = Integer.parseInt(minggu2.substring(5,7));
        this.tahun1 = Integer.parseInt(minggu2.substring(0,4));
        
        this.bulanan = this.bulan;
        this.tahunan = this.tahun;
        System.out.println("hari 1 "+hari1);
        System.out.println("bulan 1 "+bulan1);
        System.out.println("tahun 1 "+tahun1);
        this.minggu = waktu.getMinggu(hari,bulan);
        tPengeluaran = text.toMoneyCase(Integer.toString(getTotal("transaksi_beli", "total_hrg","")));
        valTotalS.setText(tPengeluaran);
        tabPengeluaran.addChangeListener(new javax.swing.event.ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                JTabbedPane tabbedPane = (JTabbedPane) e.getSource();
                selectedIndex = tabbedPane.getSelectedIndex()+1;
                switch(selectedIndex){
                    case 1:
                        System.out.println("Menampilkan Panel semua");
                        tPengeluaran = text.toMoneyCase(Integer.toString(getTotal("transaksi_beli", "total_hrg","")));
                        valTotalS.setText(tPengeluaran);
                        break;
                    case 2:
                        System.out.println("Menampilkan Panel harian");
                        tPengeluaran = text.toMoneyCase(Integer.toString(getTotal("transaksi_beli", "total_hrg","WHERE tanggal >= '"+ tanggalDipilih +"' AND tanggal <= '"+ String.format("%s-%s-%s",tahun,bulan,hari+1)+"'")));
                        valTotalH.setText(tPengeluaran);
                        break;
                    case 3:
                        System.out.println("Menampilkan Panel rentang tanggal");
                        tPengeluaran = text.toMoneyCase(Integer.toString(getTotal("transaksi_beli", "total_hrg","WHERE tanggal >= '"+minggu1+"' AND tanggal <= '"+String.format("%s-%s-%s",tahun1,bulan1,hari1+1)+"'")));
                        valTotalM.setText(tPengeluaran);
                        break;
                    case 4:
                        System.out.println("Menampilkan Panel bulanan");
                        tPengeluaran = text.toMoneyCase(Integer.toString(getTotal("transaksi_beli", "total_hrg","WHERE YEAR(tanggal) = '"+tahunan+"' AND MONTH(tanggal) = '"+bulanan+"'")));
                        valTotalB.setText(tPengeluaran);
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
        
        JLabel[] lbls = new JLabel[]{
            this.valIDPengeluaran,this.valIDKaryawan, this.valIDTransaksi, this.valNamaKaryawan, 
            this.valTanggal, this.valHarga
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
        this.updateTabel(tabelDataS);
        
        keyword = "WHERE tanggal >= '"+ tanggalDipilih +"' AND tanggal <= '"+ String.format("%s-%s-%s",tahun,bulan,hari+1)+"'";
        System.out.println("isi keyword :"+keyword);
        this.updateTabel(tabelDataH);
        
        keyword = "WHERE YEAR(tanggal) = '"+this.tahunan+"' AND MONTH(tanggal) = '"+this.bulanan+"'";
        System.out.println("isi keyword :"+keyword);
        this.updateTabel(tabelDataB);
        
        keyword = "WHERE tanggal >= '"+minggu1+"' AND tanggal <= '"+String.format("%s-%s-%s",tahun1,bulan1,hari1+1)+"'";
        System.out.println("isi keyword :"+keyword);
        this.updateTabel(tabelDataM);
    }
    
    private Statement getStat(){
        try{  
            Class.forName("com.mysql.jdbc.Driver");  
            Connection con=DriverManager.getConnection(  
                "jdbc:mysql://localhost:3306/"+this.namadb,"root","");  
            Statement stmt=con.createStatement();
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
            System.out.println(sql);
            ResultSet res = stat.executeQuery(sql);
            while(res.next()){
                data = res.getInt("total");
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
    
    private void updateTabel(JTable tabel) throws ParseException{
        try{
            DefaultTableModel model = (DefaultTableModel) tabel.getModel();
            Date tanggalData = new Date();
            int rows = 0;
            String data[] = new String[6];
            String sql = "SELECT id_tr_beli, id_karyawan, total_hrg, tanggal FROM transaksi_beli " + keyword + " ORDER BY id_tr_beli DESC";
//            System.out.println(sql);
            // mengeksekusi query
            trb.res = trb.stat.executeQuery(sql);
            // mendapatkan semua data yang ada didalam tabel
            while(trb.res.next()){
                // menyimpan data dari tabel ke object
                data[0] = trb.res.getString("id_tr_beli").replace("TRB", "LPG");
                data[1] = trb.res.getString("id_karyawan");
                data[2] = this.karyawan.getNama(trb.res.getString("id_karyawan"));
                data[3] = text.toMoneyCase(trb.res.getString("total_hrg"));
                tanggalData = tanggalMilis.parse(trb.res.getString("tanggal"));
                data[4] = date.format(tanggalData);
                data[5] = time.format(tanggalData);
                model.addRow(data);
            }
            tabel.setModel(model);
        }catch(SQLException ex){
            ex.printStackTrace();
            Message.showException(this, "Terjadi kesalahan saat mengambil data dari database\n" + ex.getMessage(), ex, true);
        }
    }
    
    private void showData(JTable tabel) throws ParseException{
        // mendapatkan data-data
        this.idTr = tabel.getValueAt(tabel.getSelectedRow(), 0).toString().replace("LPG", "TRB");
        this.idPd = this.idTr.replace("TRB", "LPG");
        this.IDKaryawan = tabel.getValueAt(tabel.getSelectedRow(),1).toString();
        this.namaKaryawan = tabel.getValueAt(tabel.getSelectedRow(),2).toString();
        this.totalHrg = text.toIntCase(tabel.getValueAt(tabel.getSelectedRow(),3).toString());
        String tanggal1 = this.trb.getTanggal(this.idTr);
        Date d = tanggalMilis.parse(tanggal1);
        this.tanggal = date.format(d);

        // menampilkan data-data
        this.valIDTransaksi.setText("<html><p>:&nbsp;"+this.idTr+"</p></html>");
        this.valIDPengeluaran.setText("<html><p>:&nbsp;"+this.idPd+"</p></html>");
        this.valIDKaryawan.setText("<html><p>:&nbsp;"+this.IDKaryawan+"</p></html>");
        this.valNamaKaryawan.setText("<html><p>:&nbsp;"+this.namaKaryawan+"</p></html>");
        this.valHarga.setText("<html><p>:&nbsp;"+this.totalHrg+"</p></html>");
        this.valTanggal.setText("<html><p>:&nbsp;"+this.tanggal+"</p></html>");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        valIDPengeluaran = new javax.swing.JLabel();
        valIDTransaksi = new javax.swing.JLabel();
        valIDKaryawan = new javax.swing.JLabel();
        valNamaKaryawan = new javax.swing.JLabel();
        valHarga = new javax.swing.JLabel();
        valTanggal = new javax.swing.JLabel();
        btnDetail = new javax.swing.JLabel();
        tabPengeluaran = new javax.swing.JTabbedPane();
        LPSEMUA = new javax.swing.JPanel();
        lpSemua = new javax.swing.JScrollPane();
        tabelDataS = new javax.swing.JTable();
        valTotalS = new javax.swing.JLabel();
        pengeluaranS = new javax.swing.JLabel();
        LPHARIAN = new javax.swing.JPanel();
        lpHarian = new javax.swing.JScrollPane();
        tabelDataH = new javax.swing.JTable();
        valTotalH = new javax.swing.JLabel();
        pengeluaranH = new javax.swing.JLabel();
        tbHarian = new com.toedter.calendar.JDateChooser();
        LPBULANAN = new javax.swing.JPanel();
        lpBulanan = new javax.swing.JScrollPane();
        tabelDataB = new javax.swing.JTable();
        valTotalB = new javax.swing.JLabel();
        pengeluaranB = new javax.swing.JLabel();
        tbBulanan = new com.toedter.calendar.JMonthChooser();
        tbTahunan = new com.toedter.calendar.JYearChooser();
        LPRentang = new javax.swing.JPanel();
        lpMingguan = new javax.swing.JScrollPane();
        tabelDataM = new javax.swing.JTable();
        valTotalM = new javax.swing.JLabel();
        pengeluaranM = new javax.swing.JLabel();
        tbMinggu1 = new com.toedter.calendar.JDateChooser();
        jDateChooser3 = new com.toedter.calendar.JDateChooser();
        inpCari = new javax.swing.JTextField();
        background = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(957, 650));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        valIDPengeluaran.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        valIDPengeluaran.setForeground(new java.awt.Color(0, 0, 0));
        valIDPengeluaran.setText(":");
        add(valIDPengeluaran, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 65, 200, 32));

        valIDTransaksi.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        valIDTransaksi.setForeground(new java.awt.Color(0, 0, 0));
        valIDTransaksi.setText(":");
        add(valIDTransaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 110, 200, 32));

        valIDKaryawan.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        valIDKaryawan.setForeground(new java.awt.Color(0, 0, 0));
        valIDKaryawan.setText(":");
        add(valIDKaryawan, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 154, 200, 32));

        valNamaKaryawan.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        valNamaKaryawan.setForeground(new java.awt.Color(0, 0, 0));
        valNamaKaryawan.setText(":");
        add(valNamaKaryawan, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 198, 200, 32));

        valHarga.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        valHarga.setForeground(new java.awt.Color(0, 0, 0));
        valHarga.setText(":");
        valHarga.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        add(valHarga, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 242, 200, 32));

        valTanggal.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        valTanggal.setForeground(new java.awt.Color(0, 0, 0));
        valTanggal.setText(":");
        add(valTanggal, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 287, 200, 32));

        btnDetail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/gambar_icon/btn-detail-075.png"))); // NOI18N
        btnDetail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDetailMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDetailMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDetailMouseExited(evt);
            }
        });
        add(btnDetail, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 710, -1, -1));

        LPSEMUA.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabelDataS.setBackground(new java.awt.Color(255, 255, 255));
        tabelDataS.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        tabelDataS.setForeground(new java.awt.Color(0, 0, 0));
        tabelDataS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Pengeluaran", "ID Karyawan", "Nama Karyawan", "Total Pengeluaran", "Tanggal", "Waktu"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
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

        LPSEMUA.add(lpSemua, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 925, 260));

        valTotalS.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        valTotalS.setForeground(new java.awt.Color(0, 0, 0));
        valTotalS.setText(":");
        LPSEMUA.add(valTotalS, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 530, 290, 36));

        pengeluaranS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/gambar_icon/laporan-pengeluaran-075.png"))); // NOI18N
        pengeluaranS.setText("lbll");
        LPSEMUA.add(pengeluaranS, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 263, 490, -1));

        tabPengeluaran.addTab("Semua", LPSEMUA);

        LPHARIAN.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabelDataH.setBackground(new java.awt.Color(255, 255, 255));
        tabelDataH.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        tabelDataH.setForeground(new java.awt.Color(0, 0, 0));
        tabelDataH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Pengeluaran", "ID Karyawan", "Nama Karyawan", "Total Pengeluaran", "Tanggal", "Waktu"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
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

        LPHARIAN.add(lpHarian, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 925, 260));

        valTotalH.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        valTotalH.setForeground(new java.awt.Color(0, 0, 0));
        valTotalH.setText(":");
        LPHARIAN.add(valTotalH, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 530, 290, 36));

        pengeluaranH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/gambar_icon/laporan-pengeluaran-075.png"))); // NOI18N
        pengeluaranH.setText("lbll");
        LPHARIAN.add(pengeluaranH, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 263, 490, -1));

        tbHarian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbHarianMouseClicked(evt);
            }
        });
        LPHARIAN.add(tbHarian, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 280, -1, -1));

        tabPengeluaran.addTab("Harian", LPHARIAN);

        LPBULANAN.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabelDataB.setBackground(new java.awt.Color(255, 255, 255));
        tabelDataB.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        tabelDataB.setForeground(new java.awt.Color(0, 0, 0));
        tabelDataB.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Pengeluaran", "ID Karyawan", "Nama Karyawan", "Total Pengeluaran", "Tanggal", "Waktu"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
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

        LPBULANAN.add(lpBulanan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 925, 260));

        valTotalB.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        valTotalB.setForeground(new java.awt.Color(0, 0, 0));
        valTotalB.setText(":");
        LPBULANAN.add(valTotalB, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 530, 290, 36));

        pengeluaranB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/gambar_icon/laporan-pengeluaran-075.png"))); // NOI18N
        pengeluaranB.setText("lbll");
        LPBULANAN.add(pengeluaranB, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 263, 490, -1));

        tbBulanan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbBulananMouseClicked(evt);
            }
        });
        LPBULANAN.add(tbBulanan, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 270, -1, -1));
        LPBULANAN.add(tbTahunan, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 270, -1, -1));

        tabPengeluaran.addTab("Bulanan", LPBULANAN);

        LPRentang.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabelDataM.setBackground(new java.awt.Color(255, 255, 255));
        tabelDataM.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        tabelDataM.setForeground(new java.awt.Color(0, 0, 0));
        tabelDataM.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Pengeluaran", "ID Karyawan", "Nama Karyawan", "Total Pengeluaran", "Tanggal", "Waktu"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
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

        LPRentang.add(lpMingguan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 925, 260));

        valTotalM.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        valTotalM.setForeground(new java.awt.Color(0, 0, 0));
        valTotalM.setText(":");
        LPRentang.add(valTotalM, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 530, 290, 36));

        pengeluaranM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/gambar_icon/laporan-pengeluaran-075.png"))); // NOI18N
        pengeluaranM.setText("lbll");
        LPRentang.add(pengeluaranM, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 263, 490, -1));

        tbMinggu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbMinggu1MouseClicked(evt);
            }
        });
        LPRentang.add(tbMinggu1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 280, -1, -1));
        LPRentang.add(jDateChooser3, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 270, -1, -1));

        tabPengeluaran.addTab("Rentang Tanggal", LPRentang);

        add(tabPengeluaran, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 930, 330));

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
        add(inpCari, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 350, 220, -1));

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/gambar/app-laporan-pengeluaran-075.png"))); // NOI18N
        background.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void inpCariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpCariKeyTyped
        try {
            String key = this.inpCari.getText();
            String data = "";
            if(key.contains("TRB")|| key.contains("LPG")|| key.contains("trb")|| key.contains("lpg")){
                data = "TRB"+key.substring(3, key.length());
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
            if(key.contains("TRB")|| key.contains("LPG")|| key.contains("trb")|| key.contains("lpg")){
                data = "TRB"+key.substring(3, key.length());
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
            }
            this.updateTabel(tabel);
        } catch (ParseException ex) {
            Logger.getLogger(LaporanJual.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_inpCariKeyReleased

    private void tabelDataBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelDataBKeyPressed
        try {
            this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            if(evt.getKeyCode() == KeyEvent.VK_UP){
                this.idSelected = this.tabelDataB.getValueAt(tabelDataB.getSelectedRow() - 1, 0).toString();
                this.showData(tabelDataB);
            }else if(evt.getKeyCode() == KeyEvent.VK_DOWN){
                this.idSelected = this.tabelDataB.getValueAt(tabelDataB.getSelectedRow() + 1, 0).toString();
                this.showData(tabelDataB);
            }
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } catch (ParseException ex) {
            Logger.getLogger(LaporanBeli.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tabelDataBKeyPressed

    private void tabelDataBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelDataBMouseClicked
        try {
            this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            // menampilkan data pembeli
            this.idSelected = this.tabelDataB.getValueAt(tabelDataB.getSelectedRow(), 0).toString();
            this.showData(tabelDataB);
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } catch (ParseException ex) {
            Logger.getLogger(LaporanBeli.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tabelDataBMouseClicked

    private void tabelDataMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelDataMKeyPressed
        try {
            this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            if(evt.getKeyCode() == KeyEvent.VK_UP){
                this.idSelected = this.tabelDataM.getValueAt(tabelDataM.getSelectedRow() - 1, 0).toString();
                this.showData(tabelDataM);
            }else if(evt.getKeyCode() == KeyEvent.VK_DOWN){
                this.idSelected = this.tabelDataM.getValueAt(tabelDataM.getSelectedRow() + 1, 0).toString();
                this.showData(tabelDataM);
            }
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } catch (ParseException ex) {
            Logger.getLogger(LaporanBeli.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tabelDataMKeyPressed

    private void tabelDataMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelDataMMouseClicked
        try {
            // TODO add your handling code here:
            this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            // menampilkan data pembeli
            this.idSelected = this.tabelDataM.getValueAt(tabelDataM.getSelectedRow(), 0).toString();
            this.showData(tabelDataM);
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } catch (ParseException ex) {
            Logger.getLogger(LaporanBeli.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tabelDataMMouseClicked

    private void tabelDataHKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelDataHKeyPressed
        try {
            this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            if(evt.getKeyCode() == KeyEvent.VK_UP){
                this.idSelected = this.tabelDataH.getValueAt(tabelDataH.getSelectedRow() - 1, 0).toString();
                this.showData(tabelDataH);
            }else if(evt.getKeyCode() == KeyEvent.VK_DOWN){
                this.idSelected = this.tabelDataH.getValueAt(tabelDataH.getSelectedRow() + 1, 0).toString();
                this.showData(tabelDataH);
            }
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } catch (ParseException ex) {
            Logger.getLogger(LaporanBeli.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tabelDataHKeyPressed

    private void tabelDataHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelDataHMouseClicked
        try {
            this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            // menampilkan data pembeli
            this.idSelected = this.tabelDataH.getValueAt(tabelDataH.getSelectedRow(), 0).toString();
            this.showData(tabelDataH);
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } catch (ParseException ex) {
            Logger.getLogger(LaporanBeli.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tabelDataHMouseClicked

    private void tabelDataSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelDataSKeyPressed
        try {
            this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            if(evt.getKeyCode() == KeyEvent.VK_UP){
                this.idSelected = this.tabelDataS.getValueAt(tabelDataS.getSelectedRow() - 1, 0).toString();
                this.showData(tabelDataS);
            }else if(evt.getKeyCode() == KeyEvent.VK_DOWN){
                this.idSelected = this.tabelDataS.getValueAt(tabelDataS.getSelectedRow() + 1, 0).toString();
                this.showData(tabelDataS);
            }
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } catch (ParseException ex) {
            Logger.getLogger(LaporanBeli.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tabelDataSKeyPressed

    private void tabelDataSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelDataSMouseClicked
        try {
            this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            // menampilkan data pembeli
            this.idSelected = this.tabelDataS.getValueAt(tabelDataS.getSelectedRow(), 0).toString();
            this.showData(tabelDataS);
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } catch (ParseException ex) {
            Logger.getLogger(LaporanBeli.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tabelDataSMouseClicked

    private void inpCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inpCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inpCariActionPerformed

    private void btnDetailMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDetailMouseEntered
        this.btnDetail.setIcon(Gambar.getAktiveIcon(this.btnDetail.getIcon().toString()));
    }//GEN-LAST:event_btnDetailMouseEntered

    private void btnDetailMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDetailMouseExited
        this.btnDetail.setIcon(Gambar.getBiasaIcon(this.btnDetail.getIcon().toString()));
    }//GEN-LAST:event_btnDetailMouseExited

    private void btnDetailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDetailMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_btnDetailMouseClicked

    private void tbMinggu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMinggu1MouseClicked
        // TODO add your handling code here:
        tbMinggu1.getDate();
    }//GEN-LAST:event_tbMinggu1MouseClicked

    private void tbHarianMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbHarianMouseClicked
        // TODO add your handling code here:
        System.out.println(tbHarian.getDate());
    }//GEN-LAST:event_tbHarianMouseClicked

    private void tbBulananMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbBulananMouseClicked
        // TODO add your handling code here:
        System.out.println(tbBulanan.getComponentCount());
    }//GEN-LAST:event_tbBulananMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel LPBULANAN;
    private javax.swing.JPanel LPHARIAN;
    private javax.swing.JPanel LPRentang;
    private javax.swing.JPanel LPSEMUA;
    private javax.swing.JLabel background;
    private javax.swing.JLabel btnDetail;
    private javax.swing.JTextField inpCari;
    private com.toedter.calendar.JDateChooser jDateChooser3;
    private javax.swing.JScrollPane lpBulanan;
    private javax.swing.JScrollPane lpHarian;
    private javax.swing.JScrollPane lpMingguan;
    private javax.swing.JScrollPane lpSemua;
    private javax.swing.JLabel pengeluaranB;
    private javax.swing.JLabel pengeluaranH;
    private javax.swing.JLabel pengeluaranM;
    private javax.swing.JLabel pengeluaranS;
    private javax.swing.JTabbedPane tabPengeluaran;
    private javax.swing.JTable tabelDataB;
    private javax.swing.JTable tabelDataH;
    private javax.swing.JTable tabelDataM;
    private javax.swing.JTable tabelDataS;
    private com.toedter.calendar.JMonthChooser tbBulanan;
    private com.toedter.calendar.JDateChooser tbHarian;
    private com.toedter.calendar.JDateChooser tbMinggu1;
    private com.toedter.calendar.JYearChooser tbTahunan;
    private javax.swing.JLabel valHarga;
    private javax.swing.JLabel valIDKaryawan;
    private javax.swing.JLabel valIDPengeluaran;
    private javax.swing.JLabel valIDTransaksi;
    private javax.swing.JLabel valNamaKaryawan;
    private javax.swing.JLabel valTanggal;
    private javax.swing.JLabel valTotalB;
    private javax.swing.JLabel valTotalH;
    private javax.swing.JLabel valTotalM;
    private javax.swing.JLabel valTotalS;
    // End of variables declaration//GEN-END:variables
}
