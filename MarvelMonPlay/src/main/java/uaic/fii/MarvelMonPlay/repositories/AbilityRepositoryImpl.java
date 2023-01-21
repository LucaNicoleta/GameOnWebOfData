package uaic.fii.MarvelMonPlay.repositories;

import org.eclipse.rdf4j.query.TupleQueryResult;
import org.springframework.stereotype.Repository;
import uaic.fii.MarvelMonPlay.endpoints.SparqlEndpoint;
import uaic.fii.MarvelMonPlay.models.abilities.Ability;
import uaic.fii.MarvelMonPlay.utils.IRIFactory;

@Repository
public class AbilityRepositoryImpl implements AbilityRepository{

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
    public void saveOrUpdate(Ability ability) {
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
    public void delete(Ability ability) {
        sparqlEndpoint.executeUpdate(
            "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
            "DELETE {IRI:" + ability.RES_IDENTIFIER + " ?p ?o} " +
            "WHERE {IRI:" + ability.RES_IDENTIFIER + " ?p ?o}"
        );
    }
}
