package uaic.fii.MarvelMonPlay.repositories;

import org.eclipse.rdf4j.query.TupleQueryResult;

public interface CharacterRepository {
    TupleQueryResult getMarvelCharacters();
    TupleQueryResult getPokemonCharacters();
}
