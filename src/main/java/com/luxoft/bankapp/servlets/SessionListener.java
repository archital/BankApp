package com.luxoft.bankapp.servlets;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by acer on 14.02.15.
 */
public class SessionListener implements HttpSessionListener {

        @Override
        public void sessionCreated(HttpSessionEvent httpSessionEvent) {
            final ServletContext servletContext = httpSessionEvent.getSession().getServletContext();
            synchronized (SessionListener.class){
                Integer clientsConnected = (Integer)servletContext.getAttribute("clientsConnected");
                if (clientsConnected==null){
                    clientsConnected = 1;
                } else {
                    clientsConnected++;
                }
                servletContext.setAttribute("clientsConnected", clientsConnected);
            }
        }

        @Override
        public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
            final ServletContext servletContext = httpSessionEvent.getSession().getServletContext();
            synchronized (SessionListener.class){
                Integer clientsConnected = (Integer)servletContext.getAttribute("clientsConnected");
                clientsConnected--;
                servletContext.setAttribute("clientsConnected", clientsConnected);
            }
        }
}
