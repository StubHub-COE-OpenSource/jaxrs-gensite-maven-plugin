package com.github.coe.gensite.jaxrs.model;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@XmlRootElement(name = "operation")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = { "methodSourceName", "descriptionTitle", "descriptionValue", "pathValue", "consumeValues", "produceValues" })
@JsonIgnoreProperties(ignoreUnknown = true)
public class OperationDescription extends AbstractElementDescription {
    public Method methodSource;

    public GET get;
    public POST post;
    public PUT put;
    public DELETE delete;

    public List<MessageDescription> inputMessageDescriptions;
    public MessageDescription outputMessageDescription;

    public void addInputMessageDescription(MessageDescription parameterType) {
        if (inputMessageDescriptions == null) {
            inputMessageDescriptions = new ArrayList<MessageDescription>();
        }
        inputMessageDescriptions.add(parameterType);
    }

    public void setMethodSource(Method methodSource) {
        this.methodSource = methodSource;
    }

    public String getMethodSourceName() {
        return methodSource.getName();
    }

    public List<MessageDescription> getInputMessageDescriptions() {
        return inputMessageDescriptions;
    }

    public void setInputMessageDescriptions(List<MessageDescription> inputMessageDescriptions) {
        this.inputMessageDescriptions = inputMessageDescriptions;
    }

    public MessageDescription getOutputMessageDescription() {
        return outputMessageDescription;
    }

    public void setOutputMessageDescription(MessageDescription outputMessageDescription) {
        this.outputMessageDescription = outputMessageDescription;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
