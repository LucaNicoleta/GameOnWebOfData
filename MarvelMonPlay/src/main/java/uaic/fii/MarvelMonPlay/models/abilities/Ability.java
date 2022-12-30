package uaic.fii.MarvelMonPlay.models.abilities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Ability {
    @JsonProperty("name")
    private final String name;
    @JsonProperty("description")
    private String description;

    public Ability(String name, String description) {
        this.name = name;
        this.description = description;
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
