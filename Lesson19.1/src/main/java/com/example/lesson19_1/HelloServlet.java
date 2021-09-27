package com.example.lesson19_1;

import java.io.*;
import javax.servlet.http.*;

public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        try(PrintWriter pwOut = response.getWriter()){
            pwOut.println(request.getRequestURI() + " cookies");
            for(Cookie cookie: request.getCookies()){
                pwOut.println(cookie.getName() + " " + cookie.getValue());
            }
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try(PrintWriter pwOut = response.getWriter()){
            pwOut.println("post request submitted");
        }
    }

    public void destroy() {
    }
}