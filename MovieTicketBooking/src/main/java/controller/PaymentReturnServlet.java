package controller;

import dao.TicketDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Ticket;
import model.TicketStatus;

import java.io.IOException;

@WebServlet("/payment-return")
public class PaymentReturnServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ticketId = request.getParameter("ticketId");
        String responseCode = request.getParameter("vnp_ResponseCode");
        TicketDAO ticketDAO = new TicketDAO();
        Ticket ticket = ticketDAO.getTicketById(Integer.parseInt(ticketId));
        if(ticket != null){
            if("00".equals(responseCode)){
                ticketDAO.updateTicketStatus(ticket, TicketStatus.PAID);
                request.setAttribute("message", "Thanh toán thành công");
            }
            else{
                ticketDAO.updateTicketStatus(ticket, TicketStatus.CANCELLED);
                request.setAttribute("message", "Thanh toán thất bại");
            }
            request.setAttribute("ticket", ticket);
        }
        request.getRequestDispatcher("/WEB-INF/view/payment-result.jsp").forward(request, response);
    }

}
