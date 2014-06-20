package com.github.coe.gensite.jaxrs.model;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.jaxrs.model.wadl.Description;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement(name = "project")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "name", "description" })
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageDescription {
    public Class<?> messageType;

    @XmlElement(name = "id", required = false)
    public Description description;

    public MessageDescription() {
        super();
    }

    public MessageDescription(Class<?> messageType) {
        super();
        this.messageType = messageType;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public Class<?> getMessageType() {
        return messageType;
    }

    public void setMessageType(Class<?> messageType) {
        this.messageType = messageType;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
