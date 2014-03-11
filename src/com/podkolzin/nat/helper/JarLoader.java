package com.podkolzin.nat.helper;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created with IntelliJ IDEA.
 * User: podko_000
 * Date: 08.03.14
 * Time: 12:35
 * To change this template use File | Settings | File Templates.
 */
public class JarLoader {
    private static final Class<?>[] parameters = new Class[]{URL.class};

    public static void addFile(File f) throws IOException {
        addURL(f.toURI().toURL());
    }//end method

    /**
     * Adds the content pointed by the URL to the classpath.
     * @param u the URL pointing to the content to be added
     * @throws IOException
     */
    public static void addURL(URL u) throws IOException {
        URLClassLoader sysloader = (URLClassLoader)ClassLoader.getSystemClassLoader();
        Class<?> sysclass = URLClassLoader.class;
        try {
            Method method = sysclass.getDeclaredMethod("addURL",parameters);
            method.setAccessible(true);
            method.invoke(sysloader,new Object[]{ u });
        } catch (Throwable t) {
            t.printStackTrace();
            throw new IOException("Error, could not add URL to system classloader");
        }//end try catch
    }//end method
}
