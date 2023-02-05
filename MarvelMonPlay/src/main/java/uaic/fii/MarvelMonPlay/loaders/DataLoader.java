package uaic.fii.MarvelMonPlay.loaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uaic.fii.MarvelMonPlay.externalApi.MarvelApi;
import uaic.fii.MarvelMonPlay.models.Action;
import uaic.fii.MarvelMonPlay.models.Element;
import uaic.fii.MarvelMonPlay.models.abilities.Ability;
import uaic.fii.MarvelMonPlay.models.characters.Marvel;
import uaic.fii.MarvelMonPlay.models.characters.Pokemon;
import uaic.fii.MarvelMonPlay.models.items.Item;
import uaic.fii.MarvelMonPlay.services.MarvelService;
import uaic.fii.MarvelMonPlay.services.PlayerService;

//TODO: This class is TEMPORARILY used for testing different components
@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private PlayerService playerService;

    @Autowired
    private MarvelService marvelService;

    @Autowired
    private MarvelApi marvelApi;

    @Override
    public void run(String... args) {
//        Marvel m = new Marvel("Hulk", "Hulk", "", "");
//        Item i1 = new Item("WaterProja", Element.WATER, Action.DECREASE_ENEMY_DEFENSE);
//        Item i2 = new Item("AirProja", Element.AIR, Action.DECREASE_ENEMY_POWER_ATTACK);
//        Item i3 = new Item("EarthProja", Element.EARTH, Action.INCREASE_DEFENSE);
//        Item i4 = new Item("FireProja", Element.FIRE, Action.INCREASE_POWER_ATTACK);
//        m.addItemToInventory(i1);
//        m.addItemToInventory(i2);
//        m.addItemToInventory(i3);
//        m.addItemToInventory(i4);
//
//        Pokemon ditto = new Pokemon("ditto", "ditto", "");
//        Ability a1 = new Ability("ab1", "ability1", "no-desc1");
//        Ability a2 = new Ability("ab2", "ability2", "no-desc2");
//        ditto.addAbility(a1);
//        ditto.addAbility(a2);
//
//        Pokemon pokemon = new Pokemon("poke", "poke-name", "no-url");
//        m.addPokemonToInventory(ditto);
//        m.addPokemonToInventory(pokemon);
//
//        marvelService.save(m, true);
    }
}
