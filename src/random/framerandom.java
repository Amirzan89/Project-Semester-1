package random;

import com.manage.Barang;
import com.manage.ManageTransaksiBeli;
import com.manage.ManageTransaksiJual;
import com.manage.Message;
import com.manage.Text;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
public class framerandom extends javax.swing.JFrame {
    private String keyword = "";
    private final ManageTransaksiBeli trb = new ManageTransaksiBeli();
    private final ManageTransaksiJual trj = new ManageTransaksiJual();
    private final Barang barang = new Barang();
    private final Text text = new Text();
    
    DateFormat tanggalMilis = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private final DateFormat tanggalFull = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss ");
    private final DateFormat date = new SimpleDateFormat("dd-MM-yyyy");
    private final DateFormat time = new SimpleDateFormat("ss:mm:hh");
    private final DateFormat timeMillis = new SimpleDateFormat("ss.SSS:mm:hh");
    
    public framerandom() throws ParseException {
        initComponents();
        updateTabel(tabelDataS1);
    }
    private Object[][] getData() throws ParseException{
        try{
            java.util.Date tanggalData = new java.util.Date();
            int max1 = trb.getJumlahData("transaksi_beli", keyword),max2 = trb.getJumlahData("transaksi_jual", keyword), max3 = max1+max2;
            Object obj1[][] = new Object[max1][6];
            Object obj2[][] = new Object[max2][6];
            Object obj3[][] = new Object[max3][6];
            int rows1 = 0;
            int rows2 = 0;
            int rows3 = 0;
            String sql = "SELECT id_tr_beli, id_barang, jumlah_brg, total_hrg, tanggal FROM transaksi_beli " + keyword + " ORDER BY id_tr_beli DESC";
            // mendefinisikan object berdasarkan total rows dan cols yang ada didalam tabel
            System.out.println(sql);
            // mengeksekusi query
            trb.res = trb.stat.executeQuery(sql);
            // mendapatkan semua data yang ada didalam tabel
            while(trb.res.next()){
                // menyimpan data dari tabel ke object
                obj1[rows1][0] = trb.res.getString("id_tr_beli").replace("TRB", "LPG");
                obj1[rows1][1] = barang.getNamaBarang(trb.res.getString("id_barang"));
                obj1[rows1][2] = trb.res.getString("jumlah_brg");
                obj1[rows1][3] = text.toMoneyCase(trb.res.getString("total_hrg"));
                tanggalData = tanggalMilis.parse(trb.res.getString("tanggal"));
                obj1[rows1][4] = date.format(tanggalData);
                obj1[rows1][5] = time.format(tanggalData);
                rows1++; // rows akan bertambah 1 setiap selesai membaca 1 row pada tabel
            }
            sql = "SELECT id_tr_jual, id_barang, jumlah_brg, total_hrg, tanggal FROM transaksi_jual " + keyword + " ORDER BY id_tr_jual DESC";
            System.out.println(sql);
            // mengeksekusi query
            trj.res = trj.stat.executeQuery(sql);
            // mendapatkan semua data yang ada didalam tabel
            while(trj.res.next()){
                // menyimpan data dari tabel ke object
                obj2[rows2][0] = trj.res.getString("id_tr_jual").replace("TRJ", "LPD");
                obj2[rows2][1] = barang.getNamaBarang(trj.res.getString("id_barang"));
                obj2[rows2][2] = trj.res.getString("jumlah_brg");
                obj2[rows2][3] = text.toMoneyCase(trj.res.getString("total_hrg"));
                tanggalData = tanggalMilis.parse(trj.res.getString("tanggal"));
                obj2[rows2][4] = date.format(tanggalData);
                obj2[rows2][5] = time.format(tanggalData);
                rows2++; // rows akan bertambah 1 setiap selesai membaca 1 row pada tabel
            }
//            max1 = 0;
//            max2 = 0;
            rows1 = 0;
            rows2 = 0;
            rows3 = 0;
            
            String data1,data2,data3,data4;
            Date data = new Date(),tanggalA = new Date(),tanggalB = new Date(),waktuA = new Date(),waktuB = new Date();
            while(rows3 < max3){
                System.out.println(obj1[rows1][4].toString());
                rows3++;
                data1 = obj1[rows1][4].toString();
                data2 = obj2[rows2][4].toString();
                data3 = obj1[rows1][5].toString();
                data4 = obj2[rows2][5].toString();
                tanggalA = date.parse(data1);
                tanggalB = date.parse(data2);
                waktuA = time.parse(data3);
                waktuB = time.parse(data4);
                if(tanggalA.compareTo(tanggalB)>0){
                    if(waktuA.compareTo(waktuB)>0){
                        obj3[rows3][0] = obj1[rows1][0];
                        obj3[rows3][1] = obj1[rows1][1];
                        obj3[rows3][2] = obj1[rows1][2];
                        obj3[rows3][3] = obj1[rows1][3];
                        obj3[rows3][4] = obj1[rows1][4];
                        obj3[rows3][5] = obj1[rows1][5];
                        rows1++;
                        rows3++;
                    }else{
                        obj3[rows3][0] = obj2[rows2][0];
                        obj3[rows3][1] = obj2[rows2][1];
                        obj3[rows3][2] = obj2[rows2][2];
                        obj3[rows3][3] = obj2[rows2][3];
                        obj3[rows3][4] = obj2[rows2][4];
                        obj3[rows3][5] = obj2[rows2][5];
                        rows2++;
                        rows3++;
                    }
                }else if(tanggalA.compareTo(tanggalB)==0){
                    if(waktuA.compareTo(waktuB)>0){
                        obj3[rows3][0] = obj1[rows1][0];
                        obj3[rows3][1] = obj1[rows1][1];
                        obj3[rows3][2] = obj1[rows1][2];
                        obj3[rows3][3] = obj1[rows1][3];
                        obj3[rows3][4] = obj1[rows1][4];
                        obj3[rows3][5] = obj1[rows1][5];
                        rows1++;
                        rows3++;
                    }else{
                        obj3[rows3][0] = obj2[rows2][0];
                        obj3[rows3][1] = obj2[rows2][1];
                        obj3[rows3][2] = obj2[rows2][2];
                        obj3[rows3][3] = obj2[rows2][3];
                        obj3[rows3][4] = obj2[rows2][4];
                        obj3[rows3][5] = obj2[rows2][5];
                        rows2++;
                        rows3++;
                    }
                }else if(tanggalA.compareTo(tanggalB) < 0){
                    if(waktuA.compareTo(waktuB)>0){
                        obj3[rows3][0] = obj1[rows1][0];
                        obj3[rows3][1] = obj1[rows1][1];
                        obj3[rows3][2] = obj1[rows1][2];
                        obj3[rows3][3] = obj1[rows1][3];
                        obj3[rows3][4] = obj1[rows1][4];
                        obj3[rows3][5] = obj1[rows1][5];
                        rows1++;
                        rows3++;
                    }else{
                        obj3[rows3][0] = obj2[rows2][0];
                        obj3[rows3][1] = obj2[rows2][1];
                        obj3[rows3][2] = obj2[rows2][2];
                        obj3[rows3][3] = obj2[rows2][3];
                        obj3[rows3][4] = obj2[rows2][4];
                        obj3[rows3][5] = obj2[rows2][5];
                        rows2++;
                        rows3++;
                    }
                }
            }
            return obj3;
        }catch(SQLException ex){
            ex.printStackTrace();
            Message.showException(this, "Terjadi kesalahan saat mengambil data dari database\n" + ex.getMessage(), ex, true);
        }
        return null;
    }
    private Object[][] getData1() throws ParseException{
        try{
            java.util.Date tanggalData = new java.util.Date();
            int max1 = trb.getJumlahData("transaksi_beli", keyword),max2 = trb.getJumlahData("transaksi_jual", keyword), max3 = max1+max2;
            Object obj1[][] = new Object[max1][6];
            int rows1 = 0;
            String sql = "SELECT id_tr_beli, id_barang, jumlah_brg, total_hrg, tanggal FROM transaksi_beli " + keyword + " ORDER BY id_tr_beli DESC";
            // mendefinisikan object berdasarkan total rows dan cols yang ada didalam tabel
            System.out.println(sql);
            // mengeksekusi query
            trb.res = trb.stat.executeQuery(sql);
            // mendapatkan semua data yang ada didalam tabel
            while(trb.res.next()){
                // menyimpan data dari tabel ke object
                obj1[rows1][0] = trb.res.getString("id_tr_beli").replace("TRB", "LPG");
                obj1[rows1][1] = barang.getNamaBarang(trb.res.getString("id_barang"));
                obj1[rows1][2] = trb.res.getString("jumlah_brg");
                obj1[rows1][3] = text.toMoneyCase(trb.res.getString("total_hrg"));
                tanggalData = tanggalMilis.parse(trb.res.getString("tanggal"));
                obj1[rows1][4] = date.format(tanggalData);
                obj1[rows1][5] = time.format(tanggalData);
                rows1++; // rows akan bertambah 1 setiap selesai membaca 1 row pada tabel
            }
            return obj1;
        }catch(SQLException ex){
            ex.printStackTrace();
            Message.showException(this, "Terjadi kesalahan saat mengambil data dari database\n" + ex.getMessage(), ex, true);
        }
        return null;
    }
    private Object[][] getData2() throws ParseException{
        try{
            java.util.Date tanggalData = new java.util.Date();
            int max1 = trb.getJumlahData("transaksi_beli", keyword),max2 = trb.getJumlahData("transaksi_jual", keyword), max3 = max1+max2;
            Object obj2[][] = new Object[max2][6];
            int rows2 = 0;
            String sql = "SELECT id_tr_jual, id_barang, jumlah_brg, total_hrg, tanggal FROM transaksi_jual " + keyword + " ORDER BY id_tr_jual DESC";
            System.out.println(sql);
            // mengeksekusi query
            trj.res = trj.stat.executeQuery(sql);
            // mendapatkan semua data yang ada didalam tabel
            while(trj.res.next()){
                // menyimpan data dari tabel ke object
                obj2[rows2][0] = trj.res.getString("id_tr_jual").replace("TRJ", "LPD");
                obj2[rows2][1] = barang.getNamaBarang(trj.res.getString("id_barang"));
                obj2[rows2][2] = trj.res.getString("jumlah_brg");
                obj2[rows2][3] = text.toMoneyCase(trj.res.getString("total_hrg"));
                tanggalData = tanggalMilis.parse(trj.res.getString("tanggal"));
                obj2[rows2][4] = date.format(tanggalData);
                obj2[rows2][5] = time.format(tanggalData);
                rows2++; // rows akan bertambah 1 setiap selesai membaca 1 row pada tabel
            }
            return obj2;
        }catch(SQLException ex){
            ex.printStackTrace();
            Message.showException(this, "Terjadi kesalahan saat mengambil data dari database\n" + ex.getMessage(), ex, true);
        }
        return null;
    }
    
