package uaic.fii.MarvelMonPlay.repositories.impl;

import org.eclipse.rdf4j.query.TupleQueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uaic.fii.MarvelMonPlay.endpoints.SparqlEndpoint;
import uaic.fii.MarvelMonPlay.models.abilities.Ability;
import uaic.fii.MarvelMonPlay.models.characters.Pokemon;
import uaic.fii.MarvelMonPlay.repositories.PokemonRepository;
import uaic.fii.MarvelMonPlay.services.AbilityService;
import uaic.fii.MarvelMonPlay.utils.IRIFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Repository
public class PokemonRepositoryImpl implements PokemonRepository {
    private final SparqlEndpoint sparqlEndpoint;

    @Autowired
    private AbilityService abilityService;

    public PokemonRepositoryImpl(SparqlEndpoint sparqlEndpoint){
        this.sparqlEndpoint = sparqlEndpoint;
    }
    @Override
    public void save(Pokemon pokemon, boolean cascadeSave) {
        sparqlEndpoint.executeUpdate(
                "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
                "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
                "INSERT DATA {" +
                "IRI:" + pokemon.RES_IDENTIFIER + " a " + "IRI:Pokemon" + ". " +
                "IRI:" + pokemon.RES_IDENTIFIER + " foaf:name " + "\"" + pokemon.getName() + "\"" + ". " +
                "IRI:" + pokemon.RES_IDENTIFIER + " IRI:healthPoints " + "\"" + pokemon.getHealthPoints() + "\"" + ". " +
                "IRI:" + pokemon.RES_IDENTIFIER + " IRI:hasPowerAttack " + "\"" + pokemon.getPowerAttack() + "\"" + ". " +
                "IRI:" + pokemon.RES_IDENTIFIER + " IRI:hasDefense " + "\"" + pokemon.getPowerDefense() + "\"" + ". " +
                "IRI:" + pokemon.RES_IDENTIFIER + " IRI:hasImageURL " + "\"" + pokemon.getImageURL() + "\"" + " ." +
                getInsertStatementsForAbilities(pokemon) +
                "}"
        );

        if(cascadeSave){
            saveAbilities(pokemon.getAbilities());
        }
    }

    @Override
    public void update(Pokemon pokemon, boolean cascadeUpdate) {
        sparqlEndpoint.executeUpdate(
            "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
            "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
            "DELETE {" +
                "IRI:" + pokemon.RES_IDENTIFIER + " foaf:name ?o1. " +
                "IRI:" + pokemon.RES_IDENTIFIER + " IRI:healthPoints ?o2. " +
                "IRI:" + pokemon.RES_IDENTIFIER + " IRI:hasPowerAttack ?o3. " +
                "IRI:" + pokemon.RES_IDENTIFIER + " IRI:hasDefense ?o4. " +
                "IRI:" + pokemon.RES_IDENTIFIER + " IRI:hasImageURL ?o5. " +
                getSelectStatementsForAbilities(pokemon, false) +
            "}" +
            "INSERT {" +
                "IRI:" + pokemon.RES_IDENTIFIER + " foaf:name " + "\"" + pokemon.getName() + "\"" + ". " +
                "IRI:" + pokemon.RES_IDENTIFIER + " IRI:healthPoints " + "\"" + pokemon.getHealthPoints() + "\"" + ". " +
                "IRI:" + pokemon.RES_IDENTIFIER + " IRI:hasPowerAttack " + "\"" + pokemon.getPowerAttack() + "\"" + ". " +
                "IRI:" + pokemon.RES_IDENTIFIER + " IRI:hasDefense " + "\"" + pokemon.getPowerDefense() + "\"" + ". " +
                "IRI:" + pokemon.RES_IDENTIFIER + " IRI:hasImageURL " + "\"" + pokemon.getImageURL() + "\". " +
                getInsertStatementsForAbilities(pokemon) +
            "}" +
            "WHERE {" +
                "IRI:" + pokemon.RES_IDENTIFIER + " foaf:name ?o1. " +
                "IRI:" + pokemon.RES_IDENTIFIER + " IRI:healthPoints ?o2. " +
                "IRI:" + pokemon.RES_IDENTIFIER + " IRI:hasPowerAttack ?o3. " +
                "IRI:" + pokemon.RES_IDENTIFIER + " IRI:hasDefense ?o4. " +
                "IRI:" + pokemon.RES_IDENTIFIER + " IRI:hasImageURL ?o5. " +
                getSelectStatementsForAbilities(pokemon, true) +
            "}"
        );

        if(cascadeUpdate){
            updateAbilities(pokemon.getAbilities());
        }
    }

    private void updateAbilities(List<Ability> abilities) {
        abilities.forEach(ability -> abilityService.update(ability));
    }

    private String getSelectStatementsForAbilities(Pokemon pokemon, boolean withOptionalClause) {
        List<Ability> abilities = pokemon.getAbilities();
        AtomicReference<String> statements = new AtomicReference<>("");
        abilities.forEach(ability -> {
            String str = withOptionalClause ? "OPTIONAL{IRI:" + pokemon.RES_IDENTIFIER + " IRI:hasAbility ?" + ability.getName() + "}. "
                                            : "IRI:" + pokemon.RES_IDENTIFIER + " IRI:hasAbility ?" + ability.getName() + ". ";
            statements.getAndAccumulate(str, (s, s2) -> s + s2);
        });
        return statements.get();
    }

    private void saveAbilities(List<Ability> abilities) {
        abilities.forEach(ability -> abilityService.save(ability));
    }

    private String getInsertStatementsForAbilities(Pokemon pokemon) {
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
            "SELECT ?character ?name (GROUP_CONCAT(DISTINCT ?ability; separator = \",\") AS ?abilities) ?healthPoints ?powerAttack ?powerDefense ?imageURL" +
            "WHERE {" +
            "    ?character a IRI:Pokemon ." +
            "    ?character foaf:name ?name ." +
            "    ?character IRI:hasAbility ?ability ." +
            "    ?character IRI:healthPoints ?healthPoints ." +
            "    ?character IRI:hasPowerAttack ?powerAttack ." +
            "    ?character IRI:hasDefense ?powerDefense ." +
            "    ?character IRI:hasImageURL ?imageURL ." +
            "}" +
            "GROUP BY ?character ?name ?healthPoints ?powerAttack ?powerDefense ?imageURL"
        );
    }

    @Override
    public TupleQueryResult findByName(String name) {
        return sparqlEndpoint.executeQuery(
            "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
            "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
            "SELECT ?character (GROUP_CONCAT(DISTINCT ?ability; separator = \",\") AS ?abilities) ?healthPoints ?powerAttack ?powerDefense ?imageURL " +
            "WHERE {" +
            "    ?character a IRI:Pokemon ." +
            "    ?character foaf:name \"" + name + "\". " +
            "    ?character IRI:hasAbility ?ability ." +
            "    OPTIONAL{?character IRI:healthPoints ?healthPoints .}" +
            "    OPTIONAL{?character IRI:hasPowerAttack ?powerAttack .}" +
            "    OPTIONAL{?character IRI:hasDefense ?powerDefense .}" +
            "    ?character IRI:hasImageURL ?imageURL ." +
            "}" +
            "GROUP BY ?character ?healthPoints ?powerAttack ?powerDefense ?imageURL"
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
