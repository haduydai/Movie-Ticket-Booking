package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Movie;
import model.MovieStatus;

public interface IMovieDAO {
	// lấy danh sách phim
	List<Movie> getAllMovies();

	// Get movie have name contains a string
	List<Movie> getMoviesHaveNameLikeString(String str);

	// tìm kiếm phim bằng id
	Movie getMovieById(int id);

	// thêm database
	boolean addMovie(Movie movie);

	// xoá phim bằng id
	int deleteMovieById(int id);
	
	// update phim
	
	int updateMovie(int id, Movie newMovie);

	// Update tình trạng phim
	int updateMovieStatusById(int id, MovieStatus status);
	
	List<Movie> getMoviesByCinemaId(int cinemaId);

	// tìm kiếm phim theo thể loại
	List<Movie> getMoviesByType(String type);


}
