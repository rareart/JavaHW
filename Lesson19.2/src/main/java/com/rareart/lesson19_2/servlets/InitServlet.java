package com.rareart.lesson19_2.servlets;

import com.rareart.lesson19_2.сonnection.AutoInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

public class InitServlet extends HttpServlet {

    private AutoInitializer autoInitializer;

    @Override
    public void init() throws ServletException {
        super.init();
        final ServletContext servletContext = getServletContext();

        Object ai = servletContext.getAttribute("dbAutoInit");
        if(!(ai instanceof AutoInitializer)){
            throw new ServletException("Auto initializer does not initialize or class cast error");
        } else {
            autoInitializer = (AutoInitializer) ai;
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        String initFlag = req.getHeader("InitFlag");
        if (initFlag == null){
            resp.setStatus(400);
            return;
        }
        if (initFlag.equals("true")){
            try {
                autoInitializer.autoInitDBFromResources();
                resp.setStatus(201);
            } catch (SQLException | IllegalAccessException e) {
                resp.setStatus(500);
            }
        } else {
            resp.setStatus(200);
        }
    }
}
