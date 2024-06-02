package ru.flamexander.http.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.flamexander.http.server.application.exceptions.HttpException;
import ru.flamexander.http.server.application.processors.CalculatorRequestProcessor;
import ru.flamexander.http.server.application.processors.CreateNewProductProcessor;
import ru.flamexander.http.server.application.processors.GetAllProductsProcessor;
import ru.flamexander.http.server.application.processors.HelloWorldRequestProcessor;
import ru.flamexander.http.server.application.processors.calc.CalcAddRequestProcessor;
import ru.flamexander.http.server.application.processors.calc.CalcDivRequestProcessor;
import ru.flamexander.http.server.application.processors.calc.CalcMulRequestProcessor;
import ru.flamexander.http.server.application.processors.calc.CalcSubRequestProcessor;
import ru.flamexander.http.server.processors.*;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Dispatcher {
    private final Map<String, RequestProcessor> router;
    private final RequestProcessor unknownOperationRequestProcessor;
    private final RequestProcessor optionsRequestProcessor;
    private final RequestProcessor staticResourcesProcessor;
    private final ResponseProcessor responseProcessor;

    private static final Logger logger = LoggerFactory.getLogger(Dispatcher.class.getName());

    public Dispatcher() {
        this.router = new HashMap<>();
        this.router.put("GET /calc", new CalculatorRequestProcessor());
        this.router.put("GET /add", new CalcAddRequestProcessor());
        this.router.put("GET /subtract", new CalcSubRequestProcessor());
        this.router.put("GET /multiply", new CalcMulRequestProcessor());
        this.router.put("GET /div", new CalcDivRequestProcessor());

        this.router.put("GET /hello", new HelloWorldRequestProcessor());
        this.router.put("GET /items", new GetAllProductsProcessor());
        this.router.put("POST /items", new CreateNewProductProcessor());

        this.unknownOperationRequestProcessor = new DefaultUnknownOperationProcessor();
        this.optionsRequestProcessor = new DefaultOptionsProcessor();
        this.staticResourcesProcessor = new DefaultStaticResourcesProcessor();

        this.responseProcessor = new DefaultResponseProcessor();

        logger.info("Диспетчер проинициализирован");
    }

    public void execute(HttpRequest httpRequest, OutputStream outputStream) throws IOException {
        try {
            if (httpRequest.getMethod() == HttpMethod.OPTIONS) {
                optionsRequestProcessor.execute(httpRequest, outputStream);
                return;
            }
            if (Files.exists(Paths.get("static/", httpRequest.getUri().substring(1)))) {
                staticResourcesProcessor.execute(httpRequest, outputStream);
                return;
            }
            if (!router.containsKey(httpRequest.getRouteKey())) {
                long count = router.keySet().stream()
                        .filter(rout -> rout.endsWith(httpRequest.getUri()))
                        .count();
                if (count > 0) {
                    throw new HttpException(HttpStatus.METHOD_NOT_ALLOWED);
                } else {
                    unknownOperationRequestProcessor.execute(httpRequest, outputStream);
                }
                return;
            }
            router.get(httpRequest.getRouteKey()).execute(httpRequest, outputStream);
        } catch (HttpException e) {
            responseProcessor.execute(HttpStatus.valueOf(e.getMessage()), "\r\n", httpRequest, outputStream);
        }
    }
}
