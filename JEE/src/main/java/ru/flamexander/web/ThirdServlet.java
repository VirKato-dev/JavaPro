package ru.flamexander.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ThirdServlet", urlPatterns = "/show_page")
public class ThirdServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(ThirdServlet.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Log: Third");
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.printf("<html><body><h1>Third request</h1></body></html>");
        out.close();
    }
}
