package uaic.fii.MarvelMonPlay.api.mappings;

public class CurrentGameStateDto {
    private final String currentSceneResIdentifier;
    private final String marvelResIdentifier;
    private final String chosenOption;

    public CurrentGameStateDto(String currentSceneResIdentifier, String marvelResIdentifier, String chosenOption) {
        this.currentSceneResIdentifier = currentSceneResIdentifier;
        this.marvelResIdentifier = marvelResIdentifier;
        this.chosenOption = chosenOption;
    }

    public String getCurrentSceneResIdentifier() {
        return currentSceneResIdentifier;
    }

    public String getMarvelResIdentifier() {
        return marvelResIdentifier;
    }

    public String getChosenOption() {
        return chosenOption;
    }
}
