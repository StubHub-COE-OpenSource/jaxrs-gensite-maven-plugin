package com.github.coe.gensite.jaxrs.scanner;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.github.coe.gensite.jaxrs.model.OperationDescription;
import com.github.coe.gensite.jaxrs.model.ServiceDescription;

public class ClassAnnotationScannerTest {
    @Test
    public void testDeepScanSourceStrictJaxRsServiceContract() throws JsonGenerationException, JsonMappingException, IOException {
        ServiceDescription scanSourceClass = ClassAnnotationScanner.deepScanSource(StrictJaxRsServiceContract.class);
        System.out.println(scanSourceClass.toJSON());
    }

    @Test
    public void testDeepScanSourceXtendedServiceContract() throws JsonGenerationException, JsonMappingException, IOException {
        ServiceDescription scanSourceClass = ClassAnnotationScanner.deepScanSource(XtendedServiceContract.class);
        System.out.println(scanSourceClass.toJSON());

        List<OperationDescription> operationDescriptions = scanSourceClass.getOperationDescriptions();
        for (OperationDescription operationDescription : operationDescriptions) {
            System.out.println(operationDescription.toJSON());
        }
    }
}
