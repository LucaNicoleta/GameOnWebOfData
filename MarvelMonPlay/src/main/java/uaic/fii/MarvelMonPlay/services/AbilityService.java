package uaic.fii.MarvelMonPlay.services;

import uaic.fii.MarvelMonPlay.models.abilities.Ability;

import java.util.List;

public interface AbilityService {
    List<Ability> findAll();
    void saveOrUpdate(Ability ability);
    void delete(Ability ability);
}
