package uaic.fii.MarvelMonPlay.repositories;

import org.eclipse.rdf4j.query.TupleQueryResult;
import uaic.fii.MarvelMonPlay.models.characters.Pokemon;

public interface PokemonRepository {
    void save(Pokemon pokemon, boolean cascadeSave);
    void update(Pokemon pokemon, boolean cascadeUpdate);
    TupleQueryResult findAll();
    void delete(Pokemon pokemon);
}
