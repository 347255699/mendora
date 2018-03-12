import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.Vertx;
import org.mendora.base.cluster.ClusterHandler;
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
            ClassLoader cl = ApplicationMain.class.getClassLoader();
            ClusterHandler clusterHandler = new ClusterHandler() {
                @Override
                public void handle(Vertx vertx) {
                    // we should do something.
                }
            };
            BaseLauncher.launch(ApplicationMain.class.getProtectionDomain().getCodeSource().getLocation(), cl, clusterHandler);
            JsonObject doc = ConfigHolder.asJson();
            doc.forEach(e -> System.out.println(e.getKey() + ":" + e.getValue()));
            Logger logger = LoggerFactory.getLogger(ApplicationMain.class);
            logger.info("test {}", "ApplicationMain");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
