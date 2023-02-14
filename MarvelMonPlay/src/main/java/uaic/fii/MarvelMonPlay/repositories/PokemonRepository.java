package uaic.fii.MarvelMonPlay.repositories;

import org.eclipse.rdf4j.query.TupleQueryResult;
import uaic.fii.MarvelMonPlay.models.characters.Pokemon;

public interface PokemonRepository {
    void save(Pokemon pokemon, boolean cascadeSave);
    void update(Pokemon pokemon, boolean cascadeUpdate);
    void updateDefense(String RES_IDENTIFIER, int value);
    void updateHealth(String RES_IDENTIFIER, int value);
    void updateAttack(String RES_IDENTIFIER, int value);
    TupleQueryResult findAll();
    TupleQueryResult findByName(String name);
    TupleQueryResult findByResIdentifier(String resIdentifier);
    void delete(Pokemon pokemon);
    void choosePokemonToFight(String RES_IDENTIFIER, String PokemonEnemy_RES);
    TupleQueryResult findCurrentPokemonEnemy(String Res_Marvel);
    TupleQueryResult findPokemonsInFight(String MarvelRes);
    TupleQueryResult findByNameAndOwner(String name, String marvel_res);

}
