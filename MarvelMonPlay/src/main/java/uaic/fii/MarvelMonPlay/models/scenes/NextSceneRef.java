package uaic.fii.MarvelMonPlay.models.scenes;

import java.util.List;



public class NextSceneRef {
    public String criteria;

    public String RES_IDENTIFIER;

    public List<String> posibleScenesRES;

    public NextSceneRef(String RES_IDENTIFIER, String criteria, List<String> nextScenes){

        this.criteria = criteria;
        this.RES_IDENTIFIER = RES_IDENTIFIER;
        this.posibleScenesRES = nextScenes;
    }
}