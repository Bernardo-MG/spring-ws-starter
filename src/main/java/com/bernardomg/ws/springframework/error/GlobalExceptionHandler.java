/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023-2025 the original author or authors.
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

package com.bernardomg.ws.springframework.error;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bernardomg.exception.MissingIdException;
import com.bernardomg.validation.domain.exception.FieldFailureException;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.ws.response.domain.model.ErrorResponse;
import com.bernardomg.ws.response.domain.model.FailureResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * Captures and handles general use exceptions. This includes validation exceptions.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Default constructor.
     */
    public GlobalExceptionHandler() {
        super();
    }

    @ExceptionHandler({ IllegalArgumentException.class, HttpMessageConversionException.class,
            DataAccessException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ErrorResponse handleBadRequestException(final Exception ex, final WebRequest request) {
        log.warn(ex.getMessage(), ex);

        return new ErrorResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Bad request");
    }

    /**
     * Handles unmapped exceptions.
     *
     * @param ex
     *            exception to handle
     * @param request
     *            request
     * @return internal error response
     */
    @ExceptionHandler({ RuntimeException.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final ErrorResponse handleExceptionDefault(final Exception ex, final WebRequest request) {
        log.warn(ex.getMessage(), ex);

        return new ErrorResponse(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), "Internal error");
    }

    @ExceptionHandler({ MissingIdException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ErrorResponse handleMissingDataException(final MissingIdException ex, final WebRequest request) {
        final String message;

        log.warn(ex.getMessage(), ex);

        message = String.format("Id %s not found", String.valueOf(ex.getId()));

        return new ErrorResponse(String.valueOf(HttpStatus.NOT_FOUND.value()), message);
    }

    @ExceptionHandler({ FieldFailureException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final FailureResponse handleValidationException(final FieldFailureException ex, final WebRequest request) {
        final Map<String, List<FieldFailure>> failures;

        log.warn(ex.getMessage(), ex);

        failures = ex.getFailures()
            .stream()
            .collect(Collectors.groupingBy(FieldFailure::field));

        return new FailureResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Field validation failure",
            failures);
    }

    /**
     * Transforms Spring's field error into our custom field error.
     *
     * @param error
     *            error object to transform
     * @return our custom error object
     */
    private final FieldFailure toFieldFailure(final org.springframework.validation.FieldError error) {
        final Collection<String> codes;
        final String             code;

        log.error("{}.{} with value {}: {}", error.getObjectName(), error.getField(), error.getRejectedValue(),
            error.getDefaultMessage());

        // TODO: add an error mapper
        if (error.getCodes() == null) {
            codes = List.of();
        } else {
            codes = Arrays.asList(error.getCodes());
        }
        if (codes.contains("NotNull") || codes.contains("NotEmpty")) {
            code = "empty";
        } else {
            code = "";
        }

        return new FieldFailure(code, error.getDefaultMessage(), error.getField(), error.getRejectedValue());
    }

    @Override
    protected final ResponseEntity<Object> handleExceptionInternal(final Exception ex, @Nullable final Object body,
            final HttpHeaders headers, final HttpStatusCode statusCode, final WebRequest request) {
        final ErrorResponse response;
        final String        message;

        log.error(ex.getMessage());

        message = "Server error. Contact admin.";

        response = new ErrorResponse(String.valueOf(statusCode.value()), message);

        return super.handleExceptionInternal(ex, response, headers, statusCode, request);
    }

    @Override
    protected final ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
            final HttpHeaders headers, final HttpStatusCode status, final WebRequest request) {
        final Map<String, List<FieldFailure>> failures;
        final FailureResponse                 response;

        failures = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(this::toFieldFailure)
            .collect(Collectors.groupingBy(FieldFailure::field));

        response = new FailureResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Field validation failure",
            failures);

        return super.handleExceptionInternal(ex, response, headers, status, request);
    }

    @Override
    protected final ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers,
            final HttpStatusCode status, final WebRequest request) {
        final ErrorResponse response;

        log.warn(ex.getMessage(), ex);

        response = new ErrorResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Bad request");

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
