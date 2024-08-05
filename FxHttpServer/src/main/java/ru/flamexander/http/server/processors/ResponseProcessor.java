package ru.flamexander.http.server.processors;

import ru.flamexander.http.server.HttpRequest;
import ru.flamexander.http.server.HttpStatus;

import java.io.IOException;
import java.io.OutputStream;

public interface ResponseProcessor {
    void execute(HttpStatus httpStatus, String payload, HttpRequest request, OutputStream output) throws IOException;
}
