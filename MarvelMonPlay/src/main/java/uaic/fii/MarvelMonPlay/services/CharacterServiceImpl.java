package uaic.fii.MarvelMonPlay.services;

import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.springframework.stereotype.Service;
import uaic.fii.MarvelMonPlay.endpoints.SparqlEndpoint;
import uaic.fii.MarvelMonPlay.models.Character;
import uaic.fii.MarvelMonPlay.models.Marvel;

import java.util.ArrayList;
import java.util.List;

@Service
public class CharacterServiceImpl implements CharacterService{
    private final SparqlEndpoint endpoint;

    public CharacterServiceImpl(SparqlEndpoint endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public List<Character> getMarvelCharacters() {
        List<Character> marvelList = new ArrayList<>();
        TupleQueryResult tupleQueryResult = endpoint.executeQuery(
                        "PREFIX IRI: <http://IRI#>" +
                        "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
                        "select ?character ?name ?imageUrl ?description where {" +
                        "    ?character a IRI:Marvel ." +
                        "    ?character foaf:name ?name ." +
                        "    ?character IRI:hasImageUrl ?imageUrl ." +
                        "    OPTIONAL{?character IRI:hasDescription ?description}" +
                        "}");

        while (tupleQueryResult.hasNext()) {
            BindingSet bindingSet = tupleQueryResult.next();
            String name = bindingSet.getValue("name").stringValue();
            String imageUrl = bindingSet.getValue("imageUrl").stringValue();
            String description = "";
            if(bindingSet.getValue("description") != null){
                description = bindingSet.getValue("description").stringValue();
            }

            marvelList.add(new Marvel(name, imageUrl, description));
        }
        marvelList.forEach(System.out::println);
        return marvelList;
    }

    @Override
    public List<Character> getPokemonCharacters() {
        return null;
    }
}
