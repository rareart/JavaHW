package com.example.lesson19_1;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ServletDemo extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("name");
        String age = req.getParameter("age");
        String[] params = req.getParameterValues("param");
        try (PrintWriter pwOut = resp.getWriter()) {
            pwOut.println(getServletContext().getInitParameter("webAppName"));
            pwOut.println("Name: " + (username != null ? username : "empty"));
            pwOut.println("Age: " + (age != null ? age : "empty"));
            pwOut.println("Params:");
            if (params == null) {
                pwOut.println("empty");
            } else {
                for (String param : params) {
                    pwOut.println(param);
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        String username = null;
        String userage = null;
        String gender = null;
        String country = null;
        String[] courses = new String[3];
        String cookiesFlag;

        Cookie[] cookies = req.getCookies();
        if (cookies == null) {
            username = req.getParameter("username");
            userage = req.getParameter("userage");
            gender = req.getParameter("gender");
            country = req.getParameter("country");
            courses = req.getParameterValues("courses");
            cookiesFlag = req.getParameter("cookies");
            if (cookiesFlag != null && cookiesFlag.equals("agree")) {
                Cookie cName = new Cookie("name", username);
                Cookie cAge = new Cookie("age", userage);
                Cookie cGender = new Cookie("gender", gender);
                Cookie cCountry = new Cookie("country", country);
                Cookie cJavaSE = new Cookie("JavaSE", "false");
                Cookie cJavaFX = new Cookie("JavaFX", "false");
                Cookie cJavaEE = new Cookie("JavaEE", "false");
                for (String course : courses) {
                    if (course.equals("JavaSE")) {
                        cJavaSE.setValue("true");
                    }
                    if (course.equals("JavaFX")) {
                        cJavaFX.setValue("true");
                    }
                    if (course.equals("JavaEE")) {
                        cJavaEE.setValue("true");
                    }
                }
                Cookie acceptCookies = new Cookie("cookies", "true");
                resp.addCookie(cName);
                resp.addCookie(cAge);
                resp.addCookie(cGender);
                resp.addCookie(cCountry);
                resp.addCookie(cJavaSE);
                resp.addCookie(cJavaFX);
                resp.addCookie(cJavaEE);
                resp.addCookie(cJavaEE);
                resp.addCookie(acceptCookies);
            }
        } else {
            for (Cookie cookie1 : cookies) {
                if (cookie1.getName().equals("cookies") && cookie1.getValue().equals("true")) {
                    for (Cookie cookie : cookies) {
                        switch (cookie.getName()) {
                            case "name":
                                username = cookie.getValue();
                                break;
                            case "age":
                                userage = cookie.getValue();
                                break;
                            case "gender":
                                gender = cookie.getValue();
                                break;
                            case "country":
                                country = cookie.getValue();
                                break;
                            case "JavaSE":
                                if (cookie.getValue().equals("true")) {
                                    courses[0] = "JavaSE";
                                }
                                break;
                            case "JavaFX":
                                if (cookie.getValue().equals("true")) {
                                    courses[1] = "JavaFX";
                                }
                                break;
                            case "JavaEE":
                                if (cookie.getValue().equals("true")) {
                                    courses[2] = "JavaEE";
                                }
                                break;
                        }
                    }
                    break;
                }
            }
        }

        try (PrintWriter pwOut = resp.getWriter()) {
            pwOut.println("<p>host: " + req.getHeader("host") + "</p>");
            pwOut.println("<p>user-agent: " + req.getHeader("user-agent") + "</p>");
            pwOut.println();
            pwOut.println("<p>Name: " + username + "</p>");
            pwOut.println("<p>Age: " + userage + "</p>");
            pwOut.println("<p>Gender: " + gender + "</p>");
            pwOut.println("<p>Country: " + country + "</p>");
            pwOut.println("<h4>Courses:</h4>");
            for (String str : courses) {
                pwOut.println("<p> " + str + " </p>");
            }
        }
    }

}
