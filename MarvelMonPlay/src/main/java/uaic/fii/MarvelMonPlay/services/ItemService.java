package uaic.fii.MarvelMonPlay.services;

import uaic.fii.MarvelMonPlay.models.characters.Pokemon;
import uaic.fii.MarvelMonPlay.models.items.Item;

public interface ItemService extends ItemServiceCrud {
    /**
     *
     * @param item
     * @param pokemon
     * @return <b>pokemon</b> after used the <b>item</b>
     */
    Pokemon useItemOn(Item item, Pokemon pokemon);
}
