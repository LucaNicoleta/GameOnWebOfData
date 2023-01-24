package uaic.fii.MarvelMonPlay.services;

import uaic.fii.MarvelMonPlay.models.players.Player;

import java.util.List;

public interface PlayerService {
    Player findPlayer(String username, String encryptedPassword);
    void saveOrUpdate(Player marvel, boolean cascadeSaveOrUpdate);
    void delete(Player marvel);
}
