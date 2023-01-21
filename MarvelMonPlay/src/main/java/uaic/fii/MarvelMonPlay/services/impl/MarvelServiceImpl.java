package uaic.fii.MarvelMonPlay.services.impl;

import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.springframework.stereotype.Service;
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
                String imageUrl = bindingSet.getValue("imageUrl").stringValue();
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
    public void save(Marvel marvel, boolean cascadeSaveOrUpdate) {
        marvelRepository.save(marvel, cascadeSaveOrUpdate);
    }

    @Override
    public void delete(Marvel marvel) {
        marvelRepository.delete(marvel);
    }
}
