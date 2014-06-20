package com.github.coe.gensite.jaxrs.scanner;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Set;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;

import com.github.coe.gensite.jaxrs.model.ServiceDescription;
import com.github.frtu.simple.helpers.reflect.ClassPathScanningAnnotationProvider;

/**
 * This is the Classpath scanner for all annotation Class<? extends Annotation> that is passed on parameter.
 * 
 * @author frtu
 */
public class JaxRsClassPathScanner {
    static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(JaxRsClassPathScanner.class);

    /**
     * Search for classpath and return a list of {@link ServiceDescription}
     * 
     * @param scannedAnnotationType
     * @return
     */
    public static ArrayList<ServiceDescription> scanAllCandidates(Class<? extends Annotation> scannedAnnotationType) {
        Set<BeanDefinition> findCandidateComponents = ClassPathScanningAnnotationProvider.findCandidateComponents(scannedAnnotationType);

        final ArrayList<ServiceDescription> allServiceDescriptions = new ArrayList<ServiceDescription>();
        for (BeanDefinition beanDefinition : findCandidateComponents) {
            AnnotatedBeanDefinition annotationMetadata = (AnnotatedBeanDefinition) beanDefinition;
            String beanClassName = annotationMetadata.getBeanClassName();
            try {
                final ServiceDescription scanServiceDescription = ClassAnnotationScanner.deepScanSource(beanClassName);
                allServiceDescriptions.add(scanServiceDescription);
            } catch (IllegalArgumentException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return allServiceDescriptions;
    }
}
