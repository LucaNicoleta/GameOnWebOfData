package uaic.fii.MarvelMonPlay.services;

import uaic.fii.MarvelMonPlay.models.items.Item;

import java.util.List;

public interface ItemServiceCrud {
    List<Item> findAll();
    void saveOrUpdate(Item item);
    void delete(Item item);
}
