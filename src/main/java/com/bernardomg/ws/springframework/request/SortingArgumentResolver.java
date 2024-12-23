/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023 the original author or authors.
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bernardomg.ws.springframework.request;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.domain.Sorting.Direction;
import com.bernardomg.data.domain.Sorting.Property;

/**
 * Argument resolver to acquire a {@link Sorting} from the request parameters.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public final class SortingArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public final Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
            final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) {
        final String[]             propertiesParams;
        final Collection<Property> properties;

        propertiesParams = webRequest.getParameterValues("sort");
        if (propertiesParams != null) {
            properties = Arrays.stream(propertiesParams)
                .map(p -> p.split(","))
                .filter(p -> p.length >= 2)
                .map(this::toProperty)
                .toList();
        } else {
            properties = List.of();
        }

        return new Sorting(List.copyOf(properties));
    }

    @Override
    public final boolean supportsParameter(final MethodParameter parameter) {
        return parameter.getParameterType()
            .equals(Sorting.class);
    }

    private final Property toProperty(final String[] parts) {
        final String    propertyName;
        final String    directionName;
        final Direction direction;

        directionName = parts[1].trim()
            .toUpperCase();
        if ("desc".equalsIgnoreCase(directionName)) {
            direction = Direction.DESC;
        } else {
            direction = Direction.ASC;
        }

        propertyName = parts[0].trim();

        return new Property(propertyName, direction);
    }

}
