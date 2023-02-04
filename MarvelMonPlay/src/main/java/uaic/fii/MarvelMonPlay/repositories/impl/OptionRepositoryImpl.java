package uaic.fii.MarvelMonPlay.repositories.impl;

import org.eclipse.rdf4j.query.TupleQueryResult;
import uaic.fii.MarvelMonPlay.endpoints.SparqlEndpoint;
import uaic.fii.MarvelMonPlay.utils.IRIFactory;
import uaic.fii.MarvelMonPlay.models.scenes.Option;

public class OptionRepositoryImpl {
    private final SparqlEndpoint sparqlEndpoint;

    public OptionRepositoryImpl(SparqlEndpoint sparqlEndpoint) {
        this.sparqlEndpoint = sparqlEndpoint;
    }

    public TupleQueryResult findPerScene(String SceneIdentifier) {
        return sparqlEndpoint.executeQuery(
                "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
                        "PREFIX vgo: <http://purl.org/net/VideoGameOntology#>" +
                        "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
                        "select ?option  where {" +
                        "    ?option foaf:a IRI:Option ." +
                        "    IRI:" + SceneIdentifier + " IRI:hasOption ?option ." +
                        "}");
    }

    public void save(Option option) {
        sparqlEndpoint.executeUpdate(
                "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
                        "PREFIX vgo: <http://purl.org/net/VideoGameOntology#>" +
                        "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
                        "INSERT DATA {" +
                        "IRI:" + option.RES_IDENTIFIER + " foaf:a " + "IRI:Option" + " ." +
                        "IRI:" + option.RES_IDENTIFIER + " IRI:optionValue " + "IRI:" + option.optValue.name() + ""
                        + " . " +
                        "IRI:" + option.RES_IDENTIFIER + " IRI:hasContent " + "\"" + option.content + "\"" + " . " +
                        "IRI:" + option.RES_IDENTIFIER + " IRI:triggerEvent " + "IRI:" + option.eventTriggered.name()
                        + "" + " . }");
    }

    /*
     * public void update(Option option) {
     * sparqlEndpoint.executeUpdate(
     * "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
     * "PREFIX vgo: <http://purl.org/net/VideoGameOntology#>" +
     * "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
     * "DELETE {" +
     * "IRI:" + item.RES_IDENTIFIER + " IRI:canPerformAction ?o3. }" +
     * "INSERT {" +
     * "IRI:" + item.RES_IDENTIFIER + " IRI:canPerformAction " + "IRI:" +
     * item.getAction().toString() + "" + " . }" +
     * "WHERE {" +
     * "IRI:" + item.RES_IDENTIFIER + " IRI:canPerformAction ?o3. }"
     * );
     * }
     * 
     * @Override
     * public void delete(Item item) {
     * sparqlEndpoint.executeUpdate(
     * "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
     * "DELETE {IRI:" + item.RES_IDENTIFIER + " ?p ?o} " +
     * "WHERE {IRI:" + item.RES_IDENTIFIER + " ?p ?o}"
     * );
     * }
     */
}
