package com.apps.filmtrackappv2.service;

import com.apps.filmtrackappv2.domain.Image;
import com.apps.filmtrackappv2.domain.ImageType;
import com.apps.filmtrackappv2.domain.Movie;
import com.apps.filmtrackappv2.model.ImageDTO;
import com.apps.filmtrackappv2.repos.ImageRepository;
import com.apps.filmtrackappv2.repos.MovieRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ImageService {

	private final ImageRepository imageRepository;
	private final MovieRepository movieRepository;

	public ImageService(final ImageRepository imageRepository, final MovieRepository movieRepository) {
		this.imageRepository = imageRepository;
		this.movieRepository = movieRepository;
	}

	public List<ImageDTO> findAll() {
		return imageRepository.findAll().stream().map(image -> mapToDTO(image, new ImageDTO()))
				.collect(Collectors.toList());
	}

	public List<ImageDTO> findAllByMovie(Long movieId) {
		final Movie movie = movieRepository.findById(movieId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "movie not found"));
		return imageRepository.findAllByMovie(movie).stream().map(image -> mapToDTO(image, new ImageDTO()))
				.collect(Collectors.toList());
	}

	public ImageDTO get(final String id) {
		return imageRepository.findById(id).map(image -> mapToDTO(image, new ImageDTO()))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	public String create(final ImageDTO imageDTO) {
		final Image image = new Image();
		mapToEntity(imageDTO, image);
		return imageRepository.save(image).getFilePath();
	}

	public void update(final String id, final ImageDTO imageDTO) {
		final Image image = imageRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		mapToEntity(imageDTO, image);
		imageRepository.save(image);
	}

	public void delete(final String id) {
		imageRepository.deleteById(id);
	}

	private ImageDTO mapToDTO(final Image image, final ImageDTO imageDTO) {
		imageDTO.setFilePath(image.getFilePath());
		imageDTO.setAspectRatio(image.getAspectRatio());
		imageDTO.setHeight(image.getHeight());
		imageDTO.setWidth(image.getWidth());
		imageDTO.setImageType(image.getImageType() == ImageType.BACKDROP ? "BACKDROP" : "POSTER");
		imageDTO.setMovie(image.getMovie() == null ? null : image.getMovie().getId());
		return imageDTO;
	}

	private Image mapToEntity(final ImageDTO imageDTO, final Image image) {
		image.setFilePath(imageDTO.getFilePath());
		image.setAspectRatio(imageDTO.getAspectRatio());
		image.setHeight(imageDTO.getHeight());
		image.setWidth(imageDTO.getWidth());
		image.setImageType(imageDTO.getImageType() == "BACKDROP" ? ImageType.BACKDROP : ImageType.POSTER);
		if (imageDTO.getMovie() != null
				&& (image.getMovie() == null || !image.getMovie().getId().equals(imageDTO.getMovie()))) {
			final Movie movie = movieRepository.findById(imageDTO.getMovie())
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "movie not found"));
			image.setMovie(movie);
		}
		return image;
	}

}
