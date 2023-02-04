package uaic.fii.MarvelMonPlay.repositories.impl;

import org.eclipse.rdf4j.query.TupleQueryResult;

import uaic.fii.MarvelMonPlay.endpoints.SparqlEndpoint;
import uaic.fii.MarvelMonPlay.models.scenes.NextSceneRef;
import uaic.fii.MarvelMonPlay.utils.IRIFactory;

public class NextScenesRepository {
    private final SparqlEndpoint sparqlEndpoint;

    public NextScenesRepository(SparqlEndpoint sparqlEndpoint) {
        this.sparqlEndpoint = sparqlEndpoint;
    }

    public TupleQueryResult findRefbyScene(String SceneIdentifier) {
        return sparqlEndpoint.executeQuery(
                "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
                        "PREFIX vgo: <http://purl.org/net/VideoGameOntology#>" +
                        "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
                        "select ?scene ?criteria ?ref where {" +
                        "    IRI:" + SceneIdentifier + " IRI:hasNextScenesRef ?ref ." +
                        "    ?ref IRI:containsRefTo ?scene ." +
                        "    ?ref IRI:hasSelectionCriteria ?criteria ." +

                        "}");
    }

    public void save(NextSceneRef sceneRef) {
        StringBuilder s = new StringBuilder();
        for (String ref : sceneRef.posibleScenesRES)
            s.append("    IRI:" + sceneRef.RES_IDENTIFIER + " IRI:containsRefTo IRI:" + ref + " .");

        sparqlEndpoint.executeUpdate(
                "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
                        "PREFIX vgo: <http://purl.org/net/VideoGameOntology#>" +
                        "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
                        "insert data {" +
                        "IRI:" + sceneRef.RES_IDENTIFIER + " foaf:a " + "IRI:NextScenesRef" + " ." +
                        "IRI:" + sceneRef.RES_IDENTIFIER + " IRI:hasSelectionCriteria \"" + sceneRef.criteria + "\" ." +

                        s.toString() +
                        "}");
    }
}
