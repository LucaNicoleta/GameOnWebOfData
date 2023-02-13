package uaic.fii.MarvelMonPlay.repositories;

import org.eclipse.rdf4j.query.TupleQueryResult;

import uaic.fii.MarvelMonPlay.models.scenes.Option;

public interface OptionRepository {
    TupleQueryResult findPerScene(String SceneIdentifier);
    void save(Option option);
    TupleQueryResult getEventTriggeredByOption(String currentSceneRes, String choosenOption);
    TupleQueryResult getContentOfOption(String currentSceneRes, String choosenOption);
}
