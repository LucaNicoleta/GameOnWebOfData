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

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/update/health")
    public String updateHealth(@RequestBody Pokemon pokemon) throws ResourceNotFoundException {
        pokemonService.updateHealth(pokemon.RES_IDENTIFIER, pokemon.getHealthPoints());
        return "Health updated successfully!";
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/update/attack")
    public String updateAttack(@RequestBody Pokemon pokemon) throws ResourceNotFoundException {
        pokemonService.updateAttack(pokemon.RES_IDENTIFIER, pokemon.getPowerAttack());
        return "Attack updated successfully!";
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/update/defense")
    public String updateDefense(@RequestBody Pokemon pokemon) throws ResourceNotFoundException {
        pokemonService.updateDefense(pokemon.RES_IDENTIFIER, pokemon.getPowerDefense());
        return "Defense updated successfully!";
    }
}