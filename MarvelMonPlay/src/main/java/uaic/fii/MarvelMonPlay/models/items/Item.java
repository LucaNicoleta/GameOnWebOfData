package uaic.fii.MarvelMonPlay.models.items;

import uaic.fii.MarvelMonPlay.models.characters.Pokemon;

public abstract class Item {
    public final String RES_IDENTIFIER;
    private String color;
    public abstract Pokemon performItemAction(Pokemon pokemon);

    public Item(String RES_IDENTIFIER, String color) {
        this.RES_IDENTIFIER = RES_IDENTIFIER;
        this.color = color;
    }

    @Override
    public String toString() {
        return "Item{" +
                "color='" + color + '\'' +
                '}';
    }
}
