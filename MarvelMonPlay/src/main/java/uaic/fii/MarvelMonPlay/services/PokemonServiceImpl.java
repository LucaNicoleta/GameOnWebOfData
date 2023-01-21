package uaic.fii.MarvelMonPlay.services;

import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.springframework.stereotype.Service;
import uaic.fii.MarvelMonPlay.models.abilities.Ability;
import uaic.fii.MarvelMonPlay.models.characters.Pokemon;
import uaic.fii.MarvelMonPlay.repositories.PokemonRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PokemonServiceImpl implements PokemonService {
    private final PokemonRepository pokemonRepository;

    public PokemonServiceImpl(PokemonRepository pokemonRepository){
        this.pokemonRepository = pokemonRepository;
    }
    @Override
    public List<Pokemon> findAll() {
        List<Pokemon> pokemonList = new ArrayList<>();
        try (TupleQueryResult tupleQueryResult = pokemonRepository.findAll()) {
            while (tupleQueryResult.hasNext()) {
                BindingSet bindingSet = tupleQueryResult.next();
                String name = bindingSet.getValue("name").stringValue();
                String abilities = bindingSet.getValue("abilities").stringValue();
                List<Ability> abilityList = getAbilities(abilities);
                String healthPoints = bindingSet.getValue("healthPoints").stringValue();
                Pokemon pokemon = new Pokemon(name, name);
                pokemon.setAbilities(abilityList);
                pokemon.setHealthPoints(Integer.parseInt(healthPoints));
                pokemonList.add(pokemon);
            }
        }
        return pokemonList;
    }

    private List<Ability> getAbilities(String abilities) {
        String[] abilitiesSplit = abilities.split(",");
        List<Ability> abilityList = new ArrayList<>();
        for (String s : abilitiesSplit) {
            String abilityName = s.substring(s.indexOf("#") + 1);
            abilityList.add(new Ability(abilityName, abilityName, "NoDescription"));
        }
        return abilityList;
    }

    @Override
    public void saveOrUpdate(Pokemon pokemon, boolean cascadeSaveOrUpdate) {
        pokemonRepository.saveOrUpdate(pokemon, cascadeSaveOrUpdate);
    }

    @Override
    public void delete(Pokemon pokemon) {
        pokemonRepository.delete(pokemon);
    }
}
