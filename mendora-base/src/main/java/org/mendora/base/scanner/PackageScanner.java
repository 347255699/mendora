package org.mendora.base.scanner;

import java.io.IOException;
import java.util.Set;

/**
 * Scanner for instantiation a class set blow target package path.
 * Created by kam on 2018/2/4.
 */
public interface PackageScanner<T> {
    String FILE_SUFFIX = ".class";

    /**
     * Find target element blow package except two class name.
     *
     * @param packageName
     * @param except
     * @param except2
     * @return
     * @throws IOException
     */
    Set<String> classNames(String packageName, String except, String except2) throws IOException;

    /**
     * Find target element blow package except single class name.
     *
     * @param packageName
     * @param except
     * @return
     * @throws IOException
     */
    Set<String> classNames(String packageName, String except) throws IOException;

    /**
     * Scanning target class blow package except @param except and @param except2 class.
     *
     * @param packageName
     * @param except
     * @param except2
     * @return
     */
    Set<T> scan(String packageName, Class<?> except, Class<?> except2) throws IOException;

    /**
     * Scanning target class blow package except @param except class.
     *
     * @param packageName
     * @param except
     * @return
     */
    Set<T> scan(String packageName, Class<?> except) throws IOException;

    /**
     * Instantiation a class set.
     *
     * @param classNames
     * @return
     */
    Set<?> instantiation(Set<String> classNames, Class<?> tClass) throws IOException;
}
