package com.github.coe.gensite.jaxrs.model;

import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;

public class MappingFactory {
    public static ObjectMapper createJaxbObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        AnnotationIntrospector introspector = new JaxbAnnotationIntrospector();
        // make deserializer use JAXB annotations (only)
        objectMapper.getDeserializationConfig().setAnnotationIntrospector(introspector);
        // make serializer use JAXB annotations (only)
        objectMapper.getSerializationConfig().setAnnotationIntrospector(introspector);
        return objectMapper;
    }
}
