package ru.flamexander.http.server.validation;

import ru.flamexander.http.server.HttpRequest;
import ru.flamexander.http.server.HttpStatus;
import ru.flamexander.http.server.application.exceptions.HttpException;

public class HeaderValidation {
    public static void check(HttpRequest request, String headerName, String headerValue, HttpStatus status) {
        String acceptHeader = request.getHeader(headerName);
        if (acceptHeader == null || (!acceptHeader.contains("*/*") && !acceptHeader.contains(headerValue))) {
            throw new HttpException(status);
        }
    }
}
