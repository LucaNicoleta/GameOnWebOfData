package uaic.fii.MarvelMonPlay.services;

import uaic.fii.MarvelMonPlay.exceptions.ItemNotFoundException;
import uaic.fii.MarvelMonPlay.models.items.Item;

import java.util.List;

public interface ItemServiceCrud {
    List<Item> findAll();
    Item findByResIdentifier(String itemResIdentifier) throws ItemNotFoundException;
    void save(Item item);
    void update(Item item);
    void delete(Item item);
}
