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

        String movieIdStr = request.getParameter("movieId");
        if (movieIdStr == null || movieIdStr.isEmpty()) {
            response.sendRedirect("home");
            return;
        }
        
        try {
            int movieId = Integer.parseInt(movieIdStr);
            ShowTimeDAO dao = new ShowTimeDAO();
            

            List<ShowTime> allShowTimes = dao.getShowTimesByMovieIdAndNextNDays(movieId, 7);
            

            List<LocalDate> next7Days = new ArrayList<>();
            LocalDate today = LocalDate.now();
            for (int i = 0; i < 7; i++) {
                next7Days.add(today.plusDays(i));
            }


            request.setAttribute("allShowTimes", allShowTimes);
            request.setAttribute("next7Days", next7Days);
            

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