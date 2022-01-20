package com.apps.filmtrackappv2.rest;

import com.apps.filmtrackappv2.model.ImageDTO;
import com.apps.filmtrackappv2.service.ImageService;

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
public class ImageController {

    private final ImageService imageService;
 
    public ImageController(final ImageService imageService) {
        this.imageService = imageService;
    }


    @GetMapping(value = "/api/images")
    public ResponseEntity<List<ImageDTO>> getAllImages() {
        return ResponseEntity.ok(imageService.findAll());
    }
    
    @GetMapping("/api/movies/{id}/images")
    public ResponseEntity<List<ImageDTO>> getAllImagesByMovie(@PathVariable final Long id) {
        return ResponseEntity.ok(imageService.findAllByMovie(id));
    }
    
    @GetMapping("/api/images/{id}")
    public ResponseEntity<ImageDTO> getImage(@PathVariable final String id) {
        return ResponseEntity.ok(imageService.get(id));
    }

    @PostMapping("/api/images")
    public ResponseEntity<String> createImage(@RequestBody @Valid final ImageDTO imageDTO) {
        return new ResponseEntity<>(imageService.create(imageDTO), HttpStatus.CREATED);
    }

    @PutMapping("/api/images/{id}")
    public ResponseEntity<Void> updateImage(@PathVariable final String id,
            @RequestBody @Valid final ImageDTO imageDTO) {
        imageService.update(id, imageDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/images/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable final String id) {
        imageService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
