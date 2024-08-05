package ru.flamexander.http.server.application.processors.calc;

import ru.flamexander.http.server.HttpRequest;
import ru.flamexander.http.server.HttpStatus;
import ru.flamexander.http.server.processors.DefaultResponseProcessor;
import ru.flamexander.http.server.processors.RequestProcessor;
import ru.flamexander.http.server.validation.HeaderValidation;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class CalcMulRequestProcessor implements RequestProcessor {
    @Override
    public void execute(HttpRequest httpRequest, OutputStream output) throws IOException {
        HeaderValidation.check(httpRequest, "Accept", "text/html", HttpStatus.NOT_ACCEPTABLE);

        double a = Double.parseDouble(httpRequest.getParameter("a").replace(",", "."));
        double b = Double.parseDouble(httpRequest.getParameter("b").replace(",", "."));
        double result = a * b;
        DecimalFormat df = new DecimalFormat("#.#", DecimalFormatSymbols.getInstance(Locale.CANADA));
        String outMessage = String.format("%s * %s = %s", df.format(a), df.format(b), df.format(result));

        String response = "Content-Type: text/html\r\n\r\n<html><body><h1>" + outMessage + "</h1></body></html>";
        new DefaultResponseProcessor().execute(HttpStatus.OK, response, httpRequest, output);
    }
}
