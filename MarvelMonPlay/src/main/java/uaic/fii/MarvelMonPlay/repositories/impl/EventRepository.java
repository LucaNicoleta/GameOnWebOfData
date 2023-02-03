package uaic.fii.MarvelMonPlay.repositories.impl;

import uaic.fii.MarvelMonPlay.endpoints.SparqlEndpoint;
import uaic.fii.MarvelMonPlay.models.Event;
import uaic.fii.MarvelMonPlay.utils.IRIFactory;
public class EventRepository {
    private final SparqlEndpoint sparqlEndpoint;

    public EventRepository(SparqlEndpoint sparqlEndpoint){
        this.sparqlEndpoint = sparqlEndpoint;
    }

    public void save(Event event) {
        sparqlEndpoint.executeUpdate(
            "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
            "PREFIX vgo: <http://purl.org/net/VideoGameOntology#>" +
            "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
            "INSERT DATA {" +
            "IRI:" + event.name() + " foaf:a " + "IRI:Event" + " . }"
        );
    }
}


