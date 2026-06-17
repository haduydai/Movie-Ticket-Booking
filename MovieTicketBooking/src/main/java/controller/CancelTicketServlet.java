package controller;

import dao.ITicketDAO;
import dao.TicketDAO;
import model.Ticket;
import model.TicketStatus;
import model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/cancel-ticket")
public class CancelTicketServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			HttpSession session = request.getSession();
			User loggedInUser = (User) session.getAttribute("user");
			if (loggedInUser == null) {
				response.sendRedirect("login");
				return;
			}

			int ticketId = Integer.parseInt(request.getParameter("ticketId"));
			ITicketDAO ticketDAO = new TicketDAO();
			Ticket ticket = ticketDAO.getTicketById(ticketId);

			if (ticket == null || ticket.getUser().getId() != loggedInUser.getId()) {
				session.setAttribute("error", "Vé không hợp lệ hoặc bạn không có quyền hủy vé này.");
				response.sendRedirect("profile");
				return;
			}

			if (ticket.getStatus() == TicketStatus.UNPAID) {

				ticketDAO.updateTicketStatus(ticket, TicketStatus.CANCELLED);
				session.setAttribute("message", "Đã hủy vé thành công!");
			} else if (ticket.getStatus() == TicketStatus.PAID) {

				ticketDAO.updateTicketStatus(ticket, TicketStatus.REFUND_PENDING);
				session.setAttribute("message", "Gửi yêu cầu hủy vé & hoàn tiền thành công! Vui lòng chờ admin xét duyệt.");
			} else {
				session.setAttribute("error", "Vé này không thể hủy (đã bị hủy, đang chờ duyệt hoặc đã check-in).");
			}

		} catch (Exception e) {
			e.printStackTrace();
			request.getSession().setAttribute("error", "Lỗi khi hủy vé!");
		}

		response.sendRedirect("profile");
	}
}