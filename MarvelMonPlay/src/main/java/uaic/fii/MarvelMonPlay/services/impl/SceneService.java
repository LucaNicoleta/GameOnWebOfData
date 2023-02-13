package uaic.fii.MarvelMonPlay.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.TupleQueryResult;

import org.springframework.stereotype.Service;
import uaic.fii.MarvelMonPlay.exceptions.ResourceNotFoundException;
import uaic.fii.MarvelMonPlay.models.scenes.NextSceneRef;
import uaic.fii.MarvelMonPlay.models.scenes.Option;
import uaic.fii.MarvelMonPlay.models.scenes.OptionsEnum;
import uaic.fii.MarvelMonPlay.models.scenes.Scene;
import uaic.fii.MarvelMonPlay.models.scenes.SceneTypes;
import uaic.fii.MarvelMonPlay.models.Event;
import uaic.fii.MarvelMonPlay.repositories.impl.SceneRepository;

@Service
public class SceneService {
    SceneRepository sceneRepository;


    public SceneService(SceneRepository sceneRepository) {
        this.sceneRepository = sceneRepository;
    }

    public Scene findByPlayerStats(NextSceneRef ref, String ResMarvel, String criteria) {
        Scene scene = null;
        if (criteria.equals("ELEMENT"))
            try (TupleQueryResult tqr = sceneRepository.findSceneWithReqElement(ref, ResMarvel)) {
                if (tqr.hasNext()) {
                    BindingSet bindingSet = tqr.next();

                    String scenelRES = bindingSet.getValue("s").isIRI() ? bindingSet.getValue("s").toString() : "";

                    try {
                        return findByResIdentifier(scenelRES.substring(scenelRES.indexOf('#') + 1));
                    } catch (ResourceNotFoundException e) {
                        e.printStackTrace();
                        return null;
                    }

                }
            }
        else
            try (TupleQueryResult tqr = sceneRepository.findSceneWithReqPokemon(ref, ResMarvel)) {
                if (tqr.hasNext()) {
                    BindingSet bindingSet = tqr.next();

                    String scenelRES = bindingSet.getValue("s").isIRI() ? bindingSet.getValue("s").toString() : "";

                    try {
                        return findByResIdentifier(scenelRES.substring(scenelRES.indexOf('#') + 1));
                    } catch (ResourceNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        return null;
                    }

                }
            }
        return scene;
    }
    public void save(Scene s){
        sceneRepository.save(s);
    }
    public Scene findByChoosenOption(NextSceneRef ref, String option) {
        Scene scene = null;
        try (TupleQueryResult tqr = sceneRepository.findSceneWithReqOption(ref, option)) {
            if (tqr.hasNext()) {
                BindingSet bindingSet = tqr.next();

                String scenelRES = bindingSet.getValue("s").isIRI() ? bindingSet.getValue("s").toString() : "";

                try {
                    return findByResIdentifier(scenelRES.substring(scenelRES.indexOf('#') + 1));
                } catch (ResourceNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return null;
                }

            }
        }

        return scene;
    }

    public Scene findByResIdentifier(String RES_Identifier) throws ResourceNotFoundException {
        Scene scene = null;
        try (TupleQueryResult tqr = sceneRepository.findSceneInfo(RES_Identifier)) {
            if (tqr.hasNext()) {
                BindingSet bindingSet = tqr.next();
                String text = bindingSet.getValue("content").stringValue();
                String imageURL = bindingSet.getValue("imageUrl").stringValue();
                String sceneType = bindingSet.getValue("type").stringValue();
                String MarvelRES = bindingSet.getValue("Marvel").isIRI() ? bindingSet.getValue("Marvel").toString()
                        : "";
                String req = bindingSet.getValue("req").stringValue();

                //System.out.println(text + " \n" + imageURL + "\n" + sceneType + "\n" + MarvelRES + "\n" + req);
                if (SceneTypes.ACTIVE.name().equals(sceneType))
                    return new Scene(RES_Identifier, text, imageURL, findOptionsPerScene(RES_Identifier),
                            SceneTypes.ACTIVE, MarvelRES, req);
                return new Scene(RES_Identifier, text, imageURL, Collections.<Option>emptyList(), SceneTypes.PASSIVE,
                        MarvelRES, req);

            }
        }

        return scene;
    }

    public List<Option> findOptionsPerScene(String RES_Identifier) {
        List<Option> options = new ArrayList<Option>();
        try (TupleQueryResult tqr = sceneRepository.findSceneOptions(RES_Identifier)) {
            while (tqr.hasNext()) {
                BindingSet bindingSet = tqr.next();
                String text = bindingSet.getValue("content").stringValue();
                String val = bindingSet.getValue("value").stringValue().substring(11);
                String event = bindingSet.getValue("event").stringValue().substring(11);
                String res = bindingSet.getValue("option").toString();
                //System.out.println(text + " \n" + val + "\n" + event + "\n");
                options.add(new Option(res, text, Event.valueOf(event), OptionsEnum.valueOf(val)));
            }
        }
        return options;
    }
    
    public Scene getFirstScene() throws ResourceNotFoundException {
        return findByResIdentifier("S1");
    }
    
    }
