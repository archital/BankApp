package com.luxoft.bankapp.servlets;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by SCJP on 13.02.2015.
 */
public class WelcomeServlet extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletOutputStream servletOutputStream = response.getOutputStream();
        response.setContentType("text/html");
        servletOutputStream.println("<style type=\"text/css\">\n" +
                "     body {\n" +
                "      background: #45d59e;}\n" +

                ".explainText{\n" +
                "                      text-align:center;\n" +
                "                        font-family: Georgia;\n" +
                "                        font-size: 50px;\n" +
                "                        form-weight: bold;\n" +
                "                               }" +
                "</style> " +

                "<a  class=\"explainText\" style=\"display: block; margin: 0 auto;\"> Welcome in my Bank app</a>");
        servletOutputStream.println("<input type=\"button\" value=\"Login\" onClick='location.href=\"login.html\"' id = \"loginButton\" style=\"display: block; margin: 0 auto;\">");

    }
}

