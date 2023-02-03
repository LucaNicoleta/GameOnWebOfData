package uaic.fii.MarvelMonPlay.databasePopulationScripts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import uaic.fii.MarvelMonPlay.endpoints.SparqlEndpointImpl;
import uaic.fii.MarvelMonPlay.exceptions.ResourceNotFoundException;
import uaic.fii.MarvelMonPlay.models.Action;
import uaic.fii.MarvelMonPlay.models.Element;
import uaic.fii.MarvelMonPlay.models.Event;
import uaic.fii.MarvelMonPlay.models.items.Item;
import uaic.fii.MarvelMonPlay.models.scenes.NextSceneRef;
import uaic.fii.MarvelMonPlay.models.scenes.Option;
import uaic.fii.MarvelMonPlay.models.scenes.OptionsEnum;
import uaic.fii.MarvelMonPlay.models.scenes.Scene;
import uaic.fii.MarvelMonPlay.models.scenes.SceneTypes;
import uaic.fii.MarvelMonPlay.repositories.MarvelRepository;
import uaic.fii.MarvelMonPlay.repositories.impl.ActionRepositoryImpl;
import uaic.fii.MarvelMonPlay.repositories.impl.EventRepository;
import uaic.fii.MarvelMonPlay.repositories.impl.ItemRepositoryImpl;
import uaic.fii.MarvelMonPlay.repositories.impl.NextScenesRepository;
import uaic.fii.MarvelMonPlay.repositories.impl.OptionRepositoryImpl;
import uaic.fii.MarvelMonPlay.repositories.impl.SceneRepository;
import uaic.fii.MarvelMonPlay.services.impl.SceneService;

public class addAllItems {
    static private final SparqlEndpointImpl serv = new SparqlEndpointImpl("http://localhost:7200/repositories/db",
            "http://localhost:7200/repositories/db/statements");

    static private void addActions() {
        ActionRepositoryImpl ac = new ActionRepositoryImpl(serv);
        for (Action action : Action.values()) {
            ac.save(action);
        }
    }

    static private void addEvents() {
        EventRepository ac = new EventRepository(serv);
        for (Event event : Event.values()) {
            ac.save(event);
        }
    }

    static private void addItems() {
        List<Item> i = new ArrayList<Item>();
        i.add(new Item("WaterProja", Element.WATER, Action.DECREASE_ENEMY_DEFENSE));
        i.add(new Item("AirProja", Element.AIR, Action.DECREASE_ENEMY_POWER_ATTACK));
        i.add(new Item("EarthProja", Element.EARTH, Action.INCREASE_DEFENSE));
        i.add(new Item("FireProja", Element.FIRE, Action.INCREASE_POWER_ATTACK));
        ItemRepositoryImpl ac = new ItemRepositoryImpl(serv);
        for (Item item : i) {
            ac.save(item);
        }
    }

