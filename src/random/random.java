package random;
import com.manage.Text;
import com.data.db.Database;
import com.data.db.DatabaseTables;
import com.manage.Message;
import com.media.Audio;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
public class random {
    private final static Text text = new Text();
    private final static Database db = new Database();
    
    private static Statement getStat(){
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
    public static String getData(String table, String field, String kondisi){
        try{
            Statement stat = getStat();
            String sql = "SELECT "+field+" FROM "+table + " " + kondisi;
            ResultSet res = stat.executeQuery(sql);
            if(res.next()){
                return res.getString(field);
            }
        }catch(SQLException ex){
            Audio.play(Audio.SOUND_ERROR);
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan!\n\nError message : "+ex.getMessage(), "Peringatan!", JOptionPane.WARNING_MESSAGE);
        }
        System.out.println("data kosong");
        return "null";
    }
    private static Object[] getData() throws ParseException{
        try{
            Statement stat = getStat();
            int rows = 1;
            ArrayList<Object> data = new ArrayList<Object>();
            Object[] obj = new Object[4];
            String sql = "SELECT nama_tr_beli AS nama,total_hrg AS total, id_tr_beli AS jenis, tanggal FROM transaksi_beli UNION SELECT nama_tr_jual,total_hrg,id_tr_jual,tanggal FROM transaksi_jual ORDER BY tanggal DESC";
            System.out.println(sql);
            ResultSet res = stat.executeQuery(sql);
            res = stat.executeQuery(sql);
            // mendapatkan semua data yang ada didalam tabel
            while(res.next()){
                // menyimpan data dari tabel ke object
                data.add(res.getString("nama"));
                obj[0] = res.getString("nama");
                obj[1] = res.getInt("total");
                obj[2] = res.getString("jenis");
                obj[3] = res.getString("tanggal");
                data.add(obj);
//                obj[rows-1][4] = 
                rows++; // rows akan bertambah 1 setiap selesai membaca 1 row pada tabel
            }
            int rows1 = 0;
            System.out.println("panjang "+data.size());
            while(rows1 <= data.size()){
                System.out.println("data 1 :"+data.get(rows1));
                System.out.println("data 2 :"+data.get(rows1));
                System.out.println("data 3 :"+data.get(rows1));
                System.out.println("data 4 :"+data.get(rows1));
                rows1++;
            }
            return data.toArray();
        }catch(SQLException ex){
//            Message.showException(this, "Terjadi kesalahan saat mengambil data dari database\n" + ex.getMessage(), ex, true);
                System.out.println("erorr");
        }
        return null;
    }
    public static void main(String[] args) {
        System.out.println(text.toMoneyCase("-10000"));
    }
}










