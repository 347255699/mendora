package org.mendora.util.scanner;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 * created by:xmf
 * date:2018/3/9
 * description:
 */
@Slf4j
@RequiredArgsConstructor
public class PackageScannerImpl<T> implements PackageScanner<T> {
    private static final String MODULE_NAME = "PSCANNER:";
    @NonNull
    private String packagePath;
    @NonNull
    private ClassLoader cl;

    /**
     * instantiation class list
     *
     * @param classNames
     * @param tClass
     * @return
     * @throws Exception
     */
    @Override
    public List<T> instantiation(List<String> classNames, Class<T> tClass) {
        List<T> objs = new ArrayList<>(classNames.size());
        classNames.forEach(className -> {
            try {
                Class<?> clazz = Class.forName(className);
                if (!clazz.isInterface() && !clazz.isEnum()) {
                    if (clazz.isAssignableFrom(tClass)) {
                        T t = (T) clazz.newInstance();
                        objs.add(t);
                    }
                }
            } catch (Exception e) {
                log.error(MODULE_NAME + e.getMessage());
            }
        });
        return objs;
    }

    /**
     * scanning class package except a target class
     *
     * @param except
     * @return
     * @throws Exception
     */
    @Override
    public List<T> scan(Class<T> except) {
        List<String> classNames = classNames(except.getName());
        return instantiation(classNames, except);
    }

    /**
     * scanning class package except two target class
     *
     * @param except
     * @param except2
     * @return
     * @throws Exception
     */
    @Override
    public List<T> scan(Class<T> except, Class<?> except2) {
        List<String> classNames = classNames(except.getName(), except2.getName());
        return instantiation(classNames, except);
    }

    @Override
    public List<String> classNames(String except, String except2) {
        return classNames(this.packagePath, new ArrayList<>(), name -> {
            if (!except.equals(name) && !except2.equals(name))
                return name;
            return null;
        });
    }

    @Override
    public List<String> classNames(String except) {
        return classNames(this.packagePath, new ArrayList<>(), name -> {
            if (!except.equals(name))
                return name;
            return null;
        });
    }

    @Override
    public List<String> classNames() {
        return classNames(packagePath, new ArrayList<>(), name -> name);
    }

    /**
     * scanning class names below package path.
     *
     * @param packagePath
     * @param nameList
     * @return
     */
    private List<String> classNames(String packagePath, List<String> nameList, ScannerFilter filter) {
        // "." -> "/"
        String splashPath = dotToSplash(packagePath);
        URL url = cl.getResource(splashPath);
        String filePath = getRootPath(url);
        /**
         * get classes in that package.
         * normal file in the directory.
         * if the web server does not unzip the jar file, then classes will exist in jar file.
         */
        List<String> names = null;
        // contains the name of the class file. e.g., Demo.class will be stored as "Demo"
        if (isJarFile(filePath)) {
            log.info(MODULE_NAME + filePath + " is a jar.");
            // jar file
            names = readFromJarFile(filePath, splashPath);
        } else {
            log.info(MODULE_NAME + filePath + " is a directory.");
            // directory
            names = readFromDirectory(filePath);
        }
        names.forEach(name -> {
            if (isClassFile(name)) {
                String fullyQualifiedName = toFullyQualifiedName(name, packagePath);
                fullyQualifiedName = filter.filte(fullyQualifiedName);
                if (fullyQualifiedName != null)
                    nameList.add(fullyQualifiedName);
            } else {
                /**
                 * this is a directory
                 * check this directory for more classes
                 */
                classNames(packagePath + "." + name, nameList, filter);
            }
        });
        return nameList;
    }

    /**
     * read class name list below jar.
     *
     * @param jarPath
     * @param splashedPackageName
     * @return
     * @throws IOException
     */
    @SneakyThrows
    private List<String> readFromJarFile(String jarPath, String splashedPackageName) {
        log.info("loading from jar:" + jarPath);
        JarInputStream jarIn = new JarInputStream(new FileInputStream(jarPath));
        JarEntry entry = jarIn.getNextJarEntry();
        List<String> classNames = new ArrayList<>();
        while (null != entry) {
            String name = entry.getName();
            if (name.startsWith(splashedPackageName) && isClassFile(name))
                classNames.add(name.substring(name.lastIndexOf("/") + 1));
            entry = jarIn.getNextJarEntry();
        }
        return classNames;
    }

    /**
     * read class list below directory
     *
     * @param path
     * @return
     */
    private List<String> readFromDirectory(String path) {
        File file = new File(path);
        String[] names = file.list();
        if (null == names)
            return null;
        return Arrays.asList(names);
    }

    /**
     * turn to qualified class name.
     *
     * @param shortName
     * @param basePackage
     * @return
     */
    private String toFullyQualifiedName(String shortName, String basePackage) {
        StringBuilder sb = new StringBuilder(basePackage);
        sb.append('.');
        sb.append(trimExtension(shortName));

        return sb.toString();
    }

    /**
     * "Demo.class" -> "Demo"
     */
    private String trimExtension(String name) {
        int pos = name.indexOf('.');
        if (-1 != pos) {
            return name.substring(0, pos);
        }
        return name;
    }

    /**
     * "file:/home/whf/cn/fh" -> "/home/whf/cn/fh"
     * "jar:file:/home/whf/foo.jar!cn/fh" -> "/home/whf/foo.jar"
     */
    private String getRootPath(URL url) {
        String fileUrl = url.getFile();
        int pos = fileUrl.indexOf('!');
        if (-1 == pos) {
            return fileUrl;
        }
        return fileUrl.substring(5, pos);
    }

    /**
     * dot replace to splash, '.' -> '/'
     */
    private String dotToSplash(String packagePath) {
        return packagePath.replaceAll("\\.", "/");
    }

    private boolean isJarFile(String name) {
        return name.endsWith(FILE_SUFFIX_JAR);
    }

    private boolean isClassFile(String name) {
        return name.endsWith(FILE_SUFFIX_CLASS);
    }
}
