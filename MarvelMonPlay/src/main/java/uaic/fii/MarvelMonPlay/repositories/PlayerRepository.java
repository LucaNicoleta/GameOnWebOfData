package uaic.fii.MarvelMonPlay.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import uaic.fii.MarvelMonPlay.models.players.Player;

import java.util.Optional;

public interface PlayerRepository {
    Optional<Player> findPlayerByUsername(String username);
    boolean existsPlayerByUsername(String username);
    void save(Player player);
    void delete(Player player);
}
