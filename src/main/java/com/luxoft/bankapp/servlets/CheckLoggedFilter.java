package com.luxoft.bankapp.servlets;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by SCJP on 13.02.2015.
 */
public class CheckLoggedFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpSession session = ((HttpServletRequest) req).getSession();
        String clientName = (String) session.getAttribute("clientName");
        String path = ((HttpServletRequest) req).getRequestURI();
        HttpServletResponse response = (HttpServletResponse) resp;

        if (path.startsWith("/login") || path.equals("/welcome") || path.equals("/") || clientName != null || path.startsWith("/style"))  {
            chain.doFilter(req, resp);
        } else {
            response.sendRedirect("login.html");
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
