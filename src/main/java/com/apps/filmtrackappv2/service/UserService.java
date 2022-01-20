package com.apps.filmtrackappv2.service;

import com.apps.filmtrackappv2.domain.Movie;
import com.apps.filmtrackappv2.domain.User;
import com.apps.filmtrackappv2.model.UserDTO;
import com.apps.filmtrackappv2.repos.MovieRepository;
import com.apps.filmtrackappv2.repos.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    public UserService(final UserRepository userRepository, final MovieRepository movieRepository) {
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(user -> mapToDTO(user, new UserDTO()))
                .collect(Collectors.toList());
    }

    public UserDTO get(final Long id) {
        return userRepository.findById(id)
                .map(user -> mapToDTO(user, new UserDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final UserDTO userDTO) {
        final User user = new User();
        mapToEntity(userDTO, user);
        return userRepository.save(user).getId();
    }

    public void update(final Long id, final UserDTO userDTO) {
        final User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }

    public void delete(final Long id) {
        userRepository.deleteById(id);
    }

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setFavoriteMovies(user.getFavoriteMovieMovies() == null ? null : user.getFavoriteMovieMovies().stream()
                .map(movie -> movie.getId())
                .collect(Collectors.toList()));
        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        if (userDTO.getFavoriteMovies() != null) {
            final List<Movie> favoriteMovies = movieRepository.findAllById(userDTO.getFavoriteMovies());
            if (favoriteMovies.size() != userDTO.getFavoriteMovies().size()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "one of favoriteMovies not found");
            }
            user.setFavoriteMovieMovies(favoriteMovies.stream().collect(Collectors.toSet()));
        }
        return user;
    }

}
