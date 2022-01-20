package com.apps.filmtrackappv2.service.TMDB;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Image {

	@JsonProperty("file_path")
	private String filePath;

	@JsonProperty("vote_average")
	private int voteAverage;

	@JsonProperty("vote_count")
	private int voteCount;

	private int width;

	private int height;

	@JsonProperty("aspect_ratio")
	private float aspectRatio;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public int getVoteAverage() {
		return voteAverage;
	}

	public void setVoteAverage(int voteAverage) {
		this.voteAverage = voteAverage;
	}

	public int getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(int voteCount) {
		this.voteCount = voteCount;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getAspectRatio() {
		return aspectRatio;
	}

	public void setAspectRatio(float aspectRatio) {
		this.aspectRatio = aspectRatio;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Image [filePath=");
		builder.append(filePath);
		builder.append(", voteAverage=");
		builder.append(voteAverage);
		builder.append(", voteCount=");
		builder.append(voteCount);
		builder.append(", width=");
		builder.append(width);
		builder.append(", height=");
		builder.append(height);
		builder.append(", aspectRatio=");
		builder.append(aspectRatio);
		builder.append("]");
		return builder.toString();
	}	

}

