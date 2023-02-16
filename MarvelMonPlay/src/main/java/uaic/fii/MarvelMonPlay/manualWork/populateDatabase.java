package uaic.fii.MarvelMonPlay.manualWork;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


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
import uaic.fii.MarvelMonPlay.repositories.PokemonRepository;
import uaic.fii.MarvelMonPlay.repositories.impl.ActionRepositoryImpl;
import uaic.fii.MarvelMonPlay.repositories.impl.EventRepository;
import uaic.fii.MarvelMonPlay.repositories.impl.ItemRepositoryImpl;
import uaic.fii.MarvelMonPlay.repositories.impl.MarvelRepositoryImpl;
import uaic.fii.MarvelMonPlay.repositories.impl.NextScenesRepository;
import uaic.fii.MarvelMonPlay.repositories.impl.OptionRepositoryImpl;
import uaic.fii.MarvelMonPlay.repositories.impl.PokemonRepositoryImpl;
import uaic.fii.MarvelMonPlay.repositories.impl.SceneRepository;
import uaic.fii.MarvelMonPlay.services.NextScene;
import uaic.fii.MarvelMonPlay.services.impl.SceneService;

public class populateDatabase {
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
        SceneRepository sc = new SceneRepository(serv);
        NextScenesRepository rf = new NextScenesRepository(serv);
        MarvelRepository mr = new MarvelRepositoryImpl(serv);
        PokemonRepository pr = new PokemonRepositoryImpl(serv);
        OptionRepositoryImpl ac = new OptionRepositoryImpl(serv);
        mr.insertMarvelEnemy();
        // Scene1
        sc.deleteAllScenes();
        ac.deleteAllOptions();
        Scene s1 = new Scene("S1",
                "You just woke up in a strange place. You look around and let yourself be overwhelmed by the magnificence of the surroundings. You find yourself laying in a patch of earth scattered by the most crystalline waters. You get up and go to inspect the area but your own reflection in the water steals your attention. A strange aura seems to be wrapped around your persona.",
                "/realms/water.png", Collections.<Option>emptyList(), SceneTypes.PASSIVE, "", "NONE");
        s1.setReference("S2");
        ss.add(s1);

        // Scene2
        List<Option> i = new ArrayList<Option>();
        i.add(new Option("S2A", "http://i.annihil.us/u/prod/marvel/i/mg/e/90/50febf4ae101d.jpg", Event.CHOOSE_WATER, OptionsEnum.A));
        i.add(new Option("S2B", "http://i.annihil.us/u/prod/marvel/i/mg/d/d0/5269657a74350.jpg", Event.CHOOSE_AIR, OptionsEnum.B));
        i.add(new Option("S2C", "http://i.annihil.us/u/prod/marvel/i/mg/8/f0/4c003f17db9a6.jpg", Event.CHOOSE_EARTH, OptionsEnum.C));
        i.add(new Option("S2D", "http://i.annihil.us/u/prod/marvel/i/mg/3/70/5261a7f7b0917.jpg", Event.CHOOSE_FIRE, OptionsEnum.D));
        //ss.add(new Scene("S2", "Who do you want to see as your reflexion?", "null", i, SceneTypes.ACTIVE, "", "NONE"));
        // Scene3
        // Scene4
        i.add(new Option("S4A", "I'm a what?", Event.NONE, OptionsEnum.A));
        i.add(new Option("S4B", "Who are you again?", Event.NONE, OptionsEnum.B));
        i.add(new Option("S4C", "I'll need more details than that", Event.NONE, OptionsEnum.C));
        i.add(new Option("S4D", "Um...hello to you too...", Event.NONE, OptionsEnum.D));

        i.add(new Option("S6A", "Pokemon?", Event.NONE, OptionsEnum.A));
        i.add(new Option("S6B", "Slow down!", Event.NONE, OptionsEnum.B));
        i.add(new Option("S6C", "I just want to go home...", Event.NONE, OptionsEnum.C));
        i.add(new Option("S6D", "What is exactly a pokemon?", Event.NONE, OptionsEnum.D));

