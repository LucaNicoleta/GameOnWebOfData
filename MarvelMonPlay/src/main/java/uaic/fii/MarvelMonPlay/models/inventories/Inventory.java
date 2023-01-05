package uaic.fii.MarvelMonPlay.models.inventories;

import java.util.ArrayList;
import java.util.List;

public class Inventory<T> {
    private List<T> elements;

    public Inventory() {
        this.elements = new ArrayList<>();
    }

    public List<T> getElements(){
        return new ArrayList<>(elements);
    }

    public void addToInventory(T t){
        elements.add(t);
    }

    public void removeFromInventory(T t){
        elements.remove(t);
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "elements=" + elements +
                '}';
    }
}
