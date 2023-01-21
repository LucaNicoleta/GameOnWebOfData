package uaic.fii.MarvelMonPlay.models.items;

import uaic.fii.MarvelMonPlay.models.characters.Pokemon;

public abstract class Item {
    public final String RES_IDENTIFIER;
    private final String name;
    private final String color;
    private final String description;
    public abstract Pokemon performItemAction(Pokemon pokemon);

    public Item(String RES_IDENTIFIER, String name, String color, String description) {
        this.RES_IDENTIFIER = RES_IDENTIFIER;
        this.name = name;
        this.color = color;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
