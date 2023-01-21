package uaic.fii.MarvelMonPlay.services;

import uaic.fii.MarvelMonPlay.models.characters.Marvel;

import java.util.List;

public interface MarvelService {
    List<Marvel> findAll();
    void save(Marvel marvel, boolean cascadeSaveOrUpdate);
    void delete(Marvel marvel);
}
