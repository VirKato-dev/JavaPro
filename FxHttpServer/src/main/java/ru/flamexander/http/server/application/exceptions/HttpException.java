package ru.flamexander.http.server.application.exceptions;

import ru.flamexander.http.server.HttpStatus;

public class HttpException extends RuntimeException {
    public HttpException() {
        super();
    }

    public HttpException(HttpStatus status) {
        super(status.name());
    }
}
