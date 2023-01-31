package uaic.fii.MarvelMonPlay.repositories.impl;

import org.eclipse.rdf4j.query.TupleQueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uaic.fii.MarvelMonPlay.endpoints.SparqlEndpoint;
import uaic.fii.MarvelMonPlay.managers.PasswordManager;
import uaic.fii.MarvelMonPlay.models.players.Player;
import uaic.fii.MarvelMonPlay.repositories.MarvelRepository;
import uaic.fii.MarvelMonPlay.repositories.PlayerRepository;
import uaic.fii.MarvelMonPlay.utils.IRIFactory;

@Service
public class PlayerRepositoryImpl implements PlayerRepository {

    private final SparqlEndpoint sparqlEndpoint;

    @Autowired
    private MarvelRepository marvelRepository;

    public PlayerRepositoryImpl(SparqlEndpoint sparqlEndpoint) {
        this.sparqlEndpoint = sparqlEndpoint;
    }

    @Override
    public TupleQueryResult findPlayerByUsername(String username) {
        return sparqlEndpoint.executeQuery(
                "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
                "PREFIX vgo: <http://purl.org/net/VideoGameOntology#>" +
                "SELECT ?player ?encryptedPassword ?marvelCharacter ?level ?appUserRole " +
                "WHERE {" +
                "  ?player a vgo:Player. " +
                "  ?player vgo:username \"" + username + "\". " +
                "  ?player IRI:hasEncryptedPassword ?encryptedPassword ." + //TODO: encryptedPassword does not seem to be really encrypted
                "  ?player IRI:hasMarvelCharacter ?marvelCharacter ." +
                "  ?player IRI:hasLevel ?level ." +
                "  ?player IRI:hasAppUserRole ?appUserRole ." +
            "}"
        );
    }

    public void save(Player player, boolean cascadeSave) {
        sparqlEndpoint.executeUpdate(
            "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
            "PREFIX vgo: <http://purl.org/net/VideoGameOntology#>" +
            "INSERT DATA {" +
                "IRI:" + player.RES_IDENTIFIER + " a " + "vgo:Player" + "; " +
                "vgo:username " + "\"" + player.getUsername() + "\"" + ". " +
                "IRI:" + player.RES_IDENTIFIER + " IRI:hasEncryptedPassword " + "\"" + player.getPassword() + "\"" + ". " +  //TODO: should encrypt the password?
                "IRI:" + player.RES_IDENTIFIER + " IRI:hasMarvelCharacter IRI:" + player.getMarvelCharacter().RES_IDENTIFIER + ". " +
                "IRI:" + player.RES_IDENTIFIER + " IRI:hasLevel " + player.getLevel().ordinal() + ". " +
                "IRI:" + player.RES_IDENTIFIER + " IRI:hasAppUserRole " + player.getAppUserRole().ordinal() + ". " +
            "}"
        );

        if(cascadeSave){
            marvelRepository.save(player.getMarvelCharacter(), true);
        }
    }

    public void delete(Player player) {
        sparqlEndpoint.executeUpdate(
                "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
                "DELETE {IRI:" + player.RES_IDENTIFIER + " ?p ?o} " +
                "WHERE {IRI:" + player.RES_IDENTIFIER + " ?p ?o}"
        );
    }
}
