package uaic.fii.MarvelMonPlay.manualWork;

import uaic.fii.MarvelMonPlay.endpoints.SparqlEndpointImpl;
import uaic.fii.MarvelMonPlay.models.scenes.Option;
import uaic.fii.MarvelMonPlay.models.scenes.Scene;
import uaic.fii.MarvelMonPlay.repositories.MarvelRepository;
import uaic.fii.MarvelMonPlay.repositories.OptionRepository;
import uaic.fii.MarvelMonPlay.repositories.PokemonRepository;
import uaic.fii.MarvelMonPlay.repositories.impl.MarvelRepositoryImpl;
import uaic.fii.MarvelMonPlay.repositories.impl.NextScenesRepository;
import uaic.fii.MarvelMonPlay.repositories.impl.OptionRepositoryImpl;
import uaic.fii.MarvelMonPlay.repositories.impl.PokemonRepositoryImpl;
import uaic.fii.MarvelMonPlay.repositories.impl.SceneRepository;
import uaic.fii.MarvelMonPlay.services.NextScene;

public class manualTesting {
    static private final SparqlEndpointImpl serv = new SparqlEndpointImpl("http://localhost:7200/repositories/db",
    "http://localhost:7200/repositories/db/statements");
    static private SceneRepository sceneRepository = new SceneRepository(serv);
    static private NextScenesRepository refRepository = new NextScenesRepository(serv);
    static private MarvelRepository marvelRepository = new MarvelRepositoryImpl(serv);
    static private OptionRepository optionRepository = new OptionRepositoryImpl(serv);
    static private PokemonRepository pokemonRepository = new PokemonRepositoryImpl(serv);
    
    public static void main(String[] args){/* 
        System.out.println("Testam NextScene");

        NextScene sss = new NextScene(sceneRepository, refRepository, marvelRepository, optionRepository, pokemonRepository );
        Scene s = sss.nextScene("S8", "Namor1", "A");

        Scene s_1 = sss.nextScene("S8", "Dust1", "A");

        Scene s2 = sss.nextScene("S9", "Namor1", "A");
        Scene s_startFight = sss.nextScene("S18", "Namor1", "A");
        Scene s_round1 = sss.nextScene(s_startFight.RES_IDENTIFIER, s_startFight.MarvelRES, "A");
        Scene s_round2 = sss.nextScene(s_round1.RES_IDENTIFIER, s_round1.MarvelRES, "A");
        Scene s_final = sss.nextScene(s_round2.RES_IDENTIFIER, s_round2.MarvelRES, "A");

  
        System.out.println("Scena8 when Marvel has the pokemon:");
        System.out.println(s.RES_IDENTIFIER);

        System.out.println("Scena8 when Marvel doesn't has the pokemon:");
        System.out.println(s_1.RES_IDENTIFIER);
        
        System.out.println("Scena9:");
        System.out.println(s2.RES_IDENTIFIER);

        System.out.println("Scena18:");
        System.out.println(s_startFight.RES_IDENTIFIER);
        System.out.println(s_startFight.text);
        for(Option o:s_startFight.options)
        System.out.println(o.content);
        System.out.println("round1:");
        System.out.println(s_round1.RES_IDENTIFIER);
        System.out.println(s_round1.text);
        System.out.println(s_round1.MarvelRES);
        for(Option o:s_round1.options)
        System.out.println(o.content);
        System.out.println("round2:");
        System.out.println(s_round2.RES_IDENTIFIER);
        System.out.println(s_round2.text);
        for(Option o:s_round2.options)
        System.out.println(o.content);
        System.out.println("final match:");
        System.out.println(s_final.RES_IDENTIFIER);
        System.out.println(s_final.text);
*/
    }
}