        i.add(new Option("S8A", "I think not!", Event.NONE, OptionsEnum.A));
        i.add(new Option("S8B", "Thank you", Event.NONE, OptionsEnum.B));
        i.add(new Option("S8C", "How is that supposed to help me?!", Event.NONE, OptionsEnum.C));
        i.add(new Option("S8D", "Are you sure that’s all?", Event.NONE, OptionsEnum.D));

        i.add(new Option("S9A", "Try catch the pokemon", Event.NONE, OptionsEnum.A));
        i.add(new Option("S9B", "Go straight to the gate", Event.NONE, OptionsEnum.B));

        i.add(new Option("S10A", "Try talk to him", Event.NONE, OptionsEnum.A));
        i.add(new Option("S10B", "Jump in the water after him", Event.NONE, OptionsEnum.B));
 
        i.add(new Option("S14A", "Inspect the aria", Event.NONE, OptionsEnum.A));
        i.add(new Option("S14B", "Go straight to the gate", Event.NONE, OptionsEnum.B));
       
        i.add(new Option("S15A", "/trials/water-1.png", Event.COLLECT_WATER_ITEM, OptionsEnum.A));
        i.add(new Option("S15B", "/trials/water-2.png", Event.NONE, OptionsEnum.B));
        i.add(new Option("S15C", "/trials/water-3.png", Event.NONE, OptionsEnum.C));
        i.add(new Option("S15D", "/trials/water-4.png", Event.NONE, OptionsEnum.D));
   
        i.add(new Option("S18A", "Let's fight", Event.START_FIGHT, OptionsEnum.A));
        i.add(new Option("S18B", "The 'no fight' option sounds good", Event.NONE, OptionsEnum.B));
        
        ss.add(new Scene("S2", "Who do you want to see as your reflexion ?", "", i.subList(0, 4), SceneTypes.ACTIVE, "",
                "NONE"));
        ss.add(new Scene("S3-WATER",
                "As you analyze your own reflection, you observe a mysterious creature in the depths of the water looking curiously at you.In the blink of an eye, it jumps out of the water and turns into a small ball that lands in your palm.Before you understand what is happening, you feel the presence of another person behind you.",
                "/additional_images/squirtle.png", Collections.emptyList(), SceneTypes.PASSIVE, "", "A"));
        ss.add(new Scene("S3-EARTH",
                "     As you analyze your own reflection, you observe a mysterious creature in the depths of the water looking curiously at you. A glimpse of suspicioun appears in its eyes and the creature is slowly moving away.Before you understand what is happening, you feel the presence of another person behind you.",
                "/additional_images/squirtle.png", Collections.emptyList(), SceneTypes.PASSIVE, "", "C"));
        ss.add(new Scene("S3-AIR",
                "As you analyze your own reflection, you observe a mysterious creature in the depths of the water looking curiously at you. A glimpse of suspicioun appears in its eyes and the creature is slowly moving away.Before you understand what is happening, you feel the presence of another person behind you.",
                "/additional_images/squirtle.png", Collections.emptyList(), SceneTypes.PASSIVE, "", "B"));
        ss.add(new Scene("S3-FIRE",
                "As you analyze your own reflection, you observe a mysterious creature in the depths of the water looking curiously at you.  The creature disappears as quickly as it appeared, but not before it took a brief, fearful look at you. Before you understand what is happening, you feel the presence of another person behind you.",
                "/additional_images/squirtle.png", Collections.emptyList(), SceneTypes.PASSIVE, "", "D"));

