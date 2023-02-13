package uaic.fii.MarvelMonPlay.services;

import uaic.fii.MarvelMonPlay.exceptions.AbilityNotFoundException;
import uaic.fii.MarvelMonPlay.exceptions.PokemonNotFoundException;
import uaic.fii.MarvelMonPlay.exceptions.ResourceNotFoundException;
import uaic.fii.MarvelMonPlay.models.Event;
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
    void updateDefense(String RES_IDENTIFIER, int value);
    void updateHealth(String RES_IDENTIFIER, int value);
    void updateAttack(String RES_IDENTIFIER, int value);
    void delete(Pokemon pokemon);
    Event fightRound(Pokemon pokemon_mc, Pokemon pokemon_npc);
    List<Pokemon> findPokemonsInFight(String MarvelRes);
    String findCurrentPokemonEnemy(String Res_pokemon);
    void pickPokemonForFight(String marvel_res,String pokemon_name, String pokemon_enemy_res);
    Pokemon findByNameAndOwner(String name, String marvel_res);
}
