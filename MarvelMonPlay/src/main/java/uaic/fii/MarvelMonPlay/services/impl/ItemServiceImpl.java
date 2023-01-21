package uaic.fii.MarvelMonPlay.services.impl;

import org.springframework.stereotype.Service;
import uaic.fii.MarvelMonPlay.models.characters.Pokemon;
import uaic.fii.MarvelMonPlay.models.items.Item;
import uaic.fii.MarvelMonPlay.services.ItemService;

@Service
public class ItemServiceImpl extends ItemServiceCrudImpl implements ItemService {
    @Override
    public Pokemon useItemOn(Item item, Pokemon pokemon) {
        return item.performItemAction(pokemon);
    }
}