    static private void addScenesAndOptions() {
        List<Scene> ss = new ArrayList<Scene>();
        //Scene1
        ss.add(new Scene("S1",
                "You just woke up in a strange place. You look around and let yourself be overwhelmed by the magnificence of the surroundings. You find yourself laying in a patch of earth scattered by the most crystalline waters. You get up and go to inspect the area but your own reflection in the water steals your attention. A strange aura seems to be wrapped around your persona.",
                "/realms/water.png", Collections.<Option>emptyList(), SceneTypes.PASSIVE, "","NONE"));
        //Scene2
                List<Option> i = new ArrayList<Option>();
        i.add(new Option("S2A", "url", Event.CHOOSE_WATER, OptionsEnum.A));
        i.add(new Option("S2B", "url", Event.CHOOSE_AIR, OptionsEnum.B));
        i.add(new Option("S2C", "url", Event.CHOOSE_EARTH, OptionsEnum.C));
        i.add(new Option("S2D", "url", Event.CHOOSE_FIRE, OptionsEnum.D));
        //Scene3
        //Scene4
        i.add(new Option("S4A", "I'm a what?", Event.NONE, OptionsEnum.A));
        i.add(new Option("S4B", "Who are you again?", Event.NONE, OptionsEnum.B));
        i.add(new Option("S4C", "I'll need more details than that", Event.NONE, OptionsEnum.C));
        i.add(new Option("S4D", "Um...hello to you too...", Event.NONE, OptionsEnum.D));
        
        ss.add(new Scene("S2", "Who do you want to see as your reflexion ?", "", i.subList(0, 4), SceneTypes.ACTIVE, "","NONE" ));
        ss.add(new Scene("S3-WATER", "As you analyze your own reflection, you observe a mysterious creature in the depths of the water looking curiously at you.In the blink of an eye, it jumps out of the water and turns into a small ball that lands in your palm.Before you understand what is happening, you feel the presence of another person behind you.", "/aditional_image", Collections.emptyList(), SceneTypes.PASSIVE, "","WATER" ));
        ss.add(new Scene("S3-EARTH", "     As you analyze your own reflection, you observe a mysterious creature in the depths of the water looking curiously at you. A glimpse of suspicioun appears in its eyes and the creature is slowly moving away.Before you understand what is happening, you feel the presence of another person behind you.", "/aditional_image", Collections.emptyList(), SceneTypes.PASSIVE, "","EARTH" ));
        ss.add(new Scene("S3-AIR", "As you analyze your own reflection, you observe a mysterious creature in the depths of the water looking curiously at you. A glimpse of suspicioun appears in its eyes and the creature is slowly moving away.Before you understand what is happening, you feel the presence of another person behind you.", "/aditional_image", Collections.emptyList(), SceneTypes.PASSIVE, "","AIR" ));
        ss.add(new Scene("S3-FIRE", "As you analyze your own reflection, you observe a mysterious creature in the depths of the water looking curiously at you.  The creature disappears as quickly as it appeared, but not before it took a brief, fearful look at you. Before you understand what is happening, you feel the presence of another person behind you.", "/aditional_image", Collections.emptyList(), SceneTypes.PASSIVE, "","FIRE" ));
 
        ss.add(new Scene("S4-FIRE", "He sensed that you’re a fire elemental as opose to him and he got scared.", "/marvel", i.subList(4, 8), SceneTypes.ACTIVE, "","NONE" ));
        ss.add(new Scene("S4-WATER", "He sensed that you’re a fire elemental as opose to him and he got scared.", "/marvel", i.subList(4, 8), SceneTypes.ACTIVE, "","NONE" ));
        ss.add(new Scene("S4-EARTH", "He sensed that you’re a earth elemental. Him, being a water elemental doesn’t know if he can trust you.", "/marvel", i.subList(4, 7), SceneTypes.ACTIVE, "","NONE" ));
        ss.add(new Scene("S4-AIR", "He sensed that you’re a water elemental as him and he accepted you as an owner.", "/marvel", i.subList(4, 7), SceneTypes.ACTIVE, "","NONE" ));

        OptionRepositoryImpl ac = new OptionRepositoryImpl(serv);
        for (Option o : i) {
            ac.save(o);
        }
        SceneRepository sc = new SceneRepository(serv);
        for (Scene s : ss) {
            sc.save(s);
        }
        NextScenesRepository rf = new NextScenesRepository(serv);
        List<NextSceneRef> r_list = new ArrayList<>();
        
        r_list.add(new NextSceneRef("S1_ref", "NONE", new ArrayList<String>( Arrays.asList("S2"))));
        r_list.add(new NextSceneRef("S2_ref", "ELEMENT", new ArrayList<String>( Arrays.asList("S3-WATER","S3-AIR","S3-FIRE","S3-EARTH"))));
        r_list.add(new NextSceneRef("S3-FIRE_ref", "NONE", new ArrayList<String>( Arrays.asList("S4-FIRE"))));
        r_list.add(new NextSceneRef("S3-EARTH_ref", "NONE", new ArrayList<String>( Arrays.asList("S4-EARTH"))));
        r_list.add(new NextSceneRef("S3-WATER_ref", "NONE", new ArrayList<String>( Arrays.asList("S4-WATER"))));
        r_list.add(new NextSceneRef("S3-AIR_ref", "NONE", new ArrayList<String>( Arrays.asList("S4-AIR"))));

        for(NextSceneRef r: r_list){
            rf.save(r);
            sc.setRef(r.RES_IDENTIFIER.substring(0, r.RES_IDENTIFIER.indexOf('_')), r.RES_IDENTIFIER);
        }
        SceneService sss = new SceneService(sc,rf);
        Scene s = sss.nextScene("S2", "Marvel1", "A");
        System.out.println(s.RES_IDENTIFIER);
        try {
            System.out.println( sss.findByResIdentifier("S2").options.size());
        } catch (ResourceNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //addActions();
        //addEvents();
        //addItems();
        addScenesAndOptions();
    }
}
