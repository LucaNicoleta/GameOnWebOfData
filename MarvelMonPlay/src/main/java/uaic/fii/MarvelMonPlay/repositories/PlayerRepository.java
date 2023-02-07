package uaic.fii.MarvelMonPlay.repositories;

import org.eclipse.rdf4j.query.TupleQueryResult;
import uaic.fii.MarvelMonPlay.exceptions.ResourceNotFoundException;
import uaic.fii.MarvelMonPlay.models.characters.Marvel;
import uaic.fii.MarvelMonPlay.models.levels.Level;
import uaic.fii.MarvelMonPlay.models.players.Player;

public interface PlayerRepository {
    TupleQueryResult findPlayerByUsername(String username);
    void save(Player player, boolean cascadeSave);
    void delete(Player player);
    void update(Player player, boolean cascadeSave);

    void updateLevel(String PLAYER_RES_IDENTIFIER, Level level);

    void setMarvelCharacter(String PLAYER_RES_IDENTIFIER, Marvel marvel, boolean cascadeSave);

    void updateForRestart(String PLAYER_RES_IDENTIFIER) throws ResourceNotFoundException;
}
