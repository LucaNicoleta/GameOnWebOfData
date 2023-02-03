package uaic.fii.MarvelMonPlay.models.characters;

import com.fasterxml.jackson.annotation.JsonProperty;
import uaic.fii.MarvelMonPlay.models.items.Item;

import java.util.ArrayList;
import java.util.List;

public class Marvel extends Character{
    @JsonProperty("imageURL")
    private final String imageURL;

    @JsonProperty("description")
    private final String description;

    @JsonProperty("pokemonInventory")
    private List<Pokemon> pokemonInventory;

    @JsonProperty("itemInventory")
    private List<Item> itemInventory;

    public Marvel(String RES_IDENTIFIER, String name, String imageURL, String description) {
        super(RES_IDENTIFIER, name);
        this.imageURL = imageURL;
        this.description = description;
        pokemonInventory = new ArrayList<>();
        itemInventory = new ArrayList<>();
    }

    public List<Pokemon> getPokemonInventory() {
        return new ArrayList<>(pokemonInventory);
    }

    public List<Item> getItemInventory() {
        return new ArrayList<>(itemInventory);
    }

    public void addPokemonToInventory(Pokemon pokemon){
        pokemonInventory.add(pokemon);
    }
    
    public boolean checkIfPokemonInInventory(Pokemon pokemon){
        return pokemonInventory.contains(pokemon);
    }

    public boolean checkIfItemInInventory(Item item){
        return itemInventory.contains(item);
    }
    
    public void removePokemonFromInventory(Pokemon pokemon){
        pokemonInventory.remove(pokemon);
    }

    public void addItemToInventory(Item item){
        itemInventory.add(item);
    }

    public void removeItemFromInventory(Item item){
        itemInventory.remove(item);
    }
    public String getImageURL() {
        return imageURL;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Marvel{" +
                "imageURL='" + imageURL + '\'' +
                ", description='" + description + '\'' +
                ", pokemonInventory=" + pokemonInventory +
                ", itemInventory=" + itemInventory +
                ", name='" + name + '\'' +
                '}';
    }
}
