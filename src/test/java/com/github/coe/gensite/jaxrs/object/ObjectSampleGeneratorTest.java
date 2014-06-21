package com.github.coe.gensite.jaxrs.object;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.github.coe.gensite.jaxrs.object.ObjectSampleGenerator;

public class ObjectSampleGeneratorTest {
    @Test
    public void testGetJSONSampleFromStringClassOfT() {
        String jsonSampleFromString = ObjectSampleGenerator.getJSONSampleFromString(TestBean.class);
        assertNotNull(jsonSampleFromString);
        assertEquals("{\"id\":\"id\",\"name\":\"name\"}", jsonSampleFromString);
    }

    @Test
    public void testGetXMLSampleFromStringClassOfT() {
        String xmlSampleFromString = ObjectSampleGenerator.getXMLSampleFromString(TestBean.class);
        assertNotNull(xmlSampleFromString);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><testBean><id>id</id><name>name</name></testBean>",
                     xmlSampleFromString);
    }

}
