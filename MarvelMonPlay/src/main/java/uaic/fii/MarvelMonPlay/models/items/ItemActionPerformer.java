package uaic.fii.MarvelMonPlay.models.items;

import uaic.fii.MarvelMonPlay.models.characters.Pokemon;

public interface ItemActionPerformer {
    Pokemon performItemAction(Pokemon pokemon);
}