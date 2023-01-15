package uaic.fii.MarvelMonPlay.models.players;

import uaic.fii.MarvelMonPlay.managers.PasswordManager;
import uaic.fii.MarvelMonPlay.models.characters.Marvel;

public class Player {
    public final String RES_IDENTIFIER;
    private String username;
    private final String encryptedPassword;
    private Marvel marvelCharacter;
    private int level;

    public Player(String RES_IDENTIFIER, String username, String password, Marvel marvelCharacter, int level) {
        this.RES_IDENTIFIER = RES_IDENTIFIER;
        this.username = username;
        this.encryptedPassword = new PasswordManager(password).getEncryptedPassword();
        this.marvelCharacter = marvelCharacter;
        this.level = level;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Marvel getMarvelCharacter() {
        return marvelCharacter;
    }

    public void setMarvelCharacter(Marvel marvelCharacter) {
        this.marvelCharacter = marvelCharacter;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
