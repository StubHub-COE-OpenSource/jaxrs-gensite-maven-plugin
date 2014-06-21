package com.github.coe.gensite.jaxrs.maven;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

import com.github.coe.gensite.jaxrs.model.AbstractElementDescription;
import com.github.coe.gensite.jaxrs.model.ServiceDescription;
import com.github.coe.gensite.jaxrs.scanner.JaxRsClassPathScanner;

/**
 * Scan the classpath for JAXRS annotations.
 * 
 * @author frtu
 */
@Mojo(name = "scan", // configurator = "include-project-dependencies",
requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME)
public class JaxrsAnnoationScannerMojo extends AbstractMojo {
    /**
     * This is where the output is generated.
     */
    @Parameter(property = "jaxrs.outputDirectory", defaultValue = "target")
    private File outputDirectory;

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject mavenProject;

    public void execute() throws MojoExecutionException {
        initClassloader(getLog());

        getLog().info("Generate file into " + outputDirectory.getAbsolutePath());
        File f = outputDirectory;
        if (!f.exists()) {
            f.mkdirs();
        }
        File touch = new File(f, "doc.html");

        final ArrayList<ServiceDescription> allServiceDescriptions = JaxRsClassPathScanner.scanAllCandidates(javax.ws.rs.Path.class);
        for (AbstractElementDescription serviceDescription : allServiceDescriptions) {
            System.out.println(serviceDescription);
        }

        // FileWriter w = null;
        // try {
        // w = new FileWriter(touch);
        //
        // for (BeanDefinition beanDefinition : findCandidateComponents) {
        // String beanClassName = beanDefinition.getBeanClassName();
        // w.write(beanClassName);
        // }
        // } catch (IOException e) {
        // throw new MojoExecutionException("Error creating file " + touch, e);
        // } finally {
        // if (w != null) {
        // try {
        // w.close();
        // } catch (IOException e) {
        // // ignore
        // }
        // }
        // }
    }

    @SuppressWarnings("unchecked")
    private void initClassloader(Log log) {
        List<String> classpathElements;
        try {
            classpathElements = mavenProject.getCompileClasspathElements();
        } catch (DependencyResolutionRequiredException e) {
            throw new RuntimeException(e);
        }
        ClassloaderUtil.initMavenWithCompileClassloader(classpathElements, log);
    }
}