package uaic.fii.MarvelMonPlay.services.impl;

import org.springframework.stereotype.Service;
import uaic.fii.MarvelMonPlay.exceptions.ResourceNotFoundException;
import uaic.fii.MarvelMonPlay.externalApi.MarvelApi;
import uaic.fii.MarvelMonPlay.externalApi.PokeApi;
import uaic.fii.MarvelMonPlay.models.characters.Marvel;
import uaic.fii.MarvelMonPlay.models.characters.Pokemon;
import uaic.fii.MarvelMonPlay.services.EnemyGeneratorService;

@Service
public class BasicEnemyGeneratorServiceImpl implements EnemyGeneratorService {
    private final PokeApi pokeApi;
    private final MarvelApi marvelApi;

    public BasicEnemyGeneratorServiceImpl(PokeApi pokeApi, MarvelApi marvelApi){
        this.pokeApi = pokeApi;
        this.marvelApi = marvelApi;
    }
    @Override
    public Marvel generateMarvelEnemy(int level) {
        switch (level){
            case 1: return getNPCMarvel("Thor", "charizard", 5, 3);
            case 2: return getNPCMarvel("Professor X", "gyarados", 10, 6);
            case 3: return getNPCMarvel("Iceman", "dialga", 15, 10);
            default: return getNPCMarvel("Captain America", "jynx", 18, 12);
        }
    }

    private Marvel getNPCMarvel(String marvelName, String pokemonName, int pokemonAttack, int pokemonDefense) {
        Marvel marvel;
        Pokemon pokemon;
        try {
            pokemon = pokeApi.getPokemonByName(pokemonName);
            pokemon.setPowerAttack(pokemonAttack);
            pokemon.setPowerDefense(pokemonDefense);
        } catch (ResourceNotFoundException e) {
            pokemon = new Pokemon("NPCPokemon"+pokemonName, pokemonName, pokemonAttack, pokemonDefense);
        }

        try {
            marvel = marvelApi.getMarvelCharacter(marvelName);
        } catch (ResourceNotFoundException e) {
            marvel = new Marvel("NPCMarvel"+(marvelName.replace(" ", "")), marvelName, "", "");
        }

        marvel.addPokemonToInventory(pokemon);
        return marvel;
    }
}
