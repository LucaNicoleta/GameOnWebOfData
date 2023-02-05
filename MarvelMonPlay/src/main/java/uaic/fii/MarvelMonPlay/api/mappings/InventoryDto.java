package uaic.fii.MarvelMonPlay.api.mappings;

import uaic.fii.MarvelMonPlay.models.characters.Pokemon;
import uaic.fii.MarvelMonPlay.models.items.Item;

import java.util.ArrayList;
import java.util.List;

public class InventoryDto {
    private List<Pokemon> pokemonInventory;
    private List<Item> itemInventory;

    public InventoryDto(){}

    private InventoryDto(List<Pokemon> pokemonInventory, List<Item> itemInventory){
        this.pokemonInventory = pokemonInventory;
        this.itemInventory = itemInventory;
    }

    public InventoryDto withPokemonInventory(List<Pokemon> pokemonInventory){
        this.pokemonInventory = pokemonInventory;
        return this;
    }

    public InventoryDto withItemInventory(List<Item> itemInventory){
        this.itemInventory = itemInventory;
        return this;
    }

    public List<Pokemon> getPokemonInventory() {
        return new ArrayList<>(pokemonInventory);
    }

    public List<Item> getItemInventory() {
        return new ArrayList<>(itemInventory);
    }

    public InventoryDto build(){
        return new InventoryDto(pokemonInventory, itemInventory);
    }
}
