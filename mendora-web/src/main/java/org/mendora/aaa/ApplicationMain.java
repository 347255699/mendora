package org.mendora.aaa;

import org.mendora.aaa.launcher.AAALauncher;

/**
 * created by:xmf
 * date:2018/3/7
 * description:
 */
public class ApplicationMain {
    public static void main(String[] args) {
        AAALauncher.launch(ApplicationMain.class.getProtectionDomain().getCodeSource().getLocation());
    }
}
