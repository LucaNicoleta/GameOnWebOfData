package uaic.fii.MarvelMonPlay.models.scenes;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Scene {
    @JsonProperty("text")
    public String text;
    @JsonProperty("backgroundImage")
    public String imageURL;
    @JsonProperty("identifier")
    public String RES_IDENTIFIER;
    @JsonProperty("type")
    public SceneTypes sceneType;
    @JsonProperty("options")
    public List<Option> options;
    @JsonProperty("marvel")
    public String MarvelRES;
    public String requirement;
    public String refRES;
    public Scene(String RES_IDENTIFIER, String text, String imageURL, List<Option> options, SceneTypes sceneType, String MarvelRES, String req){

        this.text = text;
        this.RES_IDENTIFIER = RES_IDENTIFIER;
        this.imageURL = imageURL;
        this.options = options;
        this.sceneType = sceneType;
        this.MarvelRES = MarvelRES;
        this.requirement = req;
    }
    public void setReference(String refRES){
        this.refRES = refRES;
    }
    @Override
    public String toString() {
        return "{" +
                "imageURL='" + imageURL + '\'' +
                ", type=" + sceneType +
                ", marvel=" + MarvelRES +
                ",text='" + text + '\'' +
                '}';
    }
}
