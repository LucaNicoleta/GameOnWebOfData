package uaic.fii.MarvelMonPlay.services;

import uaic.fii.MarvelMonPlay.models.characters.Character;

import java.util.List;

public interface CharacterService {
    List<Character> getMarvelCharacters();
    List<Character> getPokemonCharacters();
}
