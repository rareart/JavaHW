package com.example.lesson19_1;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ForwardingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("name");
        String age = req.getParameter("age");
        String[] params = req.getParameterValues("param");
        String apiKey = req.getParameter("apikey");

        if (params != null && apiKey == null){
            ServletContext servletContext = getServletContext();
            RequestDispatcher dispatcher = servletContext.getRequestDispatcher("/servletdemo");
            dispatcher.forward(req, resp);
        } else {
            if (params != null || apiKey == null) {
                resp.sendRedirect(req.getContextPath() + "/404demo");
            }
        }

        try(PrintWriter pwOut = resp.getWriter()){
            pwOut.println(getServletContext().getInitParameter("webAppName"));
            pwOut.println("Response with API key:");
            pwOut.println("Name: " + (username!=null? username : "empty"));
            pwOut.println("Age: " + (age!=null? age : "empty"));
            pwOut.println("API key: " + apiKey);
        }
    }
}