    private void updateTabel(JTable table) throws ParseException{
        JTable table1 = new JTable();
        Object[][] data;
        if(table == tabelDataS){
          table1 = table;
          data = getData();
        }
        if(table == tabelDataS1){
          table1 = table;
          data = getData1();
        }
        if(table == tabelDataS2){
          table1 = table;
          data = getData2();
        }
        table1.setModel(new javax.swing.table.DefaultTableModel(
            getData(),
            new String [] {
                "Nama Transaksi", "Total Harga", "Jenis Transaksi", "Tanggal"
            }
        ){
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lpSemua = new javax.swing.JScrollPane();
        tabelDataS = new javax.swing.JTable();
        lpSemua1 = new javax.swing.JScrollPane();
        tabelDataS1 = new javax.swing.JTable();
        lpSemua2 = new javax.swing.JScrollPane();
        tabelDataS2 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        tabelDataS1.setBackground(new java.awt.Color(255, 255, 255));
        tabelDataS1.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        tabelDataS1.setForeground(new java.awt.Color(0, 0, 0));
        tabelDataS1.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelDataS1.setGridColor(new java.awt.Color(0, 0, 0));
        tabelDataS1.setSelectionBackground(new java.awt.Color(26, 164, 250));
        tabelDataS1.setSelectionForeground(new java.awt.Color(250, 246, 246));
        tabelDataS1.getTableHeader().setReorderingAllowed(false);
        tabelDataS1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelDataS1MouseClicked(evt);
            }
        });
        tabelDataS1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabelDataS1KeyPressed(evt);
            }
        });
        lpSemua1.setViewportView(tabelDataS1);

        tabelDataS2.setBackground(new java.awt.Color(255, 255, 255));
        tabelDataS2.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        tabelDataS2.setForeground(new java.awt.Color(0, 0, 0));
        tabelDataS2.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelDataS2.setGridColor(new java.awt.Color(0, 0, 0));
        tabelDataS2.setSelectionBackground(new java.awt.Color(26, 164, 250));
        tabelDataS2.setSelectionForeground(new java.awt.Color(250, 246, 246));
        tabelDataS2.getTableHeader().setReorderingAllowed(false);
        tabelDataS2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelDataS2MouseClicked(evt);
            }
        });
        tabelDataS2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabelDataS2KeyPressed(evt);
            }
        });
        lpSemua2.setViewportView(tabelDataS2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lpSemua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lpSemua1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lpSemua2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lpSemua, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lpSemua1, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lpSemua2, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tabelDataS2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelDataS2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelDataS2KeyPressed

    private void tabelDataS2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelDataS2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelDataS2MouseClicked

    private void tabelDataS1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelDataS1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelDataS1KeyPressed

    private void tabelDataS1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelDataS1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelDataS1MouseClicked

    private void tabelDataSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelDataSKeyPressed
        //        try {
            ////            this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            //            if(evt.getKeyCode() == KeyEvent.VK_UP){
                //                this.idSelected = this.tabelDataS.getValueAt(tabelDataS.getSelectedRow() - 1, 0).toString();
                //                this.showData(tabelDataS);
                //            }else if(evt.getKeyCode() == KeyEvent.VK_DOWN){
                //                this.idSelected = this.tabelDataS.getValueAt(tabelDataS.getSelectedRow() + 1, 0).toString();
                //                this.showData(tabelDataS);
                //            }
            ////            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            //        } catch (ParseException ex) {
            //            Logger.getLogger(LaporanBeli.class.getName()).log(Level.SEVERE, null, ex);
            //        }
    }//GEN-LAST:event_tabelDataSKeyPressed

    private void tabelDataSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelDataSMouseClicked
        //        try {
            ////            this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            //            // menampilkan data pembeli
            //            this.idSelected = this.tabelDataS.getValueAt(tabelDataS.getSelectedRow(), 0).toString();
            //            this.showData(tabelDataS);
            ////            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            //        } catch (ParseException ex) {
            //            Logger.getLogger(LaporanBeli.class.getName()).log(Level.SEVERE, null, ex);
            //        }
    }//GEN-LAST:event_tabelDataSMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(framerandom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(framerandom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(framerandom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(framerandom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new framerandom().setVisible(true);
                } catch (ParseException ex) {
                    Logger.getLogger(framerandom.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane lpSemua;
    private javax.swing.JScrollPane lpSemua1;
    private javax.swing.JScrollPane lpSemua2;
    private javax.swing.JTable tabelDataS;
    private javax.swing.JTable tabelDataS1;
    private javax.swing.JTable tabelDataS2;
    // End of variables declaration//GEN-END:variables
}
