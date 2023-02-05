package uaic.fii.MarvelMonPlay.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uaic.fii.MarvelMonPlay.api.mappings.CurrentGameStateDto;
import uaic.fii.MarvelMonPlay.api.mappings.InventoryDto;
import uaic.fii.MarvelMonPlay.exceptions.AbilityNotFoundException;
import uaic.fii.MarvelMonPlay.exceptions.ItemNotFoundException;
import uaic.fii.MarvelMonPlay.exceptions.PokemonNotFoundException;
import uaic.fii.MarvelMonPlay.exceptions.ResourceNotFoundException;
import uaic.fii.MarvelMonPlay.models.characters.Marvel;
import uaic.fii.MarvelMonPlay.models.levels.Level;
import uaic.fii.MarvelMonPlay.models.levels.Stage;
import uaic.fii.MarvelMonPlay.models.players.Player;
import uaic.fii.MarvelMonPlay.models.scenes.Scene;
import uaic.fii.MarvelMonPlay.services.EnemyGeneratorService;
import uaic.fii.MarvelMonPlay.services.InventoryService;
import uaic.fii.MarvelMonPlay.services.PlayerService;
import uaic.fii.MarvelMonPlay.services.impl.SceneService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(GameController.BASE_URL)
public class GameController {
    public static final String BASE_URL = "/api/v1/game";
    private final EnemyGeneratorService enemyGeneratorService;
    @Autowired
    private PlayerService playerService;
    private final SceneService sceneService;

    @Autowired
    private InventoryService inventoryService;

    public GameController(EnemyGeneratorService enemyGeneratorService, SceneService sceneService) {
        this.enemyGeneratorService = enemyGeneratorService;
        this.sceneService = sceneService;
    }

    @GetMapping("/npc/{level}")
    @ResponseStatus(HttpStatus.OK)
    public List<Marvel> getMarvelNPC(@PathVariable Level level) {
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

    @GetMapping("/restart")
    @ResponseStatus(HttpStatus.OK)
    public Scene restartGame(HttpServletRequest request) throws ResourceNotFoundException {
        Principal principal = request.getUserPrincipal();
        String username = principal.getName();
        Player player = playerService.findPlayerByUsername(username);
        player.setMarvelCharacter(new Marvel(" "," ", " ", " "));
        player.setLevel(new Level(Stage.WATER, sceneService.findByResIdentifier("S1")));
        playerService.update(player, true);
        return player.getLevel().getScene();
    }

    @GetMapping("/inventory")
    @ResponseBody
    public InventoryDto getPokemonInventory(HttpServletRequest request) throws PokemonNotFoundException, AbilityNotFoundException, ItemNotFoundException {
        Principal principal = request.getUserPrincipal();
        String username = principal.getName();
        Player player = playerService.findPlayerByUsername(username);
        return inventoryService.getMarvelInventory(player.getMarvelCharacter().RES_IDENTIFIER);
    }

    // This is for test
    @GetMapping("/inventoryWithoutAuthentication/{marvelIdentifier}")
    @ResponseBody
    public InventoryDto getPokemonInventoryWithoutAuthentication(@PathVariable String marvelIdentifier) throws PokemonNotFoundException, AbilityNotFoundException, ItemNotFoundException {
        return inventoryService.getMarvelInventory(marvelIdentifier);
    }
}