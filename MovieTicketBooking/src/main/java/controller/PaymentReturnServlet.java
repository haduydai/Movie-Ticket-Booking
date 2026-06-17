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
                if (ticket.getStatus() == TicketStatus.CANCELLED) {
                    request.setAttribute("message", "Thanh toán thất bại: Thời gian giữ ghế (30 giây) đã hết hạn và ghế đã bị giải phóng.");
                } else {
                    ticketDAO.updateTicketStatus(ticket, TicketStatus.PAID);
                    if (ticket.getBooking() != null) {
                        String txnRef = request.getParameter("vnp_TransactionNo");
                        if (txnRef == null || txnRef.trim().isEmpty()) {
                            txnRef = "TXN-" + System.currentTimeMillis();
                        }
                        String paymentUrl = request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
                        ticketDAO.savePaymentTransaction(
                            ticket.getBooking().getId(),
                            ticket.getTotalPrice().doubleValue(),
                            ticket.getPaymentMethod().name(),
                            txnRef,
                            paymentUrl,
                            "SUCCESS"
                        );
                    }
                    request.setAttribute("message", "Thanh toán thành công");
                }
            }
            else{
                if (ticket.getStatus() != TicketStatus.CANCELLED) {
                    ticketDAO.updateTicketStatus(ticket, TicketStatus.CANCELLED);
                }
                request.setAttribute("message", "Thanh toán thất bại");
            }
            request.setAttribute("ticket", ticket);
        }
        request.getRequestDispatcher("/WEB-INF/view/payment-result.jsp").forward(request, response);
    }

}
