package com.github.coe.gensite.jaxrs.object;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;

/**
 * Create an ObjectMapper using JAXB Annotation from package javax.xml.bind.annotation.
 * 
 * {@link http://wiki.fasterxml.com/JacksonJAXBAnnotations}
 * 
 * @author frtu
 */
public class MappingFactory {
    public static ObjectMapper createJaxbObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        AnnotationIntrospector introspector = new JaxbAnnotationIntrospector();
        objectMapper.setAnnotationIntrospector(introspector);
        AnnotationIntrospector secondary = new JacksonAnnotationIntrospector();
        
        objectMapper.setAnnotationIntrospector(AnnotationIntrospector.pair(introspector, secondary));

        return objectMapper;
    }
}
