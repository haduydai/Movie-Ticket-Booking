package controller;

import dao.UserDAO;
import model.Role;
import model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import utils.PasswordUtils;
import jakarta.servlet.http.HttpSession;
import utils.EmailUtils;


@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

	// Thêm hàm doGet để mở trang đăng ký
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //  Get input data
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username").trim();
        String email = request.getParameter("email").trim();
        String phone = request.getParameter("phone").trim();
        String pass = request.getParameter("password").trim();
        String rePass = request.getParameter("confirmPassword").trim();

        // check input validation
        // check username
        if(username == null || username.isBlank()) {
            sendJsonResponse(response, "error", "Vui lòng nhập tên đăng nhập!");
            return;
        }
        if(!username.matches("^[a-z0-9]+$")) {
            sendJsonResponse(response, "error", "Tên đăng nhập chỉ gồm chữ thường và số.");
            return;
        }
        if(username.length() < 3 || username.length() > 30) {
            sendJsonResponse(response, "error", "Tên đăng nhập phải có độ dài 3-20 kí tự.");
            return;
        }
        // check username is exist
        UserDAO dao = new UserDAO();
        if (dao.checkUser(username) != null) {
            sendJsonResponse(response, "error", "Tên đăng nhập đã tồn tại! Vui lòng chọn tên khác.");
            return;
        }
        
        // check email
        if (email == null || email.isBlank()) {
            sendJsonResponse(response, "error", "Email không được để trống.");
            return;
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            sendJsonResponse(response, "error", "Email không hợp lệ.");
            return;
        }
        
        // Kiểm tra xem Email đã tồn tại chưa
        if (dao.getUserByEmail(email) != null) {
            sendJsonResponse(response, "error", "Email này đã tồn tại.");
            return;
        }
        
        // check phonenumber
        if (phone == null || phone.isBlank()) {
            sendJsonResponse(response, "error", "Số điện thoại không được để trống.");
            return;
        }
        if (!phone.matches("^0\\d{9}$")) {
            sendJsonResponse(response, "error", "Số điện thoại không hợp lệ");
            return;
        }
        if (dao.getUserByPhone(phone) != null) {
            sendJsonResponse(response, "error", "Số điện thoại đã tồn tại.");
            return;
        }

        
        // check password
        if (pass == null || pass.length() == 0) {
            sendJsonResponse(response, "error", "Mật khẩu không được để trống.");
            return;
        }
        if (pass.length() > 40) {
            sendJsonResponse(response, "error", "Mật khẩu tối đa 40 kí tự.");
            return;
        }
        if (!pass.matches("^[a-z0-9]+$")) {
            sendJsonResponse(response, "error", "Mật khẩu chỉ được chứa chữ thường và số.");
            return;
        } 
        if (!pass.equals(rePass)) {
            sendJsonResponse(response, "error", "Mật khẩu xác nhận không khớp!");
            return;
        }

        User newUser = new User(username, pass, email, phone, Role.USER);
       try {
           //tạo mã otp ngâu nhiên
           String otp = EmailUtils.generateOTP();
           //gửi mã vào email của người dùng
           EmailUtils.sendEmail(email,"Xác thực đăng ký MyCinema","Mã otp của bạn là:");
           //tạo session lưu tạm user vào
                //khỏi tạo session
                HttpSession session = request.getSession();
                session.setAttribute("newUser",newUser);
                //lưu tạm otp
                session.setAttribute("otp",otp);
                //tụ huỷ sau 5p
                session.setMaxInactiveInterval(300);
                sendJsonResponse(response,"success","verify-otp");

       } catch (Exception e) {
           e.printStackTrace();
           sendJsonResponse(response, "error", "Lỗi gửi email! Vui lòng đảm bảo email của bạn có thật.");
       }
    }

        //  biến Servlet thành API trả về JSON
    private void sendJsonResponse(HttpServletResponse response, String status, String message) {
        try {
            response.setContentType("application/json; charset=UTF-8");
            String json = String.format("{\"status\":\"%s\", \"message\":\"%s\"}", status, message);
            response.getWriter().write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}