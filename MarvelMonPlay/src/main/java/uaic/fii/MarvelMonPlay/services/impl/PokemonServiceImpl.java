package uaic.fii.MarvelMonPlay.services.impl;

import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.springframework.stereotype.Service;
import uaic.fii.MarvelMonPlay.exceptions.ResourceNotFoundException;
import uaic.fii.MarvelMonPlay.models.abilities.Ability;
import uaic.fii.MarvelMonPlay.models.characters.Pokemon;
import uaic.fii.MarvelMonPlay.repositories.PokemonRepository;
import uaic.fii.MarvelMonPlay.services.PokemonService;

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
                String abilitiesValue = bindingSet.getValue("abilities").stringValue();
                List<Ability> abilityList = getAbilities((abilitiesValue));
                Value healthPointsValue = bindingSet.getValue("healthPoints");
                Value powerAttackValue = bindingSet.getValue("powerAttack");
                Value powerDefensevalue = bindingSet.getValue("powerDefense");

                int healthPoints = healthPointsValue == null ? Pokemon.MAX_HEALTH_POINTS : Integer.parseInt(healthPointsValue.stringValue());
                int powerAttack = powerAttackValue == null ? Pokemon.DEFAULT_POWER_ATTACK : Integer.parseInt(powerAttackValue.stringValue());
                int powerDefense = powerDefensevalue == null ? Pokemon.DEFAULT_POWER_DEFENSE : Integer.parseInt(powerDefensevalue.stringValue());

                pokemonList.add(new Pokemon(name, name, abilityList, healthPoints, powerAttack, powerDefense));
            }
        }
        return pokemonList;
    }

    @Override
    public Pokemon findByName(String name) throws ResourceNotFoundException {
        Pokemon pokemon = null;
        try(TupleQueryResult tqr = pokemonRepository.findByName(name)){
            if(tqr.hasNext()){
                BindingSet bindingSet = tqr.next();
                String abilitiesValue = bindingSet.getValue("abilities").stringValue();
                List<Ability> abilityList = getAbilities((abilitiesValue));
                Value healthPointsValue = bindingSet.getValue("healthPoints");
                Value powerAttackValue = bindingSet.getValue("powerAttack");
                Value powerDefensevalue = bindingSet.getValue("powerDefense");

                int healthPoints = healthPointsValue == null ? Pokemon.MAX_HEALTH_POINTS : Integer.parseInt(healthPointsValue.stringValue());
                int powerAttack = powerAttackValue == null ? Pokemon.DEFAULT_POWER_ATTACK : Integer.parseInt(powerAttackValue.stringValue());
                int powerDefense = powerDefensevalue == null ? Pokemon.DEFAULT_POWER_DEFENSE : Integer.parseInt(powerDefensevalue.stringValue());

                pokemon = new Pokemon(name, name, abilityList, healthPoints, powerAttack, powerDefense);
            }
        }
        if(pokemon == null) {
            throw new ResourceNotFoundException("Pokemon " + name + " not found");
        }

        return pokemon;
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
    public void save(Pokemon pokemon, boolean cascadeSaveOrUpdate) {
        pokemonRepository.save(pokemon, cascadeSaveOrUpdate);
    }

    @Override
    public void update(Pokemon pokemon, boolean cascadeUpdate) {
        pokemonRepository.update(pokemon, cascadeUpdate);
    }

    @Override
    public void delete(Pokemon pokemon) {
        pokemonRepository.delete(pokemon);
    }
}
