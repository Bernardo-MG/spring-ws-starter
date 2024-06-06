
package com.bernardomg.ws.test.architecture.predicate;

public class Predicates {

    public static final CachedMethodPredicate areCachedMethod() {
        return new CachedMethodPredicate();
    }

    public static final CachingAnnotationPredicate areCachingAnnotation() {
        return new CachingAnnotationPredicate();
    }

    public static final ConfigurationClassPredicate areConfigurationClasses() {
        return new ConfigurationClassPredicate();
    }

    public static final ControllerClassPredicate areControllerClasses() {
        return new ControllerClassPredicate();
    }

    public static final ServiceClassPredicate areServiceClasses() {
        return new ServiceClassPredicate();
    }

    public static final ValidatorClassPredicate areValidatorClasses() {
        return new ValidatorClassPredicate();
    }

}
