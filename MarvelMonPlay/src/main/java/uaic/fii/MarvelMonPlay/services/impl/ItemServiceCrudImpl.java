package uaic.fii.MarvelMonPlay.services.impl;

import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.springframework.beans.factory.annotation.Autowired;

import uaic.fii.MarvelMonPlay.exceptions.ItemNotFoundException;
import uaic.fii.MarvelMonPlay.models.Action;
import uaic.fii.MarvelMonPlay.models.Element;
import uaic.fii.MarvelMonPlay.models.items.Item;
import uaic.fii.MarvelMonPlay.repositories.ItemRepository;
import uaic.fii.MarvelMonPlay.services.ItemServiceCrud;

import java.util.ArrayList;
import java.util.List;

public class ItemServiceCrudImpl implements ItemServiceCrud {
    @Autowired
    private  ItemRepository itemRepository;

    @Override
    public List<Item> findAll() {
        List<Item> items = new ArrayList<>();
        try (TupleQueryResult tupleQueryResult = itemRepository.findAll()) {
            while (tupleQueryResult.hasNext()) {
                BindingSet bindingSet = tupleQueryResult.next();
                System.out.println(bindingSet);
                String name = bindingSet.getValue("name").stringValue();
                Action action = Action.valueOf(bindingSet.getValue("action").stringValue());
                //TODO: find another implementation
                Item item;
                
                //items.add(item);
            }
        }
        return items;
    }

    @Override
    public Item findByResIdentifier(String itemResIdentifier) throws ItemNotFoundException {
        try (TupleQueryResult tqr = itemRepository.findByResIdentifier(itemResIdentifier)) {
            if(tqr.hasNext()){
                BindingSet bs = tqr.next();
                Value elementValue = bs.getValue("element");
                Value actionValue = bs.getValue("action");

                String elementWithIRI = elementValue.stringValue();
                String elementWithoutIRI = elementWithIRI.substring(elementWithIRI.indexOf("#") + 1);

                String actionWithIRI = actionValue.stringValue();
                String actionWithoutIRI = actionWithIRI.substring(actionWithIRI.indexOf("#") + 1);

                Element element = Element.valueOf(elementWithoutIRI);
                Action action = Action.valueOf(actionWithoutIRI);
                return new Item(itemResIdentifier, element, action);
            }
            throw new ItemNotFoundException("Item " + itemResIdentifier + " has not been found");
        }
    }

    @Override
    public void save(Item item) {
        itemRepository.save(item);
    }

    @Override
    public void update(Item item) {
        itemRepository.update(item);
    }

    @Override
    public void delete(Item item) {
        itemRepository.delete(item);
    }
}
