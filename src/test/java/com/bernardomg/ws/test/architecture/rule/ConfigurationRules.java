
package com.bernardomg.ws.test.architecture.rule;

import com.bernardomg.ws.test.architecture.predicate.Predicates;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

public final class ConfigurationRules {

    @ArchTest
    static final ArchRule configuration_should_be_in_configuration_package = classes()
        .that(Predicates.areConfigurationClasses())
        .should()
        .resideInAPackage("..config..");

    @ArchTest
    static final ArchRule configuration_should_be_suffixed                 = classes()
        .that(Predicates.areConfigurationClasses())
        .should()
        .haveSimpleNameEndingWith("Config");

}
