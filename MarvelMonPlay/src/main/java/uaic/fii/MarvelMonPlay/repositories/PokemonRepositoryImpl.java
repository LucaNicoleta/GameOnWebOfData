package uaic.fii.MarvelMonPlay.repositories;

import org.eclipse.rdf4j.query.TupleQueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uaic.fii.MarvelMonPlay.endpoints.SparqlEndpoint;
import uaic.fii.MarvelMonPlay.models.abilities.Ability;
import uaic.fii.MarvelMonPlay.models.characters.Pokemon;
import uaic.fii.MarvelMonPlay.services.AbilityService;
import uaic.fii.MarvelMonPlay.utils.IRIFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Repository
public class PokemonRepositoryImpl implements PokemonRepository{
    private final SparqlEndpoint sparqlEndpoint;

    @Autowired
    private AbilityService abilityService;

    public PokemonRepositoryImpl(SparqlEndpoint sparqlEndpoint){
        this.sparqlEndpoint = sparqlEndpoint;
    }
    @Override
    public void saveOrUpdate(Pokemon pokemon, boolean cascadeSaveOrUpdate) {
        sparqlEndpoint.executeUpdate(
                "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
                "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
                "INSERT DATA {" +
                "IRI:" + pokemon.RES_IDENTIFIER + " a " + "IRI:Pokemon" + ". " +
                "IRI:" + pokemon.RES_IDENTIFIER + " foaf:name " + "\"" + pokemon.getName() + "\"" + ". " +
                "IRI:" + pokemon.RES_IDENTIFIER + " IRI:healthPoints " + "\"" + pokemon.getHealthPoints() + "\"" + ". " +
                getStatementsForAbilities(pokemon) +
                "}"
        );

        if(cascadeSaveOrUpdate){
            saveOrUpdateAbilities(pokemon.getAbilities());
        }
    }

    private void saveOrUpdateAbilities(List<Ability> abilities) {
        abilities.forEach(ability -> abilityService.saveOrUpdate(ability));
    }

    private String getStatementsForAbilities(Pokemon pokemon) {
        List<Ability> abilities = pokemon.getAbilities();
        AtomicReference<String> statements = new AtomicReference<>("");
        abilities.forEach(ability -> statements.getAndAccumulate("IRI:"+pokemon.RES_IDENTIFIER + " IRI:hasAbility " +
                "IRI:" + ability.RES_IDENTIFIER + ". ", (s, s2) -> s + s2));
        return statements.get();
    }

    @Override
    public TupleQueryResult findAll() {
        return sparqlEndpoint.executeQuery(
            "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
            "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
            "SELECT ?character ?name (GROUP_CONCAT(DISTINCT ?ability; separator = \",\") AS ?abilities) ?healthPoints " +
            "WHERE {" +
            "    ?character a IRI:Pokemon ." +
            "    ?character foaf:name ?name ." +
            "    ?character IRI:hasAbility ?ability ." +
            "    ?character IRI:healthPoints ?healthPoints ." +
            "}" +
            "GROUP BY ?character ?name ?healthPoints"
        );
    }

    @Override
    public void delete(Pokemon pokemon) {
        sparqlEndpoint.executeUpdate(
            "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
            "DELETE {IRI:" + pokemon.RES_IDENTIFIER + " ?p ?o} " +
            "WHERE {IRI:" + pokemon.RES_IDENTIFIER + " ?p ?o}"
        );
    }
}
