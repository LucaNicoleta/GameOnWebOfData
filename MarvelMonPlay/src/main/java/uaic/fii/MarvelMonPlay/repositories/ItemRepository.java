package uaic.fii.MarvelMonPlay.repositories;

import org.eclipse.rdf4j.query.TupleQueryResult;
import uaic.fii.MarvelMonPlay.models.items.Item;

public interface ItemRepository {
    TupleQueryResult findAll();
    TupleQueryResult findByResIdentifier(String itemResIdentifier);
    void save(Item item);
    void update(Item item);
    void delete(Item item);
}
