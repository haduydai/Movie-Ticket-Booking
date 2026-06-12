package controller_admin;

import java.util.logging.Level;
import java.util.logging.Logger;
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
import jakarta.servlet.http.Part;
import dao.IMovieDAO;
import dao.MovieDAO;

@WebServlet("/admin/movie/edit")
@MultipartConfig
public class EditMovieServlet extends HttpServlet {
	private static final Logger logger = Logger.getLogger(EditMovieServlet.class.getName());
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("pageView", "/WEB-INF/admin/admin-editmovie.jsp");
		// Get movie by id and put it to view
		IMovieDAO mDAO = new MovieDAO();
		String idPara = request.getParameter("id");
		int id = 0;
		try {
			id = Integer.parseInt(idPara);
		} catch(NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/admin/movies");
			logger.log(Level.WARNING, "Invalid movie ID format", e);
			return;
		}
		Movie movie = mDAO.getMovieById(id);
		if(movie == null) {
			request.getSession().setAttribute("movieMessage", "Không tìm thấy phim.");
			response.sendRedirect(request.getContextPath() + "/admin/movies");
			return;
		}
		request.setAttribute("movie", movie);
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/admin/admin-layout.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handlerUpdate(request, response);
		response.sendRedirect(request.getContextPath() + "/admin/movies");
	}
	
	// Handler update movie
	private void handlerUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		try {
			// Get parameter and check error
			int id = Integer.parseInt(request.getParameter("id"));
			String name = request.getParameter("name");
			if(name == null || name.isBlank()) {
				backToEditPage(request, response, "Thiếu tên phim");
				return;
			}
			String type = request.getParameter("type");
			if(type == null || type.isBlank()) {
				backToEditPage(request, response, "Thiếu thể loại");
				return;
			}
		    String directorName = request.getParameter("directorName");
		    if(directorName == null || directorName.isBlank()) {
				backToEditPage(request, response, "Thiếu tên đạo diễn");
				return;
			}
		    String actorsName = request.getParameter("actorsName");
		    if(actorsName == null || actorsName.isBlank()) {
				backToEditPage(request, response, "Thiếu tên các diễn viên");
				return;
			}
		    String durationStr = request.getParameter("duration");
		    if(durationStr == null || durationStr.isBlank()) {
				backToEditPage(request, response, "Thiếu thời lượng phim");
				return;
			}
		    int duration = Integer.parseInt(durationStr);
		    if(duration < 0) {
		    	backToEditPage(request, response, "Thời lượng phim phải dương");
				return;
		    }
		    String country = request.getParameter("country");
		    if(country == null || country.isBlank()) {
				backToEditPage(request, response, "Thiếu tên quốc gia");
				return;
			}
			String imageUrl = null;
			try {
				Part posterPart = request.getPart("poster");
				if (posterPart != null && posterPart.getSize() > 0) {
					String contentType = posterPart.getContentType();
					if (contentType == null || !contentType.startsWith("image/")) {
						backToEditPage(request, response, "File poster phải là ảnh");
						return;
					}
					if (posterPart.getSize() > 5 * 1024 * 1024) { // 5MB
						backToEditPage(request, response, "Kích thước poster tối đa 5MB");
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
					imageUrl = request.getParameter("existingImageUrl");
				}
			} catch (IllegalStateException ex) {
				backToEditPage(request, response, "File tải lên quá lớn");
				return;
			}
		    if(imageUrl == null || imageUrl.isBlank()) {
				backToEditPage(request, response, "Thiếu đường dẫn hình");
				return;
			}
		    String status = request.getParameter("status");
		    String description = request.getParameter("description");
			Movie movie = new Movie(name, type, directorName, actorsName, description, duration, country, imageUrl, MovieStatus.valueOf(status));
		    int update = new MovieDAO().updateMovie(id, movie);
		    // Put message to session
		    HttpSession session = request.getSession();
			// check movie have update in database
		    if(update > 0) {
			    session.setAttribute("movieMessage", "Cập nhật thành công!");
		    } else {
		    	session.setAttribute("movieMessage", "Không cập nhật!");
		    }
		} catch(NumberFormatException e) {
			// number format during parsing id/duration
			e.printStackTrace();
			request.getSession().setAttribute("movieMessage", "Dữ liệu không hợp lệ. Vui lòng kiểm tra lại.");
		} catch (Exception e) {
			e.printStackTrace();
			request.getSession().setAttribute("movieMessage", "Lỗi máy chủ khi cập nhật phim. Vui lòng thử lại sau.");
		}
	}
	
	// Back to edit page for edit again
	private void backToEditPage(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
		request.setAttribute("errorMessage", message);
		doGet(request, response);
	}

}
