package ru.flamexander.http.server.application.processors.calc;

import ru.flamexander.http.server.HttpRequest;
import ru.flamexander.http.server.HttpStatus;
import ru.flamexander.http.server.processors.DefaultResponseProcessor;
import ru.flamexander.http.server.processors.RequestProcessor;
import ru.flamexander.http.server.validation.HeaderValidation;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class CalcDivRequestProcessor implements RequestProcessor {
    @Override
    public void execute(HttpRequest httpRequest, OutputStream output) throws IOException {
        HeaderValidation.check(httpRequest, "Accept", "text/html", HttpStatus.NOT_ACCEPTABLE);

        double a = Double.parseDouble(httpRequest.getParameter("a").replace(",", "."));
        double b = Double.parseDouble(httpRequest.getParameter("b").replace(",", "."));
        DecimalFormat df = new DecimalFormat("#.#", DecimalFormatSymbols.getInstance(Locale.CANADA));
        double result = a / b;
        String outMessage;
        if (Double.isNaN(result) || Double.isInfinite(result)) {
            outMessage = String.format("%s / %s = WRONG", df.format(a), df.format(b));
        } else {
            outMessage = String.format("%s / %s = %s", df.format(a), df.format(b), df.format(result));
        }

        String response = "Content-Type: text/html\r\n\r\n<html><body><h1>" + outMessage + "</h1></body></html>";
        new DefaultResponseProcessor().execute(HttpStatus.OK, response, httpRequest, output);
    }
}
