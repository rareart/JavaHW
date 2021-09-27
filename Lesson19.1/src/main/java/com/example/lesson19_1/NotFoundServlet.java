package com.example.lesson19_1;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class NotFoundServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(PrintWriter pwOut = resp.getWriter()){
            pwOut.println(getServletConfig().getInitParameter("wrongRequest"));
            pwOut.println("Error 404: Not Found!");
        }
    }
}
