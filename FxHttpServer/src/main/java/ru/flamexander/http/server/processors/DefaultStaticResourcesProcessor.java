package ru.flamexander.http.server.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.flamexander.http.server.HttpRequest;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DefaultStaticResourcesProcessor implements RequestProcessor {
    public static final String RESOURCES = "FxHttpServer/src/main/resources/";
    private static final Logger log = LoggerFactory.getLogger(DefaultStaticResourcesProcessor.class);

    @Override
    public void execute(HttpRequest httpRequest, OutputStream output) throws IOException {
        String filename = httpRequest.getUri().substring(1);
        Path filePath = Paths.get(RESOURCES + "static", filename);
        String fileType = filename.substring(filename.lastIndexOf(".") + 1);
        if (Files.exists(filePath)) {
            byte[] fileData = Files.readAllBytes(filePath);

            String contentDisposition = "";
            if (fileType.equals("pdf")) {
                contentDisposition = "Content-Disposition: attachment;filename=" + filename + "\r\n";
            }

            File file = filePath.toFile();
            long lastModified = file.lastModified();
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
            String lastModifiedString = sdf.format(new Date(lastModified));

            String ifModifiedSince = httpRequest.getHeader("If-Modified-Since");
            if (ifModifiedSince != null) {
                Date ifModifiedSinceDate = null;
                try {
                    ifModifiedSinceDate = sdf.parse(ifModifiedSince);
                    lastModified = sdf.parse(lastModifiedString).getTime();
                } catch (ParseException e) {
                    log.info(e.getMessage());
                }

                if (ifModifiedSinceDate != null && lastModified <= ifModifiedSinceDate.getTime()) {
                    String response = "HTTP/1.1 304 Not Modified\r\n" +
                            "\r\n";
                    output.write(response.getBytes());
                    return;
                }
            }

            String response = "HTTP/1.1 200 OK\r\n" +
                    "Content-Length: " + fileData.length + "\r\n" +
                    contentDisposition +
                    "Cache-Control: no-cache\r\n" +
                    "Last-Modified: " + lastModifiedString + "\r\n" +
                    "\r\n";
            output.write(response.getBytes());
            output.write(fileData);
        }
    }
}
