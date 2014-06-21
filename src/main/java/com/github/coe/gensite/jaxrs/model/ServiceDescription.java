package com.github.coe.gensite.jaxrs.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@XmlRootElement(name = "service")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = { "classSourceCanonicalName", "descriptionTitle", "descriptionValue", "pathValue", "consumeValues",
                      "produceValues" })
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceDescription extends AbstractElementDescription {
    public Class<?> classSource;
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

    public List<OperationDescription> getOperationDescriptions() {
        return operationDescriptions;
    }
}
