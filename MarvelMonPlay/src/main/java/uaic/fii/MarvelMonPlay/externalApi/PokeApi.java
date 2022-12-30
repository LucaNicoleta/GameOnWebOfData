package uaic.fii.MarvelMonPlay.externalApi;

import uaic.fii.MarvelMonPlay.exceptions.ResourceNotFoundException;
import uaic.fii.MarvelMonPlay.models.characters.Pokemon;

public interface PokeApi {
    Pokemon getPokemonByName(String name) throws ResourceNotFoundException;
}
