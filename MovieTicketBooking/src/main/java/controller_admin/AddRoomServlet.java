package controller_admin;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import dao.CinemaDAO;
import dao.RoomDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cinema;
import model.Room;

@WebServlet("/admin/room/add")
public class AddRoomServlet extends HttpServlet {
	private static final Logger logger = Logger.getLogger(AddRoomServlet.class.getName());

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("pageView", "/WEB-INF/admin/admin-addroom.jsp");
		request.setAttribute("listCinemas", new CinemaDAO().getAllCinema());
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/admin/admin-layout.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handlerAdd(request, response);
		response.sendRedirect(request.getContextPath() + "/admin/rooms");
	}
	
	private void handlerAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String name = request.getParameter("name");
			if(name == null || name.isBlank()) {
				backToAddPage(request, response, "Thiếu tên phòng");
				return;
			}
			
			String cinemaIdStr = request.getParameter("cinemaId");
			if(cinemaIdStr == null) {
				backToAddPage(request, response, "Thiếu chi nhánh");
				return;
			}
			int cinemaId = Integer.parseInt(cinemaIdStr);
			
			String cols = request.getParameter("cols");
			if(cols == null || cols.isBlank()) {
				backToAddPage(request, response, "Thiếu số cột");
				return;
			}
			int numberOfColumns = Integer.parseInt(cols);
			if(numberOfColumns < 1) {
				backToAddPage(request, response, "Số cột phải lớn hơn 1");
				return;
			}
			
			String rows = request.getParameter("rows");
			if(rows == null || rows.isBlank()) {
				backToAddPage(request, response, "Thiếu số hàng");
				return;
			}
			int numberOfRows = Integer.parseInt(rows);
			if(numberOfRows < 1 || numberOfRows > 26) {
				backToAddPage(request, response, "Số hàng phải lớn hơn 1 và bé hơn 26");
				return;
			}
			
			Cinema cm = new CinemaDAO().getCinemaById(cinemaId);
			Room room = new Room(name, numberOfColumns, numberOfRows, cm);
			HttpSession session = request.getSession();
			try {
				boolean res = new RoomDAO().addRoom(room, cinemaId);
				if(res) {
					session.setAttribute("roomMessage", "Thêm phòng thành công!");
				} else {
					session.setAttribute("roomMessage", "Thêm phòng thất bại!");
				}
			} catch (Exception e) {
				logger.log(Level.SEVERE, "Error adding room", e);
				session.setAttribute("roomMessage", "Lỗi máy chủ khi thêm phòng. Vui lòng thử lại sau.");
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		} catch (NumberFormatException e) {
			logger.log(Level.WARNING, "Invalid number format in AddRoomServlet", e);
			backToAddPage(request, response, "Dữ liệu nhập không hợp lệ");
		}
		
	}

	private void backToAddPage(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
		request.setAttribute("errorMessage", message);
		doGet(request, response);
	}

}
