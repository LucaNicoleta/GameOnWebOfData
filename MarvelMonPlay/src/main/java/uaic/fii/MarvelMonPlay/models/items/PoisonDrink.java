package uaic.fii.MarvelMonPlay.models.items;

import uaic.fii.MarvelMonPlay.models.characters.Pokemon;

public class PoisonDrink extends Item {
    public static final int DECREASE_POINTS_LIFE_BY = 10;

    public PoisonDrink(String RES_IDENTIFIER, String color) {
        super(RES_IDENTIFIER, color);
    }

    @Override
    public Pokemon performItemAction(Pokemon pokemon) {
        int pokemonLife = pokemon.getHealthPoints();
        pokemon.setHealthPoints(Math.max(pokemonLife - DECREASE_POINTS_LIFE_BY, 0));
        return pokemon;
    }
}
