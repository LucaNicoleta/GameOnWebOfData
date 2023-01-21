package uaic.fii.MarvelMonPlay.models.abilities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Ability {
    public final String RES_IDENTIFIER;
    @JsonProperty("name")
    private final String name;
    @JsonProperty("description")
    private String description;

    public Ability(String RES_IDENTIFIER, String name, String description) {
        this.RES_IDENTIFIER = RES_IDENTIFIER;
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Ability{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
