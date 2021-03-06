package com.codecool.VideoStoreRestApi.services;

import com.codecool.VideoStoreRestApi.Exceptions.MovieNotFoundException;
import com.codecool.VideoStoreRestApi.model.Director;
import com.codecool.VideoStoreRestApi.model.Movie;
import com.codecool.VideoStoreRestApi.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.sql.Date;
import java.util.Collection;
import java.util.List;

@Component
public class MovieService {

    private MovieRepository movieRepository;
    private DirectorService directorService;

    @Autowired
    public MovieService(MovieRepository movieRepository, DirectorService directorService) {
        this.movieRepository = movieRepository;
        this.directorService = directorService;
    }

    public Collection<Movie> getAll() {
        Collection<Movie> movieList = this.movieRepository.findMovieByArchivedIsFalse();
        if(movieList.isEmpty()) throw new MovieNotFoundException("no movies were found");
        return movieList;
    }

    public void createMovie(String title, String firstNameDirector, String lastNameDirector, String yearAsString, int length) {
        Date year = Date.valueOf(yearAsString + "-01-01");
        Movie movie = new Movie();
        Director director;

        if(checkIfDirectorExist(firstNameDirector, lastNameDirector)){
            director = this.directorService.getDirector(firstNameDirector, lastNameDirector);
        }
        else {
            director = setDirector(firstNameDirector, lastNameDirector);
        }
        movie.setTitle(title);
        movie.setDirectors(director);
        movie.setYear(year);
        movie.setLength(length);

       this.movieRepository.save(movie);
    }

    public void updateMovie(Long id, String title, Integer idDirector, String yearAsString, Integer length){
        Movie movie = this.movieRepository.findOne(id);
        if(movie == null) throw new MovieNotFoundException("no movie was found");

        if(title != null){
            movie.setTitle(title);
        }
        if(idDirector != null){
            movie.setIdMovie(idDirector);
        }
        if(yearAsString != null){
            Date year = Date.valueOf(yearAsString + "-01-01");
            movie.setYear(year);
        }
        if(length  != null){
            movie.setLength(length);
        }
        this.movieRepository.save(movie);
    }

    public void deleteMovie(Long id) {
       Movie movie = this.movieRepository.findOne(id);
       if(movie == null) throw new MovieNotFoundException("no movie was found");
       movie.setArchived(true);
       this.movieRepository.save(movie);
    }

    public void deleteAllMovies() {
        List<Movie> listMovies = this.movieRepository.findAll();
        if(listMovies.isEmpty()) throw new MovieNotFoundException("no movies were found");
        for (Movie movie : listMovies) {
            movie.setArchived(true);
            this.movieRepository.save(movie);
        }
    }

    public Movie getById(Long id) {
        Movie movie = this.movieRepository.getMovieByIdMovieAndArchivedIsFalse(id);
        if(movie == null) throw new MovieNotFoundException("no movie was found");
        return movie;
    }

    private boolean checkIfDirectorExist(String firstNameDirector, String lastNameDirector){
        return this.directorService.isDirectorExist(firstNameDirector, lastNameDirector);
    }

    private Director setDirector(String firstNameDirector, String lastNameDirector){
        Director director = new Director();
        director.setFirstName(firstNameDirector);
        director.setLastName(lastNameDirector);
        this.directorService.saveDirector(director);
        return director;
    }
}
