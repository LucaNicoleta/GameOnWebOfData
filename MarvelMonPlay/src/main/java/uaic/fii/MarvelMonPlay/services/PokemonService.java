package uaic.fii.MarvelMonPlay.services;

import uaic.fii.MarvelMonPlay.models.characters.Pokemon;

import java.util.List;

public interface PokemonService {
    List<Pokemon> findAll();
    void save(Pokemon pokemon, boolean cascadeSaveOrUpdate);
    void delete(Pokemon pokemon);
}
