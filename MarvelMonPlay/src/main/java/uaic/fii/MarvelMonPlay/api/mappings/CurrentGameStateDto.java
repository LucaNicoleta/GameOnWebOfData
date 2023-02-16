package uaic.fii.MarvelMonPlay.api.mappings;

import lombok.Data;

@Data
public class CurrentGameStateDto {
    private final String currentSceneResIdentifier;
    private final String playerResIdentifier;
    private final String chosenOption;

    public CurrentGameStateDto(String currentSceneResIdentifier, String marvelResIdentifier, String chosenOption) {
        this.currentSceneResIdentifier = currentSceneResIdentifier;
        this.playerResIdentifier = marvelResIdentifier;
        this.chosenOption = chosenOption;
    }

    public String getCurrentSceneResIdentifier() {
        return currentSceneResIdentifier;
    }

    public String getPlayerResIdentifier() {
        return playerResIdentifier;
    }

    public String getChosenOption() {
        return chosenOption;
    }
}
