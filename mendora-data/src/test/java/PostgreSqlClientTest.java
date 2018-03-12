import org.mendora.data.ApplicationMain;
import org.mendora.data.accesser.DataAccesser;
import org.mendora.data.launcher.DataLauncher;

/**
 * Created by kam on 2018/3/12.
 */
public class PostgreSqlClientTest {
    public static void main(String[] args) {
        ClassLoader cl = ApplicationMain.class.getClassLoader();
        DataLauncher.launch(ApplicationMain.class.getProtectionDomain().getCodeSource().getLocation(), cl);
        DataAccesser.rxQuery("")
                .subscribe(rows -> {
                    System.out.println(rows.toString());
                });
    }
}
