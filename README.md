jaxrs-gensite-maven-plugin
==========================

A Maven plugin to generate site using JAX-RS annotation

## Features supported

* Scan a class
* Scan a classpath

## Scan

You can scan a Class or a Classpath to return all the data you want to collect using our model data in package com.github.coe.gensite.jaxrs.model.

### Scan a class with ClassAnnotationScanner

The method com.github.coe.gensite.jaxrs.scanner.ClassAnnotationScanner.deepScanSource(..) can be used to scan a particular class and extract all his data into com.github.coe.gensite.jaxrs.model.ServiceDescription.

```ClassAnnotationScanner.deepScanSource("package.ServiceInterface");
```

### Scan a classpath for all candidates with JaxRsClassPathScanner

The method com.github.coe.gensite.jaxrs.scanner.JaxRsClassPathScanner.scanAllCandidates(..) scan the current classpath to extract all candidates, from which it call ClassAnnotationScanner.deepScanSource(..) to extract data.

```JaxRsClassPathScanner.scanAllCandidates(javax.ws.rs.Path.class);
```
