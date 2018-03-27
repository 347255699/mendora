package org.mendora.service.rear;

import org.mendora.service.rear.launcher.RearLauncher;

/**
 * created by:xmf
 * date:2018/3/12
 * description:
 */
public class ApplicationMain {
    public static void main(String[] args) {
        RearLauncher.launch(ApplicationMain.class.getProtectionDomain().getCodeSource().getLocation());
    }
}
