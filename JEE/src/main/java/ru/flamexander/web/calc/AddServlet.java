package ru.flamexander.web.calc;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.flamexander.web.ThirdServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(name = "AddServlet", urlPatterns = "/add")
public class AddServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(AddServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Log: Add");
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        Map<String,String[]> params = req.getParameterMap();
        String result = "";
        if (params.containsKey("a") && params.containsKey("b")) {
            double a = Double.parseDouble(params.get("a")[0]);
            double b = Double.parseDouble(params.get("b")[0]);
            result = String.format("%f + %f = %f", a, b, a + b);
        }

        out.printf("<html><body><h1>Addition</h1><p>%s</p></body></html>", result);
        out.close();
    }
}
