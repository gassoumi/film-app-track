package com.apps.filmtrackappv2.service.TMDB;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import com.apps.filmtrackappv2.domain.Genre;
import com.apps.filmtrackappv2.domain.Movie;
import com.apps.filmtrackappv2.domain.Image;
import com.apps.filmtrackappv2.domain.ImageType;
import com.apps.filmtrackappv2.domain.Video;
import com.apps.filmtrackappv2.repos.GenreRepository;
import com.apps.filmtrackappv2.repos.ImageRepository;
import com.apps.filmtrackappv2.repos.MovieRepository;
import com.apps.filmtrackappv2.repos.VideoRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.net.URI;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

@Component
public class ScheduledTasks {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private VideoRepository videoRepository;

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private static final String API_KEY = "69d4e3dcf905ea9cd265580d0b20cdbe";

    private HttpClient getHttpClient() {
        HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(10)).build();
        return httpClient;
    }

    private HttpRequest getHttpRequest(String url) {
        return HttpRequest.newBuilder().GET().uri(URI.create(url))
                .setHeader("User-Agent", "Java 11 HttpClient").setHeader("content-type", "application/json").build();
    }

    // should start after getAndSaveGenres method
    @Scheduled(fixedDelay = 10 * 1000 * 60 * 60, initialDelay = 1000 * 60 * 2)
    public void getSomeMovies() {
        log.info("Send a http request to get some movies at {}", dateFormat.format(new Date()));
        var httpClient = getHttpClient();
        int pageNumber = 3;
        String url = String.format("https://api.themoviedb.org/3/discover/movie?api_key=%s&language=en-US&page=%d",
                API_KEY, pageNumber);

        HttpRequest request = getHttpRequest(url);

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            // print response headers
            // HttpHeaders headers = response.headers();
            // headers.map().forEach((k, v) -> System.out.println(k + ":" + v));
            int statusCode = response.statusCode();
            if (statusCode == 200) {
                ObjectMapper mapper = new ObjectMapper();

                String body = response.body();
                MovieResult movieResult = mapper.readValue(body, MovieResult.class);
                // pretty print
                // String prettyMovie =
                // mapper.writerWithDefaultPrettyPrinter().writeValueAsString(movieResult);
                // System.out.println(prettyMovie);
                for (var movie : movieResult.getResults()) {
                    try {
                        if (movieRepository.findById(movie.getId()).isEmpty()) {
                            final Movie movieToSave = new Movie(movie.getId(), movie.getTitle(), movie.getOverview(),
                                    LocalDate.parse(movie.getReleaseDate()), movie.getVoteCount(),
                                    movie.getVoteAverage(), movie.getAdult(), movie.getPosterPath(),
                                    movie.getBackdropPath());
                            if (movie.getGenres() != null) {
                                final List<Genre> genres = genreRepository.findAllById(movie.getGenres());
                                if (genres.size() != movie.getGenres().size()) {
                                    throw new Exception("one of genres not found");
                                }
                                movieToSave.setGenres(genres.stream().collect(Collectors.toSet()));
                            }
                            Movie movieSaved = movieRepository.save(movieToSave);
                            log.info("Movie was created with id {}", movieSaved.getId());
                            getImage(movieSaved, httpClient);
                            getVideos(movieToSave, httpClient);
                        } else {
                            log.info("Movie {} is already exist", movie.getId());
                        }
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                }
                log.info("finish adding new movies");
            } else {
                log.error("Error while sending a http request with status code {}", statusCode);
            }
        } catch (IOException | InterruptedException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }


    // run only once so maximize the fixedDelay
    //@Scheduled(initialDelay = 1000 * 30, fixedDelay = Long.MAX_VALUE)
    public void getAndSaveGenres() {
        log.info("Send a http request to get all genres of movies at {}", dateFormat.format(new Date()));
        var httpClient = getHttpClient();
        String url = String.format("https://api.themoviedb.org/3/genre/movie/list?api_key=%s&language=en-US", API_KEY);

        HttpRequest request = getHttpRequest(url);

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();
            if (statusCode == 200) {
                ObjectMapper mapper = new ObjectMapper();

                String body = response.body();
                GenreResult genreResult = mapper.readValue(body, GenreResult.class);

                for (var genre : genreResult.getGenres()) {
                    try {
                        if (genreRepository.findById(genre.getId()).isEmpty()) {
                            Long id = genreRepository.save(genre).getId();
                            log.info("Genre {} was created with id {}", genre.getName(), id);
                        } else {
                            log.info("genre {} is already exist", genre.getName());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error(e.getMessage());
                    }
                }
            } else {
                log.error("Error while sending a http request with status code {}", statusCode);
            }
        } catch (IOException | InterruptedException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    private void getVideos(Movie movie, HttpClient httpClient) {
        String url = String.format("https://api.themoviedb.org/3/movie/%d/videos?api_key=%s",
                movie.getId(), API_KEY);

        HttpRequest request = getHttpRequest(url);

        HttpResponse<String> response;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();
            if (statusCode == 200) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                String body = response.body();
                VideosResult videosResult = mapper.readValue(body, VideosResult.class);
                //"published_at": "2015-02-26T03:19:25.000Z",
                // DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                for (var video : videosResult.getResults()) {
                    DateTime dateTime = DateTime.parse(video.getPublishedAt());
                    LocalDateTime publishedDate = LocalDateTime.parse(dateTime.toLocalDateTime().toString());
                    log.info(publishedDate.toString());
                    Video videoToSave = new Video(video.getId(), video.getName(), video.getSite(),
                            video.getOfficial(), video.getKey(), publishedDate, movie);
                    videoRepository.save(videoToSave);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void getImage(Movie movie, HttpClient httpClient) {
        String url = String.format("https://api.themoviedb.org/3/movie/%d/images?api_key=%s",
                movie.getId(), API_KEY);

        HttpRequest request = getHttpRequest(url);

        HttpResponse<String> response;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();
            if (statusCode == 200) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                String body = response.body();
                ImagesResult imagesResult = mapper.readValue(body, ImagesResult.class);
                for (var image : imagesResult.getBackdrops()) {
                    final Image imageToSave = new Image(image.getFilePath(), image.getAspectRatio(), image.getHeight(),
                            image.getWidth(), movie, ImageType.BACKDROP);
                    imageRepository.save(imageToSave);
                }
                for (var image : imagesResult.getPosters()) {
                    final Image imageToSave = new Image(image.getFilePath(), image.getAspectRatio(), image.getHeight(),
                            image.getWidth(), movie, ImageType.POSTER);
                    imageRepository.save(imageToSave);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            // e.printStackTrace();
        }
    }

    static class GenreResult {
        List<Genre> genres;

        public List<Genre> getGenres() {
            return genres;
        }

        public void setGenres(List<Genre> genres) {
            this.genres = genres;
        }
    }

}