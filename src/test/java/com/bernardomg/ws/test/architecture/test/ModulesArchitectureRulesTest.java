
package com.bernardomg.ws.test.architecture.test;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packages = { "com.bernardomg" }, importOptions = ImportOption.DoNotIncludeTests.class)
public class ModulesArchitectureRulesTest {

    @ArchTest
    static final ArchRule module_dependencies_are_respected = layeredArchitecture().consideringAllDependencies()

        .layer("Data")
        .definedBy("com.bernardomg.data..")
        .layer("Exceptions")
        .definedBy("com.bernardomg.exception..")
        .layer("WS response")
        .definedBy("com.bernardomg.ws.response..")
        .layer("WS config")
        .definedBy("com.bernardomg.ws.configuration..")
        .layer("WS Spring error")
        .definedBy("com.bernardomg.ws.springframework.error..")
        .layer("WS Spring response")
        .definedBy("com.bernardomg.ws.springframework.response..")
        .layer("WS Spring request")
        .definedBy("com.bernardomg.ws.springframework.request..")

        .whereLayer("Data")
        .mayOnlyBeAccessedByLayers("Data", "WS response", "WS Spring response", "WS Spring request")
        .whereLayer("Exceptions")
        .mayOnlyBeAccessedByLayers("WS Spring error")
        .whereLayer("WS response")
        .mayOnlyBeAccessedByLayers("WS Spring response", "WS Spring error")
        .whereLayer("WS Spring response")
        .mayOnlyBeAccessedByLayers("WS config")
        .whereLayer("WS Spring error")
        .mayOnlyBeAccessedByLayers("WS config")
        .whereLayer("WS config")
        .mayNotBeAccessedByAnyLayer();

}
