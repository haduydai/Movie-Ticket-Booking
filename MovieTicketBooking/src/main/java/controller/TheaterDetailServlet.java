package controller;

import dao.CinemaDAO;
import dao.MovieDAO;
import dao.RoomDAO;
import model.Cinema;
import model.Movie;
import model.Room;
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

            String idStr = request.getParameter("id");
            if (idStr == null) {
                response.sendRedirect("theaters");
                return;
            }
            int cinemaId = Integer.parseInt(idStr);
            

            CinemaDAO cinemaDAO = new CinemaDAO();
            Cinema cinema = cinemaDAO.getCinemaById(cinemaId);
            if(cinema == null){
                response.sendRedirect("theaters");
                return;
            }
            

            RoomDAO roomDAO = new RoomDAO();
            List<Room> rooms = roomDAO.getRoomByCinemaId(cinemaId);
            

            MovieDAO movieDAO = new MovieDAO();
            List<Movie> moviesAtCinema;
            Integer selectedRoomId = null;
            
            String roomIdStr = request.getParameter("roomId");
            if (roomIdStr != null && !roomIdStr.trim().isEmpty()) {
                try {
                    int roomId = Integer.parseInt(roomIdStr);
                    moviesAtCinema = movieDAO.getMoviesByRoomId(roomId);
                    selectedRoomId = roomId;
                } catch (NumberFormatException e) {
                    moviesAtCinema = movieDAO.getMoviesByCinemaId(cinemaId);
                }
            } else {
                moviesAtCinema = movieDAO.getMoviesByCinemaId(cinemaId);
            }
            

            request.setAttribute("cinema", cinema);
            request.setAttribute("rooms", rooms);
            request.setAttribute("selectedRoomId", selectedRoomId);
            request.setAttribute("moviesAtCinema", moviesAtCinema);
            
            request.getRequestDispatcher("/WEB-INF/view/theaterDetail.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            response.sendRedirect("theaters");
        }
    }
}