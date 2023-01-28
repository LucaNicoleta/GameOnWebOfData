package uaic.fii.MarvelMonPlay.services;

import uaic.fii.MarvelMonPlay.models.characters.Marvel;
import uaic.fii.MarvelMonPlay.models.levels.Level;

import java.util.List;

public interface EnemyGeneratorService {
    List<Marvel> generateMarvelEnemy(Level level);
}
