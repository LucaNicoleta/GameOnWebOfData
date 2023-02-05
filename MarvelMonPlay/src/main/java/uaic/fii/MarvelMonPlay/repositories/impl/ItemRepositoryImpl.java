package uaic.fii.MarvelMonPlay.repositories.impl;

import org.eclipse.rdf4j.query.TupleQueryResult;
import org.springframework.stereotype.Repository;
import uaic.fii.MarvelMonPlay.endpoints.SparqlEndpoint;
import uaic.fii.MarvelMonPlay.models.items.Item;
import uaic.fii.MarvelMonPlay.repositories.ItemRepository;
import uaic.fii.MarvelMonPlay.utils.IRIFactory;

@Repository
public class ItemRepositoryImpl implements ItemRepository {
    private final SparqlEndpoint sparqlEndpoint;

    public ItemRepositoryImpl(SparqlEndpoint sparqlEndpoint){
        this.sparqlEndpoint = sparqlEndpoint;
    }

    @Deprecated
    @Override
    public TupleQueryResult findAll() {
        return sparqlEndpoint.executeQuery(
            "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
            "PREFIX vgo: <http://purl.org/net/VideoGameOntology#>" +
            "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
            "select ?item ?action where {" +
            "    ?item foaf:a vgo:Item ." +
            "    ?item IRI:canPerformAction ?action ." +
            "}"
        );
    }

    @Override
    public TupleQueryResult findByResIdentifier(String itemResIdentifier) {
        return sparqlEndpoint.executeQuery(
            "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
            "PREFIX vgo: <http://purl.org/net/VideoGameOntology#>" +
            "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
            "    SELECT ?element ?action WHERE {" +
            "    IRI:" + itemResIdentifier + " foaf:a vgo:Item ." +
            "    IRI:" + itemResIdentifier + " IRI:belongsToElement ?element ." +
            "    IRI:" + itemResIdentifier + " IRI:canPerformAction ?action ." +
            "}"
        );
    }

    @Override
    public void save(Item item) {
        sparqlEndpoint.executeUpdate(
            "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
            "PREFIX vgo: <http://purl.org/net/VideoGameOntology#>" +
            "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
            "INSERT DATA {" +
            "IRI:" + item.RES_IDENTIFIER + " foaf:a " + "vgo:Item" + " ." +
            "IRI:" + item.RES_IDENTIFIER + " IRI:belongsToElement IRI:" + item.getElement().name() + ". " +
            "IRI:" + item.RES_IDENTIFIER + " IRI:canPerformAction IRI:" + item.getAction().name() + ". }"
        );
    }

    @Override
    public void update(Item item) {
        sparqlEndpoint.executeUpdate(
            "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
            "PREFIX vgo: <http://purl.org/net/VideoGameOntology#>" +
            "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
            "DELETE {" +
                "IRI:" + item.RES_IDENTIFIER + " IRI:canPerformAction ?o3. }" +
            "INSERT {" +
                "IRI:" + item.RES_IDENTIFIER + " IRI:canPerformAction " + "IRI:" + item.getAction().toString() + "" + " . }" +
            "WHERE {" +
                "IRI:" + item.RES_IDENTIFIER + " IRI:canPerformAction ?o3. }"
        );
    }

    @Override
    public void delete(Item item) {
        sparqlEndpoint.executeUpdate(
            "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
            "DELETE {IRI:" + item.RES_IDENTIFIER + " ?p ?o} " +
            "WHERE {IRI:" + item.RES_IDENTIFIER + " ?p ?o}"
        );
    }
}
