package uaic.fii.MarvelMonPlay.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.TupleQueryResult;

import org.springframework.stereotype.Service;
import uaic.fii.MarvelMonPlay.models.scenes.NextSceneRef;
import uaic.fii.MarvelMonPlay.repositories.impl.NextScenesRepository;

@Service
public class NextScenesRefService {
    NextScenesRepository sceneRefRepository;

    public NextScenesRefService(NextScenesRepository sceneRefRepository) {
        this.sceneRefRepository = sceneRefRepository;
    }

    public NextSceneRef findByResIdentifier(String RES_Identifier)  {
        NextSceneRef scene = null;
        List<String> scenes = new ArrayList<String>();
        String criteria="";
        String refRES="";
        try (TupleQueryResult tqr = sceneRefRepository.findRefbyScene(RES_Identifier)) {
            while (tqr.hasNext()) {
                BindingSet bindingSet = tqr.next();

                String s = bindingSet.getValue("scene").toString();
                criteria = bindingSet.getValue("criteria").stringValue();
                refRES = bindingSet.getValue("ref").stringValue();
                String ref = s.substring(s.indexOf('#') + 1);
                scenes.add(ref);

                System.out.println(ref + " \n" + criteria + "\n");


            }
        }

        return new NextSceneRef(refRES.substring(refRES.indexOf('#') + 1), criteria, scenes);
    }


}
