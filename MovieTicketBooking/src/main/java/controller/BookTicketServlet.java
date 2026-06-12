package controller;

import dao.IMovieDAO;
import dao.MovieDAO;
import dao.ShowTimeDAO;
import model.ShowTime;
import model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/book-ticket")
public class BookTicketServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy movieId từ tham số URL
        String movieIdStr = request.getParameter("movieId");
        if (movieIdStr == null || movieIdStr.isEmpty()) {
            response.sendRedirect("home"); // Nếu không có ID phim thì về trang chủ
            return;
        }
        
        try {
            int movieId = Integer.parseInt(movieIdStr);
            ShowTimeDAO dao = new ShowTimeDAO();
            
            // Lấy danh sách tất cả suất chiếu của phim này trong 7 ngày tới
            List<ShowTime> allShowTimes = dao.getShowTimesByMovieIdAndNextNDays(movieId, 7);
            
            // 4. Tạo danh sách 7 ngày tới để hiển thị Tabs
            List<LocalDate> next7Days = new ArrayList<>();
            LocalDate today = LocalDate.now();
            for (int i = 0; i < 7; i++) {
                next7Days.add(today.plusDays(i));
            }

            // Gửi dữ liệu sang JSP
            request.setAttribute("allShowTimes", allShowTimes);
            request.setAttribute("next7Days", next7Days);
            
            // Lấy tên phim từ suất chiếu đầu tiên (nếu có) để hiển thị tiêu đề
            IMovieDAO movieDao = new MovieDAO();
            model.Movie movie = movieDao.getMovieById(movieId);
            if (movie != null) {
                request.setAttribute("movieName", movieDao.getMovieById(movieId).getName());
            }

            request.getRequestDispatcher("/WEB-INF/view/book-ticket.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect("home");
        }
    }
}