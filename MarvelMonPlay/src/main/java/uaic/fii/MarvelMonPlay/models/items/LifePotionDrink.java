package uaic.fii.MarvelMonPlay.services.items;

import uaic.fii.MarvelMonPlay.models.items.Item;

public class LifePotionDrink extends Item {

    public static final int INCREASE_LIFE_BY = 10;
    public LifePotionDrink(String color) {
        super(color);
    }

    @Override
    public void performAction() {
        System.out.println("Life potion drink increases life by " + INCREASE_LIFE_BY);
    }
}
