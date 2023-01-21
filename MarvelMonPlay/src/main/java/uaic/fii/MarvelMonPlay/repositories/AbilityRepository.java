package uaic.fii.MarvelMonPlay.repositories;

import org.eclipse.rdf4j.query.TupleQueryResult;
import uaic.fii.MarvelMonPlay.models.abilities.Ability;

public interface AbilityRepository {
    TupleQueryResult findAll();
    void save(Ability ability);
    void delete(Ability ability);
}
