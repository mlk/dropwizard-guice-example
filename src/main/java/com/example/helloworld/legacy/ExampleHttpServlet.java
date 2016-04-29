package com.example.helloworld.legacy;

import com.example.helloworld.services.CounterService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class ExampleHttpServlet extends HttpServlet {
    private final CounterService counterService;

    @Inject
    public ExampleHttpServlet(CounterService counterService) {
        this.counterService = counterService;
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Content-Type", "text/plain");
        OutputStream os = resp.getOutputStream();
        os.write(("Hello, I have been called " + counterService.next() + " time(s) on this server [" + req.getServerName() + "]").getBytes());
        os.close();
    }
}
