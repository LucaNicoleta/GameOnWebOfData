package uaic.fii.MarvelMonPlay.externalApi;

import uaic.fii.MarvelMonPlay.exceptions.ResourceNotFoundException;
import uaic.fii.MarvelMonPlay.models.Marvel;

public interface MarvelApi {
    Marvel getMarvelCharacter(String characterNameStartsWith) throws ResourceNotFoundException;
}
