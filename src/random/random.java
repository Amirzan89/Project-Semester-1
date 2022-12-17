package random;
import com.data.db.AES;
import com.manage.Text;
import com.data.db.Database;
import com.data.db.DatabaseTables;
import com.data.db.Hashing_Algorithm;
import com.manage.Message;
import com.media.Audio;
import com.users.UserLevels;
import static com.users.UserLevels.ADMIN;
import com.users.Users;
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
    private final static Hashing_Algorithm hash = new Hashing_Algorithm();
    private final static Text text = new Text();
    private final static Database db = new Database();
    private final static AES aes = new AES();
    private static Statement getStat(){
        try{  
            Class.forName("com.mysql.jdbc.Driver");  
            Connection con=DriverManager.getConnection(  
                "jdbc:mysql://localhost:3306/bisnis","root","");  
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
    public String getData(String table, String field, String kondisi){
        try{
            Statement stat = getStat();
            String sql = "SELECT "+field+" FROM "+table + " " + kondisi;
            System.out.println(sql);
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
    private static int getTotal(String table, String kolom, String kondisi){
        try {
            Statement stat = getStat();
            int data = 0;
            String sql = "SELECT SUM("+kolom+") AS total FROM "+table+" "+kondisi;
            System.out.println("sql sum "+sql);
            ResultSet res = stat.executeQuery(sql);
            if(res.next()){
                System.out.println("data ditemukan");
                data = res.getInt("total");
                System.out.println("jumlahnya "+data);
            }
            return data;
        } catch (SQLException ex) {
//            Logger.getLogger(LaporanJual.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("salah ");
            ex.printStackTrace();
        }catch(NullPointerException n){
//            n.printStackTrace();
            System.out.println("errorr ");
            return 0;
        }
        return -1;
    }
    private String removeDelim(String num){
        return num.replaceAll("_", "").replaceAll(",", "").replaceAll("\\.", "");
    }
    public boolean isNumber(String str){
        // jika input null maka akan mengembalikan nilai false
        if(str == null){
            return false;
        }
        try{
            Long.parseLong(this.removeDelim(str));
            return true;
        }catch(NumberFormatException ex){
            return false;
        }
    }
    private String toMoneyCase1(String money){
        // mengecek apakah input adalah sebuah number atau tidak
        if(this.isNumber(money)){
            // mengubah menjadi money case
            return String.format("Rp. %.d,00", Long.parseLong(money));
        }
        // jika input bukan sebuah number maka akan mengembalikan nilai 'Rp. -1.00'
        return "Rp. -1.00";
    }
    public static void main(String[] args) throws SQLException, Exception {
        random random = new random();
        String password = "Amirzan@06112003";
        System.out.println("password asli "+password);
        String hash1 = "",hashing = "";
        boolean validasi = false;
//        for(int i = 0; i<3;i++){
//            hashing = random.getData("users", "password", "WHERE id_user = 'PB00"+i+"'");
//            validasi = hash.checkpw(password, hashing);
//            System.out.println("hasil pembeli ke "+i+" : "+validasi);
//        }
//            hashing = random.getData("users", "password", "WHERE id_user = 'PB010'");
//            validasi = hash.checkpw(password, hashing);
//            System.out.println("hasil pembeli ke 10 : "+validasi);
//        for(int i = 2; i<5;i++){
//            hashing = random.getData("users", "password", "WHERE id_user = 'PG00"+i+"'");
//            validasi = hash.checkpw(password, hashing);
//            System.out.println("hasil pegawai ke "+i+" : "+validasi);
//        }
//        for(int i = 1; i<10;i++){
//            hashing = random.getData("users", "password", "WHERE id_user = 'SP00"+i+"'");
//            validasi = hash.checkpw(password, hashing);
//            System.out.println("hasil supplier ke "+i+" : "+validasi);
//        }
//            hashing = random.getData("users", "password", "WHERE id_user = 'SP010'");
//            validasi = hash.checkpw(password, hashing);
//            System.out.println("hasil supplier ke 10 : "+validasi);
        for(int i = 0; i<1;i++){
            hash1 = hash.hash(password,15);
            System.out.println("hasil hash "+hash1);
        }
//        boolean hasil = hash.checkpw(password, hash1);
//        System.out.println("hasil "+hasil);
//        System.out.println("data password "+hashing);
        
    }
}










