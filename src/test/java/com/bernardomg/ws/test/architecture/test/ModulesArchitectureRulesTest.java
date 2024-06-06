
package com.bernardomg.ws.test.architecture.test;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(
        packages = { "com.bernardomg" },
        importOptions = ImportOption.DoNotIncludeTests.class)
public class ModulesArchitectureRulesTest {

    @ArchTest
    static final ArchRule module_dependencies_are_respected = layeredArchitecture().consideringAllDependencies()

        .layer("Exceptions")
        .definedBy("com.bernardomg.exception..")
        .layer("Web response")
        .definedBy("com.bernardomg.web.response..")
        .layer("WS response")
        .definedBy("com.bernardomg.ws.response..")
        .layer("WS error")
        .definedBy("com.bernardomg.ws.error..")
        .layer("WS config")
        .definedBy("com.bernardomg.ws.config..")

        .whereLayer("Exceptions")
        .mayOnlyBeAccessedByLayers("WS error")
        .whereLayer("Web response")
        .mayOnlyBeAccessedByLayers("WS response", "WS error")
        .whereLayer("WS response")
        .mayOnlyBeAccessedByLayers("WS config")
        .whereLayer("WS error")
        .mayOnlyBeAccessedByLayers("WS config")
        .whereLayer("WS config")
        .mayNotBeAccessedByAnyLayer();

}
