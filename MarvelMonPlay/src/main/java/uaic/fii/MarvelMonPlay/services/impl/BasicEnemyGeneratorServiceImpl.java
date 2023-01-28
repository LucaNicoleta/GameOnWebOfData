package uaic.fii.MarvelMonPlay.services.impl;

import org.springframework.stereotype.Service;
import uaic.fii.MarvelMonPlay.exceptions.ResourceNotFoundException;
import uaic.fii.MarvelMonPlay.externalApi.MarvelApi;
import uaic.fii.MarvelMonPlay.externalApi.PokeApi;
import uaic.fii.MarvelMonPlay.models.characters.Marvel;
import uaic.fii.MarvelMonPlay.models.characters.Pokemon;
import uaic.fii.MarvelMonPlay.models.levels.Level;
import uaic.fii.MarvelMonPlay.services.EnemyGeneratorService;

import java.util.List;

@Service
public class BasicEnemyGeneratorServiceImpl implements EnemyGeneratorService {
    private final PokeApi pokeApi;
    private final MarvelApi marvelApi;

    public BasicEnemyGeneratorServiceImpl(PokeApi pokeApi, MarvelApi marvelApi){
        this.pokeApi = pokeApi;
        this.marvelApi = marvelApi;
    }
    @Override
    public List<Marvel> generateMarvelEnemy(Level level) {
        switch (level){
            case WATER: {
                Marvel triton = getNPCMarvel("Triton");
                Pokemon tentacruel = getNPCPokemon("tentacruel", 5, 3);
                triton.addPokemonToInventory(tentacruel);
                return List.of(triton);
            }
            case AIR: {
                Marvel storm = getNPCMarvel("Storm");
                Pokemon fearow = getNPCPokemon("fearow", 10, 6);
                storm.addPokemonToInventory(fearow);
                return List.of(storm);
            }
            case EARTH: {
                Marvel avalanche = getNPCMarvel("Avalanche");
                Pokemon boldore = getNPCPokemon("boldore", 15, 10);
                avalanche.addPokemonToInventory(boldore);
                return List.of(avalanche);
            }
            case FIRE:{
                Marvel firestar = getNPCMarvel("Firestar");
                Pokemon charmander = getNPCPokemon("charmander", 18, 20);
                firestar.addPokemonToInventory(charmander);

                Marvel firebird = getNPCMarvel("Firebird");
                Pokemon vulpix = getNPCPokemon("vulpix", 20, 14);
                Pokemon litten = getNPCPokemon("litten", 27, 5);
                firebird.addPokemonToInventory(vulpix);
                firebird.addPokemonToInventory(litten);
                return List.of(firestar, firebird);
            }
            default: {
                return List.of();
            }
        }
    }

    private Marvel getNPCMarvel(String marvelName) {
        Marvel marvel;
        try {
            marvel = marvelApi.getMarvelCharacter(marvelName);
        } catch (ResourceNotFoundException e) {
            marvel = new Marvel(marvelName.replaceAll(" ", ""), marvelName, "", "");
        }
        return marvel;
    }

    private Pokemon getNPCPokemon(String name, int powerAttack, int powerDefense){
        Pokemon pokemon;
        try {
            pokemon = pokeApi.getPokemonByName(name);
            pokemon.setPowerAttack(powerAttack);
            pokemon.setPowerDefense(powerDefense);
        } catch (ResourceNotFoundException e) {
            pokemon = new Pokemon(name, name, powerAttack, powerDefense);
        }
        return pokemon;
    }
}
