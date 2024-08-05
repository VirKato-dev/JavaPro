package ru.flamexander.http.server.application.processors;

import ru.flamexander.http.server.HttpRequest;
import ru.flamexander.http.server.HttpStatus;
import ru.flamexander.http.server.processors.DefaultResponseProcessor;
import ru.flamexander.http.server.processors.RequestProcessor;
import ru.flamexander.http.server.validation.HeaderValidation;

import java.io.IOException;
import java.io.OutputStream;

public class HelloWorldRequestProcessor implements RequestProcessor {
    @Override
    public void execute(HttpRequest httpRequest, OutputStream output) throws IOException {
        HeaderValidation.check(httpRequest, "Accept", "text/html", HttpStatus.NOT_ACCEPTABLE);

        String response = "Content-Type: text/html\r\n\r\n<html><body><h1>Hello World!!!</h1></body></html>";
        new DefaultResponseProcessor().execute(HttpStatus.OK, response, httpRequest, output);
    }
}
