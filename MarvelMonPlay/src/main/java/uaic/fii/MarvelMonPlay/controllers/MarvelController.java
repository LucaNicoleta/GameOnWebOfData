package uaic.fii.MarvelMonPlay.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uaic.fii.MarvelMonPlay.exceptions.ResourceNotFoundException;
import uaic.fii.MarvelMonPlay.externalApi.MarvelApi;
import uaic.fii.MarvelMonPlay.models.characters.Marvel;
import uaic.fii.MarvelMonPlay.services.MarvelService;

import java.util.List;

@RestController
@RequestMapping("/marvel")
public class MarvelController {
    private final MarvelService marvelService;
    private final MarvelApi marvelApi;

    public MarvelController(MarvelService marvelService, MarvelApi marvelApi){
        this.marvelService = marvelService;
        this.marvelApi = marvelApi;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Marvel> getMarvelCharacters(){
        return marvelService.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{name}")
    public Marvel getMarvelCharacter(@PathVariable("name") String nameStartsWith) throws ResourceNotFoundException {
        return marvelApi.getMarvelCharacter(nameStartsWith);
    }
}
