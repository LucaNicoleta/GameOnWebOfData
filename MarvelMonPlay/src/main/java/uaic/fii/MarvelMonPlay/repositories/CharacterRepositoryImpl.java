package uaic.fii.MarvelMonPlay.repositories;

import org.eclipse.rdf4j.query.TupleQueryResult;
import org.springframework.stereotype.Repository;
import uaic.fii.MarvelMonPlay.endpoints.SparqlEndpoint;
import uaic.fii.MarvelMonPlay.utils.IRIFactory;

@Repository
public class CharacterRepositoryImpl implements CharacterRepository{
    private final SparqlEndpoint sparqlEndpoint;

    public CharacterRepositoryImpl(SparqlEndpoint sparqlEndpoint){
        this.sparqlEndpoint = sparqlEndpoint;
    }


    @Override
    public TupleQueryResult getMarvelCharacters() {
        return sparqlEndpoint.executeQuery(
            "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
            "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
            "select ?character ?name ?imageUrl ?description where {" +
            "    ?character a IRI:Marvel ." +
            "    ?character foaf:name ?name ." +
            "    ?character IRI:hasImageUrl ?imageUrl ." +
            "    OPTIONAL{?character IRI:hasDescription ?description}" +
            "}"
        );
    }

    @Override
    public TupleQueryResult getPokemonCharacters() {
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
}
