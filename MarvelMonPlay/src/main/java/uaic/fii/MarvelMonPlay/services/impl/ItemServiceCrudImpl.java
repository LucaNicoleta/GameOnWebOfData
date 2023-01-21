package uaic.fii.MarvelMonPlay.services.impl;

import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uaic.fii.MarvelMonPlay.models.items.Item;
import uaic.fii.MarvelMonPlay.models.items.LifePotionDrink;
import uaic.fii.MarvelMonPlay.models.items.PoisonDrink;
import uaic.fii.MarvelMonPlay.repositories.ItemRepository;
import uaic.fii.MarvelMonPlay.services.ItemServiceCrud;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemServiceCrudImpl implements ItemServiceCrud {
    @Autowired
    private  ItemRepository itemRepository;

    @Override
    public List<Item> findAll() {
        List<Item> items = new ArrayList<>();
        try (TupleQueryResult tupleQueryResult = itemRepository.findAll()) {
            while (tupleQueryResult.hasNext()) {
                BindingSet bindingSet = tupleQueryResult.next();
                String name = bindingSet.getValue("name").stringValue();

                //TODO: find another implementation
                Item item;
                if (name.equals(PoisonDrink.NAME)) {
                    item = new PoisonDrink(PoisonDrink.NAME);
                } else {
                    item = new LifePotionDrink(LifePotionDrink.NAME);
                }
                items.add(item);
            }
        }
        return items;
    }

    @Override
    public void saveOrUpdate(Item item) {
        itemRepository.saveOrUpdate(item);
    }

    @Override
    public void delete(Item item) {
        itemRepository.delete(item);
    }
}
