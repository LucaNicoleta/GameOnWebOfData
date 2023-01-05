package uaic.fii.MarvelMonPlay.models.characters;

import com.fasterxml.jackson.annotation.JsonProperty;
import uaic.fii.MarvelMonPlay.models.inventories.Inventory;
import uaic.fii.MarvelMonPlay.models.items.Item;

public class Marvel extends Character{
    @JsonProperty("imageURL")
    private final String imageURL;

    @JsonProperty("description")
    private final String description;

    @JsonProperty("pokemonInventory")
    private Inventory<Pokemon> pokemonInventory;

    @JsonProperty("itemInventory")
    private Inventory<Item> itemInventory;

    public Marvel(String name, String imageURL, String description) {
        super(name);
        this.imageURL = imageURL;
        this.description = description;
        pokemonInventory = new Inventory<>();
        itemInventory = new Inventory<>();
    }

    public Inventory<Pokemon> getPokemonInventory() {
        return pokemonInventory;
    }

    public Inventory<Item> getItemInventory() {
        return itemInventory;
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
