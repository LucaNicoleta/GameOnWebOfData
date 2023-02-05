package uaic.fii.MarvelMonPlay.api.mappings;

public class PlayerDto {
    private String playerResIdentifier;
    private String marvelResIdentifier;

    private PlayerDto(String marvelResIdentifier, String playerResIdentifier){
        this.marvelResIdentifier = marvelResIdentifier;
        this.playerResIdentifier = playerResIdentifier;
    }

    public PlayerDto withMarvelResIdentifier(String marvelResIdentifier){
        this.marvelResIdentifier = marvelResIdentifier;
        return this;
    }

    public PlayerDto withPlayerResIdentifier(String playerResIdentifier){
        this.playerResIdentifier = playerResIdentifier;
        return this;
    }

    public String getPlayerResIdentifier() {
        return playerResIdentifier;
    }

    public String getMarvelResIdentifier() {
        return marvelResIdentifier;
    }

    public PlayerDto build(){
        return new PlayerDto(marvelResIdentifier, playerResIdentifier);
    }
}
