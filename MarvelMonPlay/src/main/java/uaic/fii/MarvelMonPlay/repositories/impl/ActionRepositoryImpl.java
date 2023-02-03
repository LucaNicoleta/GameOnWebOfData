package uaic.fii.MarvelMonPlay.repositories.impl;

import uaic.fii.MarvelMonPlay.endpoints.SparqlEndpoint;
import uaic.fii.MarvelMonPlay.models.Action;
import uaic.fii.MarvelMonPlay.utils.IRIFactory;

public class ActionRepositoryImpl {
    private final SparqlEndpoint sparqlEndpoint;

    public ActionRepositoryImpl(SparqlEndpoint sparqlEndpoint){
        this.sparqlEndpoint = sparqlEndpoint;
    }

    public void save(Action action) {
        sparqlEndpoint.executeUpdate(
            "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
            "PREFIX vgo: <http://purl.org/net/VideoGameOntology#>" +
            "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
            "INSERT DATA {" +
            "IRI:" + action.name() + " foaf:a " + "IRI:Action" + " . }"
        );
    }
}
