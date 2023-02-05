package uaic.fii.MarvelMonPlay.services;

import uaic.fii.MarvelMonPlay.api.mappings.InventoryDto;
import uaic.fii.MarvelMonPlay.exceptions.AbilityNotFoundException;
import uaic.fii.MarvelMonPlay.exceptions.ItemNotFoundException;
import uaic.fii.MarvelMonPlay.exceptions.PokemonNotFoundException;

public interface InventoryService {
    InventoryDto getMarvelInventory(String marvelResIdentifier) throws PokemonNotFoundException, AbilityNotFoundException, ItemNotFoundException;
}
