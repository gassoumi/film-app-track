package com.apps.filmtrackappv2.service.TMDB;

public class ImagesResult {
	private int id;
	private Image[] backdrops;
	private Image[] posters;
	private Image[] logos;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Image[] getLogos() {
		return logos;
	}

	public void setLogos(Image[] logos) {
		this.logos = logos;
	}

	public Image[] getBackdrops() {
		return backdrops;
	}

	public void setBackdrops(Image[] backdrops) {
		this.backdrops = backdrops;
	}

	public Image[] getPosters() {
		return posters;
	}

	public void setPosters(Image[] posters) {
		this.posters = posters;
	}

}
