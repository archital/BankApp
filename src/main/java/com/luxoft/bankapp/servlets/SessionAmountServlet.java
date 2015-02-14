package com.luxoft.bankapp.servlets;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by acer on 14.02.15.
 */
public class SessionAmountServlet extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            HttpSession httpSession = ((HttpServletRequest) request).getSession();
            ServletContext servletContext = httpSession.getServletContext();
            Integer clientsConnected = (Integer) servletContext.getAttribute("clientsConnected");

            ServletOutputStream servletOutputStream = response.getOutputStream();
            response.setContentType("text/html");
            servletOutputStream.println("Number of opening sessions: ");
            servletOutputStream.println(clientsConnected);

    }
}
