package org.mendora.aider;

import org.mendora.aider.launcher.AiderLauncher;

/**
 * created by:xmf
 * date:2018/3/7
 * description:
 */
public class ApplicationMain {
    public static void main(String[] args) {
        AiderLauncher.launch(ApplicationMain.class.getProtectionDomain().getCodeSource().getLocation());
    }
}
