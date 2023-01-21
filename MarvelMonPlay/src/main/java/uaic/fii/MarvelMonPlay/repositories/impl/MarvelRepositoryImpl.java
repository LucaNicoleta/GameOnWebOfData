package uaic.fii.MarvelMonPlay.repositories.impl;

import org.eclipse.rdf4j.query.TupleQueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uaic.fii.MarvelMonPlay.endpoints.SparqlEndpoint;
import uaic.fii.MarvelMonPlay.models.characters.Marvel;
import uaic.fii.MarvelMonPlay.models.characters.Pokemon;
import uaic.fii.MarvelMonPlay.models.items.Item;
import uaic.fii.MarvelMonPlay.repositories.MarvelRepository;
import uaic.fii.MarvelMonPlay.services.ItemServiceCrud;
import uaic.fii.MarvelMonPlay.services.PokemonService;
import uaic.fii.MarvelMonPlay.utils.IRIFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Repository
public class MarvelRepositoryImpl implements MarvelRepository {

    private final SparqlEndpoint sparqlEndpoint;
    @Autowired
    private PokemonService pokemonService;

    @Autowired
    private ItemServiceCrud itemServiceCrud;

    public MarvelRepositoryImpl(SparqlEndpoint sparqlEndpoint){
        this.sparqlEndpoint = sparqlEndpoint;
    }

    @Override
    public TupleQueryResult findAll() {
        return sparqlEndpoint.executeQuery(
        "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
                "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
                "select ?character ?name ?imageUrl ?description where {" +
                "    ?character a IRI:Marvel ." +
                "    ?character foaf:name ?name ." +
                "    ?character IRI:hasImageUrl ?imageUrl ." +
                "    OPTIONAL{?character IRI:hasDescription ?description}" +
                "}"
        );
    }

    @Override
    public void save(Marvel marvel, boolean cascadeSave) {
        sparqlEndpoint.executeUpdate(
            "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
            "INSERT DATA {" +
                "IRI:" + marvel.RES_IDENTIFIER + " a " + "IRI:Marvel" + " ." +
                "IRI:" + marvel.RES_IDENTIFIER + " IRI:hasName " + "\"" + marvel.getName() + "\"" + " ." +
                "IRI:" + marvel.RES_IDENTIFIER + " IRI:hasDescription " + "\"" + marvel.getDescription() + "\"" + " ." +
                "IRI:" + marvel.RES_IDENTIFIER + " IRI:hasImageURL " + "\"" + marvel.getImageURL() + "\"" + " ." +
                getStatementsForItems(marvel) +
                getStatementsForPokemon(marvel) +
            "}"
        );

        if(cascadeSave){
            saveItems(marvel.getItemInventory());
            savePokemon(marvel.getPokemonInventory());
        }
    }

    @Override
    public void delete(Marvel marvel) {
        sparqlEndpoint.executeUpdate(
                "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
                "DELETE {IRI:" + marvel.RES_IDENTIFIER + " ?p ?o} " +
                "WHERE {IRI:" + marvel.RES_IDENTIFIER + " ?p ?o}"
        );
    }

    private String getStatementsForPokemon(Marvel marvel) {
        List<Pokemon> pokemonInventory = marvel.getPokemonInventory();
        AtomicReference<String> pokemonStatements = new AtomicReference<>("");
        pokemonInventory.forEach(pokemon -> pokemonStatements.getAndAccumulate("IRI:" + marvel.RES_IDENTIFIER + " IRI:hasInventoryPokemon " +
                "IRI:" + pokemon.RES_IDENTIFIER + " .", (s, s2) -> s + s2));
        return pokemonStatements.get();
    }

    private String getStatementsForItems(Marvel marvel) {
        List<Item> itemInventory = marvel.getItemInventory();
        AtomicReference<String> itemStatements = new AtomicReference<>("");
        itemInventory.forEach(item -> itemStatements.getAndAccumulate("IRI:" + marvel.RES_IDENTIFIER + " IRI:hasInventoryItem " +
                "IRI:" + item.RES_IDENTIFIER + " .", (s, s2) -> s + s2));
        return itemStatements.get();
    }

    private void savePokemon(List<Pokemon> pokemonInventory) {
        pokemonInventory.forEach(pokemon -> pokemonService.save(pokemon, true));
    }

    private void saveItems(List<Item> itemInventory) {
        itemInventory.forEach(item -> itemServiceCrud.save(item));
    }
}
