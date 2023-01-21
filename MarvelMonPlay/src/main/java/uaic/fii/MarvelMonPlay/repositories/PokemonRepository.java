package uaic.fii.MarvelMonPlay.repositories;

import org.eclipse.rdf4j.query.TupleQueryResult;
import uaic.fii.MarvelMonPlay.models.characters.Pokemon;

public interface PokemonRepository {
    void saveOrUpdate(Pokemon pokemon, boolean cascadeSaveOrUpdate);
    TupleQueryResult findAll();
    void delete(Pokemon pokemon);
}
