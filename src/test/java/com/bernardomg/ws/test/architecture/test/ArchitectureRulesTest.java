
package com.bernardomg.ws.test.architecture.test;

import com.bernardomg.framework.testing.architecture.rule.CodingRules;
import com.bernardomg.framework.testing.architecture.rule.DependencyRules;
import com.bernardomg.framework.testing.architecture.rule.springframework.SpringRules;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchTests;

@AnalyzeClasses(packages = { "com.bernardomg.data", "com.bernardomg.exception", "com.bernardomg.ws" },
        importOptions = ImportOption.DoNotIncludeTests.class)
public class ArchitectureRulesTest {

    @ArchTest
    static final ArchTests codingRules        = ArchTests.in(CodingRules.class);

    @ArchTest
    static final ArchTests configurationRules = ArchTests.in(SpringRules.class);

    @ArchTest
    static final ArchTests dependencyRules    = ArchTests.in(DependencyRules.class);

}
