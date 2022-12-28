package uaic.fii.MarvelMonPlay.loaders;

import org.eclipse.rdf4j.rio.RDFFormat;
import org.springframework.util.ResourceUtils;
import uaic.fii.MarvelMonPlay.endpoints.SparqlEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uaic.fii.MarvelMonPlay.externalApi.MarvelApiImpl;
import uaic.fii.MarvelMonPlay.models.Marvel;
import uaic.fii.MarvelMonPlay.services.CharacterServiceImpl;

//TODO: This class is TEMPORARILY used for testing different components
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private SparqlEndpoint sparqlEndpoint;

    @Override
    public void run(String... args) throws Exception {
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
        new CharacterServiceImpl(sparqlEndpoint).getMarvelCharacters();

        //retrieves marvel character from MARVEL API
        Marvel marvel = new MarvelApiImpl().getMarvelCharacter("Hulk"); //Spider-Man (Ultimate)
        System.out.println(marvel);

        // Close the repository connection
        // sparqlEndpoint.close();
        // sparqlEndpoint.shutDown();
    }
}
