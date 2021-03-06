package com.codecool.VideoStoreRestApi.controllers;

import com.codecool.VideoStoreRestApi.model.Director;
import com.codecool.VideoStoreRestApi.model.Movie;
import com.codecool.VideoStoreRestApi.services.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/store/directors")
public class DirectorController {

    private DirectorService directorService;

    @Autowired
    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Collection<Director> getAllDirectors(){
        return directorService.getAllDirectors();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Director getDirector(@PathVariable("id") Long id_director){
        return directorService.getDirectorById(id_director);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteDirectorById(@PathVariable("id") Long id_director){
        directorService.deleteDirectorById(id_director);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteAllDirectors(){
        directorService.deleteAllDirectors();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void updateDirectorById(@PathVariable("id") Long id_director,
                                   @RequestParam(value = "first_name", required = false) String first_name,
                                   @RequestParam("last_name") String last_name){
        directorService.updateDirector(id_director, first_name, last_name);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void postDirector(@RequestParam("first_name") String first_name,
                             @RequestParam("last_name") String last_name){
        directorService.createDirector(first_name, last_name);
    }

    @RequestMapping(value = "/{id}/movies", method = RequestMethod.GET)
    public Collection<Movie> getDirectorMovies(@PathVariable("id") Long idDirector){
        return directorService.getDirectorMovies(idDirector);
    }
}