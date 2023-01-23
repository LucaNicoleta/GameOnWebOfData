package uaic.fii.MarvelMonPlay.utils;

import uaic.fii.MarvelMonPlay.models.characters.Marvel;

public class RegisterDto {
    private final String username;
    private final String password;
    private final Marvel marvelCharacter;
    private final String  RES_IDENTIFIER;;

    public RegisterDto(String usernameOrEmail, String password, Marvel marvelCharacter, String RES_IDENTIFIER) {
        this.RES_IDENTIFIER = RES_IDENTIFIER;
        this.username = usernameOrEmail;
        this.password = password;
        this.marvelCharacter = marvelCharacter;
    }

    public String getRES_IDENTIFIER() {
        return RES_IDENTIFIER;
    }

    public String getUsername() {
        return username;
    }

    public Marvel getMarvelCharacter() {
        return marvelCharacter;
    }

    public String getPassword() {
        return password;
    }
}
