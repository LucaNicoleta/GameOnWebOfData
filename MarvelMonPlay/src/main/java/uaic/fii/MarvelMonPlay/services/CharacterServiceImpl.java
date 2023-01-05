package uaic.fii.MarvelMonPlay.services;

import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.springframework.stereotype.Service;
import uaic.fii.MarvelMonPlay.models.characters.Character;
import uaic.fii.MarvelMonPlay.models.characters.Marvel;
import uaic.fii.MarvelMonPlay.repositories.CharacterRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CharacterServiceImpl implements CharacterService{
    private final CharacterRepository characterRepository;

    public CharacterServiceImpl(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    @Override
    public List<Character> getMarvelCharacters() {
        List<Character> marvelList = new ArrayList<>();
        TupleQueryResult tupleQueryResult = characterRepository.getMarvelCharacters();

        while (tupleQueryResult.hasNext()) {
            BindingSet bindingSet = tupleQueryResult.next();
            String name = bindingSet.getValue("name").stringValue();
            String imageUrl = bindingSet.getValue("imageUrl").stringValue();
            String description = "";
            if(bindingSet.getValue("description") != null){
                description = bindingSet.getValue("description").stringValue();
            }

            marvelList.add(new Marvel(name, imageUrl, description));
        }
        marvelList.forEach(System.out::println);
        return marvelList;
    }

    @Override
    public List<Character> getPokemonCharacters() {
        List<Character> pokemonList = new ArrayList<>();

        // to be implemented
        //characterRepository.getPokemonCharacters();

        return pokemonList;
    }
}
