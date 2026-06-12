package controller_admin;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import dao.CinemaDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cinema;

@WebServlet("/admin/cinema/add")
public class AddCinemaServlet extends HttpServlet {
	private static final Logger logger = Logger.getLogger(AddCinemaServlet.class.getName());

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("pageView", "/WEB-INF/admin/admin-addcinema.jsp");
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/admin/admin-layout.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handlerAdd(request, response);
		response.sendRedirect(request.getContextPath() + "/admin/cinemas");
	}
	
	private void handlerAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String name = request.getParameter("name");
			if(name == null || name.isBlank()) {
				backToAddPage(request, response, "Thiếu tên rạp");
				return;
			}
			String address = request.getParameter("address");
			if(address == null || address.isBlank()) {
				backToAddPage(request, response, "Thiếu địa chỉ");
				return;
			}
			String status = request.getParameter("status");
			model.CinemaStatus cStatus = null;
			try {
				cStatus = (status != null && !status.isBlank()) ? model.CinemaStatus.valueOf(status) : model.CinemaStatus.OPEN;
			} catch (IllegalArgumentException ex) {
				cStatus = model.CinemaStatus.OPEN;
			}
			Cinema cm = new Cinema(name, address);
			cm.setStatus(cStatus);
			HttpSession session = request.getSession();
			try {
				boolean res = new CinemaDAO().addCinema(cm);
				if(res) {
					session.setAttribute("cinemaMessage", "Thêm rạp thành công!");
				} else {
					session.setAttribute("cinemaMessage", "Thêm rạp thất bại!");
				}
			} catch (Exception e) {
				logger.log(Level.SEVERE, "Error adding cinema", e);
				session.setAttribute("cinemaMessage", "Lỗi máy chủ khi thêm rạp. Vui lòng thử lại sau.");
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Unexpected error in AddCinemaServlet", e);
			request.getSession().setAttribute("cinemaMessage", "Lỗi không mong muốn. Vui lòng thử lại.");
		}
		
	}

	private void backToAddPage(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
		request.setAttribute("errorMessage", message);
		doGet(request, response);
	}

}
