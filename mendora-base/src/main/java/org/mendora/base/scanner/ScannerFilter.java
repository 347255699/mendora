package org.mendora.base.scanner;

import java.nio.file.Path;

/**
 * Created by kam on 2018/2/4.
 */
@FunctionalInterface
public interface ScannerFilter {
    String filte(String packagePath, Path path);
}
