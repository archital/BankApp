package com.luxoft.bankapp.servlets;

import com.luxoft.bankapp.exception.ClientNotFoundException;
import com.luxoft.bankapp.exception.DAOException;
import com.luxoft.bankapp.exception.NotEnoughFundsException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.HTMLDocument;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by acer on 14.02.15.
 */
public class WithdrawServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String clientName = (String) request.getSession().getAttribute("clientName");
        Float amount = Float.parseFloat(request.getParameter("amount"));

        try {
            Bank bank = ServiceFactory.getBankImpl().getBankByName("My Bank");

            Client client = ServiceFactory.getClientImpl().findClientInDB(bank, clientName);
            List<Account> accounts = ServiceFactory.getAccountImpl().getClientAccounts(client.getId());
               client.setActiveAccount(accounts.get(accounts.size() - 1));
            ServiceFactory.getAccountImpl().withdraw(amount, client.getActiveAccount(), client);

            response.sendRedirect("/balance");

        } catch (ClientNotFoundException e) {
            response.setContentType("text/html");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            servletOutputStream.println("No such client in DB");

        } catch (NotEnoughFundsException e) {
            response.setContentType("text/html");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            servletOutputStream.println("Not enough balance to withdraw " + amount);
        } catch (SQLException e) {
            response.setContentType("text/html");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            servletOutputStream.println("SQL exception");
        } catch (DAOException e) {
            response.setContentType("text/html");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            servletOutputStream.println("DOA exception");
        }
    }


}
