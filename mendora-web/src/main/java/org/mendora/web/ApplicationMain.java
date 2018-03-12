package org.mendora.web;

import org.mendora.web.launcher.WebLauncher;

/**
 * created by:xmf
 * date:2018/3/7
 * description:
 */
public class ApplicationMain {
    public static void main(String[] args) {
        ClassLoader cl = ApplicationMain.class.getClassLoader();
        WebLauncher.launch(ApplicationMain.class.getProtectionDomain().getCodeSource().getLocation(), cl);
    }
}
