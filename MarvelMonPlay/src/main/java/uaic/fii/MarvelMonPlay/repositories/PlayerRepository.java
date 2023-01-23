package uaic.fii.MarvelMonPlay.repositories;

import org.eclipse.rdf4j.query.TupleQueryResult;
import uaic.fii.MarvelMonPlay.models.characters.Marvel;
import uaic.fii.MarvelMonPlay.models.players.Player;

public interface PlayerRepository {
    Player findPlayerByUsername(String username);
    boolean existsPlayerByUsername(String username);
    void saveOrUpdate(Player player, boolean cascadeSaveOrUpdate);
    void delete(Player player);
}
