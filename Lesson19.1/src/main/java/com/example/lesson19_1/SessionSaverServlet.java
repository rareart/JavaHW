package com.example.lesson19_1;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class SessionSaverServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] newValues = req.getParameterValues("val");
        String clearParam = req.getParameter("clearAll");
        HttpSession session = req.getSession();
        String[] storedValues = (String[]) session.getAttribute("values");
        if(newValues != null){
            try(PrintWriter pwOut = resp.getWriter()){
                pwOut.println("For session: " + session.getId());
                pwOut.println("Stored values: " + Arrays.toString(storedValues));
                session.setAttribute("values", newValues);
                pwOut.println("New values: " + Arrays.toString(newValues));
            }
        } else {
            if(clearParam!=null && clearParam.equals("true")){
                try(PrintWriter pwOut = resp.getWriter()){
                    pwOut.println("For session: " + session.getId());
                    session.removeAttribute("values");
                    pwOut.println("All values removed");
                }
            } else {
                try(PrintWriter pwOut = resp.getWriter()){
                    pwOut.println("For session: " + session.getId());
                    pwOut.println("Stored values: " + Arrays.toString(storedValues));
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
