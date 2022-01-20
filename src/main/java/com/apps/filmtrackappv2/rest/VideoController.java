package com.apps.filmtrackappv2.rest;

import com.apps.filmtrackappv2.model.VideoDTO;
import com.apps.filmtrackappv2.service.VideoService;

import java.util.List;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class VideoController {

    private final VideoService videoService;

    public VideoController(final VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping("/api/videos")
    public ResponseEntity<List<VideoDTO>> getAllVideos() {
        return ResponseEntity.ok(videoService.findAll());
    }

    @GetMapping("/api/videos/{id}")
    public ResponseEntity<VideoDTO> getVideo(@PathVariable final Long id) {
        return ResponseEntity.ok(videoService.get(id));
    }

    @GetMapping("/api/movies/{id}/videos")
    public ResponseEntity<List<VideoDTO>> getAllVideosByMovie(@PathVariable final Long id) {
        return ResponseEntity.ok(videoService.findAllByMovie(id));
    }

    @PostMapping("/api/videos")
    public ResponseEntity<String> createVideo(@RequestBody @Valid final VideoDTO videoDTO) {
        return new ResponseEntity<>(videoService.create(videoDTO), HttpStatus.CREATED);
    }

    @PutMapping("/api/videos/{id}")
    public ResponseEntity<Void> updateVideo(@PathVariable final Long id,
                                            @RequestBody @Valid final VideoDTO videoDTO) {
        videoService.update(id, videoDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/videos/{id}")
    public ResponseEntity<Void> deleteVideo(@PathVariable final Long id) {
        videoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
