package com.luxoft.bankapp.servlets;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by SCJP on 13.02.2015.
 */
public class WelcomeServlet extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletOutputStream servletOutputStream = response.getOutputStream();
        response.setContentType("text/html");
        servletOutputStream.println("Hello I'm ATM!");
        servletOutputStream.println("<a href=\"login.html\">Login</a>");
    }
}

