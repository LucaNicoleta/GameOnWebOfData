package uaic.fii.MarvelMonPlay.services;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import uaic.fii.MarvelMonPlay.exceptions.ResourceNotFoundException;
import uaic.fii.MarvelMonPlay.models.characters.Marvel;
import uaic.fii.MarvelMonPlay.models.levels.Level;
import uaic.fii.MarvelMonPlay.models.players.Player;

public interface PlayerService {
    Player findPlayerByUsername(String username) throws UsernameNotFoundException;
    void save(Player player, boolean cascadeSave);
    void update(Player player, boolean cascadeSave);
    void updateForRestart(String PLAYER_RES_IDENTIFIER) throws ResourceNotFoundException;
    void setMarvelCharacter(String PLAYER_RES_IDENTIFIER, Marvel marvel, boolean cascadeSave);
    void updateLevel(String PLAYER_RES_IDENTIFIER, Level level);
    void delete(Player player);
}
