package uaic.fii.MarvelMonPlay.repositories;

import org.eclipse.rdf4j.query.TupleQueryResult;
import uaic.fii.MarvelMonPlay.models.characters.Marvel;

public interface MarvelRepository {
    TupleQueryResult findAll();
    void save(Marvel marvel, boolean cascadeSave);
    TupleQueryResult findByName(String name);
    TupleQueryResult findPokemonIdentifiers(String marvelResIdentifier);
    TupleQueryResult findItemIdentifiers(String marvelResIdentifier);
    TupleQueryResult findByResIdentifier(String RES_IDENTIFIER);
    void setElementRepresentativeForMarvel(String marvel_res, String element);
    void update(Marvel marvel, boolean cascadeUpdate);
    void addItemForMarvel(String marvel_res, String item_res);
    void insertMarvelEnemy();
    void delete(Marvel marvel);
    TupleQueryResult getListOfPokemonNamesOwnedByMarvel(String MarvelRES);
}
