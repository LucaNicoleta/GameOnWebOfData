package uaic.fii.MarvelMonPlay.services;

import uaic.fii.MarvelMonPlay.models.characters.Marvel;

public interface EnemyGeneratorService {
    Marvel generateMarvelEnemy(int level);
}
