package uaic.fii.MarvelMonPlay.models.characters;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Character {
    public final String RES_IDENTIFIER;
    @JsonProperty("name")
    protected final String name;

    public Character(String RES_IDENTIFIER, String name) {
        this.RES_IDENTIFIER = RES_IDENTIFIER;
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
