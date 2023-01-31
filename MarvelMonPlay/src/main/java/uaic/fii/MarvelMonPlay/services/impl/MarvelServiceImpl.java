package uaic.fii.MarvelMonPlay.services.impl;

import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.springframework.stereotype.Service;
import uaic.fii.MarvelMonPlay.exceptions.ResourceNotFoundException;
import uaic.fii.MarvelMonPlay.models.characters.Marvel;
import uaic.fii.MarvelMonPlay.repositories.MarvelRepository;
import uaic.fii.MarvelMonPlay.services.MarvelService;

import java.util.ArrayList;
import java.util.List;

@Service
public class MarvelServiceImpl implements MarvelService {

    private final MarvelRepository marvelRepository;

    public MarvelServiceImpl(MarvelRepository marvelRepository){
        this.marvelRepository = marvelRepository;
    }

    @Override
    public List<Marvel> findAll() {
        List<Marvel> marvelList = new ArrayList<>();
        try (TupleQueryResult tupleQueryResult = marvelRepository.findAll()) {
            while (tupleQueryResult.hasNext()) {
                BindingSet bindingSet = tupleQueryResult.next();
                String name = bindingSet.getValue("name").stringValue();
                String imageUrl = bindingSet.getValue("imageURL").stringValue();
                String description = "";
                if (bindingSet.getValue("description") != null) {
                    description = bindingSet.getValue("description").stringValue();
                }

                marvelList.add(new Marvel(name, name, imageUrl, description));
            }
        }
        return marvelList;
    }

    @Override
    public void save(Marvel marvel, boolean cascadeSave) {
        marvelRepository.save(marvel, cascadeSave);
    }

    @Override
    public Marvel findByName(String name) throws ResourceNotFoundException {
        Marvel marvel = null;
        try (TupleQueryResult tqr = marvelRepository.findByName(name)) {
            if (tqr.hasNext()) {
                BindingSet bindingSet = tqr.next();
                String imageUrl = bindingSet.getValue("imageURL").stringValue();
                String description = "";
                if (bindingSet.getValue("description") != null) {
                    description = bindingSet.getValue("description").stringValue();
                }
                marvel = new Marvel(name, name, imageUrl, description);
            }
        }
        if(marvel == null){
            throw new ResourceNotFoundException("Marvel character by name " + name + " not found");
        }
        return marvel;
    }

    @Override
    public Marvel findByResIdentifier(String RES_IDENTIFIER) throws ResourceNotFoundException {
        Marvel marvel = null;
        try (TupleQueryResult tqr = marvelRepository.findByResIdentifier(RES_IDENTIFIER)) {
            if (tqr.hasNext()) {
                BindingSet bindingSet = tqr.next();
                String name = bindingSet.getValue("name").stringValue();
                String imageUrl = bindingSet.getValue("imageURL").stringValue();
                Value descriptionValue = bindingSet.getValue("description");
                String description = descriptionValue == null ? "" : descriptionValue.stringValue();
                marvel = new Marvel(name, name, imageUrl, description);
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
}
