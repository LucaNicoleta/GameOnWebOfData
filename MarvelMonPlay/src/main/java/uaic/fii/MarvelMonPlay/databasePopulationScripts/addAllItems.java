package uaic.fii.MarvelMonPlay.databasePopulationScripts;

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
        SceneRepository sc = new SceneRepository(serv);
        NextScenesRepository rf = new NextScenesRepository(serv);
        /* 
        // Scene1
        Scene s1 = new Scene("S1",
                "You just woke up in a strange place. You look around and let yourself be overwhelmed by the magnificence of the surroundings. You find yourself laying in a patch of earth scattered by the most crystalline waters. You get up and go to inspect the area but your own reflection in the water steals your attention. A strange aura seems to be wrapped around your persona.",
                "/realms/water.png", Collections.<Option>emptyList(), SceneTypes.PASSIVE, "", "NONE");
        s1.setReference("S2");
        ss.add(s1);

        // Scene2
        List<Option> i = new ArrayList<Option>();
        i.add(new Option("S2A", "url", Event.CHOOSE_WATER, OptionsEnum.A));
        i.add(new Option("S2B", "url", Event.CHOOSE_AIR, OptionsEnum.B));
        i.add(new Option("S2C", "url", Event.CHOOSE_EARTH, OptionsEnum.C));
        i.add(new Option("S2D", "url", Event.CHOOSE_FIRE, OptionsEnum.D));
        ss.add(new Scene("S2", "Who do you want to see as your reflexion?", "null", i, SceneTypes.ACTIVE, "", "NONE"));
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

        ss.add(new Scene("S2", "Who do you want to see as your reflexion ?", "", i.subList(0, 4), SceneTypes.ACTIVE, "",
                "NONE"));
        ss.add(new Scene("S3-WATER",
                "As you analyze your own reflection, you observe a mysterious creature in the depths of the water looking curiously at you.In the blink of an eye, it jumps out of the water and turns into a small ball that lands in your palm.Before you understand what is happening, you feel the presence of another person behind you.",
                "/aditional_image", Collections.emptyList(), SceneTypes.PASSIVE, "", "A"));
        ss.add(new Scene("S3-EARTH",
                "     As you analyze your own reflection, you observe a mysterious creature in the depths of the water looking curiously at you. A glimpse of suspicioun appears in its eyes and the creature is slowly moving away.Before you understand what is happening, you feel the presence of another person behind you.",
                "/aditional_image", Collections.emptyList(), SceneTypes.PASSIVE, "", "C"));
        ss.add(new Scene("S3-AIR",
                "As you analyze your own reflection, you observe a mysterious creature in the depths of the water looking curiously at you. A glimpse of suspicioun appears in its eyes and the creature is slowly moving away.Before you understand what is happening, you feel the presence of another person behind you.",
                "/aditional_image", Collections.emptyList(), SceneTypes.PASSIVE, "", "B"));
        ss.add(new Scene("S3-FIRE",
                "As you analyze your own reflection, you observe a mysterious creature in the depths of the water looking curiously at you.  The creature disappears as quickly as it appeared, but not before it took a brief, fearful look at you. Before you understand what is happening, you feel the presence of another person behind you.",
                "/aditional_image", Collections.emptyList(), SceneTypes.PASSIVE, "", "D"));

        ss.add(new Scene("S4-FIRE", "He sensed that you’re a fire elemental as opose to him and he got scared.",
                "/marvel", i.subList(4, 8), SceneTypes.ACTIVE, "", "NONE"));
        ss.add(new Scene("S4-WATER", "He sensed that you’re a fire elemental as opose to him and he got scared.",
                "/marvel", i.subList(4, 8), SceneTypes.ACTIVE, "", "NONE"));
        ss.add(new Scene("S4-EARTH",
                "He sensed that you’re a earth elemental. Him, being a water elemental doesn’t know if he can trust you.",
                "/marvel", i.subList(4, 7), SceneTypes.ACTIVE, "", "NONE"));
        ss.add(new Scene("S4-AIR", "He sensed that you’re a water elemental as him and he accepted you as an owner.",
                "/marvel", i.subList(4, 8), SceneTypes.ACTIVE, "", "NONE"));

        ss.add(new Scene("S5",
                "Sorry if I scared you. Not long ago, I was just like you, a confused stranger transported out of the blue to this mysterious land. This is Eteria, a planet endowed with soul and reason but not with inhabitants. The planet is divided into 4 regions, each with an energy nucleus with the generative power specific to a fundamental element: fire, water, air, and earth. In order to break the line of loneliness, Eteria used the energy of its four elemental kernels to populate its land with people borrowed from other worlds.        ",
                "/realms/water.png", Collections.<Option>emptyList(), SceneTypes.PASSIVE, "", "NONE"));
        ss.add(new Scene("S6",
                "Every new inhabitant of the planet will receive elemental energy from one of the nuclei and usually settle in the region protected by the respective. Like me and the pokemon you saw earlier who are water elementals.",
                "/marvel", i.subList(8, 12), SceneTypes.ACTIVE, "", "NONE"));

        ss.add(new Scene("S7",
                "Sorry if I have filled your head with too much information, but you will need it if you want to survive on this planet. Each elemental region has a gate that allows teleportation to another region. These gates also have the power to send you home, but in order to ask to be transported out of the planet, you must have passed through all the elemental regions. The gates are often guarded, and if you get to the confrontation you will need pokemons. They are creatures with special abilities used by the others in fights.",
                "/realms/water.png", Collections.<Option>emptyList(), SceneTypes.PASSIVE, "", "NONE"));
        ss.add(new Scene("S8",
                "Another thing you need to know is that the elemental energy doesn’t go just to the living creatures it also can be absorbed by objects. They are called proja and usually appear near the gate. And with that, I think you have all the necessary information.",
                "/marvel", i.subList(12, 16), SceneTypes.ACTIVE, "", "NONE"));

        ss.add(new Scene("S9", "The mysterious personage disappear without a word. What should you do next? ",
                "/marvel", i.subList(16, 18), SceneTypes.ACTIVE, "", "NONE"));
        ss.add(new Scene("S9-with-pokemon",
                "The mysterious personage disappear without a word. You are heading toward the direction from which you feel energy, hoping to find the gate.",
                "/realms/water.png", Collections.<Option>emptyList(), SceneTypes.PASSIVE, "", "Pika"));
        ss.add(new Scene("S10", "How do you intend to catch the pokemon?", "/marvel", i.subList(18, 20),
                SceneTypes.ACTIVE, "", "A"));

        OptionRepositoryImpl ac = new OptionRepositoryImpl(serv);
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

        for (NextSceneRef r : r_list) {
            rf.save(r);
            sc.setRef(r.RES_IDENTIFIER.substring(0, r.RES_IDENTIFIER.indexOf('_')), r.RES_IDENTIFIER);
        }*/
        System.out.println("Testam NextScene");
        SceneService sss = new SceneService(sc, rf);
        Scene s = sss.nextScene("S8", "Marvel1", "A");

        Scene s_1 = sss.nextScene("S8", "Marvel2", "A");

        Scene s2 = sss.nextScene("S9", "Marvel1", "A");
        System.out.println("Scena8 when MArvel has the pokemon:");
        System.out.println(s.RES_IDENTIFIER);

        System.out.println("Scena8 when Marvel doesn't has the pokemon:");
        System.out.println(s_1.RES_IDENTIFIER);
        
        System.out.println("Scena9:");
        System.out.println(s2.RES_IDENTIFIER);
        try {
            System.out.println(sss.findByResIdentifier("S2").options.size());
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
