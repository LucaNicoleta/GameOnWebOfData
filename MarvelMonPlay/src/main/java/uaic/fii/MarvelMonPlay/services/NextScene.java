package uaic.fii.MarvelMonPlay.services;


import uaic.fii.MarvelMonPlay.repositories.impl.NextScenesRepository;
import uaic.fii.MarvelMonPlay.repositories.impl.OptionRepositoryImpl;
import uaic.fii.MarvelMonPlay.repositories.impl.PlayerRepositoryImpl;
import uaic.fii.MarvelMonPlay.repositories.impl.SceneRepository;
import uaic.fii.MarvelMonPlay.services.impl.MarvelServiceImpl;
import uaic.fii.MarvelMonPlay.services.impl.NextScenesRefService;
import uaic.fii.MarvelMonPlay.services.impl.OptionService;
import uaic.fii.MarvelMonPlay.services.impl.PlayerServiceImpl;
import uaic.fii.MarvelMonPlay.services.impl.PokemonServiceImpl;
import uaic.fii.MarvelMonPlay.services.impl.SceneService;
import uaic.fii.MarvelMonPlay.repositories.MarvelRepository;
import uaic.fii.MarvelMonPlay.repositories.OptionRepository;
import uaic.fii.MarvelMonPlay.repositories.PlayerRepository;
import uaic.fii.MarvelMonPlay.repositories.PokemonRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uaic.fii.MarvelMonPlay.exceptions.ResourceNotFoundException;
import uaic.fii.MarvelMonPlay.models.Action;
import uaic.fii.MarvelMonPlay.models.Event;
import uaic.fii.MarvelMonPlay.models.characters.Marvel;
import uaic.fii.MarvelMonPlay.models.characters.Pokemon;
import uaic.fii.MarvelMonPlay.models.players.Player;
import uaic.fii.MarvelMonPlay.models.scenes.NextSceneRef;
import uaic.fii.MarvelMonPlay.models.scenes.Option;
import uaic.fii.MarvelMonPlay.models.scenes.OptionsEnum;
import uaic.fii.MarvelMonPlay.models.scenes.Scene;
import uaic.fii.MarvelMonPlay.models.scenes.SceneTypes;

 
@Service
public class NextScene {
    NextScenesRefService nextServ;
    SceneService sceneService;
    MarvelService marvelService;
    OptionService optionService;
    PokemonService pokemonService;
    @Autowired
    PlayerService playerService;
    List<Option> fighOptions = new ArrayList<Option>();
    public NextScene(SceneRepository sceneRepository, NextScenesRepository nextRep, MarvelRepository marvelRepository, OptionRepository optionRepository, PokemonRepository pokemonRepository){
            this.marvelService = new MarvelServiceImpl(marvelRepository);
            this.sceneService = new SceneService(sceneRepository);
            this.nextServ = new NextScenesRefService(nextRep);
            this.optionService = new OptionService(optionRepository);
            this.pokemonService = new PokemonServiceImpl(pokemonRepository);
            
            Integer i =0;
            for(Action a: Action.values())
            {fighOptions.add(new Option("FO1"+i.toString(),a.description,Event.valueOf(a.name()),OptionsEnum.values()[i]));
            optionRepository.save(fighOptions.get(i));
            i++;}
    }
    
    public Scene startFightScene(String marvelRes){
        List<String> pokemons = marvelService.getNamesOfThePokemonsOwnedByMarvel(marvelRes);
        if(pokemons.size()==0)
        return new Scene("LOST_BECAUSE_NO_POKEMONS", "You don't have any pokemons to fight for you so you lose the game", "/events/lost_fight",Collections.<Option>emptyList(), SceneTypes.PASSIVE, marvelRes, "NONE");
       // if(pokemons.size()==1)
        //return new Scene("Start_fight", "You only own one pokemon so " +pokemons.get(0)+" will be used automatically in fight", "/additional_images/triton.png", Collections.<Option>emptyList(), SceneTypes.PASSIVE, marvelRes, "NONE");
        List<Option> options = new ArrayList<Option>();
        Integer i =0;
        for(String p: pokemons){
            options.add(new Option(marvelRes+p,p,Event.PICK_POKEMON_TO_FIGHT,OptionsEnum.values()[i]));
            optionService.save(options.get(i));
            i++;
        }
        return new Scene("Start_fight", "Which pokemon do you choose to fight this battle", "/additional_images/triton.png",options, SceneTypes.ACTIVE, marvelRes, "NONE");

    }
    
