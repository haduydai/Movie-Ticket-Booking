package controller_admin;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Role;
import model.User;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import dao.UserDAO;


@WebServlet("/admin/user/add")
public class AddAccountServlet extends HttpServlet {
	private static final Logger logger = Logger.getLogger(AddAccountServlet.class.getName());

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("pageView", "/WEB-INF/admin/admin-addaccount.jsp");
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/admin/admin-layout.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handlerAdd(request, response);
		response.sendRedirect(request.getContextPath() + "/admin/users");
	}
	
	private void handlerAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String username = request.getParameter("username");
			if(username == null || username.isBlank()) {
				backToAddPage(request, response, "Thiếu username");
				return;
			}
			String password = request.getParameter("password");
			if(password == null || password.isBlank()) {
				backToAddPage(request, response, "Thiếu mật khẩu");
				return;
			}
			String email = request.getParameter("email");
			if (email == null || email.isBlank()) {
			    backToAddPage(request, response, "Thiếu email");
			    return;
			}

			// check regex email
			if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
			    backToAddPage(request, response, "Email không đúng định dạng");
			    return;
			}

			String phonenumber = request.getParameter("phonenumber");
			if (phonenumber == null || phonenumber.isBlank()) {
			    backToAddPage(request, response, "Thiếu số điện thoại");
			    return;
			}

			// check regex phone number: bắt đầu 0, đủ 10 số
			if (!phonenumber.matches("^0\\d{9}$")) {
			    backToAddPage(request, response, "Số điện thoại phải bắt đầu bằng 0 và đủ 10 số");
			    return;
			}
			String role = request.getParameter("role");
			if(role == null || role.isBlank()) {
				backToAddPage(request, response, "Thiếu role user");
				return;
			}
			Role userRole = ("admin".equalsIgnoreCase(role)) ? Role.ADMIN : Role.USER;
			User user = new User(username, password, email, phonenumber, userRole);
			HttpSession session = request.getSession();
			try {
				boolean res = new UserDAO().addUser(user);
				if(res) {
					session.setAttribute("userMessage", "Thêm tài khoản thành công!");
				} else {
					session.setAttribute("userMessage", "Thêm tài khoản thất bại!");
				}
			} catch (Exception e) {
				logger.log(Level.SEVERE, "Error adding user", e);
				session.setAttribute("userMessage", "Lỗi máy chủ khi thêm tài khoản. Vui lòng thử lại sau.");
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Unexpected error in AddAccountServlet", e);
			request.getSession().setAttribute("userMessage", "Lỗi không mong muốn. Vui lòng thử lại.");
		}
		
	}

	private void backToAddPage(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
		request.setAttribute("errorMessage", message);
		doGet(request, response);
	}
}
