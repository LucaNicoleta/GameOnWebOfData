package uaic.fii.MarvelMonPlay.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Marvel extends Character{
    @JsonProperty("imageURL")
    private String imageURL;

    @JsonProperty("description")
    private final String description;

    public Marvel(String name, String imageURL, String description) {
        super(name);
        this.imageURL = imageURL;
        this.description = description;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return "Marvel{" +
                "imageURL='" + imageURL + '\'' +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
