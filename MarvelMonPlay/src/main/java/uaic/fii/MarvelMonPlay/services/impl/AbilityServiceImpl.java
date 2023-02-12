package uaic.fii.MarvelMonPlay.services.impl;

import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.springframework.stereotype.Service;
import uaic.fii.MarvelMonPlay.exceptions.AbilityNotFoundException;
import uaic.fii.MarvelMonPlay.models.abilities.Ability;
import uaic.fii.MarvelMonPlay.repositories.AbilityRepository;
import uaic.fii.MarvelMonPlay.services.AbilityService;

import java.util.ArrayList;
import java.util.List;

@Service
public class AbilityServiceImpl implements AbilityService {
    private final AbilityRepository abilityRepository;

    public AbilityServiceImpl(AbilityRepository abilityRepository){
        this.abilityRepository = abilityRepository;
    }
    @Override
    public List<Ability> findAll() {
        List<Ability> abilities = new ArrayList<>();
        try (TupleQueryResult tupleQueryResult = abilityRepository.findAll()) {
            while (tupleQueryResult.hasNext()) {
                BindingSet bindingSet = tupleQueryResult.next();
                String name = bindingSet.getValue("name").stringValue();
                String description =  bindingSet.getValue("description").stringValue();
                abilities.add(new Ability("to_be_changed", name, description));
            }
        }
        return abilities;
    }

    @Override
    public List<String> findAbilityIdentifiersByPokemonResIdentifier(String pokemonResIdentifier){
        List<String> abilityIdentifiers = new ArrayList<>();
        try (TupleQueryResult tqr = abilityRepository.findAbilityResIdentifiersByPokemonResIdentifier(pokemonResIdentifier)) {
            while(tqr.hasNext()){
                BindingSet bs = tqr.next();
                Value abilityResIdentifierValue = bs.getValue("ability");
                String abilityResIdentifierWithIRI = abilityResIdentifierValue.stringValue();
                String abilityResIdentifierWithoutIRI = abilityResIdentifierWithIRI.substring(abilityResIdentifierWithIRI.indexOf("#")+1);
                abilityIdentifiers.add(abilityResIdentifierWithoutIRI);
            }
        }
        return abilityIdentifiers;
    }

    @Override
    public Ability findByResIdentifier(String abilityResIdentifier) throws AbilityNotFoundException {
        try (TupleQueryResult tqr = abilityRepository.findByResIdentifier(abilityResIdentifier)) {
            if(tqr.hasNext()){
                BindingSet bs = tqr.next();
                Value nameValue = bs.getValue("name");
                Value descriptionValue = bs.getValue("description");

                String name = nameValue.stringValue();
                String description = descriptionValue.stringValue();
                return new Ability(abilityResIdentifier, name, description);
            }
            throw new AbilityNotFoundException("Ability " + abilityResIdentifier + " has not been found");
        }
    }

    @Override
    public void save(Ability ability) {
        abilityRepository.save(ability);
    }

    @Override
    public void update(Ability ability) {
        abilityRepository.update(ability);
    }

    @Override
    public void delete(Ability ability) {
        abilityRepository.delete(ability);
    }
}
