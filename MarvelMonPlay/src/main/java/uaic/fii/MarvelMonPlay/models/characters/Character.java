package uaic.fii.MarvelMonPlay.models.characters;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Character {
    @JsonProperty("name")
    protected final String name;

    public Character(String name) {
        this.name = name;
    }
}
