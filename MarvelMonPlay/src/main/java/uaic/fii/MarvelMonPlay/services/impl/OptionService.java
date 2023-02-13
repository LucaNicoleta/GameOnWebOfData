package uaic.fii.MarvelMonPlay.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.springframework.stereotype.Service;

import uaic.fii.MarvelMonPlay.models.Event;
import uaic.fii.MarvelMonPlay.models.scenes.Option;
import uaic.fii.MarvelMonPlay.repositories.OptionRepository;
import uaic.fii.MarvelMonPlay.repositories.impl.OptionRepositoryImpl;

@Service
public class OptionService {
    OptionRepository optionRepository;


    public OptionService(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }
    public void save(Option o){
optionRepository.save(o);
    }
    public Event getEventTriggeredByOption(String currentSceneRes, String choosenOption){
        String event = "NONE";
        try (TupleQueryResult tqr = optionRepository.getEventTriggeredByOption(currentSceneRes, choosenOption)) {
            while (tqr.hasNext()) {
                BindingSet bindingSet = tqr.next();
                event = bindingSet.getValue("event").stringValue().substring(11);
            }
        }
        return Event.valueOf(event);
    }
    public String getContentOfOption(String currentSceneRes, String choosenOption){
        String content = "";
        try (TupleQueryResult tqr = optionRepository.getContentOfOption(currentSceneRes, choosenOption)) {
            while (tqr.hasNext()) {
                BindingSet bindingSet = tqr.next();
                content = bindingSet.getValue("content").stringValue();
            }
        }
        return content;
    }

}
