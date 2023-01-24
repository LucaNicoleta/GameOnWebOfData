package uaic.fii.MarvelMonPlay.services;

import uaic.fii.MarvelMonPlay.models.characters.Pokemon;

import java.util.List;

public interface PokemonService {
    List<Pokemon> findAll();
    void save(Pokemon pokemon, boolean cascadeSave);
    void update(Pokemon pokemon, boolean cascadeUpdate);
    void delete(Pokemon pokemon);
}
