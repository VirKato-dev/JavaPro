package ru.flamexander.http.server.processors;

import ru.flamexander.http.server.HttpRequest;
import ru.flamexander.http.server.HttpStatus;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class DefaultResponseProcessor implements ResponseProcessor {
    @Override
    public void execute(HttpStatus httpStatus, String payload, HttpRequest request, OutputStream output) throws IOException {
        String response = httpStatus + payload;
        output.write(response.getBytes(StandardCharsets.UTF_8));
    }
}