        ss.add(new Scene("S4-FIRE", "He sensed that you’re a fire elemental as opose to him and he got scared.",
                "/additional_images/triton.png", i.subList(4, 8), SceneTypes.ACTIVE, "Triton", "NONE"));
        ss.add(new Scene("S4-WATER", "       He sensed that you’re a water elemental as him and he accepted you as an owner.",
                "/additional_images/triton.png", i.subList(4, 8), SceneTypes.ACTIVE, "Triton", "NONE"));
        ss.add(new Scene("S4-EARTH",
                "He sensed that you’re a earth elemental. Him, being a water elemental doesn’t know if he can trust you.",
                "/additional_images/triton.png", i.subList(4, 7), SceneTypes.ACTIVE, "Triton", "NONE"));
        ss.add(new Scene("S4-AIR", "He sensed that you’re a water elemental as him and he accepted you as an owner.",
                "/additional_images/triton.png", i.subList(4, 8), SceneTypes.ACTIVE, "Triton", "NONE"));

        ss.add(new Scene("S5",
                "Sorry if I scared you. Not long ago, I was just like you, a confused stranger transported out of the blue to this mysterious land. This is Eteria, a planet endowed with soul and reason but not with inhabitants. The planet is divided into 4 regions, each with an energy nucleus with the generative power specific to a fundamental element: fire, water, air, and earth. In order to break the line of loneliness, Eteria used the energy of its four elemental kernels to populate its land with people borrowed from other worlds.        ",
                "/additional_images/triton.png", Collections.<Option>emptyList(), SceneTypes.PASSIVE, "Triton", "NONE"));
        ss.add(new Scene("S6",
                "Every new inhabitant of the planet will receive elemental energy from one of the nuclei and usually settle in the region protected by the respective. Like me and the pokemon you saw earlier who are water elementals.",
                "/additional_images/triton.png", i.subList(8, 12), SceneTypes.ACTIVE, "Triton", "NONE"));

        ss.add(new Scene("S7",
                "Sorry if I have filled your head with too much information, but you will need it if you want to survive on this planet. Each elemental region has a gate that allows teleportation to another region. These gates also have the power to send you home, but in order to ask to be transported out of the planet, you must have passed through all the elemental regions. The gates are often guarded, and if you get to the confrontation you will need pokemons. They are creatures with special abilities used by the others in fights.",
                "/additional_images/triton.png", Collections.<Option>emptyList(), SceneTypes.PASSIVE, "Triton", "NONE"));
        ss.add(new Scene("S8",
                "Another thing you need to know is that the elemental energy doesn’t go just to the living creatures it also can be absorbed by objects. They are called proja and usually appear near the gate. And with that, I think you have all the necessary information.",
                "/additional_images/triton.png", i.subList(12, 16), SceneTypes.ACTIVE, "Triton", "NONE"));

        ss.add(new Scene("S9", "The mysterious personage disappear without a word. What should you do next? ",
                "/realms/water.png", i.subList(16, 18), SceneTypes.ACTIVE, "", "NONE"));
        ss.add(new Scene("S9-with-pokemon",
                "The mysterious personage disappear without a word. You are heading toward the direction from which you feel energy, hoping to find the gate.",
                "/realms/water.png", Collections.<Option>emptyList(), SceneTypes.PASSIVE, "", "squirtle"));
        ss.add(new Scene("S10", "How do you intend to catch the pokemon?", "/realms/water.png", i.subList(18, 20),
                SceneTypes.ACTIVE, "", "A"));
                ss.add(new Scene("S14", "After hours of walking, you found what seems to be the portal. What do you do?", "/portals/water.png", i.subList(20, 22),
                SceneTypes.ACTIVE, "", "NONE"));
                ss.add(new Scene("S15", "Near the gate you found 4 shining rocks, each with a different encrusted symbol. Which do you think is the real proja? ", "", i.subList(22, 26),
                SceneTypes.ACTIVE, "", "A"));
                ss.add(new Scene("S16-correct", "At the first touch, the stone turns into a sphere of blue energy. You seem to have chosen correctly.", "/projas/water.jpg", Collections.<Option>emptyList(),
                SceneTypes.PASSIVE, "", "A"));
                ss.add(new Scene("S16-incorrect", "All the stones disappear. You seem to have chosen incorrectly. ", "/portals/water.png", Collections.<Option>emptyList(),
                SceneTypes.PASSIVE, "", "NONE"));
                ss.add(new Scene("S17", "You go to the gate just to discover a familiar face waiting for you.", "/additional_images/triton.png", Collections.<Option>emptyList(),
                SceneTypes.PASSIVE, "", "NONE"));
                
