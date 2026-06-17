package controller;

import model.Ticket;
import model.User;
import utils.PasswordUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import dao.ITicketDAO;
import dao.TicketDAO;
import dao.UserDAO;

@WebServlet("/profile" )
public class ProfileServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		ITicketDAO ticketDAO = new TicketDAO();
	    // Lấy danh sách vé của user hiện tại
	    List<Ticket> history = ticketDAO.getTicketsByUserId(user.getId());
	    
	    request.setAttribute("ticketHistory", history);
	    request.getRequestDispatcher("/WEB-INF/view/profile.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Thiết lập tiếng Việt
		request.setCharacterEncoding("UTF-8");

		// Lấy user từ session
		HttpSession session = request.getSession();
		User currentUser = (User) session.getAttribute("user");

		if (currentUser == null) {
			sendJsonResponse(response, "error", "Phiên đăng nhập đã hết hạn. Vui lòng đăng nhập lại!");
			return;
		}

		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String currentPass = request.getParameter("currentPass");
		String newPass = request.getParameter("newPass");
		String confirmPass = request.getParameter("confirmPass");

		if (email != null) email = email.trim();
		if (phone != null) phone = phone.trim();
		if (currentPass != null) currentPass = currentPass.trim();
		if (newPass != null) newPass = newPass.trim();
		if (confirmPass != null) confirmPass = confirmPass.trim();

		// check email
		if (email == null || email.isBlank()) {
			sendJsonResponse(response, "error", "Email không được để trống.");
			return;
		}
		if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
			sendJsonResponse(response, "error", "Email không hợp lệ.");
			return;
		}

		// check phonenumber
		if (phone == null || phone.isBlank()) {
			sendJsonResponse(response, "error", "Số điện thoại không được để trống.");
			return;
		}
		if (!phone.matches("^0\\d{9}$")) {
			sendJsonResponse(response, "error", "Số điện thoại không hợp lệ.");
			return;
		}

		// Xử lý logic Đổi Mật Khẩu (nếu người dùng có nhập mật khẩu mới)
		if (newPass != null && !newPass.isEmpty()) {
			if (currentPass == null || currentPass.isEmpty()) {
				sendJsonResponse(response, "error", "Vui lòng nhập mật khẩu hiện tại!");
				return;
			}

			try {
				// Kiểm tra mật khẩu hiện tại từ input với mật khẩu băm từ database
				if (!PasswordUtils.checkPassword(currentPass, currentUser.getPassword())) {
					sendJsonResponse(response, "error", "Mật khẩu hiện tại không đúng!");
					return;
				}

				// Kiểm tra xác nhận mật khẩu
				if (!newPass.equals(confirmPass)) {
					sendJsonResponse(response, "error", "Mật khẩu xác nhận không khớp!");
					return;
				}

				// Kiểm tra độ dài mật khẩu mới (phải từ 6 ký tự trở lên)
				if (newPass.length() < 6) {
					sendJsonResponse(response, "error", "Mật khẩu mới phải từ 6 ký tự trở lên!");
					return;
				}

				// Mã hóa mật khẩu mới và cập nhật vào đối tượng User
				String newPassHash = PasswordUtils.hashPassword(newPass);
				currentUser.setPassword(newPassHash);
			} catch (Exception e) {
				e.printStackTrace();
				sendJsonResponse(response, "error", "Có lỗi xảy ra khi mã hóa mật khẩu.");
				return;
			}
		}

		// Cập nhật các thông tin cá nhân khác
		currentUser.setEmail(email);
		currentUser.setPhoneNumber(phone);

		// Lưu xuống Database
		UserDAO dao = new UserDAO();
		dao.updateUser(currentUser);

		// Cập nhật lại Session để hiển thị thông tin mới
		session.setAttribute("user", currentUser);

		// Trả về JSON báo thành công
		sendJsonResponse(response, "success", "Cập nhật thông tin thành công!");
	}
	private void sendJsonResponse(HttpServletResponse response, String status, String message) {
		try {
			response.setContentType("application/json; charset=UTF-8");
			String json = String.format("{\"status\":\"%s\",\"message\":\"%s\"}", status, message);
			response.getWriter().write(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}