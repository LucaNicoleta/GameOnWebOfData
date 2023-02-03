package uaic.fii.MarvelMonPlay.models.scenes;

import java.util.List;

public class Scene {
    public String text;
    public String imageURL;
    public String RES_IDENTIFIER;
    public SceneTypes sceneType;
    public List<Option> options;
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
}
