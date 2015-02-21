package com.luxoft.bankapp.servlets;

import com.luxoft.bankapp.exception.ClientExistsException;
import com.luxoft.bankapp.exception.ClientNotFoundException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by acer on 21.02.15.
 */
public class SaveClientServlet  extends HttpServlet{


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Client client;
        Integer id = Integer.parseInt(request.getParameter("id"));
        try {
        Bank bank = ServiceFactory.getBankImpl().getBankByName("My Bank");

        if(id.equals(null)) {
           client = new Client();
        } else {
            client = ServiceFactory.getClientImpl().findClientById(id);
        }
        client.setName(request.getParameter("name"));
        client.setCity(request.getParameter("city"));
        client.setEmail(request.getParameter("email"));
        ServiceFactory.getClientImpl().addClient(bank, client);
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
