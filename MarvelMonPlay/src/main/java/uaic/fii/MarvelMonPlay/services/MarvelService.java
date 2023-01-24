package uaic.fii.MarvelMonPlay.services;

import uaic.fii.MarvelMonPlay.models.characters.Marvel;

import java.util.List;

public interface MarvelService {
    List<Marvel> findAll();
    void save(Marvel marvel, boolean cascadeSave);
    void update(Marvel marvel, boolean cascadeUpdate);
    void delete(Marvel marvel);
}
