package uaic.fii.MarvelMonPlay.services;

import org.springframework.stereotype.Service;
import uaic.fii.MarvelMonPlay.models.characters.Pokemon;
import uaic.fii.MarvelMonPlay.models.items.Item;

@Service
public class ItemServiceImpl implements ItemService{
    @Override
    public Pokemon useItemOn(Item item, Pokemon pokemon) {
        return item.performItemAction(pokemon);
    }
}
