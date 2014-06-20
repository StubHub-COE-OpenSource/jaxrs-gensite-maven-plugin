package com.github.coe.gensite.jaxrs.classpath;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;

import javax.ws.rs.core.Context;

import org.apache.commons.lang3.ClassUtils;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.MethodCallback;

import com.github.coe.gensite.jaxrs.model.MessageDescription;
import com.github.coe.gensite.jaxrs.model.OperationDescription;
import com.github.coe.gensite.jaxrs.model.ServiceDescription;
import com.github.frtu.simple.helpers.reflect.ClassPathScanningAnnotationProvider;
import com.github.frtu.simple.helpers.reflect.ReflectionMapperUtil;

public class JaxRsClassPathScanner {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(JaxRsClassPathScanner.class);

    public static ServiceDescription scanSource(final Class<?> classSource) {
        final ServiceDescription scanSourceClass = ReflectionMapperUtil.scanAnnotationSource(classSource, ServiceDescription.class);
        scanSourceClass.setClassSource(classSource);
        return scanSourceClass;
    }

    public static OperationDescription scanSource(final Method methodSource) {
        OperationDescription operationDescription = ReflectionMapperUtil.scanAnnotationSource(methodSource,
                                                                                              OperationDescription.class);
        operationDescription.setMethodSource(methodSource);
        operationDescription.setOutputMessageDescription(new MessageDescription(methodSource.getReturnType()));

        Class<?>[] parameterTypes = methodSource.getParameterTypes();
        Annotation[][] parameterAnnotations = methodSource.getParameterAnnotations();
        for (int i = 0; i < parameterTypes.length; i++) {
            // Get the according parameter and annotations
            Class<?> parameterType = parameterTypes[i];
            Annotation[] parameterAnnotationArray = parameterAnnotations[i];

            // SHOULD SELECT WHAT TO USE!
            boolean isParameter = true;
            MessageDescription inputMessageDescription = new MessageDescription(parameterType);
            if (parameterAnnotationArray != null) {
                for (Annotation parameterAnnotation : parameterAnnotationArray) {
                    if (parameterAnnotation instanceof Context) {
                        isParameter = false;
                    }
                }
            }
            if (isParameter) {
                operationDescription.addInputMessageDescription(inputMessageDescription);
            } else {
                logger.info("For method name={}, skip parameterType={}", methodSource.getName(), parameterType);
            }
        }
        System.out.println(operationDescription);
        return operationDescription;
    }

    public static ArrayList<ServiceDescription> scanAllCandidates(Class<? extends Annotation> scannedAnnotationType) {
        final ArrayList<ServiceDescription> allServiceDescriptions = new ArrayList<ServiceDescription>();

        Set<BeanDefinition> findCandidateComponents = ClassPathScanningAnnotationProvider.findCandidateComponents(scannedAnnotationType);

        for (BeanDefinition beanDefinition : findCandidateComponents) {
            AnnotatedBeanDefinition annotationMetadata = (AnnotatedBeanDefinition) beanDefinition;

            String beanClassName = annotationMetadata.getBeanClassName();
            try {
                Class<?> clazz = ClassUtils.getClass(beanClassName);

                final ServiceDescription scanServiceDescription = scanSource(clazz);
                MethodCallback mc = new MethodCallback() {
                    @Override
                    public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {

                        OperationDescription scanOperationDescription = scanSource(method);
                        scanServiceDescription.addOperationDescription(scanOperationDescription);
                    }
                };
                ReflectionUtils.doWithMethods(clazz, mc);

                allServiceDescriptions.add(scanServiceDescription);
            } catch (ClassNotFoundException e) {
                logger.error("Error in instanciation of class named {}", beanClassName, e);
            }
        }
        return allServiceDescriptions;
    }
}
