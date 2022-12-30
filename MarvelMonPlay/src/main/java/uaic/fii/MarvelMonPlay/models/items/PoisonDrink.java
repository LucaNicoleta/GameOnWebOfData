package uaic.fii.MarvelMonPlay.models.items;

import uaic.fii.MarvelMonPlay.models.characters.Pokemon;

public class PoisonDrink extends Item {

    public static final int DECREASE_POINTS_LIFE_BY = 10;

    public PoisonDrink(String color) {
        super(color);
    }

    @Override
    public void performItemAction(Pokemon pokemon) {
        int pokemonLife = pokemon.getHealthPoints();
        pokemon.setHealthPoints(Math.max(pokemonLife - DECREASE_POINTS_LIFE_BY, 0));
    }
}
