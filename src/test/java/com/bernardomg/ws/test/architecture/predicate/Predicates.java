
package com.bernardomg.ws.test.architecture.predicate;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;

public class Predicates {

    public static final DescribedPredicate<JavaClass> areConfigurationClasses() {
        return new IsSpringConfigurationClass();
    }

}
