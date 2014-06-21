package com.github.coe.gensite.jaxrs.model;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.jaxrs.model.wadl.Description;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.coe.gensite.jaxrs.object.MappingFactory;

public class AbstractElementDescription {
    private ObjectMapper objectMapper;

    public Description description;
    public Path path;
    public Produces produces;
    public Consumes consumes;

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