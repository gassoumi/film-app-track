package com.apps.filmtrackappv2.service;

import com.apps.filmtrackappv2.domain.Movie;
import com.apps.filmtrackappv2.domain.Video;
import com.apps.filmtrackappv2.model.VideoDTO;
import com.apps.filmtrackappv2.repos.MovieRepository;
import com.apps.filmtrackappv2.repos.VideoRepository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class VideoService {

    private final VideoRepository videoRepository;
    private final MovieRepository movieRepository;

    public VideoService(final VideoRepository videoRepository,
                        final MovieRepository movieRepository) {
        this.videoRepository = videoRepository;
        this.movieRepository = movieRepository;
    }

    public List<VideoDTO> findAll() {
        return videoRepository.findAll()
                .stream()
                .map(video -> mapToDTO(video, new VideoDTO()))
                .collect(Collectors.toList());
    }

    public List<VideoDTO> findAllByMovie(Long id) {
        final Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return videoRepository.findAllByMovie(movie)
                .stream()
                .map(video -> mapToDTO(video, new VideoDTO()))
                .collect(Collectors.toList());
    }

    public VideoDTO get(final Long id) {
        return videoRepository.findById(id)
                .map(video -> mapToDTO(video, new VideoDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public String create(final VideoDTO videoDTO) {
        final Video video = new Video();
        mapToEntity(videoDTO, video);
        return videoRepository.save(video).getId();
    }

    public void update(final Long id, final VideoDTO videoDTO) {
        final Video video = videoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(videoDTO, video);
        videoRepository.save(video);
    }

    public void delete(final Long id) {
        videoRepository.deleteById(id);
    }

    private VideoDTO mapToDTO(final Video video, final VideoDTO videoDTO) {
        videoDTO.setId(video.getId());
        videoDTO.setName(video.getName());
        videoDTO.setSite(video.getSite());
        videoDTO.setOfficial(video.getOfficial());
        videoDTO.setKey(video.getKey());
        videoDTO.setPublishedAt(video.getPublishedAt());
        videoDTO.setMovie(video.getMovie() == null ? null : video.getMovie().getId());
        return videoDTO;
    }

    private Video mapToEntity(final VideoDTO videoDTO, final Video video) {
        video.setName(videoDTO.getName());
        video.setSite(videoDTO.getSite());
        video.setOfficial(videoDTO.getOfficial());
        video.setKey(videoDTO.getKey());
        video.setPublishedAt(videoDTO.getPublishedAt());
        if (videoDTO.getMovie() != null && (video.getMovie() == null || !video.getMovie().getId().equals(videoDTO.getMovie()))) {
            final Movie movie = movieRepository.findById(videoDTO.getMovie())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "movie not found"));
            video.setMovie(movie);
        }
        return video;
    }

}
