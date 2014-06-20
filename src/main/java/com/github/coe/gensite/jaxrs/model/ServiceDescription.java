package com.github.coe.gensite.jaxrs.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.jaxrs.model.wadl.Description;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Component;

@XmlRootElement(name = "service")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = { "classSourceCanonicalName", "descriptionTitle", "descriptionValue", "pathValue", "consumeValues",
                      "produceValues" })
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceDescription {
    private ObjectMapper objectMapper;

    public Class<?> classSource;

    public Description description;
    public Path path;
    public Produces produces;
    public Consumes consumes;
    public Component component;

    public List<OperationDescription> operationDescriptions;

    public void addOperationDescription(OperationDescription operationDescription) {
        if (operationDescriptions == null) {
            operationDescriptions = new ArrayList<OperationDescription>();
        }
        operationDescriptions.add(operationDescription);
    }

    public void setClassSource(Class<?> classSource) {
        this.classSource = classSource;
    }

    public String getClassSourceCanonicalName() {
        if (classSource == null) {
            return null;
        }
        return classSource.getCanonicalName();
    }

    public String getDescriptionTitle() {
        if (description == null) {
            return null;
        }
        return description.title();
    }

    public String getDescriptionValue() {
        if (description == null) {
            return null;
        }
        return description.value();
    }

    public String getPathValue() {
        if (path == null) {
            return null;
        }
        return path.value();
    }

    public String[] getProduceValues() {
        if (produces == null) {
            return null;
        }
        return produces.value();
    }

    public String[] getConsumeValues() {
        if (consumes == null) {
            return null;
        }
        return consumes.value();
    }

    // public List<OperationDescription> getOperationDescriptions() {
    // return operationDescriptions;
    // }

    public String toJSON() {
        if (this.objectMapper == null) {
            this.objectMapper = MappingFactory.createJaxbObjectMapper();
        }

        try {
            return this.objectMapper.writeValueAsString(this);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
