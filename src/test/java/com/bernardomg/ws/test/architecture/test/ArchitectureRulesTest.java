
package com.bernardomg.ws.test.architecture.test;

import com.bernardomg.ws.test.architecture.rule.CodingRules;
import com.bernardomg.ws.test.architecture.rule.ConfigurationRules;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchTests;

@AnalyzeClasses(
        packages = { "com.bernardomg.exception", "com.bernardomg.web" , "com.bernardomg.ws"  },
        importOptions = ImportOption.DoNotIncludeTests.class)
public class ArchitectureRulesTest {

    @ArchTest
    static final ArchTests codingRules        = ArchTests.in(CodingRules.class);

    @ArchTest
    static final ArchTests configurationRules = ArchTests.in(ConfigurationRules.class);

}
