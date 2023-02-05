package uaic.fii.MarvelMonPlay.models.levels;

import uaic.fii.MarvelMonPlay.models.scenes.Scene;

public class Level {
    public Stage stage;
    public Scene scene;

    public Level(Stage stage, Scene scene){
        this.stage = stage;
        this.scene = scene;
    }

    public Stage getStage() {
        return stage;
    }

    public Scene getScene() {
        return scene;
    }
}
