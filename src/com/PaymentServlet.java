package com;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by mano on 5/18/2020.
 */
@WebServlet(name = "PaymentServlet")
public class PaymentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter("email");
        String cash = request.getParameter("cash");
        String accountno = request.getParameter("accountno");
        String expirydate = request.getParameter("expirydate");
        String cvv = request.getParameter("cvv");

        String dbURL = "jdbc:mysql://localhost:3306/login";
        String dbUser = "root";
        String dbPass = "";
        Connection conn = null; // connection to the database
        String message = null;  // message will be sent back to client

        try {
            // connects to the database
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            conn = DriverManager.getConnection(dbURL, dbUser, dbPass);

            // constructs SQL statement
            String sql = "INSERT  INTO paymentmethod (email,cash,accountno,expirydate,cvv) values (?,?,?,?,?)";
            PreparedStatement statement = (PreparedStatement) conn.prepareStatement(sql);


            statement.setString(1, email);
            statement.setString(2, cash);
            statement.setString(3, accountno);
            statement.setString(4, expirydate);
            statement.setString(5, cvv);

            int row = statement.executeUpdate();
            if (row > 0) {
                message = "File uploaded and saved into database";
            }
        } catch (SQLException ex) {
            message = "ERROR: " + ex.getMessage();
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                // closes the database connection
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }


            }

            RequestDispatcher dispatcher = request.getRequestDispatcher("PaymentMethod.jsp");
            dispatcher.forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
