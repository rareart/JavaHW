package com.rareart.lesson19_2.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rareart.lesson19_2.dao.EmployeeDAO;
import com.rareart.lesson19_2.model.Employee;
import com.rareart.lesson19_2.model.implementation.DepartmentImpl;
import com.rareart.lesson19_2.model.implementation.EmployeeImpl;
import com.rareart.lesson19_2.model.implementation.OrganizationImpl;
import com.rareart.lesson19_2.servlets.gson_adapters.DepartmentAdapterFactory;
import com.rareart.lesson19_2.servlets.gson_adapters.EmployeeAdapterFactory;
import com.rareart.lesson19_2.servlets.gson_adapters.OrganizationAdapterFactory;
import com.rareart.lesson19_2.servlets.gson_wrappers.EmployeeLinkWrapper;

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

public class EmployeeServlet extends HttpServlet {

    private EmployeeDAO employeeDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext servletContext = getServletContext();
        Object empDAO = servletContext.getAttribute("employeeDAO");
        if (!(empDAO instanceof EmployeeDAO)){
            throw new ServletException("DAO-object does not initialize or class cast error");
        } else {
            employeeDAO = (EmployeeDAO) empDAO;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String position = req.getParameter("pos");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (id != null && name != null && position != null){
            resp.setStatus(400);
            return;
        }
        if (id != null && name != null) {
            resp.setStatus(400);
            return;
        }
        if (id != null && position != null){
            resp.setStatus(400);
            return;
        }

        Gson gson = new Gson();
        if (id == null && name == null && position == null){
            try(PrintWriter pwOut = resp.getWriter()){
                Set<Employee> employeeSet = employeeDAO.getEmployees();
                String employeesString = gson.toJson(employeeSet);
                pwOut.println(employeesString);
            } catch (SQLException throwables) {
                resp.setStatus(500);
                throw new ServletException(throwables);
            }
        } else {
            if (id != null) {
                try(PrintWriter pwOut = resp.getWriter()){
                    Employee employee = employeeDAO.findEmployeeById(Integer.parseInt(id));
                    String employeeString = gson.toJson(employee);
                    pwOut.println(employeeString);
                } catch (SQLException throwables) {
                    resp.setStatus(404);
                    throw new ServletException(throwables);
                }
            }
            if (name != null && position != null){
                try(PrintWriter pwOut = resp.getWriter()){
                    Set<Employee> employeeSet = employeeDAO.findEmployeesByNameAndPos(name, position);
                    String employeesString = gson.toJson(employeeSet);
                    pwOut.println(employeesString);
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
        String linkToDep = req.getParameter("link_to_dep");

        if (create != null && linkToDep != null){
            resp.setStatus(400);
            return;
        }
        if (create == null && linkToDep == null){
            resp.setStatus(400);
            return;
        }

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapterFactory(new OrganizationAdapterFactory(OrganizationImpl.class));
        gsonBuilder.registerTypeAdapterFactory(new DepartmentAdapterFactory(DepartmentImpl.class));
        gsonBuilder.registerTypeAdapterFactory(new EmployeeAdapterFactory(EmployeeImpl.class));
        Gson gson = gsonBuilder.create();

        if (create != null && create.equals("new")){
            req.setCharacterEncoding("UTF-8");
            BufferedReader reader;
            try{
                reader = req.getReader();
            } catch (Throwable e){
                resp.setStatus(500);
                throw new ServletException(e);
            }
            Employee employee;
            try{
                employee = gson.fromJson(reader, EmployeeImpl.class);
            } catch (Throwable e){
                resp.setStatus(400);
                throw new ServletException(e);
            } finally {
                reader.close();
            }
            try {
                if (employee.getHiring_date() != null){
                    employeeDAO.createEmployee(employee.getName(), employee.getPosition(), employee.getAge(),
                            employee.getHiring_date(), employee.getDepartment());
                } else {
                    employeeDAO.createEmployee(employee.getName(), employee.getPosition(), employee.getAge(),
                            employee.getDepartment());
                }
                resp.setStatus(201);
            } catch (SQLException throwables) {
                resp.setStatus(400);
                throw new ServletException(throwables);
            }
            return;
        } else {
            resp.setStatus(400);
        }

        if (linkToDep != null && linkToDep.equals("new_link")){
            req.setCharacterEncoding("UTF-8");
            BufferedReader reader;
            try {
                reader = req.getReader();
            } catch (Throwable e){
                resp.setStatus(500);
                throw new ServletException(e);
            }
            EmployeeLinkWrapper employeeLinkWrapper;
            try {
                employeeLinkWrapper = gson.fromJson(reader, EmployeeLinkWrapper.class);
            } catch (Throwable e){
                resp.setStatus(400);
                throw new ServletException(e);
            } finally {
                reader.close();
            }
            try {
                employeeDAO.linkEmployeeToDepartment(employeeLinkWrapper.getEmployee(), employeeLinkWrapper.getDepartment());
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
