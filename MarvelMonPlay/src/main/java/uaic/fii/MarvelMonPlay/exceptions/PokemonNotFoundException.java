package uaic.fii.MarvelMonPlay.exceptions;

public class PokemonNotFoundException extends ResourceNotFoundException{
    public PokemonNotFoundException(String msg) {
        super(msg);
    }
}