    public Scene fightRoundScene( String marvelRes, String chosenOption, Boolean firstRound){
 Scene s=null;
        if(firstRound)
        {
            s=new Scene("F1", "What is your move?","/additional_images/triton.png", fighOptions, SceneTypes.ACTIVE, marvelRes, "NONE");}
        else{
        List<Pokemon> pokemons_fighting = pokemonService.findPokemonsInFight(marvelRes);
        Event event = pokemonService.fightRound(pokemons_fighting.get(0), pokemons_fighting.get(1));
       
        if(event == Event.LOST_FIGHT)
        s=new Scene("LostGame","You lost","",Collections.emptyList(),SceneTypes.PASSIVE,"","NONE");
        else{
            if(event==Event.WON_FIGHT)
        s=new Scene("WonFight","You won the fight","/events/won_fight.jpg",Collections.emptyList(),SceneTypes.PASSIVE,"","NONE");
         else
        s=new Scene("F1", "What is your next move?","/additional_images/triton.png", fighOptions, SceneTypes.ACTIVE, marvelRes, "NONE");}}
        sceneService.save(s);
        return s;



    }
    public Scene nextScene(String currentSceneRes, String playerRes, String chosenOption) {
        Event event = optionService.getEventTriggeredByOption(currentSceneRes, chosenOption);
        if(event ==Event.CHOOSE_WATER){
            String marvel_res = "Namor"+playerRes;
            Marvel marvel = new Marvel(marvel_res,"Namor","http://i.annihil.us/u/prod/marvel/i/mg/e/90/50febf4ae101d.jpg");
            Pokemon pokemon = new Pokemon("squirtle"+playerRes, "squirtle", null, "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/7.png");
            marvel.addPokemonToInventory(pokemon);
            
            playerService.setMarvelCharacter(playerRes, marvel, true);
            marvelService.setElementRepresentativeForMarvel(marvel_res,"WATER");
      
        }
        Player player =playerService.findPlayerByUsername(playerRes);
        String marvelIdentifier = player.getMarvelCharacter()!=null?player.getMarvelCharacter().RES_IDENTIFIER:"";
        if(event ==Event.COLLECT_WATER_ITEM){
            
            
            marvelService.addItemForMarvel(marvelIdentifier, "WaterProja");;
      
        }
        if(event==Event.START_FIGHT)
        {
            Scene s = startFightScene(marvelIdentifier);
            sceneService.save(s);
          return s;  
        }


        if(event==Event.PICK_POKEMON_TO_FIGHT){
            String pokemon_name = optionService.getContentOfOption(currentSceneRes, chosenOption);
            Pokemon p = pokemonService.findByNameAndOwner(pokemon_name, marvelIdentifier);
            pokemonService.pickPokemonForFight(marvelIdentifier,p.RES_IDENTIFIER , pokemonService.findCurrentPokemonEnemy(marvelIdentifier));
            Scene s = fightRoundScene(marvelIdentifier, chosenOption, true);
            sceneService.save(s);
            return s;
        }
        if(event==Event.DECREASE_ENEMY_DEFENSE || event==Event.INCREASE_POWER_ATTACK||event==Event.INCREASE_DEFENSE||event==Event.DECREASE_ENEMY_POWER_ATTACK)
        {

            Scene s = fightRoundScene(marvelIdentifier, chosenOption, false);
            sceneService.save(s);
            return s;
        }
        NextSceneRef nextScenes = nextServ.findByResIdentifier(currentSceneRes);

        if (nextScenes.criteria.equals("NONE"))
           
                return sceneService.findByResIdentifier(nextScenes.posibleScenesRES.get(0));
            

        if (nextScenes.criteria.equals("OPTION"))
            return sceneService.findByChoosenOption(nextScenes, chosenOption);
        else

            return sceneService.findByPlayerStats(nextScenes, marvelIdentifier, nextScenes.criteria);
    }

}
