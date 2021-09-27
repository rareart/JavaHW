package com.rareart.lesson19_2.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rareart.lesson19_2.dao.DepartmentDAO;
import com.rareart.lesson19_2.model.Department;
import com.rareart.lesson19_2.model.implementation.DepartmentImpl;
import com.rareart.lesson19_2.model.implementation.OrganizationImpl;
import com.rareart.lesson19_2.servlets.gson_adapters.DepartmentAdapterFactory;
import com.rareart.lesson19_2.servlets.gson_adapters.OrganizationAdapterFactory;
import com.rareart.lesson19_2.servlets.gson_wrappers.DepartmentLinkWrapper;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Set;

public class DepartmentServlet extends HttpServlet {

    private DepartmentDAO departmentDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext servletContext = getServletContext();
        Object depDAO = servletContext.getAttribute("departmentDAO");
        if (!(depDAO instanceof DepartmentDAO)){
            throw new ServletException("DAO-object does not initialize or class cast error");
        } else {
            departmentDAO = (DepartmentDAO) depDAO;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String code = req.getParameter("code");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (id != null && code != null){
            resp.setStatus(400);
            return;
        }
        Gson gson = new Gson();
        if (id == null && code == null){
            try(PrintWriter pwOut = resp.getWriter()){
                Set<Department> departments = departmentDAO.getDepartments();
                String departmentsString = gson.toJson(departments);
                pwOut.println(departmentsString);
            } catch (SQLException throwables) {
                resp.setStatus(500);
                throw new ServletException(throwables);
            }
        } else {
            if (id != null){
                try(PrintWriter pwOut = resp.getWriter()){
                    Department department = departmentDAO.findDepartmentById(Integer.parseInt(id));
                    String departmentString = gson.toJson(department);
                    pwOut.println(departmentString);
                } catch (SQLException throwables) {
                    resp.setStatus(404);
                    throw new ServletException(throwables);
                }
            }
            if (code != null){
                try(PrintWriter pwOut = resp.getWriter()){
                    Department department = departmentDAO.findDepartmentByCode(Integer.parseInt(code));
                    String departmentString = gson.toJson(department);
                    pwOut.println(departmentString);
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
        String linkToOrg = req.getParameter("link_to_org");

        if (create != null && linkToOrg != null){
            resp.setStatus(400);
            return;
        }
        if (create == null && linkToOrg == null){
            resp.setStatus(400);
            return;
        }

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapterFactory(new OrganizationAdapterFactory(OrganizationImpl.class));
        gsonBuilder.registerTypeAdapterFactory(new DepartmentAdapterFactory(DepartmentImpl.class));
        Gson gson = gsonBuilder.create();

        if (create != null && create.equals("new")){
            req.setCharacterEncoding("UTF-8");
            BufferedReader reader;
            try {
                reader = req.getReader();
            } catch (Throwable e){
                resp.setStatus(500);
                throw new ServletException(e);
            }
            Department department;
            try{
                department = gson.fromJson(reader, DepartmentImpl.class);
            } catch (Throwable e){
                resp.setStatus(400);
                throw new ServletException(e);
            } finally {
                reader.close();
            }
            try {
                departmentDAO.createDepartment(department.getName(), department.getInternal_code(), department.getOrganization());
                resp.setStatus(201);
            } catch (SQLException throwables) {
                resp.setStatus(400);
                throw new ServletException(throwables);
            }
            return;
        } else {
            resp.setStatus(400);
        }

        if (linkToOrg != null && linkToOrg.equals("new_link")){
            req.setCharacterEncoding("UTF-8");
            BufferedReader reader;
            try {
                reader = req.getReader();
            } catch (Throwable e){
                resp.setStatus(500);
                throw new ServletException(e);
            }
            DepartmentLinkWrapper departmentLinkWrapper;
            try {
                departmentLinkWrapper = gson.fromJson(reader, DepartmentLinkWrapper.class);
            } catch (Throwable e){
                resp.setStatus(400);
                throw new ServletException(e);
            } finally {
                reader.close();
            }
            try {
                departmentDAO.linkDepartmentToOrganization(departmentLinkWrapper.getDepartment(), departmentLinkWrapper.getOrganization());
                resp.setStatus(201);
            } catch (SQLException throwables) {
                resp.setStatus(400);
                throw new ServletException(throwables);
            }
        } else {
            resp.setStatus(400);
        }
    }
}
