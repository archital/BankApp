package com.luxoft.bankapp.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by SCJP on 13.02.2015.
 */
public class LoginServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        final String clientName = request.getParameter("clientName");
        if(clientName == null){
            logger.warning("Client not found");
            throw new ServletException("No client specified.");
        }
        request.getSession().setAttribute("clientName", clientName);
        logger.info("Client"+clientName+"logger into ATM");
        response.sendRedirect("menu.html");
    }


}
