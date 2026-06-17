package controller;

import dao.MovieDAO;
import model.Movie;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/movie-detail")
public class MovieDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        String movieIdStr = request.getParameter("movieId");
        if (movieIdStr == null || movieIdStr.isBlank()) {
            movieIdStr = request.getParameter("id");
        }

        if (movieIdStr == null || movieIdStr.isBlank()) {
            response.sendRedirect("movies");
            return;
        }

        try {
            int movieId = Integer.parseInt(movieIdStr);
            MovieDAO movieDAO = new MovieDAO();
            Movie movie = movieDAO.getMovieById(movieId);

            if (movie == null) {
                response.sendRedirect("movies");
                return;
            }

            request.setAttribute("movie", movie);
            request.getRequestDispatcher("/WEB-INF/view/movieDetail.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect("movies");
        }
    }
}
