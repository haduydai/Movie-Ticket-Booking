package controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/payment")
public class PaymentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String ticketId = request.getParameter("ticketId");
        String amount= request.getParameter("amount");

        response.sendRedirect(request.getContextPath()
                                + "/payment-return"
                                + "?ticketId="
                                + ticketId
                                + "&vnp_ResponseCode=00");
    }
}
