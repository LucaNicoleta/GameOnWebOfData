package uaic.fii.MarvelMonPlay.repositories.impl;

import org.eclipse.rdf4j.query.TupleQueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uaic.fii.MarvelMonPlay.endpoints.SparqlEndpoint;
import uaic.fii.MarvelMonPlay.models.players.Player;
import uaic.fii.MarvelMonPlay.repositories.PlayerRepository;
import uaic.fii.MarvelMonPlay.services.PokemonService;
import uaic.fii.MarvelMonPlay.utils.IRIFactory;

import java.util.Optional;

@Service
public class PlayerRepositoryImpl implements PlayerRepository {

    private final SparqlEndpoint sparqlEndpoint;
    @Autowired
    private PokemonService pokemonService;

    public PlayerRepositoryImpl(SparqlEndpoint sparqlEndpoint) {
        this.sparqlEndpoint = sparqlEndpoint;
    }

    //TODO: findPlayer
    @Override
    public Optional<Player> findPlayerByUsername(String username) {
        TupleQueryResult result = sparqlEndpoint.executeQuery(
                "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
                        "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
                        "select ?RES_IDENTIFIER ?username ?encryptedPassword ?marvelCharacter ?level where {" +
                        "    ?character a IRI:Player ." +
                        "    ?character foaf:name ?name ." +
                        "    ?character IRI:hasImageUrl ?imageUrl ." +
                        "    OPTIONAL{?character IRI:hasDescription ?description}" +
                        "}"
        );
        return Optional.empty();
    }

    //TODO: verify if player exists
    public boolean existsPlayerByUsername(String username) {
//        TupleQueryResult result = sparqlEndpoint.executeQuery(
//                "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
//                        "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
//                        "select ?RES_IDENTIFIER ?username ?encryptedPassword ?marvelCharacter ?level where {" +
//                        "    ?character a IRI:Player ." +
//                        "    ?character foaf:name ?name ." +
//                        "    ?character IRI:hasImageUrl ?imageUrl ." +
//                        "    OPTIONAL{?character IRI:hasDescription ?description}" +
//                        "}"
//        );
        return false;
    }

    //TODO: saveOrUpdate
    public void save(Player player) {

    }

    public void delete(Player player) {
        sparqlEndpoint.executeUpdate(
                "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
                "DELETE {IRI:" + player.RES_IDENTIFIER + " ?p ?o} " +
                "WHERE {IRI:" + player.RES_IDENTIFIER + " ?p ?o}"
        );
    }
}
