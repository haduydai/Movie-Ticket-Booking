package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/verify-otp")
public class VerifyOTPServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/view/verify-otp.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String inputOtp = request.getParameter("otp");
        HttpSession session = request.getSession();
        String sessionOtp = (String) session.getAttribute("otp");
        // Kiểm tra OTP
        if (sessionOtp != null && sessionOtp.equals(inputOtp)) {
            // Lấy object newUser từ session (được set ở RegisterServlet)
            model.User newUser = (model.User) session.getAttribute("newUser");
        if(newUser!=null){
            //TH1:ĐĂNG KÝ
            dao.UserDAO dao = new dao.UserDAO();
            boolean isAdded = dao.addUser(newUser);
            if(isAdded){
                session.removeAttribute("newUser");//xóa vì đã tồn tại user này
                session.removeAttribute("otp");//xóa otp
                sendJsonResponse(response,"succes","login");
            }else {
                sendJsonResponse(response, "eror", "Đăng ký thất bại vui lòng thử lại");
            }
        }else{
            //TH2:QUÊN MẬT KHẨU
            sendJsonResponse(response, "success", "reset-password");
        }
        }
    }
    private void sendJsonResponse(HttpServletResponse response,String status, String message){
        try{
            response.setContentType("application/json; charset = UTF-8");
            String json = String.format("{\"status\":\"%s\",\"message\":\"%s\"}",status,message);
            response.getWriter().write(json);



        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}