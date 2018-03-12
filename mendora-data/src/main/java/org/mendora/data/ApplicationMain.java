package org.mendora.data;

import org.mendora.data.launcher.DataLauncher;

/**
 * created by:xmf
 * date:2018/3/12
 * description:
 */
public class ApplicationMain {
    public static void main(String[] args) {
        ClassLoader cl = ApplicationMain.class.getClassLoader();
        DataLauncher.launch(ApplicationMain.class.getProtectionDomain().getCodeSource().getLocation(), cl);
    }
}
