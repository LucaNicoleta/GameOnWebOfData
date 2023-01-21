package uaic.fii.MarvelMonPlay.models.items;

import uaic.fii.MarvelMonPlay.models.characters.Pokemon;

public class PoisonDrink extends Item {
    public static final int DECREASE_POINTS_LIFE_BY = 10;
    public static final String NAME = "SuperPoisonDrink";
    public static final String COLOR = "RED";
    public static final String DESCRIPTION = "This liquid decreases life by " + DECREASE_POINTS_LIFE_BY + " points.";

    public PoisonDrink(String RES_IDENTIFIER){
        super(RES_IDENTIFIER, NAME, COLOR, DESCRIPTION);
    }

    @Override
    public Pokemon performItemAction(Pokemon pokemon) {
        int pokemonLife = pokemon.getHealthPoints();
        pokemon.setHealthPoints(Math.max(pokemonLife - DECREASE_POINTS_LIFE_BY, 0));
        return pokemon;
    }
}
