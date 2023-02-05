package uaic.fii.MarvelMonPlay.repositories;

import org.eclipse.rdf4j.query.TupleQueryResult;
import uaic.fii.MarvelMonPlay.models.abilities.Ability;

public interface AbilityRepository {
    TupleQueryResult findAll();
    TupleQueryResult findAbilityResIdentifiersByPokemonResIdentifier(String pokemonResIdentifier);
    TupleQueryResult findByResIdentifier(String abilityResIdentifier);
    void save(Ability ability);
    void update(Ability ability);
    void delete(Ability ability);
}
