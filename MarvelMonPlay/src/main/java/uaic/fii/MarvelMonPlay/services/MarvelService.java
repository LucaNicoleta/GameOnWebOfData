package uaic.fii.MarvelMonPlay.services;

import uaic.fii.MarvelMonPlay.exceptions.AbilityNotFoundException;
import uaic.fii.MarvelMonPlay.exceptions.PokemonNotFoundException;
import uaic.fii.MarvelMonPlay.exceptions.ResourceNotFoundException;
import uaic.fii.MarvelMonPlay.models.characters.Marvel;
import uaic.fii.MarvelMonPlay.models.characters.Pokemon;

import java.util.List;

public interface MarvelService {
    List<Marvel> findAll();
    void save(Marvel marvel, boolean cascadeSave);
    Marvel findByName(String name) throws ResourceNotFoundException;
    List<String> findPokemonInventoryResIdentifiers(String marvelResIdentifier);
    List<Pokemon> findPokemonInventory(String marvelResIdentifier) throws PokemonNotFoundException, AbilityNotFoundException;
    Marvel findByResIdentifier(String RES_IDENTIFIER) throws ResourceNotFoundException;
    void update(Marvel marvel, boolean cascadeUpdate);
    void delete(Marvel marvel);
}
