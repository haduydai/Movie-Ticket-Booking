package controller;

import dao.CinemaDAO;
import dao.MovieDAO;
import model.Cinema;
import model.Movie;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "TheaterDetailServlet", urlPatterns = {"/theater-detail"})
public class TheaterDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            // 1. Lấy ID rạp từ URL
            String idStr = request.getParameter("id");
            if (idStr == null) {
                response.sendRedirect("theaters");
                return;
            }
            int cinemaId = Integer.parseInt(idStr);
            
            // 2. Lấy thông tin Rạp (Tên, địa chỉ...)
            CinemaDAO cinemaDAO = new CinemaDAO();
            Cinema cinema = cinemaDAO.getCinemaById(cinemaId);
            if(cinema == null){
                response.sendRedirect("theaters");
                return;
            }
            
            // 3. Lấy danh sách phim chiếu tại rạp này
            MovieDAO movieDAO = new MovieDAO();
            List<Movie> moviesAtCinema = movieDAO.getMoviesByCinemaId(cinemaId);
            
            // 4. Gửi sang JSP
            request.setAttribute("cinema", cinema);
            request.setAttribute("moviesAtCinema", moviesAtCinema);
            
            request.getRequestDispatcher("theaterDetail.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            response.sendRedirect("theaters");
        }
    }
}