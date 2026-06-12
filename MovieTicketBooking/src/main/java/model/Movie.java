package model;

public class Movie {
	private int id;
	private String name;
	private String type;
	private String directorName;
	private String actorsName;
	private String description;
	private int duration;
	private String country;
	private String imageUrl;
	private model.MovieStatus movieStatus;
	private String moiveTag;
	private String trailerUrl;

	public Movie(int id, String name, String type, String directorName, String actorsName, String description,
			int duration, String country, String imageUrl, model.MovieStatus movieStatus, String moiveTag, String trailerUrl) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.directorName = directorName;
		this.actorsName = actorsName;
		this.description = description;
		this.duration = duration;
		this.country = country;
		this.imageUrl = imageUrl;
		this.movieStatus = movieStatus;
		this.moiveTag = moiveTag;
		this.trailerUrl = trailerUrl;

	}
	// Constructor 9 tham số phục vụ cho Add/Edit Movie Servlet
	public Movie(String name, String type, String directorName, String actorsName, String description,
	             int duration, String country, String imageUrl, model.MovieStatus movieStatus) {
		this.name = name;
		this.type = type;
		this.directorName = directorName;
		this.actorsName = actorsName;
		this.description = description;
		this.duration = duration;
		this.country = country;
		this.imageUrl = imageUrl;
		this.movieStatus = movieStatus;
	}
	// Constructor 10 tham số (đầy đủ chức năng gán dữ liệu)
	public Movie(int id, String name, String type, String directorName, String actorsName, String description,
	             int duration, String country, String imageUrl, model.MovieStatus movieStatus) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.directorName = directorName;
		this.actorsName = actorsName;
		this.description = description;
		this.duration = duration;
		this.country = country;
		this.imageUrl = imageUrl;
		this.movieStatus = movieStatus;
	}
	// Constructor without movie id
	public Movie(String name, String type, String directorName, String actorsName, String description,
			int duration, String country, String imageUrl, model.MovieStatus movieStatus, String moiveTag, String trailerUrl) {
		this.name = name;
		this.type = type;
		this.directorName = directorName;
		this.actorsName = actorsName;
		this.description = description;
		this.duration = duration;
		this.country = country;
		this.imageUrl = imageUrl;
		this.movieStatus = movieStatus;
		this.moiveTag = moiveTag;
		this.trailerUrl = trailerUrl;

	}
	
	// Constructor for manage ticket by admin
	public Movie(int id, String name) {
		this.id = id;
		this.name = name;
	}

	// Getter and Setter
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDirectorName() {
		return directorName;
	}

	public void setDirectorName(String directorName) {
		this.directorName = directorName;
	}

	public String getActorsName() {
		return actorsName;
	}

	public void setActorsName(String actorsName) {
		this.actorsName = actorsName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public model.MovieStatus getMovieStatus() {
		return movieStatus;
	}

	public void setMovieStatus(model.MovieStatus movieStatus) {
		this.movieStatus = movieStatus;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getMovieTag() {
		return moiveTag;
	}

	public void setMovieTag(String moiveTag) {
		this.moiveTag = moiveTag;
	}

	public String getTrailerUrl(){ return trailerUrl;}
	public void setTrailerUrl(String trailerUrl){this.trailerUrl= trailerUrl;}
}