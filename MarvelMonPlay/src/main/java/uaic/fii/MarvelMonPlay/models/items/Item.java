package uaic.fii.MarvelMonPlay.models.items;

import uaic.fii.MarvelMonPlay.models.characters.Pokemon;

public abstract class Item {
    private String color;
    public abstract Pokemon performItemAction(Pokemon pokemon);

    public Item(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Item{" +
                "color='" + color + '\'' +
                '}';
    }
}
