package org.mendora.base.scanner;

import java.io.IOException;
import java.util.List;

/**
 * Scanner for instantiation a class set blow target package path.
 * Created by kam on 2018/2/4.
 */
public interface PackageScanner<T> {
    String FILE_SUFFIX_CLASS = ".class";
    String FILE_SUFFIX_JAR = ".jar";

    /**
     * Find target element blow package except two class name.
     *
     * @param packageName
     * @param except
     * @param except2
     * @return
     * @throws IOException
     */
    List<String> classNames(String packageName, String except, String except2) throws Exception;

    /**
     * Find target element blow package except single class name.
     *
     * @param packageName
     * @param except
     * @return
     * @throws IOException
     */
    List<String> classNames(String packageName, String except) throws Exception;

    /**
     * Scanning target class blow package except @param except and @param except2 class.
     *
     * @param except
     * @param except2
     * @return
     */
    List<T> scan(Class<T> except, Class<?> except2) throws Exception;

    /**
     * Scanning target class blow package except @param except class.
     *
     * @param except
     * @return
     */
    List<T> scan(Class<T> except) throws Exception;

    /**
     * Instantiation a class set.
     *
     * @param classNames
     * @return
     */
    List<T> instantiation(List<String> classNames, Class<T> tClass) throws Exception;
}
