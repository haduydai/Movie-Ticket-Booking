package controller;

import dao.UserDAO;
import model.User;
import utils.PasswordUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(req, resp);
    }
	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String pass = request.getParameter("password");
        
        // Check input validation
        if(username == null || username.isBlank()) {
        	backToPage("Vui lòng nhập tên đăng nhập!", request, response);
            return;
        }
        if(!username.matches("^[a-z0-9]+$")) {
        	backToPage("Tên đăng nhập chỉ gồm chữ thường và số.", request, response);
            return;
        }
        if(username.length() < 3 || username.length() > 30) {
        	backToPage("Tên đăng nhập phải có độ dài 3-20 kí tự.", request, response);
            return;
        }
        // check password
        if (pass == null || pass.length() == 0) {
            backToPage("Mật khẩu không được để trống.", request, response);
            return;
        }
        if (pass.length() > 40) {
            backToPage("Mật khẩu tối đa 40 kí tự.", request, response);
            return;
        }
        if (!pass.matches("^[a-z0-9]+$")) {
            backToPage("Mật khẩu chỉ được chứa chữ thường và số.", request, response);
            return;
        } 
        
        UserDAO dao = new UserDAO();
        User user = dao.checkUser(username);
        if (user == null) {
            backToPage("Tài khoản không tồn tại.", request, response);
        } else {

            // Trường hợp: Tài khoản có tồn tại, kiểm tra mật khẩu
        	//mã hóa mật khẩu người dùng vừa nhập vào form để so sánh với dữ liệu mật khẩu được mã hóa dưới database

            if (PasswordUtils.checkPassword(pass, user.getPassword())) {
                // Đăng nhập thành công -> Lưu vào Session
                HttpSession session = request.getSession();
                session.setAttribute("user", user);

                // TRẢ VỀ JSON THÀNH CÔNG (Thay vì sendRedirect)
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"status\": \"success\", \"message\": \"home\"}");
            } else {
                // Sai mật khẩu -> Gọi hàm báo lỗi JSON
                backToPage("Sai mật khẩu! Vui lòng thử lại.", request, response);
            }


        }
    }
    

 // Trả về lỗi dạng JSON thay vì tải lại trang
 private void backToPage(String message, HttpServletRequest request, HttpServletResponse response) {
     try {
         response.setContentType("application/json");
         response.setCharacterEncoding("UTF-8");
         String json = "{\"status\": \"error\", \"message\": \"" + message + "\"}";
         response.getWriter().write(json);
     } catch (IOException e) {
         e.printStackTrace();
     }
 }

}