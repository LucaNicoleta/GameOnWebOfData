package uaic.fii.MarvelMonPlay.services;

import uaic.fii.MarvelMonPlay.api.mappings.InventoryDto;
import uaic.fii.MarvelMonPlay.exceptions.AbilityNotFoundException;
import uaic.fii.MarvelMonPlay.exceptions.ItemNotFoundException;
import uaic.fii.MarvelMonPlay.exceptions.MarvelNotSetException;
import uaic.fii.MarvelMonPlay.exceptions.PokemonNotFoundException;
import uaic.fii.MarvelMonPlay.models.players.Player;

public interface InventoryService {
    InventoryDto getMarvelInventory(Player player) throws PokemonNotFoundException, AbilityNotFoundException, ItemNotFoundException, MarvelNotSetException;
}
