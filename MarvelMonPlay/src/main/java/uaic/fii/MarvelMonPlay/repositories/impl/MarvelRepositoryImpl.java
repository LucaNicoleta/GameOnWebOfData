package uaic.fii.MarvelMonPlay.repositories.impl;

import org.eclipse.rdf4j.query.TupleQueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uaic.fii.MarvelMonPlay.endpoints.SparqlEndpoint;
import uaic.fii.MarvelMonPlay.models.characters.Marvel;
import uaic.fii.MarvelMonPlay.models.characters.Pokemon;
import uaic.fii.MarvelMonPlay.models.items.Item;
import uaic.fii.MarvelMonPlay.repositories.ItemRepository;
import uaic.fii.MarvelMonPlay.repositories.MarvelRepository;
import uaic.fii.MarvelMonPlay.repositories.PokemonRepository;
import uaic.fii.MarvelMonPlay.utils.IRIFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Repository
public class MarvelRepositoryImpl implements MarvelRepository {

    private final SparqlEndpoint sparqlEndpoint;
    @Autowired
    private PokemonRepository pokemonRepository;
    @Autowired
    private ItemRepository itemRepository;

    public MarvelRepositoryImpl(SparqlEndpoint sparqlEndpoint){
        this.sparqlEndpoint = sparqlEndpoint;
    }

    @Override
    public TupleQueryResult findAll() {
        return sparqlEndpoint.executeQuery(
            "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
            "select ?character ?name ?imageURL ?description where {" +
            "    ?character a IRI:Marvel ." +
            "    ?character IRI:hasName ?name ." +
            "    ?character IRI:hasImageURL ?imageURL ." +
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
                getInsertStatementsForItems(marvel) +
                getInsertStatementsForPokemon(marvel) +
            "}"
        );

        if(cascadeSave){
            saveItems(marvel.getItemInventory());
            savePokemon(marvel.getPokemonInventory());
        }
    }

    @Override
    public TupleQueryResult findByName(String name) {
        return sparqlEndpoint.executeQuery(
            "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
            "SELECT ?character ?name ?imageURL ?description where {" +
            "    ?character a IRI:Marvel ." +
            "    ?character IRI:hasName" + "\"" + name + "\" ." +
            "    ?character IRI:hasImageURL ?imageURL ." +
            "    OPTIONAL{?character IRI:hasDescription ?description}" +
            "}"
        );
    }

    @Override
    public TupleQueryResult findByResIdentifier(String RES_IDENTIFIER) {
        return sparqlEndpoint.executeQuery(
            "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
            "SELECT ?name ?imageURL ?description where {" +
            "    IRI:" + RES_IDENTIFIER + " a IRI:Marvel ." +
            "    IRI:" + RES_IDENTIFIER + " IRI:hasName ?name ." +
            "    IRI:" + RES_IDENTIFIER + " IRI:hasImageURL ?imageURL ." +
            "    OPTIONAL{IRI:" + RES_IDENTIFIER + " IRI:hasDescription ?description}" +
            "}"
        );
    }

    @Override
    public void update(Marvel marvel, boolean cascadeUpdate) {
        sparqlEndpoint.executeUpdate(
            "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
            "DELETE {" +
                "IRI:" + marvel.RES_IDENTIFIER + " IRI:hasName ?o1." +
                "IRI:" + marvel.RES_IDENTIFIER + " IRI:hasDescription ?o2. " +
                "IRI:" + marvel.RES_IDENTIFIER + " IRI:hasImageURL ?o3. " +
                getSelectStatementsForItems(marvel, false) +
                getSelectStatementsForPokemon(marvel, false) +
            "}" +
            "INSERT {" +
                "IRI:" + marvel.RES_IDENTIFIER + " IRI:hasName " + "\"" + marvel.getName() + "\". " +
                "IRI:" + marvel.RES_IDENTIFIER + " IRI:hasDescription " + "\"" + marvel.getDescription() + "\". " +
                "IRI:" + marvel.RES_IDENTIFIER + " IRI:hasImageURL " + "\"" + marvel.getImageURL() + "\". " +
                getInsertStatementsForItems(marvel) +
                getInsertStatementsForPokemon(marvel) +
            "}" +
            "WHERE {" +
                "IRI:" + marvel.RES_IDENTIFIER + " IRI:hasName ?o1. " +
                "IRI:" + marvel.RES_IDENTIFIER + " IRI:hasDescription ?o2. " +
                "IRI:" + marvel.RES_IDENTIFIER + " IRI:hasImageURL ?o3. " +
                getSelectStatementsForItems(marvel, true) +
                getSelectStatementsForPokemon(marvel, true) +
            "}"
        );

        if(cascadeUpdate){
            updateItems(marvel.getItemInventory());
            updatePokemon(marvel.getPokemonInventory());
        }
    }

