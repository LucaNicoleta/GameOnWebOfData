package uaic.fii.MarvelMonPlay.loaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uaic.fii.MarvelMonPlay.models.characters.Marvel;
import uaic.fii.MarvelMonPlay.models.levels.Level;
import uaic.fii.MarvelMonPlay.models.players.AppUserRole;
import uaic.fii.MarvelMonPlay.models.players.Player;
import uaic.fii.MarvelMonPlay.services.MarvelService;
import uaic.fii.MarvelMonPlay.services.PlayerService;

//TODO: This class is TEMPORARILY used for testing different components
@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private PlayerService playerService;

    @Autowired
    private MarvelService marvelService;

    @Override
    public void run(String... args) throws Exception {
        Marvel marvel = new Marvel("NEW_MARVEL", "MARVEL_NAME", "no-image.com", "no desc");
        Player player = new Player("PLAYER_IDENT", "test_username", "test_pass", marvel, Level.EARTH, AppUserRole.USER);
        playerService.save(player, true);

        Player p = playerService.findPlayerByUsername("test_username");
        System.out.println(p.toString());

//        String subject = "http://example.com/test1";
//        String predicate = "http://example.com/test2";
//        String object = "http://example.com/test4";
//        SparqlEndpointImpl.saveTriple(subject, predicate, object);
//
//        String queryString = "SELECT * WHERE { ?s ?p ?o }";
//        TupleQueryResult result = SPARQLEndpointImpl.executeQuery(queryString);
//
//        while (result.hasNext()) {
//            BindingSet bindingSet = result.next();
//            Value s = bindingSet.getValue("s");
//            Value p = bindingSet.getValue("p");
//            Value o = bindingSet.getValue("o");
//            System.out.println(s + " " + p + " " + o);
//        }

        //add ontology file into GraphDB
        //sparqlEndpoint.importOwlFromFile(ResourceUtils.getFile("classpath:game.owl"), RDFFormat.RDFXML);

        //retrieves marvel characters from database
        //new CharacterServiceImpl(sparqlEndpoint).getMarvelCharacters();
        //retrieves marvel character from MARVEL API
//        Marvel marvel = new MarvelApiImpl().getMarvelCharacter("Hulk"); //Spider-Man (Ultimate)
//        System.out.println(marvel);

//        characterService.getPokemonCharacters().forEach(System.out::println);
//        characterService.getMarvelCharacters().forEach(System.out::println);

        //Pokemon pokemon = new PokeApiImpl().getPokemonByName("clefairy");
        //System.out.println(pokemon.toString());
        // Close the repository connection
        // sparqlEndpoint.close();
        // sparqlEndpoint.shutDown();
    }
}
