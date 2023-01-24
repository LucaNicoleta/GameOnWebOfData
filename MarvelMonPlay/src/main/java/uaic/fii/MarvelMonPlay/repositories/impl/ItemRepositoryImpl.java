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
    @Override
    public TupleQueryResult findAll() {
        return sparqlEndpoint.executeQuery(
            "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
            "PREFIX vgo: <http://purl.org/net/VideoGameOntology#>" +
            "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
            "select ?item ?name ?color ?description where {" +
            "    ?item a vgo:Item ." +
            "    ?item foaf:name ?name ." +
            "    ?item IRI:hasColor ?color ." +
            "    ?item IRI:hasDescription ?description ." +
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
            "IRI:" + item.RES_IDENTIFIER + " a " + "vgo:Item" + " ." +
            "IRI:" + item.RES_IDENTIFIER + " foaf:name " + "\"" + item.getName() + "\"" + " ." +
            "IRI:" + item.RES_IDENTIFIER + " IRI:hasColor " + "\"" + item.getColor() + "\"" + " ." +
            "IRI:" + item.RES_IDENTIFIER + " IRI:hasDescription " + "\"" + item.getDescription() + "\"" + " . }"
        );
    }

    @Override
    public void update(Item item) {
        sparqlEndpoint.executeUpdate(
            "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
            "PREFIX vgo: <http://purl.org/net/VideoGameOntology#>" +
            "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
            "DELETE {" +
                "IRI:" + item.RES_IDENTIFIER + " foaf:name ?o1." +
                "IRI:" + item.RES_IDENTIFIER + " IRI:hasColor ?o2." +
                "IRI:" + item.RES_IDENTIFIER + " IRI:hasDescription ?o3. }" +
            "INSERT {" +
                "IRI:" + item.RES_IDENTIFIER + " foaf:name " + "\"" + item.getName() + "\"" + ". " +
                "IRI:" + item.RES_IDENTIFIER + " IRI:hasColor " + "\"" + item.getColor() + "\"" + ". " +
                "IRI:" + item.RES_IDENTIFIER + " IRI:hasDescription " + "\"" + item.getDescription() + "\". }" +
            "WHERE {" +
                "IRI:" + item.RES_IDENTIFIER + " foaf:name ?o1." +
                "IRI:" + item.RES_IDENTIFIER + " IRI:hasColor ?o2." +
                "IRI:" + item.RES_IDENTIFIER + " IRI:hasDescription ?o3. }"
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
