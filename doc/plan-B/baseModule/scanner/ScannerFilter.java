package org.mendora.base.scanner;

/**
 * Created by kam on 2018/2/4.
 */
@FunctionalInterface
public interface ScannerFilter {
    String filte(String fullyQualifiedName);
}
