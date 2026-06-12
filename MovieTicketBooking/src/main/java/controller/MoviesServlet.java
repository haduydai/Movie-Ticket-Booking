package controller;

import dao.IMovieDAO;
import dao.MovieDAO;
import model.Movie;
import model.MovieStatus;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/movies")
public class MoviesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      
        IMovieDAO dao = new MovieDAO();
        
//        List<Movie> listShowing = dao.getMoviesWithStatus(MovieStatus.NOW_SHOWING);
//        List<Movie> listUpcoming = dao.getMoviesWithStatus(MovieStatus.COMING_SOON);

        String type = request.getParameter("type");
        String country = request.getParameter("country");
        String tag = request.getParameter("tag");


        List<Movie> listUpcoming;
        List<Movie> listShowing;

        boolean isFiltering = (type != null && !type.isEmpty())
                || (country != null && !country.isEmpty()) || (tag != null && !tag.isEmpty());
        if(isFiltering){
            List<Movie> filtered = dao.filterMovies(type, country, tag);

            listShowing = new ArrayList<>();
            listUpcoming = new  ArrayList<>();

            for(Movie movie : filtered){
                if(movie.getMovieStatus() == MovieStatus.NOW_SHOWING){
                    listShowing.add(movie);
                }
                else if(movie.getMovieStatus() == MovieStatus.COMING_SOON){
                    listUpcoming.add(movie);
                }
            }
        }
        else{
            listShowing = dao.getMoviesWithStatus(MovieStatus.NOW_SHOWING);
            listUpcoming = dao.getMoviesWithStatus(MovieStatus.COMING_SOON);
        }


        // Gửi dữ liệu sang trang JSP
        request.setAttribute("listShowing", listShowing);
        request.setAttribute("listUpcoming", listUpcoming);
        request.setAttribute("selectType", type);
        request.setAttribute("selectCountry", country);
        request.setAttribute("selectTag", tag);




        // Chuyển hướng về trang giao diện
        request.getRequestDispatcher("/WEB-INF/view/movies.jsp").forward(request, response);
    }
}