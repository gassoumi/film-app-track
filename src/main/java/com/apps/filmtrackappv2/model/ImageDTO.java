package com.apps.filmtrackappv2.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImageDTO {

	@JsonProperty("file_path")
	@NotNull
	private String filePath;

	@NotNull
	@Pattern(regexp = "BACKDROP|POSTER", message = "msut be BACKDROP or POSTER")
	@JsonProperty("image_type")
	private String imageType;

	@NotNull
	@JsonProperty("aspect_ratio")
	private Float aspectRatio;

	@NotNull()
	private Integer height;

	@NotNull
	private Integer width;

	@NotNull
	private Long movie;

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
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

	public void setMovie(Long movie) {
		this.movie = movie;
	}

	public Long getMovie() {
		return movie;
	}
}
