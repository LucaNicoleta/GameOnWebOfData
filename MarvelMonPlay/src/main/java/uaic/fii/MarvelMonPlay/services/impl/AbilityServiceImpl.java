package uaic.fii.MarvelMonPlay.services.impl;

import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.springframework.stereotype.Service;
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
                abilities.add(new Ability(name, name, description));
            }
        }
        return abilities;
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
