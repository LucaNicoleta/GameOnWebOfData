package uaic.fii.MarvelMonPlay.repositories.impl;

import org.eclipse.rdf4j.query.TupleQueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uaic.fii.MarvelMonPlay.endpoints.SparqlEndpoint;
import uaic.fii.MarvelMonPlay.exceptions.ResourceNotFoundException;
import uaic.fii.MarvelMonPlay.models.characters.Marvel;
import uaic.fii.MarvelMonPlay.models.levels.Level;
import uaic.fii.MarvelMonPlay.models.levels.Stage;
import uaic.fii.MarvelMonPlay.models.players.Player;
import uaic.fii.MarvelMonPlay.repositories.MarvelRepository;
import uaic.fii.MarvelMonPlay.repositories.PlayerRepository;
import uaic.fii.MarvelMonPlay.services.impl.SceneService;
import uaic.fii.MarvelMonPlay.utils.IRIFactory;

@Service
public class PlayerRepositoryImpl implements PlayerRepository {

    private final SparqlEndpoint sparqlEndpoint;


    SceneService sceneService;

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
                "IRI:" + player.RES_IDENTIFIER + " IRI:hasEncryptedPassword " + "\"" + player.getPassword() + "\"" + ". " +
                "IRI:" + player.RES_IDENTIFIER + " IRI:hasMarvelCharacter IRI:" + player.getMarvelCharacter().RES_IDENTIFIER + ". " +
                "IRI:" + player.RES_IDENTIFIER + " IRI:hasLevel " + player.getLevel().getStage().ordinal() + ". " +
                "IRI:" + player.RES_IDENTIFIER + " IRI:hasAppUserRole " + player.getAppUserRole().ordinal() + ". " +
            "}"
        );

        if(cascadeSave){
            marvelRepository.save(player.getMarvelCharacter(), true);
        }
    }

    public void update(Player player, boolean cascadeSave) {
        sparqlEndpoint.executeUpdate(
            "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
            "PREFIX vgo: <http://purl.org/net/VideoGameOntology#>" +
            "DELETE {IRI:" + player.RES_IDENTIFIER + " ?p ?o} " +
            "WHERE {IRI:" + player.RES_IDENTIFIER + " ?p ?o}" +
            "INSERT DATA {" +
                "IRI:" + player.RES_IDENTIFIER + " a " + "vgo:Player" + "; " +
                "vgo:username " + "\"" + player.getUsername() + "\"" + ". " +
                "IRI:" + player.RES_IDENTIFIER + " IRI:hasEncryptedPassword " + "\"" + player.getPassword() + "\"" + ". " +
                "IRI:" + player.RES_IDENTIFIER + " IRI:hasMarvelCharacter IRI:" + player.getMarvelCharacter().RES_IDENTIFIER + ". " +
                "IRI:" + player.RES_IDENTIFIER + " IRI:hasLevel " + player.getLevel().getStage().ordinal() + ". " +
                "IRI:" + player.RES_IDENTIFIER + " IRI:hasAppUserRole " + player.getAppUserRole().ordinal() + ". " +
            "}"
        );

        if(cascadeSave){
            marvelRepository.save(player.getMarvelCharacter(), true);
        }
    }

    @Override
    public void updateLevel(String PLAYER_RES_IDENTIFIER, Level level) {
        sparqlEndpoint.executeUpdate(
                "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
                        "PREFIX vgo: <http://purl.org/net/VideoGameOntology#>" +
                        "DELETE {IRI:" + PLAYER_RES_IDENTIFIER + " IRI:hasLevel ?o} " +
                        "WHERE {IRI:" + PLAYER_RES_IDENTIFIER + " IRI:hasLevel ?o}" +
                        "INSERT DATA {" +
                        "IRI:" + PLAYER_RES_IDENTIFIER + " IRI:hasLevel " + level.getStage().ordinal() + "}"
        );
    }

    @Override
    public void setMarvelCharacter(String PLAYER_RES_IDENTIFIER, Marvel marvel, boolean cascadeSave) {
        sparqlEndpoint.executeUpdate(
                "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
                        "PREFIX vgo: <http://purl.org/net/VideoGameOntology#>" +
                        "DELETE {IRI:" + PLAYER_RES_IDENTIFIER + " IRI:hasMarvelCharacter ?o} " +
                        "WHERE {IRI:" + PLAYER_RES_IDENTIFIER + " IRI:hasMarvelCharacter ?o}" +
                        "INSERT DATA {" +
                        "IRI:" + PLAYER_RES_IDENTIFIER + " IRI:hasMarvelCharacter IRI:" + marvel.RES_IDENTIFIER + "}"
        );

        if(cascadeSave){
            marvelRepository.save(marvel, true);
        }

    }

    @Override
    public void updateForRestart(String PLAYER_RES_IDENTIFIER) throws ResourceNotFoundException {
        Level level = new Level(Stage.WATER, sceneService.findByResIdentifier("S1"));
        sparqlEndpoint.executeUpdate(
                "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
                        "PREFIX vgo: <http://purl.org/net/VideoGameOntology#>" +
                        "DELETE {IRI:" + PLAYER_RES_IDENTIFIER + " IRI:hasMarvelCharacter ?o ." +
                        "IRI:" + PLAYER_RES_IDENTIFIER + " hasLevel ?o} " +
                        "WHERE {IRI:" + PLAYER_RES_IDENTIFIER + " ?p ?o}" +
                        "INSERT DATA {" +
                        "IRI:" + PLAYER_RES_IDENTIFIER + " IRI:hasLevel " + level.getStage().ordinal() + "}"
        );

    }

    public void delete(Player player) {
        sparqlEndpoint.executeUpdate(
                "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
                "DELETE {IRI:" + player.RES_IDENTIFIER + " ?p ?o} " +
                "WHERE {IRI:" + player.RES_IDENTIFIER + " ?p ?o}"
        );
    }
}
