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
	// Get list of all movies in database
	List<Movie> getAllMovies();
	
	// Get n movies with status
	List<Movie> getNMoviesWithStatus(int n, MovieStatus status);
	
	//Get all movies with status
	List<Movie> getMoviesWithStatus(MovieStatus status);
	
	// Get movie have name contains a string
	List<Movie> getMoviesHaveNameLikeKeyword(String keyword);

	// Get movie by id
	Movie getMovieById(int id);

	// Add movie to database
	boolean addMovie(Movie movie);

	// Delete movie by id
	int deleteMovieById(int id);
	
	// Update detail of movie with id
	int updateMovie(int id, Movie newMovie);

	// Update movie status by movie id
	int updateMovieStatusById(int id, MovieStatus status);

	// Get movies by cinema id
	List<Movie> getMoviesByCinemaId(int cinemaId);
}
