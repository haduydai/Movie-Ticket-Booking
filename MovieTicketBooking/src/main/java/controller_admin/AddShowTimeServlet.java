package controller_admin;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import dao.CinemaDAO;
import dao.MovieDAO;
import dao.RoomDAO;
import dao.ShowTimeDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cinema;
import model.Movie;
import model.MovieStatus;
import model.Room;
import model.ShowTime;

@WebServlet("/admin/showtime/add")
public class AddShowTimeServlet extends HttpServlet {
	private static final Logger logger = Logger.getLogger(AddShowTimeServlet.class.getName());

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("pageView", "/WEB-INF/admin/admin-addshowtime.jsp");
		List<Cinema> listCinemas = new CinemaDAO().getAllCinema();
		List<Movie> listMovies = new MovieDAO().getAllMovies();
		// Get movies are now showing
		List<Movie> listMoviesNowShowing = listMovies.stream().filter(m -> MovieStatus.NOW_SHOWING.equals(m.getMovieStatus())).toList();
		request.setAttribute("listCinemas", listCinemas);
		request.setAttribute("listMovies", listMoviesNowShowing);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/admin/admin-layout.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handlerAdd(request, response);
		response.sendRedirect(request.getContextPath() + "/admin/showtimes");
	}
	
	private void handlerAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String cinema = request.getParameter("cinemaId");
			if(cinema == null || cinema.isBlank()) {
				backToAddPage(request, response, "Chưa chọn rạp");
				return;	
			}
			int cinemaId = Integer.parseInt(cinema);
			
			String room = request.getParameter("roomId");
			if(room == null || room.isBlank()) {
				backToAddPage(request, response, "Chưa chọn phòng");
				return;
			}
			int roomId = Integer.parseInt(room);
			
		    String movie = request.getParameter("movieId");
		    if(movie == null || movie.isBlank()) {
		    	backToAddPage(request, response, "Chưa chọn phim");
				return;
			}
		    int movieId = Integer.parseInt(movie);
		    
		    String priceStr = request.getParameter("price");
		    if(priceStr == null || priceStr.isBlank()) {
		    	backToAddPage(request, response, "Chưa nhập giá tiền mỗi vé");
				return;
			}
			BigDecimal price;
			try {
				price = new BigDecimal(priceStr);
			} catch (NumberFormatException ex) {
				backToAddPage(request, response, "Giá tiền không hợp lệ");
				return;
			}
            
			String start = request.getParameter("startTime");
			if(start == null || start.isBlank()) {
				backToAddPage(request, response, "Chưa chọn thời gian bắt đầu");
				return;
			}
			LocalDateTime startTime;
			try {
				startTime = LocalDateTime.parse(start);
			} catch (Exception ex) {
				backToAddPage(request, response, "Thời gian không hợp lệ");
				return;
			}
            
			Room r = new RoomDAO().getRoomById(roomId);
			Cinema c = new CinemaDAO().getCinemaById(cinemaId);
			Movie m = new MovieDAO().getMovieById(movieId);
			ShowTime st = new ShowTime(c, r, m, price, startTime);
			HttpSession session = request.getSession();
			try {
				boolean res = new ShowTimeDAO().addShowTime(st);
				session.setAttribute("showtimeMessage", res ?  "Thêm suất chiếu thành công!" : "Thêm suất chiếu thất bại!");
			} catch (Exception e) {
				logger.log(Level.SEVERE, "Error adding showtime", e);
				session.setAttribute("showtimeMessage", "Lỗi máy chủ khi thêm suất chiếu. Vui lòng thử lại sau.");
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		} catch (NumberFormatException e) {
			logger.log(Level.WARNING, "Invalid number format in AddShowTimeServlet", e);
			backToAddPage(request, response, "Dữ liệu số không hợp lệ");
		}
		
	}

	private void backToAddPage(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
		request.setAttribute("errorMessage", message);
		doGet(request, response);
	}
}
