package com.apps.filmtrackappv2.domain;

import java.time.OffsetDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Entity
public class Image {

	@Id
	@Column(nullable = false, updatable = false)
	private String filePath;

	@Column(nullable = false)
	private Float aspectRatio;

	@Column(nullable = false)
	private Integer height;

	@Column(nullable = false)
	private Integer width;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "movie_id", nullable = false)
	private Movie movie;

	@Enumerated(EnumType.STRING)
	@Column(length = 10, nullable = false)
	private ImageType imageType;

	@Column(nullable = false, updatable = false)
	private OffsetDateTime dateCreated;

	@Column(nullable = false)
	private OffsetDateTime lastUpdated;

	public Image() {
	}

	public Image(String filePath, float aspectRatio, Integer height, Integer width, Movie movie, ImageType imageType) {
		this.filePath = filePath;
		this.aspectRatio = aspectRatio;
		this.height = height;
		this.width = width;
		this.movie = movie;
		this.imageType = imageType;
	}

	@PrePersist
	public void prePersist() {
		dateCreated = OffsetDateTime.now();
		lastUpdated = dateCreated;
	}

	@PreUpdate
	public void preUpdate() {
		lastUpdated = OffsetDateTime.now();
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public ImageType getImageType() {
		return imageType;
	}

	public void setImageType(ImageType imageType) {
		this.imageType = imageType;
	}

	public Float getAspectRatio() {
		return aspectRatio;
	}

	public void setAspectRatio(Float aspectRatio) {
		this.aspectRatio = aspectRatio;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(final Integer height) {
		this.height = height;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(final Integer width) {
		this.width = width;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(final Movie movie) {
		this.movie = movie;
	}

	public OffsetDateTime getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(final OffsetDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}

	public OffsetDateTime getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(final OffsetDateTime lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

}
