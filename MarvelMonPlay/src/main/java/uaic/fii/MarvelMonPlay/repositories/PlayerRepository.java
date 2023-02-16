package uaic.fii.MarvelMonPlay.repositories;

import org.eclipse.rdf4j.query.TupleQueryResult;
import uaic.fii.MarvelMonPlay.exceptions.ResourceNotFoundException;
import uaic.fii.MarvelMonPlay.models.characters.Marvel;
import uaic.fii.MarvelMonPlay.models.levels.Level;
import uaic.fii.MarvelMonPlay.models.players.AppUserRole;
import uaic.fii.MarvelMonPlay.models.players.Player;

public interface PlayerRepository {
    TupleQueryResult findPlayerByUsername(String username);
    void createAccount(String userName, String encryptedPassword, AppUserRole appUserRole);
    void save(Player player, boolean cascadeSave);
    void delete(Player player);
    void update(Player player, boolean cascadeSave);

    void updateLevel(String PLAYER_RES_IDENTIFIER, Level level);
    void updateProgressInGame(String Session_res, String new_scene_res);
    public void createNewSessionForPlayer(String res_identifier);
    void setMarvelCharacter(String PLAYER_RES_IDENTIFIER, Marvel marvel, boolean cascadeSave);
    TupleQueryResult getcurrentSceneForPlayer(String res_identifier);
    void updateForRestart(String PLAYER_RES_IDENTIFIER) throws ResourceNotFoundException;
}
