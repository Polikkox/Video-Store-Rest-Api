package com.codecool.VideoStoreRestApi.services;

import com.codecool.VideoStoreRestApi.model.Genre;
import com.codecool.VideoStoreRestApi.model.Movie;
import com.codecool.VideoStoreRestApi.repositories.GenreRepository;
import com.codecool.VideoStoreRestApi.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class GenreService {

    private GenreRepository genreRepository;
    private MovieRepository movieRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository, MovieRepository movieRepository){
        this.genreRepository = genreRepository;
        this.movieRepository = movieRepository;
    }


    public Collection<Genre> getAll() {
        return genreRepository.getAll();
    }

    public Genre getById(Long id) {
        return genreRepository.findOne(id);
    }

    public void updateGenre(int id, String name, String description, Integer movieId) {
        Genre genre = getById(id);
        if (name != null){
            genre.setName(name);
        }
        if (description != null){
            genre.setDescription(description);
        }
        if (movieId != null){
            Movie movie = moviesRepository.getById(movieId);
            genre.addMovies(movie);
        }
        genreRepository.save(genre);
    }

    public void createGenre(String name, String description) {
        Genre genre = new Genre();
        genre.setName(name);
        genre.setDescription(description);
        genreRepository.save(genre);
    }

    public Collection<Movie> getGenreMovies(int id) {
        return genreRepository.getById(id).getMovies();
    }
}
