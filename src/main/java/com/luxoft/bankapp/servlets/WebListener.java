package com.luxoft.bankapp.servlets;

import com.luxoft.bankapp.service.AccountService;
import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.ClientService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by acer on 25.02.15.
 */
public class WebListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application-context..xml");
        BankService bankService = (BankService) context.getBean("bankService");
        ClientService clientService = (ClientService) context.getBean("clientService");
        AccountService accountService = (AccountService) context.getBean("accountService");

        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute("bankService", bankService);
        servletContext.setAttribute("clientService", clientService);
        servletContext.setAttribute("accountService", accountService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
