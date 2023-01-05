package uaic.fii.MarvelMonPlay.repositories;

import org.eclipse.rdf4j.query.TupleQueryResult;
import uaic.fii.MarvelMonPlay.endpoints.SparqlEndpoint;

public class CharacterRepositoryImpl implements CharacterRepository{
    private final SparqlEndpoint sparqlEndpoint;

    public CharacterRepositoryImpl(SparqlEndpoint sparqlEndpoint){
        this.sparqlEndpoint = sparqlEndpoint;
    }


    @Override
    public TupleQueryResult getMarvelCharacters() {
        return sparqlEndpoint.executeQuery(
                "PREFIX IRI: <http://IRI#>" +
                        "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
                        "select ?character ?name ?imageUrl ?description where {" +
                        "    ?character a IRI:Marvel ." +
                        "    ?character foaf:name ?name ." +
                        "    ?character IRI:hasImageUrl ?imageUrl ." +
                        "    OPTIONAL{?character IRI:hasDescription ?description}" +
                        "}");
    }

    @Override
    public TupleQueryResult getPokemonCharacters() {
        return null;
    }
}
