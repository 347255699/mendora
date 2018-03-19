import org.mendora.data.ApplicationMain;
import org.mendora.data.launcher.DataLauncher;

/**
 * Created by kam on 2018/3/12.
 */
public class PostgreSqlClientTest {
    public static void main(String[] args) {
        DataLauncher.launch(ApplicationMain.class.getProtectionDomain().getCodeSource().getLocation());
    }
}
