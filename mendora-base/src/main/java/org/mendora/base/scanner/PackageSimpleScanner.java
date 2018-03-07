package org.mendora.base.scanner;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by kam on 2017/12/4.
 */
public class PackageSimpleScanner<T> implements PackageScanner<T> {


    @Override
    public Set<T> scan(String packageName, Class<?> except, Class<?> except2) throws Exception {
        return (Set<T>) instantiation(classNames(packageName, except.getName(), except2.getName()), except);
    }

    @Override
    public Set<T> scan(String packageName, Class<?> except) throws Exception {
        return (Set<T>) instantiation(classNames(packageName, except.getName()), except);
    }

    @Override
    public Set<?> instantiation(Set<String> classNames, Class<?> tClass) throws Exception {
        Set<Object> objs = new HashSet<>(classNames.size(), 1.0f);
        classNames.forEach(className -> {
            try {
                Class<?> clazz = Class.forName(className);
                if (!clazz.isInterface() && !clazz.isEnum()) {
                    Object o = clazz.newInstance();
                    if (tClass.isAssignableFrom(o.getClass()))
                        objs.add(o);
                }
            } catch (Exception e) {
                e.getCause().printStackTrace();
            }
        });
        return objs;
    }

    /**
     * Find target and filter class name blow package.
     *
     * @param packageName
     * @param filter
     * @return
     * @throws IOException
     */
    private Set<String> classNames(String packageName, ScannerFilter filter) throws Exception {
        String packagePath = packageName.replace(".", "/");
        Set<String> set = new HashSet<>();
        URI uri = ClassLoader.getSystemResource(packagePath).toURI();
        Files.walkFileTree(Paths.get(uri), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
                String ele = filter.filte(packagePath, path);
                if (StringUtils.isNoneEmpty(ele))
                    set.add(ele);
                return FileVisitResult.CONTINUE;
            }
        });
        return set;
    }

    @Override
    public Set<String> classNames(String packageName, String except, String except2) throws Exception {
        return classNames(packageName, (packagePath, path) -> {
            String targetPath = path.toUri().getPath();
            String eleName = targetPath.substring(targetPath.lastIndexOf("/") + 1);
            eleName = packageName + "." + eleName.substring(0, eleName.lastIndexOf("."));
            if (path.toUri().getPath().endsWith(FILE_SUFFIX) && !except.equals(eleName) && !except2.equals(eleName))
                return eleName;
            return null;
        });
    }

    @Override
    public Set<String> classNames(String packageName, String except) throws Exception {
        return classNames(packageName, (packagePath, path) -> {
            String targetPath = path.toUri().getPath();
            String eleName = targetPath.substring(targetPath.lastIndexOf("/") + 1);
            eleName = packageName + "." + eleName.substring(0, eleName.lastIndexOf("."));
            if (path.toUri().getPath().endsWith(FILE_SUFFIX) && !except.equals(eleName))
                return eleName;
            return null;
        });
    }

}
