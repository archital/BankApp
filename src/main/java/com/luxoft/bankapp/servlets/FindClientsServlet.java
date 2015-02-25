package com.luxoft.bankapp.servlets;

import com.luxoft.bankapp.exception.ClientNotFoundException;
import com.luxoft.bankapp.exception.DAOException;
import com.luxoft.bankapp.exception.NotEnoughFundsException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.AccountService;
import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.ClientService;
import com.luxoft.bankapp.service.ServiceFactory;

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
 * Created by SCJP on 20.02.2015.
 */
public class FindClientsServlet extends HttpServlet {



    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String name = (String) request.getParameter("name");
        String city= (String) request.getParameter("city");

        BankService bankService = (BankService) getServletContext().getAttribute("bankService");
        ClientService clientService = (ClientService) getServletContext().getAttribute("clientService");

        try {
            Bank bank = bankService.getBankByName("My Bank");

            Set <Client> clients = clientService.getAllClients(bank);
            List<Client> result = new ArrayList<Client>();


            if (!city.equals("") && !name.equals("")){
                for (Client client : clients) {
                    if (client.getCity().equals(city) && client.getName().equals(name)){
                        result.add(client);
                    }
                }
            } else if (!city.equals("")) {
                for (Client client : clients) {
                    if (client.getCity().equals(city)){
                        result.add(client);
                    }
                }
            } else {
                for (Client client : clients) {
                    if (client.getName().equals(name)){
                        result.add(client);
                    }
                }
            }


            request.setAttribute("clients", result);
            request.setAttribute("city", city);
            request.setAttribute("name", name);

            request.getRequestDispatcher("/findClients.jsp").forward(request, response);


        } catch (ClientNotFoundException e) {
            response.setContentType("text/html");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            servletOutputStream.println("No such clients in DB");

        }catch (SQLException e) {
            response.setContentType("text/html");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            servletOutputStream.println("SQL exception");
        }
    }


}
