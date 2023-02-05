package uaic.fii.MarvelMonPlay.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uaic.fii.MarvelMonPlay.api.mappings.CurrentGameStateDto;
import uaic.fii.MarvelMonPlay.api.mappings.PlayerDto;
import uaic.fii.MarvelMonPlay.exceptions.AbilityNotFoundException;
import uaic.fii.MarvelMonPlay.exceptions.PokemonNotFoundException;
import uaic.fii.MarvelMonPlay.exceptions.ResourceNotFoundException;
import uaic.fii.MarvelMonPlay.models.characters.Marvel;
import uaic.fii.MarvelMonPlay.models.characters.Pokemon;
import uaic.fii.MarvelMonPlay.models.levels.Level;
import uaic.fii.MarvelMonPlay.models.scenes.Scene;
import uaic.fii.MarvelMonPlay.services.EnemyGeneratorService;
import uaic.fii.MarvelMonPlay.services.MarvelService;
import uaic.fii.MarvelMonPlay.services.impl.SceneService;

import java.util.List;

@RestController
@RequestMapping(GameController.BASE_URL)
public class GameController {
    public static final String BASE_URL = "/api/v1/game";
    private final EnemyGeneratorService enemyGeneratorService;
    private final SceneService sceneService;

    @Autowired
    private MarvelService marvelService;

    public GameController(EnemyGeneratorService enemyGeneratorService, SceneService sceneService){
        this.enemyGeneratorService = enemyGeneratorService;
        this.sceneService = sceneService;
    }
    @GetMapping("/npc/{level}")
    @ResponseStatus(HttpStatus.OK)
    public List<Marvel> getMarvelNPC(@PathVariable Level level){
        return enemyGeneratorService.generateMarvelEnemy(level);
    }

    @GetMapping("/start")
    @ResponseStatus(HttpStatus.OK)
    public Scene startGame() throws ResourceNotFoundException {
        return sceneService.findByResIdentifier("S1");
    }

    @GetMapping("/nextScene")
    @ResponseStatus(HttpStatus.OK)
    public Scene nextScene(@RequestBody CurrentGameStateDto currentGameStateDto) {
        String currentSceneIdentifier = currentGameStateDto.getCurrentSceneResIdentifier();
        String marvelIdentifier = currentGameStateDto.getMarvelResIdentifier();
        String chosenOption = currentGameStateDto.getChosenOption();
        return sceneService.nextScene(currentSceneIdentifier, marvelIdentifier, chosenOption);
    }

    @GetMapping("/pokemonInventory")
    @ResponseStatus(HttpStatus.OK)
    public List<Pokemon> getPokemonInventory(@RequestBody PlayerDto playerDto) throws PokemonNotFoundException, AbilityNotFoundException {
        String marvelResIdentifier = playerDto.getMarvelResIdentifier();
        return marvelService.findPokemonInventory(marvelResIdentifier);
    }
}
