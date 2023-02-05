package uaic.fii.MarvelMonPlay.repositories.impl;

import org.eclipse.rdf4j.query.TupleQueryResult;
import org.springframework.stereotype.Repository;
import uaic.fii.MarvelMonPlay.endpoints.SparqlEndpoint;
import uaic.fii.MarvelMonPlay.models.abilities.Ability;
import uaic.fii.MarvelMonPlay.repositories.AbilityRepository;
import uaic.fii.MarvelMonPlay.utils.IRIFactory;

@Repository
public class AbilityRepositoryImpl implements AbilityRepository {

    private final SparqlEndpoint sparqlEndpoint;

    public AbilityRepositoryImpl(SparqlEndpoint sparqlEndpoint){
        this.sparqlEndpoint = sparqlEndpoint;
    }

    @Override
    public TupleQueryResult findAll() {
        return sparqlEndpoint.executeQuery(
            "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
            "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
            "select ?ability ?name ?description where {" +
            "    ?ability a IRI:Ability ." +
            "    ?ability foaf:name ?name ." +
            "    ?ability IRI:hasDescription ?description ." +
            "}"
        );
    }

    @Override
    public TupleQueryResult findAbilityResIdentifiersByPokemonResIdentifier(String pokemonResIdentifier) {
        return sparqlEndpoint.executeQuery(
            "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
            "SELECT ?ability" +
            "    WHERE { " +
            "    ?ability a IRI:Ability." +
            "    IRI:" + pokemonResIdentifier + " IRI:hasAbility ?ability." +
            "}"
        );
    }

    @Override
    public TupleQueryResult findByResIdentifier(String abilityResIdentifier) {
        return sparqlEndpoint.executeQuery(
            "    PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
            "    PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
            "    select ?name ?description " +
            "    where {" +
            "    IRI:" + abilityResIdentifier + " a IRI:Ability ." +
            "    IRI:" + abilityResIdentifier + " foaf:name ?name ." +
            "    IRI:" + abilityResIdentifier + " IRI:hasDescription ?description ." +
            "}"
        );
    }

    @Override
    public void save(Ability ability) {
        sparqlEndpoint.executeUpdate(
            "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
            "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
            "INSERT DATA {" +
            "IRI:" + ability.RES_IDENTIFIER + " a " + "IRI:Ability" + ". " +
            "IRI:" + ability.RES_IDENTIFIER + " foaf:name " + "\"" + ability.getName() + "\"" + ". " +
            "IRI:" + ability.RES_IDENTIFIER + " IRI:hasDescription " + "\"" + ability.getDescription() + "\"" + ". }"
        );
    }

    @Override
    public void update(Ability ability) {
        sparqlEndpoint.executeUpdate(
            "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
            "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
            "DELETE {" +
                "IRI:" + ability.RES_IDENTIFIER + " foaf:name ?o1." +
                "IRI:" + ability.RES_IDENTIFIER + " IRI:hasDescription ?o2. }" +
            "INSERT {" +
                "IRI:" + ability.RES_IDENTIFIER + " foaf:name " + "\"" + ability.getName() + "\"" + ". " +
                "IRI:" + ability.RES_IDENTIFIER + " IRI:hasDescription " + "\"" + ability.getDescription() + "\". }" +
            "WHERE {" +
                "IRI:" + ability.RES_IDENTIFIER + " foaf:name ?o1." +
                "IRI:" + ability.RES_IDENTIFIER + " IRI:hasDescription ?o2. }"
        );
    }

    @Override
    public void delete(Ability ability) {
        sparqlEndpoint.executeUpdate(
            "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
            "DELETE {IRI:" + ability.RES_IDENTIFIER + " ?p ?o} " +
            "WHERE {IRI:" + ability.RES_IDENTIFIER + " ?p ?o}"
        );
    }
}
