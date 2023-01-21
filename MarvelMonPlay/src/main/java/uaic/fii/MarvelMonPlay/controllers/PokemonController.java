package uaic.fii.MarvelMonPlay.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uaic.fii.MarvelMonPlay.exceptions.ResourceNotFoundException;
import uaic.fii.MarvelMonPlay.externalApi.PokeApi;
import uaic.fii.MarvelMonPlay.models.characters.Pokemon;
import uaic.fii.MarvelMonPlay.services.PokemonService;

import java.util.List;

@RestController
@RequestMapping("/pokemon")
public class PokemonController {
    private final PokemonService pokemonService;
    private final PokeApi pokeApi;

    public PokemonController(PokemonService pokemonService, PokeApi pokeApi){
        this.pokemonService = pokemonService;
        this.pokeApi = pokeApi;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Pokemon> findAll(){
        return pokemonService.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{name}")
    public Pokemon getPokemonByName(@PathVariable("name") String name) throws ResourceNotFoundException {
        return pokeApi.getPokemonByName(name);
    }
}