import io.vertx.core.json.JsonObject;
import org.mendora.base.properties.ConfigHolder;
import org.mendora.base.BaseLauncher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * created by:xmf
 * date:2018/3/7
 * description:
 */
public class ApplicationMain {
    public static void main(String[] args) {
        try {
            BaseLauncher.launch(ApplicationMain.class.getProtectionDomain().getCodeSource().getLocation());
            JsonObject doc = ConfigHolder.asJson();
            doc.forEach(e -> System.out.println(e.getKey() + ":" + e.getValue()));
            Logger logger = LoggerFactory.getLogger(ApplicationMain.class);
            logger.info("test {}", "ApplicationMain");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
