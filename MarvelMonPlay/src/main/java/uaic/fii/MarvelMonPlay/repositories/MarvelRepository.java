package uaic.fii.MarvelMonPlay.repositories;

import org.eclipse.rdf4j.query.TupleQueryResult;
import uaic.fii.MarvelMonPlay.models.characters.Marvel;

public interface MarvelRepository {
    TupleQueryResult findAll();
    void save(Marvel marvel, boolean cascadeSave);
    void update(Marvel marvel, boolean cascadeUpdate);
    void delete(Marvel marvel);
}
