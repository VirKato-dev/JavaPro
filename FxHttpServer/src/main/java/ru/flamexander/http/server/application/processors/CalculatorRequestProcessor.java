package ru.flamexander.http.server.application.processors;

import ru.flamexander.http.server.HttpRequest;
import ru.flamexander.http.server.HttpStatus;
import ru.flamexander.http.server.processors.DefaultResponseProcessor;
import ru.flamexander.http.server.processors.RequestProcessor;
import ru.flamexander.http.server.validation.HeaderValidation;

import java.io.IOException;
import java.io.OutputStream;

public class CalculatorRequestProcessor implements RequestProcessor {
    @Override
    public void execute(HttpRequest httpRequest, OutputStream output) throws IOException {
        HeaderValidation.check(httpRequest, "Accept", "text/html", HttpStatus.NOT_ACCEPTABLE);

        int a = Integer.parseInt(httpRequest.getParameter("a"));
        int b = Integer.parseInt(httpRequest.getParameter("b"));
        int result = a + b;
        String outMessage = a + " + " + b + " = " + result;

        String response = "Content-Type: text/html\r\n\r\n<html><body><h1>" + outMessage + "</h1></body></html>";
        new DefaultResponseProcessor().execute(HttpStatus.OK, response, httpRequest, output);
    }
}
