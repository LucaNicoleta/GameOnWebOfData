package uaic.fii.MarvelMonPlay.services.impl;

import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uaic.fii.MarvelMonPlay.exceptions.AbilityNotFoundException;
import uaic.fii.MarvelMonPlay.exceptions.ItemNotFoundException;
import uaic.fii.MarvelMonPlay.exceptions.PokemonNotFoundException;
import uaic.fii.MarvelMonPlay.exceptions.ResourceNotFoundException;
import uaic.fii.MarvelMonPlay.models.characters.Marvel;
import uaic.fii.MarvelMonPlay.models.characters.Pokemon;
import uaic.fii.MarvelMonPlay.models.items.Item;
import uaic.fii.MarvelMonPlay.repositories.MarvelRepository;
import uaic.fii.MarvelMonPlay.services.ItemServiceCrud;
import uaic.fii.MarvelMonPlay.services.MarvelService;
import uaic.fii.MarvelMonPlay.services.PokemonService;

import java.util.ArrayList;
import java.util.List;

@Service
public class MarvelServiceImpl implements MarvelService {

    private final MarvelRepository marvelRepository;
    @Autowired
    private PokemonService pokemonService;

    @Autowired
    private ItemServiceCrud itemServiceCrud;

    public MarvelServiceImpl(MarvelRepository marvelRepository){
        this.marvelRepository = marvelRepository;
    }

    @Override
    @Deprecated
    public List<Marvel> findAll() {
        List<Marvel> marvelList = new ArrayList<>();
        try (TupleQueryResult tupleQueryResult = marvelRepository.findAll()) {
            while (tupleQueryResult.hasNext()) {
                BindingSet bindingSet = tupleQueryResult.next();
                String name = bindingSet.getValue("name").stringValue();
                String imageUrl = bindingSet.getValue("imageURL").stringValue();


                marvelList.add(new Marvel("to_be_changed", name, imageUrl));
            }
        }
        return marvelList;
    }

    @Override
    public void save(Marvel marvel, boolean cascadeSave) {
        marvelRepository.save(marvel, cascadeSave);
    }

    @Override
    @Deprecated
    public Marvel findByName(String name) throws ResourceNotFoundException {
        Marvel marvel = null;
        try (TupleQueryResult tqr = marvelRepository.findByName(name)) {
            if (tqr.hasNext()) {
                BindingSet bindingSet = tqr.next();
                String imageUrl = bindingSet.getValue("imageURL").stringValue();

                marvel = new Marvel("to_be_changed", name, imageUrl);
            }
        }
        if(marvel == null){
            throw new ResourceNotFoundException("Marvel character by name " + name + " not found");
        }
        return marvel;
    }

    @Override
    public List<String> findPokemonInventoryResIdentifiers(String marvelResIdentifier) {
        List<String> pokemonResIdentifiers = new ArrayList<>();
        try (TupleQueryResult tqr = marvelRepository.findPokemonIdentifiers(marvelResIdentifier)) {
            while(tqr.hasNext()){
                BindingSet bs = tqr.next();
                String pokemonResIdentifierWithIRI = bs.getValue("pokemon").stringValue();
                String pokemonResIdentifierWithoutIRI = pokemonResIdentifierWithIRI.substring(pokemonResIdentifierWithIRI.indexOf("#")+1);
                pokemonResIdentifiers.add(pokemonResIdentifierWithoutIRI);
            }
        }
        return pokemonResIdentifiers;
    }

    @Override
    public List<Pokemon> findPokemonInventory(String marvelResIdentifier) throws PokemonNotFoundException, AbilityNotFoundException {
        List<Pokemon> pokemonList = new ArrayList<>();
        List<String> pokemonIdentifiers = findPokemonInventoryResIdentifiers(marvelResIdentifier);
        for(String pokemonIdentifier : pokemonIdentifiers){
            Pokemon pokemon = pokemonService.findByResIdentifier(pokemonIdentifier);
            pokemonList.add(pokemon);
        }
        return pokemonList;
    }

    @Override
    public List<String> findItemInventoryIdentifiers(String marvelResIdentifier) {
        List<String> itemIdentifiers = new ArrayList<>();
        try (TupleQueryResult tqr = marvelRepository.findItemIdentifiers(marvelResIdentifier)) {
            while (tqr.hasNext()){
                BindingSet bs = tqr.next();
                String itemIdentifierWithIRI = bs.getValue("item").stringValue();
                String itemIdentifierWithoutIRI = itemIdentifierWithIRI.substring(itemIdentifierWithIRI.indexOf("#")+1);
                itemIdentifiers.add(itemIdentifierWithoutIRI);
            }
        }
        return itemIdentifiers;
    }

    @Override
    public List<Item> findItemInventory(String marvelResIdentifier) throws ItemNotFoundException {
        List<Item> itemList = new ArrayList<>();
        List<String> itemIdentifiers = findItemInventoryIdentifiers(marvelResIdentifier);
        for(String itemIdentifier : itemIdentifiers){
            Item item = itemServiceCrud.findByResIdentifier(itemIdentifier);
            itemList.add(item);
        }
        return itemList;
    }

    @Override
    public Marvel findByResIdentifier(String RES_IDENTIFIER) throws ResourceNotFoundException {
        Marvel marvel = null;
        try (TupleQueryResult tqr = marvelRepository.findByResIdentifier(RES_IDENTIFIER)) {
            if (tqr.hasNext()) {
                BindingSet bindingSet = tqr.next();
                String name = bindingSet.getValue("name").stringValue();
                String imageUrl = bindingSet.getValue("imageURL").stringValue();
                marvel = new Marvel(RES_IDENTIFIER, name, imageUrl);
            }
        }
        if(marvel == null){
            throw new ResourceNotFoundException("Marvel by res identifier " + RES_IDENTIFIER + " not found");
        }
        return marvel;
    }

    @Override
    public void update(Marvel marvel, boolean cascadeUpdate) {
        marvelRepository.update(marvel, cascadeUpdate);
    }

    @Override
    public void delete(Marvel marvel) {
        marvelRepository.delete(marvel);
    }
    public void setElementRepresentativeForMarvel(String marvel_res, String element){
        marvelRepository.setElementRepresentativeForMarvel(marvel_res, element);
    }
    public void addItemForMarvel(String marvel_res, String item_res){
        marvelRepository.addItemForMarvel(marvel_res, item_res);
    }
    public List<String> getNamesOfThePokemonsOwnedByMarvel(String RES_Marvel){

        List<String> names = new ArrayList<String>();
        try (TupleQueryResult tqr = marvelRepository.getListOfPokemonNamesOwnedByMarvel(RES_Marvel)) {
            while (tqr.hasNext()) {
                BindingSet bindingSet = tqr.next();
                String name = bindingSet.getValue("pokemonName").stringValue();
                names.add(name);
            }
        }
        return names;
    }
}
