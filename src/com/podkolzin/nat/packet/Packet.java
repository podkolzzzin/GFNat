package com.podkolzin.nat.packet;

import com.esotericsoftware.kryo.Kryo;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by podko_000 on 25.02.14.
 */
public class Packet {
    public static void registerAll(Kryo kryo) {
        registerPackage(kryo,"com.podkolzin.nat.packet");
        registerPackage(kryo,"com.podkolzin.nat.primitives");
    }

    public static void registerPackage(Kryo kryo,String scannedPackage) {
        List<Class<?>> classes = ClassFinder.find(scannedPackage);
        for(Class<?> c:classes) {
            kryo.register(c);
            System.out.println(c.getSimpleName()+" is kryo registered");
        }
    }
}


final class ClassFinder {

    private final static char DOT = '.';
    private final static char SLASH = '/';
    private final static String CLASS_SUFFIX = ".class";
    private final static String BAD_PACKAGE_ERROR = "Unable to get resources from path '%s'. Are you sure the given '%s' package exists?";

    public final static List<Class<?>> find(final String scannedPackage) {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final String scannedPath = scannedPackage.replace(DOT, SLASH);
        final Enumeration<URL> resources;
        try {
            resources = classLoader.getResources(scannedPath);
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format(BAD_PACKAGE_ERROR, scannedPath, scannedPackage), e);
        }
        final List<Class<?>> classes = new LinkedList<Class<?>>();
        while (resources.hasMoreElements()) {
            final File file = new File(resources.nextElement().getFile());
            classes.addAll(find(file, scannedPackage));
        }
        return classes;
    }

    private final static List<Class<?>> find(final File file, final String scannedPackage) {
        final List<Class<?>> classes = new LinkedList<Class<?>>();
        final String resource = scannedPackage + DOT + file.getName();
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File nestedFile : files) {
                if(nestedFile.isDirectory())
                    classes.addAll(find(nestedFile, scannedPackage+DOT+nestedFile.getName()));
                else
                    classes.addAll(find(nestedFile, scannedPackage));
            }
        } else if (resource.endsWith(CLASS_SUFFIX)) {
            final int beginIndex = 0;
            final int endIndex = resource.length() - CLASS_SUFFIX.length();
            final String className = resource.substring(beginIndex, endIndex);
            try {
                classes.add(Class.forName(className));
            } catch (ClassNotFoundException ignore) {
            }
        }
        return classes;
    }

}