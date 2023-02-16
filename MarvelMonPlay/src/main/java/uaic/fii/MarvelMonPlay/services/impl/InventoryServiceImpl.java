package uaic.fii.MarvelMonPlay.services.impl;

import org.springframework.stereotype.Service;
import uaic.fii.MarvelMonPlay.api.mappings.InventoryDto;
import uaic.fii.MarvelMonPlay.exceptions.AbilityNotFoundException;
import uaic.fii.MarvelMonPlay.exceptions.ItemNotFoundException;
import uaic.fii.MarvelMonPlay.exceptions.MarvelNotSetException;
import uaic.fii.MarvelMonPlay.exceptions.PokemonNotFoundException;
import uaic.fii.MarvelMonPlay.models.characters.Marvel;
import uaic.fii.MarvelMonPlay.models.characters.Pokemon;
import uaic.fii.MarvelMonPlay.models.items.Item;
import uaic.fii.MarvelMonPlay.models.players.Player;
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
    public InventoryDto getMarvelInventory(Player player) throws PokemonNotFoundException, AbilityNotFoundException, ItemNotFoundException, MarvelNotSetException {
        Marvel marvel = player.getMarvelCharacter();

        if(marvel == null){
            throw new MarvelNotSetException("The player does not have a marvel character yet");
        }

        List<Pokemon> pokemonInventory = marvelService.findPokemonInventory(marvel.RES_IDENTIFIER);
        List<Item> itemInventory = marvelService.findItemInventory(marvel.RES_IDENTIFIER);
        InventoryDto inv = new InventoryDto()
                .withPokemonInventory(pokemonInventory)
                .withItemInventory(itemInventory)
                .build();
                
        return inv;
    }
}
