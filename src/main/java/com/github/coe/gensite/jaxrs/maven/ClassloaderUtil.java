package com.github.coe.gensite.jaxrs.maven;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.maven.plugin.logging.Log;
import org.springframework.util.ClassUtils;

/**
 * This is the class that help to add all projects class into the current Maven plugin classpath.
 * 
 * @author frtu
 */
public class ClassloaderUtil {
    public static void initMavenWithCompileClassloader(List<String> classpathElements, Log log) {
        try {
            Set<URL> urls = new HashSet<URL>();
            for (String element : classpathElements) {
                URL url = new File(element).toURI().toURL();
                urls.add(url);
                log.debug("Add to url list:" + urls);
            }

            log.info("All urls to the Thread.currentThread().setContextClassLoader()");
            // ClassLoader currentContextClassLoader = Thread.currentThread().getContextClassLoader();
            ClassLoader currentContextClassLoader = ClassUtils.getDefaultClassLoader();
            ClassLoader newContextClassLoader = URLClassLoader.newInstance(urls.toArray(new URL[0]), currentContextClassLoader);

            // Thread.currentThread().setContextClassLoader(newContextClassLoader);
            ClassUtils.overrideThreadContextClassLoader(newContextClassLoader);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
