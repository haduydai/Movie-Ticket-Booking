package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "movies")
@SQLDelete(sql = "UPDATE movies SET deleted_at = NOW() WHERE movie_id = ?")
@SQLRestriction("deleted_at IS NULL")
@Getter
@Setter
public class Movie extends AbsBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "movie_id")
	private int id;

	@Column(name = "movie_name", nullable = false)
	private String name;

	@Column(name = "movie_type")
	private String type;

	@Column(name = "director_name")
	private String directorName;

	@Column(name = "names_of_actors")
	private String actorsName;

	@Column(name = "movie_description", columnDefinition = "TEXT")
	private String description;

	@Column(name = "movie_duration", nullable = false)
	private int duration;

	@Column(name = "movie_country")
	private String country;

	@Column(name = "movie_image_url")
	private String imageUrl;

	@Enumerated(EnumType.STRING)
	@Column(name = "movie_status")
	private model.MovieStatus movieStatus;

	@Column(name = "movie_tag")
	private String moiveTag;

	@Column(name = "trailer_url")
	private String trailerUrl;

	// trường để  upload ảnh poster lên Cloudinary
	@Column(name = "movie_image_public_id")
	private String imagePublicId;

	// Constructor không tham số bắt buộc phải có cho Hibernate hoạt động
	public Movie() {}

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
}