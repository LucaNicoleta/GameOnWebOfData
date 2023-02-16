package uaic.fii.MarvelMonPlay.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import uaic.fii.MarvelMonPlay.api.mappings.CurrentGameStateDto;
import uaic.fii.MarvelMonPlay.api.mappings.InventoryDto;
import uaic.fii.MarvelMonPlay.exceptions.*;
import uaic.fii.MarvelMonPlay.models.characters.Marvel;
import uaic.fii.MarvelMonPlay.models.levels.Level;
import uaic.fii.MarvelMonPlay.models.levels.Stage;
import uaic.fii.MarvelMonPlay.models.players.Player;
import uaic.fii.MarvelMonPlay.models.scenes.Scene;
import uaic.fii.MarvelMonPlay.services.EnemyGeneratorService;
import uaic.fii.MarvelMonPlay.services.InventoryService;
import uaic.fii.MarvelMonPlay.services.NextScene;
import uaic.fii.MarvelMonPlay.services.PlayerService;
import uaic.fii.MarvelMonPlay.services.impl.OptionService;
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
    @Autowired
    private SceneService sceneService;
    @Autowired
    private OptionService optionService;
    private final NextScene nextSceneUseCase;

    @Autowired
    private InventoryService inventoryService;

    public GameController(EnemyGeneratorService enemyGeneratorService, NextScene nextSceneUseCase) {
        this.enemyGeneratorService = enemyGeneratorService;
        this.nextSceneUseCase = nextSceneUseCase;
    }

    @GetMapping("/npc/{stage}")
    @ResponseStatus(HttpStatus.OK)
    public List<Marvel> getMarvelNPC(@PathVariable Stage stage) {
        return enemyGeneratorService.generateMarvelEnemy(stage);
    }

    @GetMapping("/start")
    @ResponseStatus(HttpStatus.OK)
    public Scene startGame(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        System.out.println(principal);
  
        String username = principal.getName();     
         playerService.createNewSessionForPlayer(username);;
        try {
            return sceneService.getFirstScene();
        } catch (ResourceNotFoundException e) {
            return null;
        }
    }

    @PostMapping("/nextScene")
    @ResponseStatus(HttpStatus.OK)
    public Scene nextScene(@RequestBody CurrentGameStateDto currentGameStateDto) {
        System.out.println(currentGameStateDto);
        String currentSceneIdentifier = currentGameStateDto.getCurrentSceneResIdentifier();
        String playerIdentifier = currentGameStateDto.getPlayerResIdentifier();
        String chosenOption = currentGameStateDto.getChosenOption();
        Player player =playerService.findPlayerByUsername(playerIdentifier);
        String marvelIdentifier = player.getMarvelCharacter()!=null?player.getMarvelCharacter().RES_IDENTIFIER:"";

        Scene next = nextSceneUseCase.nextScene(currentSceneIdentifier, playerIdentifier, chosenOption);
        playerService.updatePlayerCurrentScene(player.getSessionRes(), next.RES_IDENTIFIER);
        return next;
    }

    @GetMapping("/currentScene")
    @ResponseStatus(HttpStatus.OK)
    public Scene currentScene(HttpServletRequest request) {
        System.out.println(request);
        Principal principal = request.getUserPrincipal();
        String username = principal.getName();
        String scene_res = playerService.getCurrentScene(username);
        return sceneService.findByResIdentifier(scene_res);
    }

    @GetMapping("/restart")
    @ResponseStatus(HttpStatus.OK)
    public Scene restartGame(HttpServletRequest request) throws ResourceNotFoundException {
        Principal principal = request.getUserPrincipal();
        String username = principal.getName();
        Player player = playerService.findPlayerByUsername(username);
        playerService.updateForRestart(player.RES_IDENTIFIER);
        return sceneService.getFirstScene();
    }

    @PostMapping("/set/marvel")
    @ResponseStatus(HttpStatus.OK)
    public String setMarvel(HttpServletRequest request, @RequestBody Marvel marvel) {
        Principal principal = request.getUserPrincipal();
        String username = principal.getName();
        Player player = playerService.findPlayerByUsername(username);
        playerService.setMarvelCharacter(player.RES_IDENTIFIER, marvel, true);
        return "Marvel character added successfully!";
    }
    @PostMapping("/update/Level")
    @ResponseStatus(HttpStatus.OK)
    public String updateLevel(HttpServletRequest request, @RequestBody Level level){
        Principal principal = request.getUserPrincipal();
        String username = principal.getName();
        Player player = playerService.findPlayerByUsername(username);
        //player.setLevel(level);
        playerService.updateLevel(player.RES_IDENTIFIER, level);
        return "Level updated successfully!";
    }

    @GetMapping("/inventory")
    @ResponseBody
    public InventoryDto getPokemonInventory(HttpServletRequest request) throws PokemonNotFoundException, AbilityNotFoundException, ItemNotFoundException, MarvelNotSetException {
        Principal principal = request.getUserPrincipal();
        String username = principal.getName();
        Player player = playerService.findPlayerByUsername(username);
        InventoryDto c = inventoryService.getMarvelInventory(player);
        return c;
    }
}