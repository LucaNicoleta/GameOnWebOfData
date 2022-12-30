package uaic.fii.MarvelMonPlay.models.items;

public abstract class Item implements ItemAction{
    private String color;

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
