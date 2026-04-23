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

public class MovieDAO implements IMovieDAO {
	// Get list of all movies in database
	@Override
	public List<Movie> getAllMovies() {
		List<Movie> list = new ArrayList<>();
		try {
			// Query string to get data
			String queryString = "SELECT movie_id, movie_name, movie_type, director_name, names_of_actors, movie_description,"
					+ " movie_duration, movie_country, movie_image_url, movie_status FROM movies ORDER BY movie_id DESC";
			// Create connection
			Connection connect = JDBCConnection.getConnection();
			Statement st = connect.createStatement();
			ResultSet rs = st.executeQuery(queryString);
			// Iterate result set to get data
			while (rs.next()) {
				list.add(mapResultSetToMovie(rs));
			}
			rs.close();
			st.close();
			connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	

	@Override
	public List<Movie> getNMoviesWithStatus(int n, MovieStatus status) {
		List<Movie> list = new ArrayList<>();
		try {
			// Query string to get data
			String queryString = "SELECT movie_id, movie_name, movie_type, director_name, names_of_actors, movie_description,"
					+ " movie_duration, movie_country, movie_image_url, movie_status"
					+ " FROM movies WHERE movie_status = ? "
					+ " ORDER BY movie_id DESC LIMIT ?";
			// Create connection
			Connection connect = JDBCConnection.getConnection();
			PreparedStatement ps = connect.prepareStatement(queryString);
			ps.setString(1, status.name());
			ps.setInt(2, n);
			ResultSet rs = ps.executeQuery();
			// Iterate result set to get data
			while (rs.next()) {
				list.add(mapResultSetToMovie(rs));
			}
			rs.close();
			ps.close();
			connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// Get all movie with status
	@Override
	public List<Movie> getMoviesWithStatus(MovieStatus status) {
		List<Movie> list = new ArrayList<>();
		try {
			// Query string to get data
			String queryString = "SELECT movie_id, movie_name, movie_type, director_name, names_of_actors, movie_description,"
					+ " movie_duration, movie_country, movie_image_url, movie_status"
					+ " FROM movies WHERE movie_status = ? ORDER BY movie_id DESC";
			// Create connection
			Connection connect = JDBCConnection.getConnection();
			PreparedStatement ps = connect.prepareStatement(queryString);
			ps.setString(1, status.name());
			ResultSet rs = ps.executeQuery();
			// Iterate result set to get data
			while (rs.next()) {
				list.add(mapResultSetToMovie(rs));
			}
			rs.close();
			ps.close();
			connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// Get movie have name contains a string
	// Ex: if u want "The walking dead" just type "the walking"
	@Override
	public List<Movie> getMoviesHaveNameLikeKeyword(String keyword) {
		List<Movie> list = new ArrayList<>();
		try {
			// Query string to get data
			String queryString = "SELECT movie_id, movie_name, movie_type, director_name, names_of_actors, movie_description,"
					+ " movie_duration, movie_country, movie_image_url, movie_status FROM movies WHERE movie_name LIKE ? ORDER BY movie_id DESC;";
			// Create connection
			Connection connect = JDBCConnection.getConnection();
			PreparedStatement ps = connect.prepareStatement(queryString);
			ps.setString(1, "%" + keyword + "%");
			ResultSet rs = ps.executeQuery();
			// Iterate result set to get data
			while (rs.next()) {
				list.add(mapResultSetToMovie(rs));
			}
			rs.close();
			ps.close();
			connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// Get movie by id
	@Override
	public Movie getMovieById(int id) {
		Movie movie = null;
		try {
			// Query string to get data
			String queryString = "SELECT * FROM movies WHERE movie_id = ?";
			// Create connection
			Connection connect = JDBCConnection.getConnection();
			PreparedStatement ps = connect.prepareStatement(queryString);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			// Iterate result set to get data
			while (rs.next()) {
				movie = mapResultSetToMovie(rs);
			}
			rs.close();
			ps.close();
			connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return movie;
	}

	// Add movie to database
	@Override
	public boolean addMovie(Movie movie) {
		try {
			// Query string to get data
			String queryString = "INSERT INTO movies (movie_name, movie_type, director_name, names_of_actors, movie_description, movie_duration, movie_country, movie_image_url, movie_status)"
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
			// Create connection
			Connection connect = JDBCConnection.getConnection();
			PreparedStatement ps = connect.prepareStatement(queryString);
			ps.setString(1, movie.getName());
			ps.setString(2, movie.getType());
			ps.setString(3, movie.getDirectorName());
			ps.setString(4, movie.getActorsName());
			ps.setString(5, movie.getDescription());
			ps.setInt(6, movie.getDuration());
			ps.setString(7, movie.getCountry());
			ps.setString(8, movie.getImageUrl());
			ps.setString(9, movie.getMovieStatus().toString());
			ps.executeUpdate();
			ps.close();
			connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// Delete movie by id
	@Override
	public int deleteMovieById(int id) {
		int update = 0;
		try {
			// Query string to get data
			String queryString = "DELETE FROM movies WHERE movie_id = ?;";
			// Create connection
			Connection connect = JDBCConnection.getConnection();
			PreparedStatement ps = connect.prepareStatement(queryString);
			ps.setInt(1, id);
			update = ps.executeUpdate();
			ps.close();
			connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return update;
	}

	// Update
	@Override
	public int updateMovie(int id, Movie newMovie) {
		Connection connect = null;
		PreparedStatement ps = null;
		int update = 0;
		// Query string to get data
		String queryString = "UPDATE movies SET movie_name = ? , movie_type = ? , "
				+ "director_name = ? , names_of_actors = ? , movie_description = ? ,"
				+ "movie_duration = ? , movie_country = ? , movie_image_url = ? ,"
				+ "movie_status = ? WHERE movie_id = ?";
		try {
			connect = JDBCConnection.getConnection();
			connect.setAutoCommit(false);
			ps = connect.prepareStatement(queryString);
			ps.setString(1, newMovie.getName());
			ps.setString(2, newMovie.getType());
			ps.setString(3, newMovie.getDirectorName());
			ps.setString(4, newMovie.getActorsName());
			ps.setString(5, newMovie.getDescription());
			ps.setInt(6, newMovie.getDuration());
			ps.setString(7, newMovie.getCountry());
			ps.setString(8, newMovie.getImageUrl());
			ps.setString(9, newMovie.getMovieStatus().toString());
			ps.setInt(10, id);
			update = ps.executeUpdate();
			connect.commit();
		} catch (SQLException e) {
			try {
				connect.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			try {
				ps.close();
				connect.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return update;
	}

	// Update movie status by movie id
	@Override
	public int updateMovieStatusById(int id, MovieStatus status) {
		int update = 0;
		try {
			// Query string to get data
			String queryString = "UPDATE movies SET movie_status = ? WHERE movie_id = ?";
			// Create connection
			Connection connect = JDBCConnection.getConnection();
			PreparedStatement ps = connect.prepareStatement(queryString);
			ps.setString(1, status.toString());
			ps.setInt(2, id);
			update = ps.executeUpdate();
			ps.close();
			connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return update;
	}

	// Get movies by cinema id
	@Override
	public List<Movie> getMoviesByCinemaId(int cinemaId) {
		List<Movie> list = new ArrayList<>();
		try {
			// Query string to get data
			String queryString = "SELECT DISTINCT m.movie_id, m.movie_name, m.movie_type, m.director_name, m.names_of_actors, m.movie_description,"
					+ " m.movie_duration, m.movie_country, m.movie_image_url, m.movie_status "
					+ " FROM movies m JOIN showtimes s ON m.movie_id = s.movie_id WHERE s.cinema_id = ?";
			// Create connection
			Connection connect = JDBCConnection.getConnection();
			PreparedStatement ps = connect.prepareStatement(queryString);
			ps.setInt(1, cinemaId);
			ResultSet rs = ps.executeQuery();
			// Iterate result set to get data
			while (rs.next()) {
				list.add(mapResultSetToMovie(rs));
			}
			rs.close();
			ps.close();
			connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	private Movie mapResultSetToMovie(ResultSet rs) {
		Movie movie = null;
		try {
			int duration = rs.getInt("movie_duration");
			int id = rs.getInt("movie_id");
			String name = rs.getString("movie_name");
			String type = rs.getString("movie_type");
			String directorName = rs.getString("director_name");
			String actorsName = rs.getString("names_of_actors");
			String description = rs.getString("movie_description");
			String country = rs.getString("movie_country");
			String imageUrl = rs.getString("movie_image_url");
			MovieStatus movieStatus = MovieStatus.valueOf(rs.getString("movie_status"));
			movie = new Movie(id, name, type, directorName, actorsName, description, duration, country, imageUrl,
					movieStatus);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return movie;
	}



}
