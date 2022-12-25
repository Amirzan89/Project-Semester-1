package com.test;

import com.data.app.Log;
import com.data.db.Database;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
public class random {
//    private static Log log = new Log();
    static DateFormat tanggalMilis = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private final static DateFormat time = new SimpleDateFormat("ss:mm:hh");
    public static void main(String[] args) throws ParseException {
        try {
            Date tanggal1 = new Date();
            String sql = "SELECT tanggal FROM transaksi_jual ORDER BY id_tr_jual DESC LIMIT 0,1",tanggal = "",hasil = "";
            Database db = new Database();
            Log.createLog();
            db.startConnection();
            db.res = db.stat.executeQuery(sql);
            if(db.res.next()){
                tanggal = db.res.getString("tanggal");
                System.out.println("tanggal db "+tanggal);
            }
            hasil = tanggal.substring(11,19);
            System.out.println("hasil "+hasil);
            System.out.println("tanggal baru "+ tanggal1);
            hasil = time.format(tanggal1);
            System.out.println("waktu "+hasil);
        } catch (SQLException ex) {
            Logger.getLogger(random.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
