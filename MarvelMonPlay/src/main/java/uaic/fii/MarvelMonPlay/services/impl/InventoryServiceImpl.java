package uaic.fii.MarvelMonPlay.services.impl;

import org.springframework.stereotype.Service;
import uaic.fii.MarvelMonPlay.api.mappings.InventoryDto;
import uaic.fii.MarvelMonPlay.exceptions.AbilityNotFoundException;
import uaic.fii.MarvelMonPlay.exceptions.ItemNotFoundException;
import uaic.fii.MarvelMonPlay.exceptions.PokemonNotFoundException;
import uaic.fii.MarvelMonPlay.models.characters.Pokemon;
import uaic.fii.MarvelMonPlay.models.items.Item;
import uaic.fii.MarvelMonPlay.services.InventoryService;
import uaic.fii.MarvelMonPlay.services.MarvelService;

import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {
    private final MarvelService marvelService;

    public InventoryServiceImpl(MarvelService marvelService){
        this.marvelService = marvelService;
    }
    @Override
    public InventoryDto getMarvelInventory(String marvelResIdentifier) throws PokemonNotFoundException, AbilityNotFoundException, ItemNotFoundException {
        List<Pokemon> pokemonInventory = marvelService.findPokemonInventory(marvelResIdentifier);
        List<Item> itemInventory = marvelService.findItemInventory(marvelResIdentifier);
        return new InventoryDto()
                .withPokemonInventory(pokemonInventory)
                .withItemInventory(itemInventory)
                .build();
    }
}
