package uaic.fii.MarvelMonPlay.models.items;

import uaic.fii.MarvelMonPlay.models.characters.Pokemon;

import static uaic.fii.MarvelMonPlay.models.characters.Pokemon.MAX_HEALTH_POINTS;

public class LifePotionDrink extends Item {

    public static final int INCREASE_LIFE_BY = 10;
    public LifePotionDrink(String color) {
        super(color);
    }

    @Override
    public Pokemon performItemAction(Pokemon pokemon) {
        int pokemonLife = pokemon.getHealthPoints();
        pokemon.setHealthPoints(Math.min(pokemonLife + INCREASE_LIFE_BY, MAX_HEALTH_POINTS));
        return pokemon;
    }
}
