package com.github.coe.gensite.jaxrs.scanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.ws.rs.core.Context;

import org.apache.commons.lang3.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.MethodCallback;

import com.github.coe.gensite.jaxrs.model.MessageDescription;
import com.github.coe.gensite.jaxrs.model.OperationDescription;
import com.github.coe.gensite.jaxrs.model.ServiceDescription;
import com.github.frtu.simple.infra.reflect.ReflectionMapperUtil;

/**
 * This is the class where Annotation are retrieved from the Class or Method source and store respectively into
 * {@link com.github.coe.gensite.jaxrs.model.ServiceDescription} or {@link com.github.coe.gensite.jaxrs.model.OperationDescription}
 * 
 * @author frtu
 */
public class ClassAnnotationScanner {
    /**
     * Scan method for OperationDescription
     * 
     * @param methodSource
     * @return
     */
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
                JaxRsClassPathScanner.logger.info("For method name={}, skip parameterType={}", methodSource.getName(),
                                                  parameterType);
            }
        }
        return operationDescription;
    }

    /**
     * Scan class for ServiceDescription
     * 
     * @param classSource
     * @return
     */
    public static ServiceDescription scanSource(final Class<?> classSource) {
        final ServiceDescription scanSourceClass = ReflectionMapperUtil.scanAnnotationSource(classSource, ServiceDescription.class);
        scanSourceClass.setClassSource(classSource);
        return scanSourceClass;
    }

    /**
     * This is the entry point for class deep scan, retrieving ServiceDescription and all inner OperationDescription.
     * 
     * @param beanClassName
     * @return
     * @throws ClassNotFoundException
     */
    public static ServiceDescription deepScanSource(String beanClassName) {
        Class<?> clazz;
        try {
            clazz = ClassUtils.getClass(beanClassName);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Argument beanClassName doesn't exist :" + beanClassName, e);
        }
        final ServiceDescription scanServiceDescription = deepScanSource(clazz);
        return scanServiceDescription;
    }

    /**
     * This is the entry point for class deep scan, retrieving ServiceDescription and all inner OperationDescription.
     * 
     * @param clazz
     * @return
     */
    public static ServiceDescription deepScanSource(Class<?> clazz) {
        final ServiceDescription scanServiceDescription = scanSource(clazz);
        MethodCallback mc = new MethodCallback() {
            @Override
            public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {

                OperationDescription scanOperationDescription = scanSource(method);
                scanServiceDescription.addOperationDescription(scanOperationDescription);
            }
        };
        ReflectionUtils.doWithMethods(clazz, mc);
        return scanServiceDescription;
    }
}
