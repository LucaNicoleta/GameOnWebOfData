package uaic.fii.MarvelMonPlay.models.inventories;

import uaic.fii.MarvelMonPlay.models.characters.Pokemon;

import java.util.ArrayList;
import java.util.List;

public class PokemonInventory extends Inventory{
    private List<Pokemon> pokemonInventory;

    public PokemonInventory(){
        pokemonInventory = new ArrayList<>();
    }

    public List<Pokemon> getPokemonInventory() {
        return new ArrayList<>(pokemonInventory);
    }

    public void addPokemonToInventory(Pokemon pokemon) {
        pokemonInventory.add(pokemon);
    }

    public void removePokemonFromInventory(Pokemon pokemon){
        pokemonInventory.remove(pokemon);
    }
}