package com.github.coe.gensite.jaxrs.model;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.jaxrs.model.wadl.Description;

public class OperationDescription {
    public Method methodSource;

    public Description description;
    public Path path;
    public Produces produces;
    public Consumes consumes;

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

    public Method getMethodSource() {
        return methodSource;
    }

    public void setMethodSource(Method methodSource) {
        this.methodSource = methodSource;
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
