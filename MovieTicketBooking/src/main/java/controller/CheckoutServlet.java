package controller;

import dao.TicketDAO;
import dao.ShowTimeDAO;
import dao.JDBCConnection;
import model.User;
import model.ShowTime;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        new dao.TicketDAO().cleanupExpiredTickets();

        String action = request.getParameter("action");
        

        if ("PAY".equals(action)) {
            processPayment(request, response);
            return;
        }


        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        String showtimeId = request.getParameter("showtimeId");
        String selectedSeats = request.getParameter("selectedSeats");
        
        if (showtimeId == null || selectedSeats == null || selectedSeats.trim().isEmpty()) {
            response.sendRedirect("home");
            return;
        }

        int showtimeIdVal = Integer.parseInt(showtimeId);
        String[] seats = selectedSeats.split(",");
        
        TicketDAO dao = new TicketDAO();
        ShowTime showTime = new ShowTimeDAO().getShowTimeById(showtimeIdVal);
        double basePrice = (showTime != null) ? showTime.getPricePerTicket().doubleValue() : 90000;


        java.util.Map<String, String> seatTypeMap = new java.util.HashMap<>();
        if (showTime != null) {
            try (java.sql.Connection conn = JDBCConnection.getConnection();
                 java.sql.PreparedStatement ps = conn.prepareStatement("SELECT row_label, column_number, seat_type FROM seats WHERE room_id = ?")) {
                ps.setInt(1, showTime.getRoom().getId());
                try (java.sql.ResultSet rs2 = ps.executeQuery()) {
                    while (rs2.next()) {
                        String key = rs2.getString("row_label").trim() + rs2.getInt("column_number");
                        seatTypeMap.put(key, rs2.getString("seat_type"));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        double totalPriceVal = 0;
        for (String seat : seats) {
            String seatName = seat.trim();
            String type = seatTypeMap.get(seatName);
            if (type == null) {
                char row = seatName.charAt(0);
                if (row == 'A' || row == 'B') type = "VIP";
                else type = "REGULAR";
            }

            if ("VIP".equalsIgnoreCase(type)) {
                totalPriceVal += basePrice + 20000;
            } else if ("SWEETBOX".equalsIgnoreCase(type)) {
                totalPriceVal += basePrice + 40000;
            } else {
                totalPriceVal += basePrice;
            }
        }


        boolean success = dao.saveBooking(user, showtimeIdVal, seats, totalPriceVal, "ONLINE");

        if (success) {
            int ticketId = dao.getLastetTicketIdByUser(user.getId());
            int elapsed = dao.getTicketElapsedSeconds(ticketId);
            int remainingSeconds = 30 - elapsed;
            if (remainingSeconds < 0) remainingSeconds = 0;

            request.setAttribute("ticketId", ticketId);
            request.setAttribute("showtimeId", showtimeId);
            request.setAttribute("selectedSeats", selectedSeats);
            request.setAttribute("totalPrice", totalPriceVal);
            request.setAttribute("remainingSeconds", remainingSeconds);
            request.getRequestDispatcher("/WEB-INF/view/checkout.jsp").forward(request, response);
        } else {
            session.setAttribute("error", "Lỗi: Ghế đã bị người khác đặt hoặc xảy ra sự cố!");
            response.sendRedirect(request.getContextPath() + "/select-seat?showtimeId=" + showtimeId);
        }
    }


    private void processPayment(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        new dao.TicketDAO().cleanupExpiredTickets();

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        String ticketIdStr = request.getParameter("ticketId");
        if (ticketIdStr == null || ticketIdStr.trim().isEmpty()) {
            response.sendRedirect("home");
            return;
        }

        int ticketId = Integer.parseInt(ticketIdStr);
        TicketDAO dao = new TicketDAO();
        model.Ticket ticket = dao.getTicketById(ticketId);


        if (ticket == null || ticket.getStatus() == model.TicketStatus.CANCELLED) {
            request.setAttribute("error", "Lỗi thanh toán! Thời gian giữ ghế (30 giây) đã hết hạn và ghế đã bị giải phóng.");
            

            request.setAttribute("ticketId", ticketIdStr);
            request.setAttribute("showtimeId", request.getParameter("showtimeId"));
            request.setAttribute("selectedSeats", request.getParameter("selectedSeats"));
            request.setAttribute("totalPrice", request.getParameter("totalPrice"));
            request.setAttribute("remainingSeconds", 0);
            request.getRequestDispatcher("/WEB-INF/view/checkout.jsp").forward(request, response);
        } else {

            response.sendRedirect(request.getContextPath()
                        + "/payment"
                        + "?ticketId="
                        + ticketId);
        }
    }
}