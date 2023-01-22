package uaic.fii.MarvelMonPlay.repositories;

import org.eclipse.rdf4j.query.TupleQueryResult;
import uaic.fii.MarvelMonPlay.models.characters.Marvel;
import uaic.fii.MarvelMonPlay.models.players.Player;

public interface PlayerRepository {
    Player findPlayer(String username, String encryptedPassword);
    void saveOrUpdate(Player player, boolean cascadeSaveOrUpdate);
    void delete(Player player);
}
