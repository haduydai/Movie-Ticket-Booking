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
			String queryString = "SELECT * FROM movies";
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

	// Get movie have name contains a string
	// Ex: if u want "The walking dead" just type "the walking"
	@Override
	public List<Movie> getMoviesHaveNameLikeString(String str) {
		List<Movie> list = new ArrayList<>();
		try {
			// Query string to get data
			String queryString = "SELECT * FROM movies WHERE LOWER(title) LIKE ?;";
			// Create connection
			Connection connect = JDBCConnection.getConnection();
			PreparedStatement ps = connect.prepareStatement(queryString);
			ps.setString(1, str);
			ResultSet rs = ps.executeQuery(queryString);
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
		int update = 0;
		// Query string to get data
		String queryString = "UPDATE movies SET movie_name = ? , movie_type = ? , "
				+ "director_name = ? , names_of_actors = ? , movie_description = ? ,"
				+ "movie_duration = ? , movie_country = ? , movie_image_url = ? ,"
				+ "movie_status = ? WHERE movie_id = ?";
		try {
			// Create connection
			Connection connect = JDBCConnection.getConnection();
			PreparedStatement ps = connect.prepareStatement(queryString);
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
			ps.close();
			connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
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
	
	@Override
	public List<Movie> getMoviesByCinemaId(int cinemaId) {
	    List<Movie> list = new ArrayList<>();
	    // Join bảng movies và cinema_movies để tìm phim
	    String sql = "SELECT m.* FROM movies m " +
	                 "JOIN cinema_movies cm ON m.movie_id = cm.movie_id " +
	                 "WHERE cm.cinema_id = ? AND m.movie_status = 'NOW_SHOWING'";
	                 // Chỉ lấy phim ĐANG CHIẾU (bỏ dòng này nếu muốn hiện cả phim sắp chiếu)
	    try {
	        Connection connect = JDBCConnection.getConnection();
	        PreparedStatement ps = connect.prepareStatement(sql);
	        ps.setInt(1, cinemaId);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	             // --- COPY ĐOẠN CODE TẠO ĐỐI TƯỢNG MOVIE TỪ HÀM getAllMovies() ---
	             int id = rs.getInt("movie_id");
	             String name = rs.getString("movie_name");
	             String type = rs.getString("movie_type");
	             String director = rs.getString("director_name");
	             String actors = rs.getString("names_of_actors");
	             String desc = rs.getString("movie_description");
	             int duration = rs.getInt("movie_duration");
	             String country = rs.getString("movie_country");
	             String img = rs.getString("movie_image_url");

	             String statusStr = rs.getString("movie_status");
	             MovieStatus status = MovieStatus.COMING_SOON;
	             if(statusStr != null) status = MovieStatus.valueOf(statusStr);

	             list.add(new Movie(id, name, type, director, actors, desc, duration, country, img, status));
	             // ----------------------------------------------------------------
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

	// tìm kiếm phim theo thể loại
	@Override
	public List<Movie> getMoviesByType(String type) {
		List<Movie> list = new ArrayList<>();
		String sql = "SELECT movie_name, movie_type, director_name, names_of_actors, movie_description, movie_duration, movie_country, movie_image_url, movie_status"
				+ "FROM movies WHERE movie_type = ? ORDER BY movie_id DESC" ;
		try {
			Connection connect = JDBCConnection.getConnection();
			PreparedStatement ps = connect.prepareStatement(sql);
			ps.setString(1, type);
			ResultSet rs = ps.executeQuery();
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

}
