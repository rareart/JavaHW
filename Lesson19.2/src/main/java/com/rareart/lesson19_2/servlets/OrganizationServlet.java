package com.rareart.lesson19_2.servlets;

import com.google.gson.Gson;
import com.rareart.lesson19_2.dao.OrganizationDAO;
import com.rareart.lesson19_2.model.Organization;
import com.rareart.lesson19_2.model.implementation.OrganizationImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Set;


public class OrganizationServlet extends HttpServlet {

    private OrganizationDAO organizationDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext servletContext = getServletContext();
        Object orgDAO = servletContext.getAttribute("organizationDAO");
        if (!(orgDAO instanceof OrganizationDAO)){
            throw new ServletException("DAO-object does not initialize or class cast error");
        } else {
            organizationDAO = (OrganizationDAO) orgDAO;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String by_name = req.getParameter("name");
        String by_id = req.getParameter("id");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        if (by_name != null && by_id != null) {
            resp.setStatus(400);
            return;
        }
        Gson gson = new Gson();
        if (by_name == null && by_id == null) {
            try (PrintWriter pwOut = resp.getWriter()) {
                Set<Organization> organizationSet = organizationDAO.getOrganizations();
                String orgSetString = gson.toJson(organizationSet);
                pwOut.println(orgSetString);
            } catch (SQLException throwables) {
                resp.setStatus(500);
                throw new ServletException(throwables);
            }
        } else {
            if (by_name != null) {
                try (PrintWriter pwOut = resp.getWriter()) {
                    Organization organization = organizationDAO.findOrganizationByName(by_name);
                    String orgString = gson.toJson(organization);
                    pwOut.println(orgString);
                } catch (SQLException throwables) {
                    resp.setStatus(500);
                    throw new ServletException(throwables);
                }
            }
            if (by_id != null) {
                try (PrintWriter pwOut = resp.getWriter()) {
                    Organization organization = organizationDAO.findOrganizationById(Integer.parseInt(by_id));
                    String orgString = gson.toJson(organization);
                    pwOut.println(orgString);
                } catch (SQLException throwables) {
                    resp.setStatus(404);
                    throw new ServletException(throwables);
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String create = req.getParameter("create");

        if (create !=null && create.equals("new")){
            req.setCharacterEncoding("UTF-8");
            BufferedReader reader;
            try {
                reader = req.getReader();
            } catch (Throwable e){
                resp.setStatus(500);
                throw new ServletException(e);
            }
            Gson gson = new Gson();
            Organization organization;
            try {
                organization = gson.fromJson(reader, OrganizationImpl.class);
            } catch (Throwable e){
                resp.setStatus(400);
                throw new ServletException(e);
            } finally {
                reader.close();
            }
            try {
                organizationDAO.createOrganization(organization.getName(), organization.getCountry());
                resp.setStatus(201);
            } catch (SQLException e) {
                resp.setStatus(400);
                throw new ServletException(e);
            }
        } else {
            resp.setStatus(400);
        }
    }
}