    private void updatePokemon(List<Pokemon> pokemonInventory) {
        pokemonInventory.forEach(pokemon -> pokemonRepository.update(pokemon, true));
    }

    private void updateItems(List<Item> itemInventory) {
        itemInventory.forEach(item -> itemRepository.update(item));
    }

    private String getSelectStatementsForPokemon(Marvel marvel, boolean withOptionalClause) {
        List<Pokemon> pokemonInventory = marvel.getPokemonInventory();
        AtomicReference<String> pokemonStatements = new AtomicReference<>("");
        pokemonInventory.forEach(pokemon -> {
            String str = withOptionalClause ? "OPTIONAL{IRI:" + marvel.RES_IDENTIFIER + " IRI:hasInventoryPokemon ?" + pokemon.getName() + "}. "
                                          : "IRI:" + marvel.RES_IDENTIFIER + " IRI:hasInventoryPokemon ?" + pokemon.getName() + ". ";
            pokemonStatements.getAndAccumulate(str, (s, s2) -> s + s2);
        });
        return pokemonStatements.get();
    }

    private String getSelectStatementsForItems(Marvel marvel, boolean withOptionalClause) {
        List<Item> itemInventory = marvel.getItemInventory();
        AtomicReference<String> itemStatements = new AtomicReference<>("");
        itemInventory.forEach(item -> {
            String str = withOptionalClause ? "OPTIONAL{IRI:" + marvel.RES_IDENTIFIER + " IRI:hasInventoryItem ?" + item.getName() + "}. "
                                            : "IRI:" + marvel.RES_IDENTIFIER + " IRI:hasInventoryItem ?" + item.getName() + ". ";
            itemStatements.getAndAccumulate(str, (s, s2) -> s + s2);
        });
        return itemStatements.get();
    }

    @Override
    public void delete(Marvel marvel) {
        sparqlEndpoint.executeUpdate(
                "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
                "DELETE {IRI:" + marvel.RES_IDENTIFIER + " ?p ?o} " +
                "WHERE {IRI:" + marvel.RES_IDENTIFIER + " ?p ?o}"
        );
    }

    private String getInsertStatementsForPokemon(Marvel marvel) {
        List<Pokemon> pokemonInventory = marvel.getPokemonInventory();
        AtomicReference<String> pokemonStatements = new AtomicReference<>("");
        pokemonInventory.forEach(pokemon -> pokemonStatements.getAndAccumulate("IRI:" + marvel.RES_IDENTIFIER + " IRI:hasInventoryPokemon " +
                "IRI:" + pokemon.RES_IDENTIFIER + " .", (s, s2) -> s + s2));
        return pokemonStatements.get();
    }

    private String getInsertStatementsForItems(Marvel marvel) {
        List<Item> itemInventory = marvel.getItemInventory();
        AtomicReference<String> itemStatements = new AtomicReference<>("");
        itemInventory.forEach(item -> itemStatements.getAndAccumulate("IRI:" + marvel.RES_IDENTIFIER + " IRI:hasInventoryItem " +
                "IRI:" + item.RES_IDENTIFIER + " .", (s, s2) -> s + s2));
        return itemStatements.get();
    }

    private void savePokemon(List<Pokemon> pokemonInventory) {
        pokemonInventory.forEach(pokemon -> pokemonRepository.save(pokemon, true));
    }

    private void saveItems(List<Item> itemInventory) {
        itemInventory.forEach(item -> itemRepository.save(item));
    }
}
