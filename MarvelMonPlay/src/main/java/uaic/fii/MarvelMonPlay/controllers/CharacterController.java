package uaic.fii.MarvelMonPlay.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uaic.fii.MarvelMonPlay.exceptions.ResourceNotFoundException;
import uaic.fii.MarvelMonPlay.externalApi.MarvelApi;
import uaic.fii.MarvelMonPlay.externalApi.PokeApi;
import uaic.fii.MarvelMonPlay.models.characters.Character;
import uaic.fii.MarvelMonPlay.models.characters.Marvel;
import uaic.fii.MarvelMonPlay.models.characters.Pokemon;
import uaic.fii.MarvelMonPlay.services.CharacterService;

import java.util.List;

@RestController
@RequestMapping("/characters")
public class CharacterController {
    private final CharacterService characterService;
    private final MarvelApi marvelApi;
    private final PokeApi pokeApi;

    public CharacterController(CharacterService characterService, MarvelApi marvelApi, PokeApi pokeApi) {
        this.characterService = characterService;
        this.marvelApi = marvelApi;
        this.pokeApi = pokeApi;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/marvel")
    public List<Character> getMarvelCharacters(){
        return characterService.getMarvelCharacters();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/pokemon")
    public List<Character> getPokemonCharacters(){
        return characterService.getPokemonCharacters();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/marvel/{name}")
    public Marvel getMarvelCharacter(@PathVariable("name") String nameStartsWith) throws ResourceNotFoundException {
        return marvelApi.getMarvelCharacter(nameStartsWith);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/pokemon/{name}")
    public Pokemon getPokemonByName(@PathVariable("name") String name) throws ResourceNotFoundException {
        return pokeApi.getPokemonByName(name);
    }
}
