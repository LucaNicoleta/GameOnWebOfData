package uaic.fii.MarvelMonPlay.services.items;

import uaic.fii.MarvelMonPlay.models.items.Item;

public class PoisonDrink extends Item {

    public static final int DECREASE_POINTS_LIFE_BY = 10;

    public PoisonDrink(String color) {
        super(color);
    }

    @Override
    public void performAction() {
        System.out.println("Poison Drink - " + DECREASE_POINTS_LIFE_BY);
    }
}
