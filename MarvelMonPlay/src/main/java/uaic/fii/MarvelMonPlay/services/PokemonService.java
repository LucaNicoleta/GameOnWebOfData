package uaic.fii.MarvelMonPlay.services;

import uaic.fii.MarvelMonPlay.exceptions.AbilityNotFoundException;
import uaic.fii.MarvelMonPlay.exceptions.PokemonNotFoundException;
import uaic.fii.MarvelMonPlay.exceptions.ResourceNotFoundException;
import uaic.fii.MarvelMonPlay.models.abilities.Ability;
import uaic.fii.MarvelMonPlay.models.characters.Pokemon;

import java.util.List;

public interface PokemonService {
    List<Pokemon> findAll();
    Pokemon findByName(String name) throws ResourceNotFoundException;
    Pokemon findByResIdentifier(String resIdentifier) throws AbilityNotFoundException, PokemonNotFoundException;
    List<Ability> getPokemonAbilities(String pokemonResIdentifier) throws AbilityNotFoundException;
    void save(Pokemon pokemon, boolean cascadeSave);
    void update(Pokemon pokemon, boolean cascadeUpdate);
    void delete(Pokemon pokemon);
}
