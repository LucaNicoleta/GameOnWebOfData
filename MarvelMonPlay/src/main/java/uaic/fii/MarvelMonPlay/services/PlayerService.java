package uaic.fii.MarvelMonPlay.services;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import uaic.fii.MarvelMonPlay.models.players.Player;

public interface PlayerService {
    Player findPlayerByUsername(String username) throws UsernameNotFoundException;
    void save(Player player, boolean cascadeSave);
    void delete(Player player);
}
