package controller_admin;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Movie;
import model.MovieStatus;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.http.Part;

import dao.MovieDAO;

@WebServlet("/admin/movie/add")
@MultipartConfig
public class AddMovieServlet extends HttpServlet {
	private static final Logger logger = Logger.getLogger(AddMovieServlet.class.getName());

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("pageView", "/WEB-INF/admin/admin-addmovie.jsp");
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/admin/admin-layout.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handlerAdd(request, response);
		response.sendRedirect(request.getContextPath() + "/admin/movies");
	}
	
	private void handlerAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String name = request.getParameter("name");
			if(name == null || name.isBlank()) {
				backToAddPage(request, response, "Thiếu tên phim");
				return;
			}
			String type = request.getParameter("type");
			if(type == null || type.isBlank()) {
				backToAddPage(request, response, "Thiếu thể loại");
				return;
			}
		    String directorName = request.getParameter("directorName");
		    if(directorName == null || directorName.isBlank()) {
		    	backToAddPage(request, response, "Thiếu tên đạo diễn");
				return;
			}
		    String actorsName = request.getParameter("actorsName");
		    if(actorsName == null || actorsName.isBlank()) {
		    	backToAddPage(request, response, "Thiếu tên các diễn viên");
				return;
			}
		    String durationStr = request.getParameter("duration");
		    if(durationStr == null || durationStr.isBlank()) {
		    	backToAddPage(request, response, "Thiếu thời lượng phim");
				return;
			}
			int duration;
			try {
				duration = Integer.parseInt(durationStr);
			} catch (NumberFormatException ex) {
				backToAddPage(request, response, "Thời lượng phim không hợp lệ");
				return;
			}
			if(duration < 0) {
				backToAddPage(request, response, "Thời lượng phim phải dương");
				return;
			}
		    String country = request.getParameter("country");
		    if(country == null || country.isBlank()) {
		    	backToAddPage(request, response, "Thiếu tên quốc gia");
				return;
			}
							String imageUrl = null;
							// handle poster upload
							try {
								Part posterPart = request.getPart("poster");
								if (posterPart != null && posterPart.getSize() > 0) {
									String contentType = posterPart.getContentType();
									if (contentType == null || !contentType.startsWith("image/")) {
										backToAddPage(request, response, "File poster phải là ảnh");
										return;
									}
									if (posterPart.getSize() > 5 * 1024 * 1024) { // 5MB limit
										backToAddPage(request, response, "Kích thước poster tối đa 5MB");
										return;
									}
									String submitted = Paths.get(posterPart.getSubmittedFileName()).getFileName().toString();
									String fileName = System.currentTimeMillis() + "-" + submitted;
									String uploadDir = request.getServletContext().getRealPath("/images/posters");
									Path uploadPath = Paths.get(uploadDir);
									if (!Files.exists(uploadPath)) {
										Files.createDirectories(uploadPath);
									}
									Path filePath = uploadPath.resolve(fileName);
									try (InputStream is = posterPart.getInputStream()) {
										Files.copy(is, filePath, StandardCopyOption.REPLACE_EXISTING);
									}
									imageUrl = request.getContextPath() + "/images/posters/" + fileName;
								} else {
									imageUrl = request.getParameter("imageUrl");
								}
							} catch (IllegalStateException ex) {
								backToAddPage(request, response, "File tải lên quá lớn");
								return;
							}
		    if(imageUrl == null || imageUrl.isBlank()) {
		    	backToAddPage(request, response, "Thiếu đường dẫn hình");
				return;
			}
		    String status = request.getParameter("status");
		    String description = request.getParameter("description");
			Movie movie = new Movie(name, type, directorName, actorsName, description, duration, country, imageUrl, MovieStatus.valueOf(status));
			HttpSession session = request.getSession();
			try {
				boolean res = new MovieDAO().addMovie(movie);
				if(res) {
					session.setAttribute("movieMessage", "Thêm phim thành công!");
				} else {
					session.setAttribute("movieMessage", "Thêm phim thất bại!");
				}
			} catch (Exception e) {
				logger.log(Level.SEVERE, "Error adding movie", e);
				session.setAttribute("movieMessage", "Lỗi máy chủ khi thêm phim. Vui lòng thử lại sau.");
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			// unexpected error
			logger.log(Level.SEVERE, "Unexpected error in AddMovieServlet", e);
			request.getSession().setAttribute("movieMessage", "Lỗi không mong muốn. Vui lòng thử lại.");
		}
		
	}

	private void backToAddPage(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
		request.setAttribute("errorMessage", message);
		doGet(request, response);
	}

}
