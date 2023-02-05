package uaic.fii.MarvelMonPlay.repositories;

import org.eclipse.rdf4j.query.TupleQueryResult;
import uaic.fii.MarvelMonPlay.models.players.Player;

public interface PlayerRepository {
    TupleQueryResult findPlayerByUsername(String username);
    void save(Player player, boolean cascadeSave);
    void delete(Player player);
    void update(Player player, boolean b);
}
