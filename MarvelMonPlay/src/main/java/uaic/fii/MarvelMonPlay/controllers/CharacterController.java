package uaic.fii.MarvelMonPlay.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import uaic.fii.MarvelMonPlay.exceptions.ResourceNotFoundException;
import uaic.fii.MarvelMonPlay.externalApi.MarvelApi;
import uaic.fii.MarvelMonPlay.models.Character;
import uaic.fii.MarvelMonPlay.models.Marvel;
import uaic.fii.MarvelMonPlay.services.CharacterService;

import java.util.List;

@RestController
@RequestMapping("/characters")
public class CharacterController {
    private final CharacterService characterService;
    private final MarvelApi marvelApi;

    public CharacterController(CharacterService characterService, MarvelApi marvelApi) {
        this.characterService = characterService;
        this.marvelApi = marvelApi;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping("/marvel")
    public List<Character> getMarvelCharacters(){
        return characterService.getMarvelCharacters();
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping("/pokemon")
    public List<Character> getPokemonCharacters(){
        return characterService.getPokemonCharacters();
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping("/marvel/name")
    public Marvel getMarvelCharacter(@RequestParam String nameStartsWith) throws ResourceNotFoundException {
        return marvelApi.getMarvelCharacter(nameStartsWith);
    }
}
