package uaic.fii.MarvelMonPlay.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uaic.fii.MarvelMonPlay.models.characters.Marvel;
import uaic.fii.MarvelMonPlay.models.levels.Level;
import uaic.fii.MarvelMonPlay.services.EnemyGeneratorService;

import java.util.List;

@RestController
@RequestMapping(GameController.BASE_URL)
public class GameController {
    public static final String BASE_URL = "/api/v1/game";
    private final EnemyGeneratorService enemyGeneratorService;

    public GameController(EnemyGeneratorService enemyGeneratorService){
        this.enemyGeneratorService = enemyGeneratorService;
    }
    @GetMapping("/npc/{level}")
    @ResponseStatus(HttpStatus.OK)
    public List<Marvel> getMarvelNPC(@PathVariable Level level){
        return enemyGeneratorService.generateMarvelEnemy(level);
    }
}
