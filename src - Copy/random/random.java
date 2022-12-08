package random;
import com.manage.Text;
import com.data.db.Database;
public class random {
    private final static Text text = new Text();
    private final static Database db = new Database();
    public static void main(String[] args) {
        String a = "SELECT SUM(total_hrg) AS total FROM transaksi_jual";
        String b = "SELECT SUM(total_hrg) AS total FROM transaksi_jual";
        String data = "3259850";
        String data4 = text.toMoneyCase(data);
        System.out.println(data4);
        String tPengeluaran = text.toMoneyCase(Integer.toString(db.sumData("transaksi_jual", "total_hrg","")));
        String hasil = Integer.toString(db.sumData("transaksi_jual", "total_hrg",""));
        System.out.println(tPengeluaran);
        System.out.println(hasil);
        System.out.println(a.equals(b));
    }
}










