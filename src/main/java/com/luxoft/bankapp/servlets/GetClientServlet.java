package com.luxoft.bankapp.servlets;

import com.luxoft.bankapp.dao.DAOFactory;
import com.luxoft.bankapp.exception.ClientExistsException;
import com.luxoft.bankapp.exception.ClientNotFoundException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.AccountService;
import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.ClientService;
import com.luxoft.bankapp.service.ServiceFactory;
import com.sun.net.httpserver.HttpServer;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by acer on 21.02.15.
 */
public class GetClientServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Integer id = Integer.parseInt(request.getParameter("id"));

        BankService bankService = (BankService) getServletContext().getAttribute("bankService");
        ClientService clientService = (ClientService) getServletContext().getAttribute("clientService");


        try {
            Client client = clientService.findClientById(id);
            request.setAttribute("client", client);
            request.getRequestDispatcher("/client.jsp").forward(request, response);



        } catch (ClientNotFoundException e) {
            response.setContentType("text/html");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            servletOutputStream.println("No such client in DB");

        }catch (SQLException e) {
            response.setContentType("text/html");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            servletOutputStream.println("SQL exception");
        } catch (ClientExistsException e) {
            e.printStackTrace();
        }
    }

}
