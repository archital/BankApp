package com.luxoft.bankapp.servlets;

import com.luxoft.bankapp.exception.ClientNotFoundException;
import com.luxoft.bankapp.model.Account;
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
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by acer on 14.02.15.
 */
public class BalanceServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String clientName = (String) request.getSession().getAttribute("clientName");

        try {
            Bank bank = ServiceFactory.getBankImpl().getBankByName("My Bank");

            Client client = ServiceFactory.getClientImpl().findClientInDB(bank, clientName);
            float balance = client.getBalance();

            request.setAttribute("balance", balance);
            request.getRequestDispatcher("/balance.jsp").forward(request, response);


        } catch (ClientNotFoundException e) {
            response.setContentType("text/html");
            ServletOutputStream out = response.getOutputStream();
            out.println("<style type=\"text/css\">\n" +
                    "\" +\n" +
                    "                \"     body {\\n\" +\n" +
                    "                \"      background: #45d59e;} > </style> " +
                    "No such client in DB");
            response.sendRedirect("/menu.html");
        } catch (SQLException e) {
          logger.severe("Error in DB query");
        }
    }
}