 ss.add(new Scene("S18", "I told you that the gates are protected. You seem like a decent person so I could just let you go, unless you want a training match.", "/additional_images/triton.png", i.subList(26, 28),
                SceneTypes.ACTIVE, "Triton","NONE"));
       
        for (Option o : i) {
            ac.save(o);
        }

        for (Scene s : ss) {
            sc.save(s);
        }

        List<NextSceneRef> r_list = new ArrayList<>();

        r_list.add(new NextSceneRef("S1_ref", "NONE", new ArrayList<String>(Arrays.asList("S2"))));
        r_list.add(new NextSceneRef("S2_ref", "OPTION",
                new ArrayList<String>(Arrays.asList("S3-WATER", "S3-AIR", "S3-FIRE", "S3-EARTH"))));
        r_list.add(new NextSceneRef("S3-FIRE_ref", "NONE", new ArrayList<String>(Arrays.asList("S4-FIRE"))));
        r_list.add(new NextSceneRef("S3-EARTH_ref", "NONE", new ArrayList<String>(Arrays.asList("S4-EARTH"))));
        r_list.add(new NextSceneRef("S3-WATER_ref", "NONE", new ArrayList<String>(Arrays.asList("S4-WATER"))));
        r_list.add(new NextSceneRef("S3-AIR_ref", "NONE", new ArrayList<String>(Arrays.asList("S4-AIR"))));
        r_list.add(new NextSceneRef("S4-FIRE_ref", "NONE", new ArrayList<String>(Arrays.asList("S5"))));
        r_list.add(new NextSceneRef("S4-EARTH_ref", "NONE", new ArrayList<String>(Arrays.asList("S5"))));
        r_list.add(new NextSceneRef("S4-WATER_ref", "NONE", new ArrayList<String>(Arrays.asList("S5"))));
        r_list.add(new NextSceneRef("S4-AIR_ref", "NONE", new ArrayList<String>(Arrays.asList("S5"))));

        r_list.add(new NextSceneRef("S5_ref", "NONE", new ArrayList<String>(Arrays.asList("S6"))));
        r_list.add(new NextSceneRef("S6_ref", "NONE", new ArrayList<String>(Arrays.asList("S7"))));
        r_list.add(new NextSceneRef("S7_ref", "NONE", new ArrayList<String>(Arrays.asList("S8"))));
        r_list.add(
                new NextSceneRef("S8_ref", "POKEMON", new ArrayList<String>(Arrays.asList("S9", "S9-with-pokemon"))));
        r_list.add(
                new NextSceneRef("S9_ref", "OPTION", new ArrayList<String>(Arrays.asList("S10", "S9-with-pokemon"))));
        
                r_list.add(new NextSceneRef("S9-with-pokemon_ref", "NONE", new ArrayList<String>(Arrays.asList("S14"))));
                r_list.add(new NextSceneRef("S14_ref", "OPTION", new ArrayList<String>(Arrays.asList("S15","S17"))));
                r_list.add(new NextSceneRef("S15_ref", "OPTION", new ArrayList<String>(Arrays.asList("S16-correct","S16-incorrect"))));
                r_list.add(new NextSceneRef("S16-correct_ref", "NONE", new ArrayList<String>(Arrays.asList("S17"))));
                r_list.add(new NextSceneRef("S16-incorrect_ref", "NONE", new ArrayList<String>(Arrays.asList("S17"))));

                r_list.add(
                        new NextSceneRef("S17_ref", "NONE", new ArrayList<String>(Arrays.asList("S18"))));
        
        for (NextSceneRef r : r_list) {
            rf.save(r);
            sc.setRef(r.RES_IDENTIFIER.substring(0, r.RES_IDENTIFIER.indexOf('_')), r.RES_IDENTIFIER);
        }
        
    }

    public static void main(String[] args) {
        addActions();
        addEvents();
        addItems();
        addScenesAndOptions();
    }
}
