package uaic.fii.MarvelMonPlay.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.TupleQueryResult;


import uaic.fii.MarvelMonPlay.exceptions.ResourceNotFoundException;
import uaic.fii.MarvelMonPlay.models.scenes.NextSceneRef;
import uaic.fii.MarvelMonPlay.models.scenes.Option;
import uaic.fii.MarvelMonPlay.models.scenes.OptionsEnum;
import uaic.fii.MarvelMonPlay.models.scenes.Scene;
import uaic.fii.MarvelMonPlay.models.scenes.SceneTypes;
import uaic.fii.MarvelMonPlay.models.Event;
import uaic.fii.MarvelMonPlay.models.characters.Marvel;
import uaic.fii.MarvelMonPlay.repositories.impl.NextScenesRepository;
import uaic.fii.MarvelMonPlay.repositories.impl.SceneRepository;

public class SceneService {
    SceneRepository sceneRepository;
    NextScenesRefService nextServ;
    public SceneService(SceneRepository sceneRepository,NextScenesRepository nextRep){
        this.sceneRepository = sceneRepository;
        this.nextServ = new NextScenesRefService(nextRep);
    }
    public Scene findByConditions(NextSceneRef ref, String ResMarvel, String req)  {
        Scene scene = null;
        try(TupleQueryResult tqr = sceneRepository.findSceneWithReq(ref, ResMarvel, req)){
            if(tqr.hasNext()){
                BindingSet bindingSet = tqr.next();

                String scenelRES = bindingSet.getValue("s").isIRI()? bindingSet.getValue("s").toString():"";

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
        try(TupleQueryResult tqr = sceneRepository.findSceneInfo(RES_Identifier)){
            if(tqr.hasNext()){
                BindingSet bindingSet = tqr.next();
                String text = bindingSet.getValue("content").stringValue();
                String imageURL = bindingSet.getValue("imageUrl").stringValue();
                String sceneType = bindingSet.getValue("type").stringValue();
                String MarvelRES = bindingSet.getValue("Marvel").isIRI()? bindingSet.getValue("Marvel").toString():"";
                String req = bindingSet.getValue("req").stringValue();

                System.out.println(text+" \n"+imageURL+"\n"+sceneType+"\n"+MarvelRES+"\n"+req);
                if(SceneTypes.ACTIVE.name().equals(sceneType))
                return new Scene(RES_Identifier, text, imageURL, findOptionsPerScene(RES_Identifier), SceneTypes.ACTIVE, MarvelRES, req);
                return new Scene(RES_Identifier, text, imageURL, Collections.<Option>emptyList(), SceneTypes.PASSIVE, MarvelRES,req);

            }
        }

        return scene;
    }
    public List<Option> findOptionsPerScene(String RES_Identifier){
        List<Option> options = new ArrayList<Option>();
        try(TupleQueryResult tqr = sceneRepository.findSceneOptions(RES_Identifier)){
            while(tqr.hasNext()){
                BindingSet bindingSet = tqr.next();
                String text = bindingSet.getValue("content").stringValue();
                String val = bindingSet.getValue("value").stringValue().substring(11);
                String event = bindingSet.getValue("event").stringValue().substring(11);
                String res = bindingSet.getValue("option").toString();
                System.out.println(text+" \n"+val+"\n"+event+"\n");
                options.add(new Option(res,text , Event.valueOf(event), OptionsEnum.valueOf(val)));
            }
        }
        return options;
    }
    public Scene nextScene(String SceneRES, String Marvel, String option){
        NextSceneRef nextScenes =  nextServ.findByResIdentifier(SceneRES);

        if(nextScenes.criteria.equals("NONE"))
            try {
                return findByResIdentifier(nextScenes.posibleScenesRES.get(0));
            } catch (ResourceNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }
                String criteria;

                if(nextScenes.criteria.equals("OPTION"))
                criteria = "\""+option+"\"";
                else{
                                    if(nextScenes.criteria.equals("ELEMENT"))
                criteria = "IRI:"+nextScenes.criteria;
                else
                criteria = "ITEM";
                }

              
            return findByConditions(nextScenes, Marvel,criteria);
    }
}
