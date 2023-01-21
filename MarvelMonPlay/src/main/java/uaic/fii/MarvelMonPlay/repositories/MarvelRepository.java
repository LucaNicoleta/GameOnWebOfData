package uaic.fii.MarvelMonPlay.repositories;

import org.eclipse.rdf4j.query.TupleQueryResult;
import uaic.fii.MarvelMonPlay.models.characters.Marvel;

public interface MarvelRepository {
    TupleQueryResult findAll();
    void saveOrUpdate(Marvel marvel, boolean cascadeSaveOrUpdate);
    void delete(Marvel marvel);
}
