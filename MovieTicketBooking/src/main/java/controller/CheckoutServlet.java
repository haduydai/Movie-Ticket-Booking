package controller;

import dao.TicketDAO;
import model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    // POST 1: Nhận dữ liệu từ trang chọn ghế -> Hiển thị trang xác nhận
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        
        // Nếu action là "PAY", tức là người dùng bấm nút "Thanh toán" ở trang checkout.jsp
        if ("PAY".equals(action)) {
            processPayment(request, response);
            return;
        }

        // Mặc định: Hiển thị trang Checkout
        String showtimeId = request.getParameter("showtimeId");
        String selectedSeats = request.getParameter("selectedSeats");
        String totalPrice = request.getParameter("totalPrice");

        request.setAttribute("showtimeId", showtimeId);
        request.setAttribute("selectedSeats", selectedSeats);
        request.setAttribute("totalPrice", totalPrice);

        request.getRequestDispatcher("/WEB-INF/view/checkout.jsp").forward(request, response);
    }

    // Xử lý lưu vé vào DB
    private void processPayment(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        int showtimeId = Integer.parseInt(request.getParameter("showtimeId"));
        String seatsStr = request.getParameter("selectedSeats"); // "A1,A2"
       //double totalPrice = Double.parseDouble(request.getParameter("totalPrice"));
        // tính tổng tiền vé trong giỏ hàng
        String paymentMethod = request.getParameter("paymentMethod");

        String[] seats = seatsStr.split(",");
        // tính tổng tiền vé dựa vào việc chọn ghế ngồi
        double totalPrice = 0;
        for(String seat : seats){
            char row= seat.charAt(0);
            if(row == 'A' || row == 'B'){
                totalPrice += 120000;
            }
            else{
                totalPrice += 90000;
            }
        }

        request.setAttribute("totalPrice", totalPrice);
        
        TicketDAO dao = new TicketDAO();
        // Gọi hàm Transaction vừa viết ở DAO
        boolean success = dao.saveBooking(user, showtimeId, seats, totalPrice, paymentMethod);

        if (success) {
            // Chuyển sang trang "Vé của tôi" hoặc trang "Thành công"
            // Ở đây mình chuyển về trang lịch sử đặt vé bạn đã có
            response.sendRedirect("profile"); 
        } else {
            request.setAttribute("error", "Lỗi thanh toán! Ghế có thể đã bị người khác đặt.");
            request.getRequestDispatcher("/WEB-INF/view/checkout.jsp").forward(request, response);
        }
    }
}