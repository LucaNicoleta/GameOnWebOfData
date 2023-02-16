package uaic.fii.MarvelMonPlay.repositories.impl;

import org.eclipse.rdf4j.query.TupleQueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uaic.fii.MarvelMonPlay.endpoints.SparqlEndpoint;
import uaic.fii.MarvelMonPlay.exceptions.ResourceNotFoundException;
import uaic.fii.MarvelMonPlay.models.characters.Marvel;
import uaic.fii.MarvelMonPlay.models.levels.Level;
import uaic.fii.MarvelMonPlay.models.levels.Stage;
import uaic.fii.MarvelMonPlay.models.players.AppUserRole;
import uaic.fii.MarvelMonPlay.models.players.Player;
import uaic.fii.MarvelMonPlay.repositories.MarvelRepository;
import uaic.fii.MarvelMonPlay.repositories.PlayerRepository;
import uaic.fii.MarvelMonPlay.services.impl.SceneService;
import uaic.fii.MarvelMonPlay.utils.IRIFactory;

@Service
public class PlayerRepositoryImpl implements PlayerRepository {

    private final SparqlEndpoint sparqlEndpoint;

    @Autowired
    private SceneService sceneService;

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
                "SELECT ?player ?encryptedPassword ?marvelCharacter ?session ?appUserRole " +
                "WHERE {" +
                "  ?player a vgo:Player. " +
                "  ?player vgo:username \"" + username + "\". " +
                "  ?player IRI:hasEncryptedPassword ?encryptedPassword ." + //TODO: encryptedPassword does not seem to be really encrypted
                "  OPTIONAL{?player IRI:hasMarvelCharacter ?marvelCharacter .}" +
                "  OPTIONAL{?player IRI:isPlayerInSession ?session .}" +
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
                //"IRI:" + player.RES_IDENTIFIER + " IRI:hasLevel " + player.getLevel().getStage().ordinal() + ". " +
                "IRI:" + player.RES_IDENTIFIER + " IRI:hasAppUserRole " + player.getAppUserRole().ordinal() + ". " +
            "}"
        );

        if(cascadeSave){
            marvelRepository.save(player.getMarvelCharacter(), true);
        }
    }

    public void createAccount(String userName, String encryptedPassword, AppUserRole appUserRole) {
        sparqlEndpoint.executeUpdate(
            "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
            "PREFIX vgo: <http://purl.org/net/VideoGameOntology#>" +
            "INSERT DATA {" +
                "IRI:" + userName + " a " + "vgo:Player" + "; " +
                "vgo:username " + "\"" + userName + "\"" + ". " +
                "IRI:" + userName + " IRI:hasEncryptedPassword " + "\"" + encryptedPassword + "\"" + ". " +
                "IRI:" + userName + " IRI:hasLevel IRI:Level_Water. " +
                "IRI:" + userName + " IRI:hasAppUserRole " + appUserRole.ordinal() + ". " +
            "}"
        );
    }
    public void createNewSessionForPlayer(String res_identifier){
        String s =            "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
        "PREFIX vgo: <http://purl.org/net/VideoGameOntology#>" +
        "PREFIX foaf: <http://xmlns.com/foaf/0.1/>"+
        "INSERT DATA {" +
            "IRI:" + res_identifier + "Session foaf:a " + "vgo:Session" + ". " +
            "IRI:" + res_identifier + " IRI:isPlayerInSession "  + res_identifier + "Session "  + ". " +
            "IRI:" + res_identifier + "Session" + " IRI:currentScene IRI:S1." +
        "}";
        System.out.println(s);
        sparqlEndpoint.executeUpdate(
            "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
            "PREFIX vgo: <http://purl.org/net/VideoGameOntology#>" +
            "PREFIX foaf: <http://xmlns.com/foaf/0.1/>"+
            "INSERT DATA {" +
                "IRI:" + res_identifier + "Session foaf:a " + "vgo:Session" + ". " +
                "IRI:" + res_identifier + " IRI:isPlayerInSession IRI:"  + res_identifier + "Session "  + ". " +
                "IRI:" + res_identifier + "Session" + " IRI:currentScene IRI:S1." +
            "}"
        );
    }
    public void updateProgressInGame(String Session_res, String new_scene_res){
        sparqlEndpoint.executeUpdate(
            "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
            "delete where{"+
            "IRI:"+Session_res+" IRI:currentScene ?o"+
            "};"+
            "INSERT data{"+
            "IRI:"+Session_res+" IRI:currentScene IRI:"+new_scene_res+
            "};"
        );
    }
    public TupleQueryResult getcurrentSceneForPlayer(String res_identifier){
        return sparqlEndpoint.executeQuery(
                "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
                "PREFIX vgo: <http://purl.org/net/VideoGameOntology#>" +
                "PREFIX foaf: <http://xmlns.com/foaf/0.1/>"+
                "SELECT ?scene " +
                "WHERE {" +
                "  ?player a vgo:Player. " +
                "  ?player vgo:username \"" + res_identifier + "\". " +
                "  ?player IRI:isPlayerInSession ?session ." + 

                "  ?session IRI:currentScene ?scene ." +
            "}"
        );
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
                //"IRI:" + player.RES_IDENTIFIER + " IRI:hasLevel " + player.getLevel().getStage().ordinal() + ". " +
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
                        "WHERE {IRI:" + PLAYER_RES_IDENTIFIER + " IRI:hasMarvelCharacter ?o};" +
                        "INSERT DATA {" +
                        "IRI:" + PLAYER_RES_IDENTIFIER + " IRI:hasMarvelCharacter IRI:" + marvel.RES_IDENTIFIER + "}"
        );

        if(cascadeSave){
            marvelRepository.save(marvel, true);
        }

    }

    @Override
    public void updateForRestart(String PLAYER_RES_IDENTIFIER) throws ResourceNotFoundException {
        Level level = new Level(Stage.WATER, sceneService.getFirstScene());
        sparqlEndpoint.executeUpdate(
                "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
                        "PREFIX vgo: <http://purl.org/net/VideoGameOntology#>" +
                        "DELETE {IRI:" + PLAYER_RES_IDENTIFIER + " IRI:hasMarvelCharacter ?o ." +
                        "IRI:" + PLAYER_RES_IDENTIFIER + " IRI:hasLevel ?o} " +
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
