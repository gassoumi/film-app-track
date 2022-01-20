package com.apps.filmtrackappv2;

import com.apps.filmtrackappv2.domain.Movie;
import com.apps.filmtrackappv2.repos.MovieRepository;
import com.apps.filmtrackappv2.service.mapper.MovieMapperRequest;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.mapstruct.factory.Mappers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
class FilmTrackAppv2ApplicationTests {

    @Autowired
    private MovieRepository movieRepository;

    private MovieMapperRequest movieMapper = Mappers.getMapper(MovieMapperRequest.class);

    @Test
    void contextLoads() {
    }

    @Test
    void testDateVideo() throws ParseException {
        // will give us the current time and date
        DateTimeFormatter df1 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        LocalDateTime current = LocalDateTime.now();
        System.out.println("current date and time : " + current);
        String publishedDate = "2015-02-26T03:19:25.000Z";
        DateTime dateTime = DateTime.parse(publishedDate);
        System.out.println(dateTime);
        System.out.println(dateTime.toLocalDate());
        System.out.println(dateTime.toLocalDateTime());
        System.out.println(dateTime.getZone());
        //var myDate = df1.parse(publishedDate);

        //System.out.println(myDate);
    }

    @Test
    void MovieMapper() {
        Movie movie = movieRepository.findById((long) 557).get();
        System.out.println(movie.getTitle());

        var result = movieMapper.toDto(movie);

        assertThat(result.getTitle()).isEqualTo("Spider-Man");
        //assertThat(movie.getTitle()).isEqualTo("Spider-Mane");
        //assertThat(movie.getTitle()).isEqualTo("Spider-Man");
    }

}
