package ru.flamexander.http.server.application.processors;

import com.google.gson.Gson;
import ru.flamexander.http.server.HttpRequest;
import ru.flamexander.http.server.HttpStatus;
import ru.flamexander.http.server.application.Item;
import ru.flamexander.http.server.application.Storage;
import ru.flamexander.http.server.processors.DefaultResponseProcessor;
import ru.flamexander.http.server.processors.RequestProcessor;
import ru.flamexander.http.server.validation.HeaderValidation;

import java.io.IOException;
import java.io.OutputStream;

public class CreateNewProductProcessor implements RequestProcessor {
    @Override
    public void execute(HttpRequest httpRequest, OutputStream output) throws IOException {
        HeaderValidation.check(httpRequest, "Accept", "application/json", HttpStatus.NOT_ACCEPTABLE);

        Gson gson = new Gson();
        Item item = gson.fromJson(httpRequest.getBody(), Item.class);
        Storage.save(item);
        String jsonOutItem = gson.toJson(item);

        String response = "Content-Type: application/json\r\n" +
                "Connection: keep-alive\r\n" +
                "Access-Control-Allow-Origin: *\r\n" +
                "\r\n" + jsonOutItem;
        new DefaultResponseProcessor().execute(HttpStatus.OK, response, httpRequest, output);
    }
}
